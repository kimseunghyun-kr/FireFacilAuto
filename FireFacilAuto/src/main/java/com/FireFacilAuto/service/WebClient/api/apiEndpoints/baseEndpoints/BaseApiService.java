package com.FireFacilAuto.service.WebClient.api.apiEndpoints.baseEndpoints;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseApiResponse;
import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.service.WebClient.api.WebClientApiService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class BaseApiService {

    private final WebClientApiService apiService;

    private final BaseApiService self;

    @Autowired
    public BaseApiService(WebClientApiService apiService, @Lazy BaseApiService self) {
        this.apiService = apiService;
        this.self = self;
    }

    @NotNull
    private List<BaseResponseItem> getBaseResponseItems(Address address, String requestType) {
        return this.self.fetchAllBaseData(address, requestType);
    }

    public List<BaseResponseItem> fetchAllTitleBaseData(Address address, String requestType) {
        List<BaseResponseItem> item = getBaseResponseItems(address, requestType);
        return item.stream().filter(data -> Integer.parseInt(data.getRegstrKindCd()) == 3).toList();
    }

    public List<BaseResponseItem> fetchAllExposInfoBaseData(Address address, String requestType) {
        List<BaseResponseItem> item = getBaseResponseItems(address, requestType);
        return item.stream().filter(data->Integer.parseInt(data.getRegstrKindCd()) == 4).toList();
    }

    @Cacheable(value = "fetchAllBaseData")
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

    public List<BaseResponseItem> fetchOnePageData(Address address, String requestType, Integer page) {

        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, requestType, page);
        BaseApiResponse apiResponse = request.retrieve().bodyToMono(BaseApiResponse.class).block();
        if(apiResponse == null) {
            return new LinkedList<>();
        }

        List<BaseResponseItem> firstApiResponseList = apiResponse.getResponse().getBody().getItems().getItem();
        log.info("firstApiResponse, {} " , firstApiResponseList);

        if(firstApiResponseList.isEmpty()) {
            return new LinkedList<>();
        }
        return firstApiResponseList;
    }



}
