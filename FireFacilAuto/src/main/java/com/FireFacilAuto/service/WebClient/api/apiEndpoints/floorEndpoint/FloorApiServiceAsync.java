package com.FireFacilAuto.service.WebClient.api.apiEndpoints.floorEndpoint;


import com.FireFacilAuto.domain.DTO.api.floorapi.FloorApiResponse;
import com.FireFacilAuto.domain.DTO.api.floorapi.FloorResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.service.WebClient.api.WebClientApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class FloorApiServiceAsync {

    private static final String URI = "getBrFlrOulnInfo";

    private final WebClientApiService apiService;

    @Autowired
    public FloorApiServiceAsync(WebClientApiService apiService) {
        this.apiService = apiService;
    }

    @Cacheable(value="fetchAllFloorData")
    public List<FloorResponseItem> fetchAllFloorData(Address address) {
        int pageNo = 1;

        return Flux.range(pageNo, Integer.MAX_VALUE).takeWhile(page -> page <= totalPages(address))
                .flatMap(page -> {
                    log.info("currpage {}", page);
                    return fetchPageData(address, page);
                })  // Fetch data for each page
                .collectList()
                .block();  // Block and wait for the result
    }

    private Flux<FloorResponseItem> fetchPageData(Address address, int pageNo) {
        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, URI, pageNo);
        Mono<FloorApiResponse> apiResponseMono = request.retrieve().bodyToMono(FloorApiResponse.class);

        return apiResponseMono
                .flatMapMany(apiResponse -> Flux.fromIterable(apiResponse.getResponse().getBody().getItems().getItem()))
                .doOnError(error -> log.error("Error fetching data for page {}", pageNo, error));
    }

    private Integer totalPages(Address address) {
        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, URI, 1);
        FloorApiResponse apiResponse = request.retrieve().bodyToMono(FloorApiResponse.class).block();

        assert apiResponse != null;
        int totalCount = apiResponse.getResponse().getBody().getTotalCount();
        int repeats = (int) Math.ceil((double) totalCount / WebClientApiService.SAFE_QUERY);
        log.warn("totalcount {}, repeats : {}", totalCount, repeats);
        return repeats;
    }
}
