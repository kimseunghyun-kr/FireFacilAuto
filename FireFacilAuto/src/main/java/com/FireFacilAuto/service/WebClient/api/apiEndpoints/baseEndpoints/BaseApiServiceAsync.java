package com.FireFacilAuto.service.WebClient.api.apiEndpoints.baseEndpoints;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseApiResponse;
import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.service.WebClient.api.WebClientApiService;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Experimental;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
@Experimental
public class BaseApiServiceAsync {
    private final WebClientApiService apiService;
    private static final String URI = "getBrBasisOulnInfo";
    private final BaseApiServiceAsync self;

    @Autowired
    public BaseApiServiceAsync(WebClientApiService apiService, @Lazy BaseApiServiceAsync self) {
        this.apiService = apiService;
        this.self = self;
    }

    @NotNull
    private List<BaseResponseItem> getBaseResponseItems(Address address) {
        return this.self.fetchAllBaseData(address);
    }

    @Deprecated
    public List<BaseResponseItem> fetchAllRecapTitleInfoBaseData(List<BaseResponseItem> item) {
        return item.stream().filter(data->Integer.parseInt(data.getRegstrKindCd()) == 1).toList();
    }

    public List<BaseResponseItem> fetchAllTitleBaseData(List<BaseResponseItem> item) {
        return item.stream().filter(data -> Integer.parseInt(data.getRegstrKindCd()) == 3 || Integer.parseInt(data.getRegstrKindCd()) == 2).toList();
    }

    public List<BaseResponseItem> fetchAllExposInfoBaseData(List<BaseResponseItem> item) {
        return item.stream().filter(data->Integer.parseInt(data.getRegstrKindCd()) == 4).toList();
    }

   @Cacheable(value= "fetchAllBaseData")
    public List<BaseResponseItem> fetchAllBaseData(Address address) {
        int pageNo = 1;
        
        return Flux.range(1, Integer.MAX_VALUE).takeWhile(page -> page <= totalPages(address))
                .flatMap(page -> {
                    log.info("currpage {}", page);
                    return fetchPageData(address, page);
                })  // Fetch data for each page
                .collectList()
                .block();  // Block and wait for the result
   }

    private Flux<BaseResponseItem> fetchPageData(Address address, int pageNo) {
        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, BaseApiServiceAsync.URI, pageNo);
        Mono<BaseApiResponse> apiResponseMono = request.retrieve().bodyToMono(BaseApiResponse.class);

        return apiResponseMono
                .flatMapMany(apiResponse -> Flux.fromIterable(apiResponse.getResponse().getBody().getItems().getItem()))
                .doOnError(error -> log.error("Error fetching data for page {}", pageNo, error));
    }

    private Integer totalPages(Address address) {
        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, URI, 1);
        BaseApiResponse apiResponse = request.retrieve().bodyToMono(BaseApiResponse.class).block();

        assert apiResponse != null;
        int totalCount = apiResponse.getResponse().getBody().getTotalCount();
        int repeats = (int) Math.ceil((double) totalCount / WebClientApiService.SAFE_QUERY);
        log.warn("totalcount {}, repeats : {}", totalCount, repeats);
        return repeats;
    }


}
