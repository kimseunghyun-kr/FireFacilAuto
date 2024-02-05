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
    private final Integer MAX_QUERY = 100;
    private final Integer SAFE_QUERY = 80;


    @Value("${webclient.api.key}")
    private String apiKey;
    private final WebClient webClient;

    @Autowired
    public WebClientApiService(WebClient webClient) {
        this.webClient = webClient;
    }


    public List<BaseResponseItem> fetchAllBaseData(Address address, String requestType) {
        log.info("Address: {}", address);
        int pageNo = 1;
        int totalCount;
        WebClient.RequestHeadersSpec<?> request = getRequestHeadersSpec(address, requestType, pageNo);
        String response = request.retrieve().bodyToMono(String.class).block();
        log.info("response {}", response);
        BaseApiResponse apiResponse = request.retrieve().bodyToMono(BaseApiResponse.class).block();
        assert apiResponse != null;
        totalCount = apiResponse.getResponse().getBody().getTotalCount();
        log.info("total counts {}" ,totalCount);

        List<BaseResponseItem> firstApiResponseList = apiResponse.getResponse().getBody().getItems().getItem();
        log.info("firstApiResponse, {} " , firstApiResponseList);

        if(firstApiResponseList.isEmpty()) {
            return new LinkedList<>();
        }

        List<BaseResponseItem> resultList = new LinkedList<>(firstApiResponseList);

        int totalRepeats = (int) Math.ceil((double) totalCount / SAFE_QUERY);
        log.info("totalrepeats {}" , totalRepeats);
        pageNo += 1;

        while(pageNo <= totalRepeats) {
            log.info("pageNo {}", pageNo);
            request = getRequestHeadersSpec(address, requestType, pageNo);
            apiResponse = request.retrieve().bodyToMono(BaseApiResponse.class).block();
            assert apiResponse != null;
            resultList.addAll(apiResponse.getResponse().getBody().getItems().getItem());
            pageNo += 1;
        }
        return resultList;

    }

    public List<FloorResponseItem> fetchAllFloorData(Address address, String requestType) {
        log.info("Address: {}", address);
        int pageNo = 1;
        int totalCount;

        WebClient.RequestHeadersSpec<?> request = getRequestHeadersSpec(address, requestType, pageNo);
        String response = request.retrieve().bodyToMono(String.class).block();
        log.info("response {}", response);

        FloorApiResponse apiResponse = request.retrieve().bodyToMono(FloorApiResponse.class).block();
        assert apiResponse != null;
        totalCount = apiResponse.getResponse().getBody().getTotalCount();
        log.info("total counts {}", totalCount);

        List<FloorResponseItem> firstApiResponseList = apiResponse.getResponse().getBody().getItems().getItem();
        log.info("firstApiResponse, {} ", firstApiResponseList.getFirst());

        if (firstApiResponseList.isEmpty()) {
            return new LinkedList<>();
        }

        List<FloorResponseItem> resultList = new LinkedList<>(firstApiResponseList);

        int totalRepeats = (int) Math.ceil((double) totalCount / SAFE_QUERY);
        log.info("totalrepeats {}", totalRepeats);
        pageNo += 1;

        while (pageNo <= totalRepeats) {
            log.info("pageNo {}", pageNo);
            request = getRequestHeadersSpec(address, requestType, pageNo);
            apiResponse = request.retrieve().bodyToMono(FloorApiResponse.class).block();
            assert apiResponse != null;
            resultList.addAll(apiResponse.getResponse().getBody().getItems().getItem());
            pageNo += 1;
        }
        return resultList;
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
                .queryParam("numOfRows", 2)
                .queryParam("pageNo", pageNo)
                .queryParam("sigunguCd", address.getSigunguCode())
                .queryParam("_type", "json")
                .build());
    }


}