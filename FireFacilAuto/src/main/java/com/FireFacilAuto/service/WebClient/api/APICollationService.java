package com.FireFacilAuto.service.WebClient.api;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoResponseItem;
import com.FireFacilAuto.domain.DTO.api.recaptitleapi.RecapTitleResponseItem;
import com.FireFacilAuto.domain.DTO.api.titleresponseapi.TitleResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.exposedInfoEndpoint.ExposedInfoApiService;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.recapTitleEndpoint.RecapTitleService;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.titleEndpoint.TitleApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import java.util.List;

@Slf4j
@Service
public class APICollationService {

    private final TitleApiService titleApiService;

    @Autowired
    public APICollationService(TitleApiService titleApiService, RecapTitleService recapTitleService, ExposedInfoApiService exposedInfoApiService) {
        this.titleApiService = titleApiService;
    }

    public void process(BaseResponseItem baseResponseItemObject, Address address) {
        
    }

    public String buildingAttributeQuery(Address address) {
        List<TitleResponseItem> resultListTit = titleApiService.fetchAllTitleData(address, "getBrTitleInfo");
        if(resultListTit.isEmpty()) {
            log.info("responseBody, {}", resultListTit);
        }

        return "";
    }

}

