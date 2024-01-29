package com.FireFacilAuto.service.WebClient;

import com.FireFacilAuto.domain.DTO.api.ApiResponse;
import com.FireFacilAuto.domain.DTO.api.ApiResponseItem;
import com.FireFacilAuto.domain.DTO.api.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class WebClientApiService {

    @Value("${webclient.api.key}")
    private String apiKey;
    private final WebClient webClient;

    @Autowired
    public WebClientApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public ResponseBody fetchData() {
        WebClient.RequestHeadersSpec<?> request = webClient.get().uri(uriBuilder -> uriBuilder
                .queryParam("serviceKey", apiKey)
                .queryParam("bjdongCd", "10300")
                .queryParam("platGbCd", "0")
                .queryParam("bun", "0012")
                .queryParam("ji", "0000")
                .queryParam("startDate", "")
                .queryParam("endDate", "")
                .queryParam("numOfRows", "10")
                .queryParam("pageNo", "1")
                .queryParam("sigunguCd", "11680")
                .build());

        ApiResponse apiResponse = request.retrieve().bodyToMono(ApiResponse.class).block();

        ResponseBody responseBody = apiResponse.getBody();

        List<ApiResponseItem> responseItemList = responseBody.getItems();

        return responseBody;
    }
}
