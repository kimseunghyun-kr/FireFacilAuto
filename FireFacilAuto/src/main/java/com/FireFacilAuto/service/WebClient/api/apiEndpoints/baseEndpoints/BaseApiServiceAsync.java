package com.FireFacilAuto.service.WebClient.api.apiEndpoints.baseEndpoints;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseApiResponse;
import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.service.WebClient.api.WebClientApiService;
import com.FireFacilAuto.service.WebClient.api.WebClientApiServiceAsync;
import jdk.jfr.Experimental;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
@Experimental
public class BaseApiServiceAsync {
    private final WebClientApiServiceAsync apiService;

    @Autowired
    public BaseApiServiceAsync(WebClientApiServiceAsync apiService) {
        this.apiService = apiService;
    }

    public Flux<BaseResponseItem> fetchDataReactive(Address address, String requestType) {
        return fetchDataPage(address,requestType);
    }

    private Flux<BaseResponseItem> fetchDataPage(Address address, String requestType) {
        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, requestType, 1);
        return request.retrieve()
                .bodyToMono(BaseApiResponse.class)
                .flatMapMany(apiResponse -> {
                    assert apiResponse != null;
                    int totalCount = apiResponse.getResponse().getBody().getTotalCount();
                    log.info("total counts {}", totalCount);

                    List<BaseResponseItem> firstApiResponseList = apiResponse.getResponse().getBody().getItems().getItem();
                    log.info("firstApiResponse, {} ", firstApiResponseList);

                    if (firstApiResponseList.isEmpty()) {
                        return Flux.empty(); // Return an empty Flux if the list is empty
                    }

                    List<BaseResponseItem> resultList = new LinkedList<>(firstApiResponseList);

                    int totalRepeats = (int) Math.ceil((double) totalCount / WebClientApiService.SAFE_QUERY);
                    log.info("totalrepeats {}", totalRepeats);

                    return fetchRemainingPages(totalRepeats, resultList, address, requestType);
                });
    }

    private Flux<BaseResponseItem> fetchRemainingPages(int totalRepeats, List<BaseResponseItem> resultList, Address address, String requestType) {
        return Flux.range(2, totalRepeats)
                .flatMap(pageNo -> {
                    log.info("pageNo {}", pageNo);
                    WebClient.RequestHeadersSpec<?> nextPageRequest = apiService.getRequestHeadersSpec(address, requestType, pageNo);
                    return nextPageRequest.retrieve()
                            .bodyToMono(BaseApiResponse.class)
                            .map(apiResponse -> {
                                assert apiResponse != null;
                                resultList.addAll(apiResponse.getResponse().getBody().getItems().getItem());
                                return resultList;
                            })
                            .flatMapMany(Flux::fromIterable); // Flatten the Mono<List<BaseResponseItem>> to Flux<BaseResponseItem>
                });
    }


}
