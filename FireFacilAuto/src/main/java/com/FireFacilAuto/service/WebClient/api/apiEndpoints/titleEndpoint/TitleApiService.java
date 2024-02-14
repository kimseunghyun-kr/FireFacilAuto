package com.FireFacilAuto.service.WebClient.api.apiEndpoints.titleEndpoint;

import com.FireFacilAuto.domain.DTO.api.titleresponseapi.TitleApiResponse;
import com.FireFacilAuto.domain.DTO.api.titleresponseapi.TitleResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.service.WebClient.api.WebClientApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class TitleApiService {

    private static final String URI = "getBrTitleInfo";
    private final WebClientApiService apiService;

    @Autowired
    public TitleApiService(WebClientApiService apiService) {
        this.apiService = apiService;
    }

    @Cacheable("fetchAllTitleData")
    public List<TitleResponseItem> fetchAllTitleData(Address address) {

        int pageNo = 1;
        int totalCount;

        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, URI, pageNo);
        apiService.StringDeserializeCheck(address, request);

        TitleApiResponse apiResponse = request.retrieve().bodyToMono(TitleApiResponse.class).block();
        assert apiResponse != null;
        totalCount = apiResponse.getResponse().getBody().getTotalCount();
        log.info("total counts {}" ,totalCount);

        List<TitleResponseItem> firstApiResponseList = apiResponse.getResponse().getBody().getItems().getItem();

        if(firstApiResponseList.isEmpty()) {
            return new LinkedList<>();
        }

        log.info("firstApiResponse, {} " , firstApiResponseList);

        List<TitleResponseItem> resultList = new LinkedList<>(firstApiResponseList);

        int totalRepeats = (int) Math.ceil((double) totalCount / WebClientApiService.SAFE_QUERY);
        log.info("totalrepeats {}" , totalRepeats);
        pageNo += 1;

        while(pageNo <= totalRepeats) {
            log.info("pageNo {}", pageNo);
            request = apiService.getRequestHeadersSpec(address, URI, pageNo);
            apiResponse = request.retrieve().bodyToMono(TitleApiResponse.class).block();
            assert apiResponse != null;
            resultList.addAll(apiResponse.getResponse().getBody().getItems().getItem());
            pageNo += 1;
        }
        return resultList;

    }
}
