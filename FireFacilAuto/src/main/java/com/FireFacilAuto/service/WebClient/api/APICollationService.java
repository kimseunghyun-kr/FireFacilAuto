package com.FireFacilAuto.service.WebClient.api;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoResponseItem;
import com.FireFacilAuto.domain.DTO.api.floorapi.FloorResponseItem;
import com.FireFacilAuto.domain.DTO.api.recaptitleapi.RecapTitleResponseItem;
import com.FireFacilAuto.domain.DTO.api.titleresponseapi.TitleResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.baseEndpoints.BaseApiService;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.exposedInfoEndpoint.ExposedInfoApiService;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.floorEndpoint.FloorApiService;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.recapTitleEndpoint.RecapTitleService;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.titleEndpoint.TitleApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.LinkedList;
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
    private final FloorApiService floorApiService;

    @Autowired
    public APICollationService(TitleApiService titleApiService, RecapTitleService recapTitleService, ExposedInfoApiService exposedInfoApiService, BaseApiService baseApiService, FloorApiService floorApiService) {
        this.titleApiService = titleApiService;
        this.recapTitleService = recapTitleService;
        this.exposedInfoApiService = exposedInfoApiService;
        this.baseApiService = baseApiService;
        this.floorApiService = floorApiService;
    }


    public List<List<BaseResponseItem>> concurrentPreGetFromApi(Address address) {
        // Create CompletableFutures for each API call
        CompletableFuture<List<BaseResponseItem>> baseTitleFuture = CompletableFuture.supplyAsync(() ->
                baseApiService.fetchAllTitleBaseData(address));

        CompletableFuture<List<BaseResponseItem>> baseExposFuture = CompletableFuture.supplyAsync(() ->
                baseApiService.fetchAllExposInfoBaseData(address));

        CompletableFuture<List<ExposedInfoResponseItem>> exposedFuture = CompletableFuture.supplyAsync(() ->
                exposedInfoApiService.fetchAllExposedInfoData(address));

        CompletableFuture<List<RecapTitleResponseItem>> recapFuture = CompletableFuture.supplyAsync(() ->
                recapTitleService.fetchAllRecapTitleData(address));

        CompletableFuture<List<TitleResponseItem>> titleFuture = CompletableFuture.supplyAsync(() ->
                titleApiService.fetchAllTitleData(address));

        // Combine all CompletableFutures into one
        CompletableFuture<Void> allOf = CompletableFuture.allOf(baseTitleFuture, baseExposFuture, exposedFuture, recapFuture, titleFuture);

        // Wait for all CompletableFutures to complete
        try {
            allOf.get();
            // Retrieve the results from the completed CompletableFutures
            List<BaseResponseItem> baseResponseTitleItemList = baseTitleFuture.get();
            List<BaseResponseItem> baseResponseExposItemList = baseExposFuture.get();
            List<ExposedInfoResponseItem> exposedInfoItemsList = exposedFuture.get();
            List<RecapTitleResponseItem> recapTitleServiceList = recapFuture.get();
            List<TitleResponseItem> titleResponseItemsList = titleFuture.get();
            // Your business logic with the retrieved lists

            if(baseResponseTitleItemList.isEmpty() && baseResponseExposItemList.isEmpty()) {
                CompletableFuture<List<BaseResponseItem>> baseFuture = CompletableFuture.supplyAsync(() ->
                        baseApiService.fetchAllBaseData(address));
                allOf.get();
                List<BaseResponseItem> baseResponseItemList = baseFuture.get();
                return new LinkedList<>(List.of(baseResponseItemList));
            }
            return new LinkedList<>(List.of(baseResponseTitleItemList,baseResponseExposItemList));

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }

    public List<ExposedInfoResponseItem> getfurtherSpecificSelect(BaseResponseItem baseResponseItemObject, List<BaseResponseItem> exposList,  Address address) {
        log.info("**************************************************");
        log.info("getfurtherSpecificSelect BaseexposListChekc {}", exposList);

        List<ExposedInfoResponseItem> exposedInfoResponseItemList = exposedInfoApiService.fetchAllExposedInfoData(address);
        log.info("getfurtherSpecificSelect exposedInfoResponseItemListCheck {}", exposedInfoResponseItemList);

        List<String> exposListWithBaseResponseItemObjectParent = exposList.stream().filter(obj -> obj.getMgmUpBldrgstPk().equals(baseResponseItemObject.getMgmBldrgstPk())).map(BaseResponseItem::getMgmBldrgstPk).toList();

        return exposedInfoResponseItemList.stream().filter(obj -> exposListWithBaseResponseItemObjectParent.contains(obj.mgmBldrgstPk)).toList();
    }

    public TitleResponseItem getTitleItemFromBase(BaseResponseItem baseResponseItem, Address address) {
        log.info("baseResponseItem whateverPk {}", baseResponseItem.getMgmBldrgstPk());


//        return titleApiService.fetchAllTitleData(address, "getBrTitleInfo").stream().peek(obj -> log.info("object value , {}", obj.mgmBldrgstPk))
//                .findFirst().orElseThrow();

        return titleApiService.fetchAllTitleData(address).stream()
                .filter(obj -> obj.mgmBldrgstPk.equals(baseResponseItem.getMgmBldrgstPk())).findFirst().orElseThrow();
    }

    public List<FloorResponseItem> getFloorItemFromTitle(TitleResponseItem titleResponseItem, Address address) {
        List<FloorResponseItem> floorResponseItems = floorApiService.fetchAllFloorData(address).stream().filter(floorObj -> floorObj.getMgmBldrgstPk().equals(titleResponseItem.mgmBldrgstPk)).toList();
        return floorResponseItems;
    }
}

