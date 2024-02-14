package com.FireFacilAuto.domain.DTO.api.baseapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null values during serialization
public class BaseResponseItem implements Serializable {
    private String bjdongCd;
    private String bldNm;
    private String block;
    private String bun;
    private String bylotCnt;
    private String crtnDay;
    private String guyukCd;
    private String guyukCdNm;
    private String ji;
    private String jiguCd;
    private String jiguCdNm;
    private String jiyukCd;
    private String jiyukCdNm;
    private String lot;
    private String mgmBldrgstPk;
    private String mgmUpBldrgstPk;
    private String naBjdongCd;
    private String naMainBun;
    private String naRoadCd;
    private String naSubBun;
    private String naUgrndCd;
    private String newPlatPlc;
    private String platGbCd;
    private String platPlc;
    private String regstrGbCd;
    private String regstrGbCdNm;
    private String regstrKindCd;
    private String regstrKindCdNm;
    private String rnum;
    private String sigunguCd;
    private String splotNm;

    private List<String> toList() {
        List<String> resultList = new ArrayList<>();

        resultList.add("bjdongCd");
        resultList.add("bldNm");
        resultList.add("block");
        resultList.add("bun");
        resultList.add("bylotCnt");
        resultList.add("crtnDay");
        resultList.add("guyukCd");
        resultList.add("guyukCdNm");
        resultList.add("ji");
        resultList.add("jiguCd");
        resultList.add("jiguCdNm");
        resultList.add("jiyukCd");
        resultList.add("jiyukCdNm");
        resultList.add("lot");
        resultList.add("mgmBldrgstPk");
        resultList.add("mgmUpBldrgstPk");
        resultList.add("naBjdongCd");
        resultList.add("naMainBun");
        resultList.add("naRoadCd");
        resultList.add("naSubBun");
        resultList.add("naUgrndCd");
        resultList.add("newPlatPlc");
        resultList.add("platGbCd");
        resultList.add("platPlc");
        resultList.add("regstrGbCd");
        resultList.add("regstrGbCdNm");
        resultList.add("regstrKindCd");
        resultList.add("regstrKindCdNm");
        resultList.add("rnum");
        resultList.add("sigunguCd");
        resultList.add("splotNm");

        return resultList;
    }

}
