package com.FireFacilAuto.domain.DTO.api.floorapi;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class FloorResponseItem implements Serializable {

    @JsonProperty("area")
    public String area;

    @JsonProperty("areaExctYn")
    public String areaExctYn;

    @JsonProperty("bjdongCd")
    public String bjdongCd;

    @JsonProperty("bldNm")
    public String bldNm;

    @JsonProperty("block")
    public String block;

    @JsonProperty("bun")
    public String bun;

    @JsonProperty("crtnDay")
    public String crtnDay;

    @JsonProperty("dongNm")
    public String dongNm;

    @JsonProperty("etcPurps")
    public String etcPurps;

    @JsonProperty("etcStrct")
    public String etcStrct;

    @JsonProperty("flrGbCd")
    public String flrGbCd;

    @JsonProperty("flrGbCdNm")
    public String flrGbCdNm;

    @JsonProperty("flrNo")
    public String flrNo;

    @JsonProperty("flrNoNm")
    public String flrNoNm;

    @JsonProperty("ji")
    public String ji;

    @JsonProperty("lot")
    public String lot;

    @JsonProperty("mainAtchGbCd")
    public String mainAtchGbCd;

    @JsonProperty("mainAtchGbCdNm")
    public String mainAtchGbCdNm;

    @JsonProperty("mainPurpsCd")
    public String mainPurpsCd;

    @JsonProperty("mainPurpsCdNm")
    public String mainPurpsCdNm;

    @JsonProperty("mgmBldrgstPk")
    public String mgmBldrgstPk;

    @JsonProperty("naBjdongCd")
    public String naBjdongCd;

    @JsonProperty("naMainBun")
    public String naMainBun;

    @JsonProperty("naRoadCd")
    public String naRoadCd;

    @JsonProperty("naSubBun")
    public String naSubBun;

    @JsonProperty("naUgrndCd")
    public String naUgrndCd;

    @JsonProperty("newPlatPlc")
    public String newPlatPlc;

    @JsonProperty("platGbCd")
    public String platGbCd;

    @JsonProperty("platPlc")
    public String platPlc;

    @JsonProperty("rnum")
    public String rnum;

    @JsonProperty("sigunguCd")
    public String sigunguCd;

    @JsonProperty("splotNm")
    public String splotNm;

    @JsonProperty("strctCd")
    public String strctCd;

    @JsonProperty("strctCdNm")
    public String strctCdNm;

    // Getters and Setters
}
