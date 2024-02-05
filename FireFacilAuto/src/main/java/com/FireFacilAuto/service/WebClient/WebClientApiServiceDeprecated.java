package com.FireFacilAuto.service.WebClient;

import com.FireFacilAuto.domain.entity.Address;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

// this is just here for development reference. to be removed in the future post production build.
public class WebClientApiServiceDeprecated {

//    public List<ApiResponseItem> fetchData(Address address) {
//        log.info("sigungucode {} ", address.getSigunguCode());
//        log.info("bcode {} ", address.getBcode());
//        WebClient.RequestHeadersSpec<?> request = webClient.get().uri(uriBuilder -> uriBuilder
//                .queryParam("serviceKey", apiKey)
//                .queryParam("bjdongCd", address.getBcode())
//                .queryParam("sigunguCd", address.getSigunguCode())
//                .queryParam("_type", "json")
//                .build());
//
//        String response = request.retrieve().bodyToMono(String.class).block();
//
//        ApiResponse apiResponse = request.retrieve().bodyToMono(ApiResponse.class).block();
//
//        assert apiResponse != null;
//        ResponseBody responseBody = apiResponse.getResponse().getBody();
//
//        return responseBody.getItems().getItem();
//    }
}
