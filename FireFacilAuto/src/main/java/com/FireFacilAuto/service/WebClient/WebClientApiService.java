package com.FireFacilAuto.service.WebClient;

import com.FireFacilAuto.domain.DTO.api.*;
import com.FireFacilAuto.domain.entity.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WebClientApiService {
    private final Integer MAX_QUERY = 100;
    private final Integer SAFE_QUERY = 80;

    private final String FLOOR_OUTLINE = "getBrFlrOulnInfo";
    private final String BASE_INFO = "getBrBasisOulnInfo";
    private final String RECAP_TITLE = "getBrRecapTitleInfo";
    private final String TITLE = "getBrTitleInfo";
    private final String ATTACHED_ADDR = "getBrAtchJibunInfo";
    private final String EXPOSE_PUBLIC_USE_AREA = "getBrExposPubuseAreaInfo";
    private final String WATER_CATCHMENT_FILTRATION = "getBrWclfInfo";
    private final String HOUSING_PRICE_INFO = "getBrHsprcInfo";
    private final String EXPOSED_SPECIFICS = "getBrExposInfo";
    private final String ZONING = "getBrJijiguInfo";


    @Value("${webclient.api.key}")
    private String apiKey;
    private final WebClient webClient;

    @Autowired
    public WebClientApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<? extends ApiResponseItem> fetchAllData(Address address, String requestType) {
        log.info("Address: {}", address);
        int pageNo = 1;
        int totalCount;

        WebClient.RequestHeadersSpec<?> request = getRequestHeadersSpec(address, requestType, pageNo);
        String response = request.retrieve().bodyToMono(String.class).block();
        log.info("response {}", response);

        ApiResponse<? extends ApiResponseItem> apiResponse = getApiResponse(requestType, request);

        assert apiResponse != null;
        totalCount = apiResponse.getResponse().getBody().getTotalCount();
        log.info("total counts {}" ,totalCount);

        List<? extends ApiResponseItem> firstApiResponseList = apiResponse.getResponse().getBody().getItems().getItem();

        if(firstApiResponseList == null || firstApiResponseList.isEmpty()) {
            return new LinkedList<>();
        }
        List<ApiResponseItem> resultList = new LinkedList<>(firstApiResponseList);

        int totalRepeats = (int) Math.ceil((double) totalCount / SAFE_QUERY);
        log.info("totalrepeats {}" , totalRepeats);
        pageNo += 1;

        while(pageNo <= totalRepeats) {
            log.info("pageNo {}", pageNo);
            request = getRequestHeadersSpec(address, requestType, pageNo);
            apiResponse = getApiResponse(requestType, request);
            assert apiResponse != null;
            resultList.addAll(apiResponse.getResponse().getBody().getItems().getItem());
            pageNo += 1;

        }

        return resultList;
    }

    private ApiResponse<? extends ApiResponseItem> getApiResponse(String requestType, WebClient.RequestHeadersSpec<?> request) {
        ApiResponse<? extends ApiResponseItem> apiResponse = null;
        if(requestType.equals(BASE_INFO)) {
            apiResponse = request.retrieve().bodyToMono(new ParameterizedTypeReference<ApiResponse<BaseApiResponse>>() {}).block();
        }

        if(requestType.equals(FLOOR_OUTLINE)) {
            apiResponse = request.retrieve().bodyToMono(new ParameterizedTypeReference<ApiResponse<FloorResponseItem>>() {}).block();
        }
        return apiResponse;
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

    private WebClient.RequestHeadersSpec<?> getRequestHeadersSpec(Address address, String endpoint, int pageNo) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                .pathSegment(endpoint)
                .queryParam("serviceKey", apiKey)
                .queryParam("bjdongCd", address.getBcode())
//                .queryParam("platGbCd", "0")
                .queryParam("bun", address.getBun())
                .queryParam("ji", address.getJi())
//                .queryParam("startDate", "")
//                .queryParam("endDate", "")
                .queryParam("numOfRows", 1)
                .queryParam("pageNo", pageNo)
                .queryParam("sigunguCd", address.getSigunguCode())
                .queryParam("_type", "json")
                .build());
    }


}