package com.FireFacilAuto.service.WebClient.apiEndpoints.baseEndpoints;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseApiResponse;
import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.service.WebClient.WebClientApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class BaseApiService {

    private final WebClientApiService apiService;

    @Autowired
    public BaseApiService(WebClientApiService apiService) {
        this.apiService = apiService;
    }

    public List<BaseResponseItem> fetchAllBaseData(Address address, String requestType) {
        int pageNo = 1;
        int totalCount;

        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, requestType, pageNo);
        apiService.StringDeserializeCheck(address, request);

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

        int totalRepeats = (int) Math.ceil((double) totalCount / WebClientApiService.SAFE_QUERY);
        log.info("totalrepeats {}" , totalRepeats);
        pageNo += 1;

        while(pageNo <= totalRepeats) {
            log.info("pageNo {}", pageNo);
            request = apiService.getRequestHeadersSpec(address, requestType, pageNo);
            apiResponse = request.retrieve().bodyToMono(BaseApiResponse.class).block();
            assert apiResponse != null;
            resultList.addAll(apiResponse.getResponse().getBody().getItems().getItem());
            pageNo += 1;
        }
        return resultList;

    }



}
