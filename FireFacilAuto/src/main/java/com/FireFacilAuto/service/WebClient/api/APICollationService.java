package com.FireFacilAuto.service.WebClient.api;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoResponseItem;
import com.FireFacilAuto.domain.DTO.api.recaptitleapi.RecapTitleResponseItem;
import com.FireFacilAuto.domain.DTO.api.titleresponseapi.TitleResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.baseEndpoints.BaseApiService;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.exposedInfoEndpoint.ExposedInfoApiService;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.recapTitleEndpoint.RecapTitleService;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.titleEndpoint.TitleApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class APICollationService {

    private final TitleApiService titleApiService;
    private final RecapTitleService recapTitleService;
    private final ExposedInfoApiService exposedInfoApiService;

    private final BaseApiService baseApiService;

    @Autowired
    public APICollationService(TitleApiService titleApiService, RecapTitleService recapTitleService, ExposedInfoApiService exposedInfoApiService, BaseApiService baseApiService) {
        this.titleApiService = titleApiService;
        this.recapTitleService = recapTitleService;
        this.exposedInfoApiService = exposedInfoApiService;
        this.baseApiService = baseApiService;
    }


    public List<BaseResponseItem> concurrentPreGetFromApi(Address address) {
        // Create CompletableFutures for each API call
        CompletableFuture<List<BaseResponseItem>> baseFuture = CompletableFuture.supplyAsync(() ->
                baseApiService.fetchAllTitleBaseData(address, "getBrBasisOulnInfo"));

        CompletableFuture<List<ExposedInfoResponseItem>> exposedFuture = CompletableFuture.supplyAsync(() ->
                exposedInfoApiService.fetchAllExposedInfoData(address, "getBrExposInfo"));

        CompletableFuture<List<RecapTitleResponseItem>> recapFuture = CompletableFuture.supplyAsync(() ->
                recapTitleService.fetchAllRecapTitleData(address, "getBrRecapTitleInfo"));

        CompletableFuture<List<TitleResponseItem>> titleFuture = CompletableFuture.supplyAsync(() ->
                titleApiService.fetchAllTitleData(address, "getBrTitleInfo"));

        // Combine all CompletableFutures into one
        CompletableFuture<Void> allOf = CompletableFuture.allOf(baseFuture, exposedFuture, recapFuture, titleFuture);

        // Wait for all CompletableFutures to complete
        try {
            allOf.get();
            // Retrieve the results from the completed CompletableFutures
            List<BaseResponseItem> baseResponseItemList = baseFuture.get();
            List<ExposedInfoResponseItem> exposedInfoItemsList = exposedFuture.get();
            List<RecapTitleResponseItem> recapTitleServiceList = recapFuture.get();
            List<TitleResponseItem> titleResponseItemsList = titleFuture.get();
            // Your business logic with the retrieved lists

            return baseResponseItemList;

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public String buildingAttributeQuery(Address address) {
        List<TitleResponseItem> resultListTit = titleApiService.fetchAllTitleData(address, "getBrTitleInfo");
        if(resultListTit.isEmpty()) {
            log.info("responseBody, {}", resultListTit);
        }

        return "";
    }

    public List<ExposedInfoResponseItem> getfurtherSpecificSelect(BaseResponseItem baseResponseItemObject, Address address) {
        List<ExposedInfoResponseItem> exposedInfoResponseItemList = exposedInfoApiService.fetchAllExposedInfoData(address, "getBrExposInfo");
        return exposedInfoResponseItemList;
    }

    public TitleResponseItem getTitleItemFromBase(BaseResponseItem baseResponseItem, Address address) {
        log.info("baseResponseItem whateverPk {}", baseResponseItem.getMgmBldrgstPk());


        return titleApiService.fetchAllTitleData(address, "getBrTitleInfo").stream().map(obj -> {log.info("object value , {}", obj.mgmBldrgstPk);
            return obj;})
                .findFirst().orElseThrow();

//        return titleApiService.fetchAllTitleData(address, "getBrTitleInfo").stream()
//                .filter(obj -> obj.mgmBldrgstPk.equals(baseResponseItem.getMgmBldrgstPk())).findFirst().orElseThrow();
    }
}

