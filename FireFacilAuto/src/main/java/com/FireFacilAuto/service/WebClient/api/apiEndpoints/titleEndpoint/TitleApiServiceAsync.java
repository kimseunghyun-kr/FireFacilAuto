package com.FireFacilAuto.service.WebClient.api.apiEndpoints.titleEndpoint;

import com.FireFacilAuto.domain.DTO.api.titleresponseapi.TitleApiResponse;
import com.FireFacilAuto.domain.DTO.api.titleresponseapi.TitleResponseItem;
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
public class TitleApiServiceAsync {

    private static final String URI = "getBrTitleInfo";

    private final WebClientApiService apiService;
    private final CacheManager cacheManager;
    private final ConcurrentHashMap<Address, ReentrantLock> addressLocks = new ConcurrentHashMap<>();

    @Autowired
    public TitleApiServiceAsync(WebClientApiService apiService, CacheManager cacheManager) {
        this.apiService = apiService;
        this.cacheManager = cacheManager;
    }

    @Cacheable(value="fetchAllTitleData")
    public List<TitleResponseItem> fetchAllTitleData(Address address) {
        // Check if the data is already in the cache
        Cache.ValueWrapper valueWrapper = Objects.requireNonNull(cacheManager.getCache("fetchAllTitleData")).get(address);

        // Use the address as a key for the lock
        ReentrantLock fetchLock = addressLocks.computeIfAbsent(address, k -> new ReentrantLock());

        // Acquire the lock
        fetchLock.lock();
        try {
            // Double-check if the value is inside the cache
            if (valueWrapper != null) {
                // Data is updated in cache
                return (List<TitleResponseItem>) valueWrapper.get();
            }

            // Fetch data from the API
            return fetchDataFromApi(address);
        } finally {
            // Release the lock
            fetchLock.unlock();
            addressLocks.remove(address, fetchLock);
        }
    }

    public List<TitleResponseItem> fetchDataFromApi(Address address) {
        int pageNo = 1;

        return Flux.range(pageNo, Integer.MAX_VALUE).takeWhile(page -> page <= totalPages(address))
                .flatMap(page -> {
                    log.info("currpage {}", page);
                    return fetchPageData(address, page);
                }, CONCURRENCYLIMIT)  // Fetch data for each page
                .collectList()
                .block();  // Block and wait for the result
    }

    private Flux<TitleResponseItem> fetchPageData(Address address, int pageNo) {
        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, URI, pageNo);
        Mono<TitleApiResponse> apiResponseMono = request.retrieve().bodyToMono(TitleApiResponse.class);

        return apiResponseMono
                .flatMapMany(apiResponse -> Flux.fromIterable(apiResponse.getResponse().getBody().getItems().getItem()))
                .doOnError(error -> log.error("Error fetching FLOOR data for page {}", pageNo, error))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(5))
                        .filter(throwable -> throwable instanceof WebClientException)
                );
    }

    private Integer totalPages(Address address) {
        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, URI, 1);
        TitleApiResponse apiResponse = request.retrieve().bodyToMono(TitleApiResponse.class).block();

        assert apiResponse != null;
        int totalCount = apiResponse.getResponse().getBody().getTotalCount();
        int repeats = (int) Math.ceil((double) totalCount / WebClientApiService.SAFE_QUERY);
        log.warn("totalcount {}, repeats : {}", totalCount, repeats);
        return repeats;
    }
}
