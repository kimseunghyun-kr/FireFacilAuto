package com.FireFacilAuto.service.WebClient;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseApiResponse;
import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.DTO.api.floorapi.FloorApiResponse;
import com.FireFacilAuto.domain.DTO.api.floorapi.FloorResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WebClientApiService {
    public static final Integer MAX_QUERY = 100;
    public static final Integer SAFE_QUERY = 80;


    @Value("${webclient.api.key}")
    private String apiKey;
    private final WebClient webClient;

    @Autowired
    public WebClientApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Map<String, Object> fetchSingleData(Address address, String requestType) {
        log.info("sigungucode {} ", address.getSigunguCode());
        log.info("bcode {} ", address.getBcode());
        WebClient.RequestHeadersSpec<?> request = getRequestHeadersSpec(address, requestType, 1);

        Map<String,Object> response = request.retrieve().bodyToMono(Map.class).block();
        log.info("response {}", response);
//        ApiResponse apiResponse = request.retrieve().bodyToMono(ApiResponse.class).block();
        return response;
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
                .queryParam("numOfRows", 2)
                .queryParam("pageNo", pageNo)
                .queryParam("sigunguCd", address.getSigunguCode())
                .queryParam("_type", "json")
                .build());
    }

    public void StringDeserializeCheck(Address address, WebClient.RequestHeadersSpec<?> request) {
        log.info("Address: {}", address);
        String response = request.retrieve().bodyToMono(String.class).block();
        log.info("response {}", response);
    }


}