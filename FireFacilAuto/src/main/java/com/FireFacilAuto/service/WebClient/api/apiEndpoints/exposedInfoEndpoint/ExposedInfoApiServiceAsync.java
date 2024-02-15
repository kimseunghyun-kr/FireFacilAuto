package com.FireFacilAuto.service.WebClient.api.apiEndpoints.exposedInfoEndpoint;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseApiResponse;
import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoApiResponse;
import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.service.WebClient.api.WebClientApiService;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.baseEndpoints.BaseApiServiceAsync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class ExposedInfoApiServiceAsync {

    private static final String URI = "getBrExposInfo";

    private final WebClientApiService apiService;

    @Autowired
    public ExposedInfoApiServiceAsync(WebClientApiService apiService) {
        this.apiService = apiService;
    }

    @Cacheable(value="fetchAllExposedInfoData")
    public List<ExposedInfoResponseItem> fetchAllExposedInfoData(Address address) {
        int pageNo = 1;

        return Flux.range(1, Integer.MAX_VALUE).takeWhile(page -> page <= totalPages(address))
                .flatMap(page -> {
                    log.info("currpage {}", page);
                    return fetchPageData(address, page);
                })  // Fetch data for each page
                .collectList()
                .block();  // Block and wait for the result
    }

    private Flux<ExposedInfoResponseItem> fetchPageData(Address address, int pageNo) {
        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, URI, pageNo);
        Mono<ExposedInfoApiResponse> apiResponseMono = request.retrieve().bodyToMono(ExposedInfoApiResponse.class);

        return apiResponseMono
                .flatMapMany(apiResponse -> Flux.fromIterable(apiResponse.getResponse().getBody().getItems().getItem()))
                .doOnError(error -> log.error("Error fetching data for page {}", pageNo, error));
    }

    private Integer totalPages(Address address) {
        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, URI, 1);
        ExposedInfoApiResponse apiResponse = request.retrieve().bodyToMono(ExposedInfoApiResponse.class).block();

        assert apiResponse != null;
        int totalCount = apiResponse.getResponse().getBody().getTotalCount();
        int repeats = (int) Math.ceil((double) totalCount / WebClientApiService.SAFE_QUERY);
        log.warn("totalcount {}, repeats : {}", totalCount, repeats);
        return repeats;
    }
}
