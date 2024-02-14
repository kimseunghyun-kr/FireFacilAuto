package com.FireFacilAuto.service.WebClient.api;

import com.FireFacilAuto.domain.entity.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@Slf4j
public class WebClientApiServiceAsync {
    public static final Integer MAX_QUERY = 100;
    public static final Integer SAFE_QUERY = 80;


    @Value("${webclient.api.key}")
    private String apiKey;
    private final WebClient webClient;

    @Autowired
    public WebClientApiServiceAsync(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Map<String,Object>> fetchSingleDataAsync(Address address, String requestType) {
        log.info("sigungucode {} ", address.getSigunguCode());
        log.info("bcode {} ", address.getBcode());
        WebClient.RequestHeadersSpec<?> request = getRequestHeadersSpec(address, requestType, 1);

        return request.retrieve().bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .doOnSuccess(response -> log.info("response {}", response));
    }

    public WebClient.RequestHeadersSpec<?> getRequestHeadersSpec(Address address, String endpoint, int pageNo) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                .pathSegment(endpoint)
                .queryParam("serviceKey", apiKey)
                .queryParam("bjdongCd", address.getBcode())
//                .queryParam("platGbCd", "0")
                .queryParam("bun", address.getBun())
                .queryParam("ji", address.getJi())
//                .queryParam("startDate", "")
//                .queryParam("endDate", "")
                .queryParam("numOfRows", SAFE_QUERY)
                .queryParam("pageNo", pageNo)
                .queryParam("sigunguCd", address.getSigunguCode())
                .queryParam("_type", "json")
                .build());
    }


    public Mono<String> stringDeserializeCheckAsync(Address address, WebClient.RequestHeadersSpec<?> request) {
        log.info("Address: {}", address);
        return request.retrieve().bodyToMono(String.class)
                .doOnSuccess(response -> log.info("response {}", response));
    }


}