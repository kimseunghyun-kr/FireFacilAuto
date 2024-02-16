package com.FireFacilAuto.service.WebClient.api.apiEndpoints.exposedInfoEndpoint;

import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoApiResponse;
import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoResponseItem;
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
import org.springframework.web.reactive.function.client.WebClientResponseException;
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

    @Cacheable(value = "fetchAllExposedInfoData", key="#p0")
    public List<ExposedInfoResponseItem> fetchAllExposedInfoData(Address address) {
        log.info("address for exposInfo at {} 2 2 2 2 2 2 2", address);
        // Check if the data is already in the cache
        Cache.ValueWrapper valueWrapper = Objects.requireNonNull(cacheManager.getCache("fetchAllExposedInfoData")).get(address);
        log.info("cache has been alerted about information wanted in fetchAllExposedData {}", valueWrapper);
        // Use the address as a key for the lock
        ReentrantLock fetchLock = addressLocks.computeIfAbsent(address, k -> new ReentrantLock());

        log.warn("lock met, lockName = {}", addressLocks.get(address));
        // Acquire the lock
        fetchLock.lock();
        log.warn("lock acquired");
        try {
            // Double-check if the value is inside the cache
            if (valueWrapper != null) {
                // Data is updated in cache
                return (List<ExposedInfoResponseItem>) valueWrapper.get();
            }

            // Fetch data from the API
            return fetchDataFromApi(address);
        } finally {
            addressLocks.remove(address, fetchLock);
            // Release the lock
            fetchLock.unlock();

        }
    }

    private List<ExposedInfoResponseItem> fetchDataFromApi(Address address) {
        int total = totalPages(address);
        log.warn("totalPages : {}", total);
        return Flux.range(1, total)
                .flatMap(page -> fetchPageData(address, page), CONCURRENCYLIMIT)
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
                    log.error("Error fetching EXPOSINFODATA data for page {}", pageNo, error);
                    if (error instanceof WebClientResponseException) {
                        String responseBody = ((WebClientResponseException) error).getResponseBodyAsString();
                        String errorCode = ((WebClientResponseException) error).getStatusCode().toString();
                        log.error("Response body: {} . errorcode : {}", responseBody, errorCode);
                    }
                }).retryWhen(Retry.backoff(3, Duration.ofSeconds(5))
                        .filter(throwable -> throwable instanceof WebClientException)
                );
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
