package com.FireFacilAuto.domain.DTO.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FloorResponseItem extends ApiResponseItem{

    @JsonProperty("area")
    private String area;

    @JsonProperty("areaExctYn")
    private String areaExctYn;

    @JsonProperty("bjdongCd")
    private String bjdongCd;

    @JsonProperty("bldNm")
    private String bldNm;

    @JsonProperty("block")
    private String block;

    @JsonProperty("bun")
    private String bun;

    @JsonProperty("crtnDay")
    private String crtnDay;

    @JsonProperty("dongNm")
    private String dongNm;

    @JsonProperty("etcPurps")
    private String etcPurps;

    @JsonProperty("etcStrct")
    private String etcStrct;

    @JsonProperty("flrGbCd")
    private String flrGbCd;

    @JsonProperty("flrGbCdNm")
    private String flrGbCdNm;

    @JsonProperty("flrNo")
    private String flrNo;

    @JsonProperty("flrNoNm")
    private String flrNoNm;

    @JsonProperty("ji")
    private String ji;

    @JsonProperty("lot")
    private String lot;

    @JsonProperty("mainAtchGbCd")
    private String mainAtchGbCd;

    @JsonProperty("mainAtchGbCdNm")
    private String mainAtchGbCdNm;

    @JsonProperty("mainPurpsCd")
    private String mainPurpsCd;

    @JsonProperty("mainPurpsCdNm")
    private String mainPurpsCdNm;

    @JsonProperty("mgmBldrgstPk")
    private String mgmBldrgstPk;

    @JsonProperty("naBjdongCd")
    private String naBjdongCd;

    @JsonProperty("naMainBun")
    private String naMainBun;

    @JsonProperty("naRoadCd")
    private String naRoadCd;

    @JsonProperty("naSubBun")
    private String naSubBun;

    @JsonProperty("naUgrndCd")
    private String naUgrndCd;

    @JsonProperty("newPlatPlc")
    private String newPlatPlc;

    @JsonProperty("platGbCd")
    private String platGbCd;

    @JsonProperty("platPlc")
    private String platPlc;

    @JsonProperty("rnum")
    private String rnum;

    @JsonProperty("sigunguCd")
    private String sigunguCd;

    @JsonProperty("splotNm")
    private String splotNm;

    @JsonProperty("strctCd")
    private String strctCd;

    @JsonProperty("strctCdNm")
    private String strctCdNm;

    // Getters and Setters
}
