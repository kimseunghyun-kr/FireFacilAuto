package com.FireFacilAuto.service.WebClient.api.apiEndpoints.baseEndpoints;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseApiResponse;
import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.service.WebClient.api.WebClientApiService;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Experimental;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
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
@Experimental
public class BaseApiServiceAsync {
    private final WebClientApiService apiService;
    private static final String URI = "getBrBasisOulnInfo";
    private final BaseApiServiceAsync self;
    private final CacheManager cacheManager;
    private final ConcurrentHashMap<Address, ReentrantLock> addressLocks = new ConcurrentHashMap<>();

    @Autowired
    public BaseApiServiceAsync(WebClientApiService apiService, @Lazy BaseApiServiceAsync self, CacheManager cacheManager) {
        this.apiService = apiService;
        this.self = self;
        this.cacheManager = cacheManager;
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
        // Check if the data is already in the cache
        Cache.ValueWrapper valueWrapper = Objects.requireNonNull(cacheManager.getCache("fetchAllBaseData")).get(address);

        // Use the address as a key for the lock
        ReentrantLock fetchLock = addressLocks.computeIfAbsent(address, k -> new ReentrantLock());

        // Acquire the lock
        fetchLock.lock();
        try {
            // Double-check if the value is inside the cache
            if (valueWrapper != null) {
                // Data is updated in cache
                return (List<BaseResponseItem>) valueWrapper.get();
            }

            // Fetch data from the API
            return fetchDataFromApi(address);
        } finally {
            // Release the lock
            fetchLock.unlock();
            addressLocks.remove(address, fetchLock);
        }
    }


    public List<BaseResponseItem> fetchDataFromApi(Address address) {
        int total = totalPages(address);
        log.warn("totalPages : {}", total);
        return Flux.range(1, total)
                .flatMap(page -> {
                    log.info("currpage {}", page);
                    return fetchPageData(address, page);
                }, CONCURRENCYLIMIT)  // Fetch data for each page
                .collectList()
                .block();  // Block and wait for the result
   }

    private Flux<BaseResponseItem> fetchPageData(Address address, int pageNo) {
        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, BaseApiServiceAsync.URI, pageNo);
        Mono<BaseApiResponse> apiResponseMono = request.retrieve().bodyToMono(BaseApiResponse.class);

        return apiResponseMono
//                .doOnNext(apiResponse -> log.info("Received API response: {}", apiResponse))
                .flatMapMany(apiResponse -> Flux.fromIterable(apiResponse.getResponse().getBody().getItems().getItem()))
                .doOnError(error -> {
                    log.error("Error fetching BASE data for page {}", pageNo, error);
                    if (error instanceof WebClientResponseException) {
                        String responseBody = ((WebClientResponseException) error).getResponseBodyAsString();
                        String errorCode = ((WebClientResponseException) error).getStatusCode().toString();
                        log.error("Response body: {} . errorcode: {}", responseBody, errorCode);
                    }
                }).retryWhen(Retry.backoff(3, Duration.ofSeconds(5))
                        .filter(throwable -> throwable instanceof WebClientException)
                );

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
