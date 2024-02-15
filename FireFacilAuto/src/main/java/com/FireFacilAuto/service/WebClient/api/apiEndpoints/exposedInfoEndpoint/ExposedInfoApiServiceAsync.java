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
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class ExposedInfoApiServiceAsync {

    private static final String URI = "getBrExposInfo";
    private final CacheManager cacheManager;
    private final WebClientApiService apiService;
    private final ConcurrentHashMap<Address, ReentrantLock> addressLocks = new ConcurrentHashMap<>();


    @Autowired
    public ExposedInfoApiServiceAsync(CacheManager cacheManager, WebClientApiService apiService) {
        this.cacheManager = cacheManager;
        this.apiService = apiService;
    }

    @Cacheable(value = "fetchAllExposedInfoData")
    public List<ExposedInfoResponseItem> fetchAllExposedInfoData(Address address) {
        // Check if the data is already in the cache
        Cache.ValueWrapper valueWrapper = Objects.requireNonNull(cacheManager.getCache("fetchAllExposedInfoData")).get(address);

        // Use the address as a key for the lock
        ReentrantLock fetchLock = addressLocks.computeIfAbsent(address, k -> new ReentrantLock());

        // Acquire the lock
        fetchLock.lock();
        try {
            // Double-check if the value is inside the cache
            if (valueWrapper != null) {
                // Data is updated in cache
                return (List<ExposedInfoResponseItem>) valueWrapper.get();
            }

            // Fetch data from the API
            return fetchDataFromApi(address);
        } finally {
            // Release the lock
            fetchLock.unlock();
            addressLocks.remove(address, fetchLock);
        }
    }

    private List<ExposedInfoResponseItem> fetchDataFromApi(Address address) {
        int total = totalPages(address);
        log.warn("totalPages : {}", total);
        return Flux.range(1, total)
                .flatMap(page -> fetchPageData(address, page))
                .collectList()
                .block();  // Block and wait for the result
    }

    private Flux<ExposedInfoResponseItem> fetchPageData(Address address, int pageNo) {
        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, URI, pageNo);
        Mono<ExposedInfoApiResponse> apiResponseMono = request.retrieve().bodyToMono(ExposedInfoApiResponse.class);

        return apiResponseMono
//                .doOnNext(apiResponse -> log.info("Received API response: {}", apiResponse))
                .flatMapMany(apiResponse -> Flux.fromIterable(apiResponse.getResponse().getBody().getItems().getItem()))
                .doOnError(error -> {
                    log.error("Error fetching data for page {}", pageNo, error);
                    if (error instanceof WebClientResponseException) {
                        String responseBody = ((WebClientResponseException) error).getResponseBodyAsString();
                        String errorCode = ((WebClientResponseException) error).getStatusCode().toString();
                        log.error("Response body: {} . errorcode : {}", responseBody, errorCode);
                    }
                });
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
