package com.FireFacilAuto.service.WebClient.api.apiEndpoints.floorEndpoint;


import com.FireFacilAuto.domain.DTO.api.floorapi.FloorApiResponse;
import com.FireFacilAuto.domain.DTO.api.floorapi.FloorResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.service.WebClient.api.WebClientApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import static com.FireFacilAuto.service.WebClient.api.WebClientApiService.CONCURRENCYLIMIT;

@Service
@Slf4j
public class FloorApiServiceAsync {

    private static final String URI = "getBrFlrOulnInfo";

    private final WebClientApiService apiService;
    private final CacheManager cacheManager;
    private final ConcurrentHashMap<Address, ReentrantLock> addressLocks = new ConcurrentHashMap<>();

    @Autowired
    public FloorApiServiceAsync(WebClientApiService apiService, CacheManager cacheManager) {
        this.apiService = apiService;
        this.cacheManager = cacheManager;
    }

    @Cacheable(value="fetchAllFloorData")
    public List<FloorResponseItem> fetchAllFloorData(Address address) {
        // Check if the data is already in the cache
        Cache.ValueWrapper valueWrapper = Objects.requireNonNull(cacheManager.getCache("fetchAllFloorData")).get(address);

        // Use the address as a key for the lock
        ReentrantLock fetchLock = addressLocks.computeIfAbsent(address, k -> new ReentrantLock());

        // Acquire the lock
        fetchLock.lock();
        try {
            // Double-check if the value is inside the cache
            if (valueWrapper != null) {
                // Data is updated in cache
                return (List<FloorResponseItem>) valueWrapper.get();
            }

            // Fetch data from the API
            return fetchDataFromApi(address);
        } finally {
            // Release the lock
            fetchLock.unlock();
            addressLocks.remove(address, fetchLock);
        }
    }

    public List<FloorResponseItem> fetchDataFromApi(Address address) {
        int pageNo = 1;

        return Flux.range(pageNo, Integer.MAX_VALUE).takeWhile(page -> page <= totalPages(address))
                .flatMap(page -> {
                    log.info("currpage {}", page);
                    return fetchPageData(address, page);
                }, CONCURRENCYLIMIT)  // Fetch data for each page
                .collectList()
                .block();  // Block and wait for the result
    }

    private Flux<FloorResponseItem> fetchPageData(Address address, int pageNo) {
        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, URI, pageNo);
        Mono<FloorApiResponse> apiResponseMono = request.retrieve().bodyToMono(FloorApiResponse.class);

        return apiResponseMono
                .flatMapMany(apiResponse -> Flux.fromIterable(apiResponse.getResponse().getBody().getItems().getItem()))
                .doOnError(error -> log.error("Error fetching FLOOR data for page {}", pageNo, error))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5))
                        .filter(throwable -> throwable instanceof WebClientException)
                );
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
