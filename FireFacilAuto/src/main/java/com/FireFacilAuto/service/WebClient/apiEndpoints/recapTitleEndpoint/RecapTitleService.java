package com.FireFacilAuto.service.WebClient.apiEndpoints.recapTitleEndpoint;

import com.FireFacilAuto.domain.DTO.api.recaptitleapi.RecapTitleApiResponse;
import com.FireFacilAuto.domain.DTO.api.recaptitleapi.RecapTitleResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.service.WebClient.WebClientApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class RecapTitleService {
    private final WebClientApiService apiService;

    @Autowired
    public RecapTitleService(WebClientApiService apiService) {
        this.apiService = apiService;
    }


    public List<RecapTitleResponseItem> fetchAllRecapTitleData(Address address, String requestType) {
        log.info("Address: {}", address);
        int pageNo = 1;
        int totalCount;

        WebClient.RequestHeadersSpec<?> request = apiService.getRequestHeadersSpec(address, requestType, pageNo);
        String response = request.retrieve().bodyToMono(String.class).block();
        log.info("response {}", response);

        RecapTitleApiResponse apiResponse = request.retrieve().bodyToMono(RecapTitleApiResponse.class).block();
        assert apiResponse != null;
        totalCount = apiResponse.getResponse().getBody().getTotalCount();
        log.info("total counts {}", totalCount);

        List<RecapTitleResponseItem> firstApiResponseList = apiResponse.getResponse().getBody().getItems().getItem();
        log.info("firstApiResponse, {} ", firstApiResponseList.getFirst());

        if (firstApiResponseList.isEmpty()) {
            return new LinkedList<>();
        }

        List<RecapTitleResponseItem> resultList = new LinkedList<>(firstApiResponseList);

        int totalRepeats = (int) Math.ceil((double) totalCount / WebClientApiService.SAFE_QUERY);
        log.info("totalrepeats {}", totalRepeats);
        pageNo += 1;

        while (pageNo <= totalRepeats) {
            log.info("pageNo {}", pageNo);
            request = apiService.getRequestHeadersSpec(address, requestType, pageNo);
            apiResponse = request.retrieve().bodyToMono(RecapTitleApiResponse.class).block();
            assert apiResponse != null;
            resultList.addAll(apiResponse.getResponse().getBody().getItems().getItem());
            pageNo += 1;
        }
        return resultList;
    }

}
