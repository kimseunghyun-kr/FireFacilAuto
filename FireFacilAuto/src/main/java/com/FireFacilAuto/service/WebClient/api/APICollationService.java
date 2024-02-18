package com.FireFacilAuto.service.WebClient.api;

import com.FireFacilAuto.domain.DTO.api.baseapi.BaseResponseItem;
import com.FireFacilAuto.domain.DTO.api.exposInfo.ExposedInfoResponseItem;
import com.FireFacilAuto.domain.DTO.api.floorapi.FloorResponseItem;
import com.FireFacilAuto.domain.DTO.api.titleresponseapi.TitleResponseItem;
import com.FireFacilAuto.domain.entity.Address;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.baseEndpoints.BaseApiServiceAsync;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.exposedInfoEndpoint.ExposedInfoApiServiceAsync;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.floorEndpoint.FloorApiServiceAsync;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.recapTitleEndpoint.RecapTitleService;
import com.FireFacilAuto.service.WebClient.api.apiEndpoints.titleEndpoint.TitleApiServiceAsync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Slf4j
@Service
public class APICollationService {

    private final TitleApiServiceAsync titleApiService;
    private final RecapTitleService recapTitleService;
    private final ExposedInfoApiServiceAsync exposedInfoApiService;
    private final BaseApiServiceAsync baseApiService;
    private final FloorApiServiceAsync floorApiService;

    @Autowired
    public APICollationService(TitleApiServiceAsync titleApiService, RecapTitleService recapTitleService, ExposedInfoApiServiceAsync exposedInfoApiService, BaseApiServiceAsync baseApiService, FloorApiServiceAsync floorApiService) {
        this.titleApiService = titleApiService;
        this.recapTitleService = recapTitleService;
        this.exposedInfoApiService = exposedInfoApiService;
        this.baseApiService = baseApiService;
        this.floorApiService = floorApiService;
    }


    public List<List<BaseResponseItem>> concurrentPreGetFromApi(Address address) {
        // Create CompletableFutures for each API call
        CompletableFuture<List<BaseResponseItem>> baseFuture = CompletableFuture.supplyAsync(() ->
                baseApiService.fetchAllBaseData(address));

        // Execute the other three tasks in the background after baseFuture is complete
        CompletableFuture<Void> sideEffectTasks = baseFuture.thenRunAsync(() -> {
            log.info("running start sideEffectTasks");
            log.info("running start sideEffectTasks exposInfo");
            CompletableFuture.supplyAsync(() -> {
                try {
                    exposedInfoApiService.fetchAllExposedInfoData(address);
                } catch (Exception e) {
                    log.error("Error in fetchAllExposedInfoData", e);
                }
                return null;
            });
            log.info("running start sideEffectTasks recaptitle");
            CompletableFuture.supplyAsync(() -> {
                recapTitleService.fetchAllRecapTitleData(address);
                return null; // dummy result
            });
            CompletableFuture.supplyAsync(() -> {
                titleApiService.fetchAllTitleData(address);
                return null; // dummy result
            });
        });

        try {
            // Retrieve the results from the completed CompletableFutures
            List<BaseResponseItem> baseResponseItemList = baseFuture.join();
            // Your business logic with the retrieved lists
            List<BaseResponseItem> baseResponseTitleItemList = baseApiService.fetchAllTitleBaseData(baseResponseItemList);
            List<BaseResponseItem> baseResponseExposItemList = baseApiService.fetchAllExposInfoBaseData(baseResponseItemList);

            if(baseResponseTitleItemList.isEmpty() && baseResponseExposItemList.isEmpty()) {
                return new LinkedList<>(List.of(baseResponseItemList));
            }
            return new LinkedList<>(List.of(baseResponseTitleItemList,baseResponseExposItemList));

        }
        catch (CompletionException e) {
            throw new RuntimeException("completableFuture completionException occured",e);
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

    public FloorResponseItem getFloorItemFromTitleForExpos(ExposedInfoResponseItem exposInfoResponseItem, TitleResponseItem titleResponseItem, Address address) {
        List<FloorResponseItem> floorResponseItems = floorApiService.fetchAllFloorData(address).stream().filter(floorObj -> floorObj.getMgmBldrgstPk().equals(titleResponseItem.mgmBldrgstPk)).toList();
        return floorResponseItems.stream()
                .filter(item -> Integer.parseInt(item.flrGbCd) == Integer.parseInt(exposInfoResponseItem.flrGbCd) &&
                        Integer.parseInt(item.flrNo) == Integer.parseInt(exposInfoResponseItem.flrNo)).findFirst().orElseThrow();
    }
}

