package com.FireFacilAuto.service.WebClient;

import com.FireFacilAuto.domain.DTO.api.*;
import com.FireFacilAuto.domain.entity.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class WebClientApiService {
    private final Integer MAX_QUERY = 100;
    private final Integer SAFE_QUERY = 80;

    @Value("${webclient.api.key}")
    private String apiKey;
    private final WebClient webClient;

    @Autowired
    public WebClientApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<ApiResponseItem> fetchAllData(Address address) {
        log.info("Address: {}", address);
        int pageNo = 1;
        int totalCount;

        WebClient.RequestHeadersSpec<?> request = getRequestHeadersSpec(address, pageNo);
        String response = request.retrieve().bodyToMono(String.class).block();
        log.info("response {}", response);
        ApiResponse apiResponse = request.retrieve().bodyToMono(ApiResponse.class).block();

        assert apiResponse != null;
        totalCount = apiResponse.getResponse().getBody().getTotalCount();
        log.info("total counts {}" ,totalCount);

        List<ApiResponseItem> firstApiResponseList = apiResponse.getResponse().getBody().getItems().getItem();
        if(firstApiResponseList == null || firstApiResponseList.isEmpty()) {
            return new LinkedList<>();
        }
        List<ApiResponseItem> resultList = new LinkedList<>(firstApiResponseList);

        int totalRepeats = (int) Math.ceil((double) totalCount / SAFE_QUERY);
        log.info("totalrepeats {}" , totalRepeats);
        pageNo += 1;

        while(pageNo <= totalRepeats) {
            log.info("pageNo {}", pageNo);
            request = getRequestHeadersSpec(address, pageNo);
            apiResponse = request.retrieve().bodyToMono(ApiResponse.class).block();
            assert apiResponse != null;
            resultList.addAll(apiResponse.getResponse().getBody().getItems().getItem());
            pageNo += 1;

        }

        return resultList;
    }

    private WebClient.RequestHeadersSpec<?> getRequestHeadersSpec(Address address, int pageNo) {
        return webClient.get().uri(uriBuilder -> uriBuilder
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

    public List<ApiResponseItem> fetchData(Address address) {
        log.info("sigungucode {} ", address.getSigunguCode());
        log.info("bcode {} ", address.getBcode());
        WebClient.RequestHeadersSpec<?> request = webClient.get().uri(uriBuilder -> uriBuilder
                .queryParam("serviceKey", apiKey)
                .queryParam("bjdongCd", address.getBcode())
                .queryParam("sigunguCd", address.getSigunguCode())
                .queryParam("_type", "json")
                .build());

//        String response = request.retrieve().bodyToMono(String.class).block();

        ApiResponse apiResponse = request.retrieve().bodyToMono(ApiResponse.class).block();

        assert apiResponse != null;
        ResponseBody responseBody = apiResponse.getResponse().getBody();

        return responseBody.getItems().getItem();
    }
}
