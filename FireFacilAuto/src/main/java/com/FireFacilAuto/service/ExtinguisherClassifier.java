package com.FireFacilAuto.service;

import com.FireFacilAuto.domain.entity.BuildTarget;
import com.FireFacilAuto.domain.entity.installation.ExtinguisherInstallation;
import com.FireFacilAuto.util.CO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.FireFacilAuto.util.CO.make;

@Service
public class ExtinguisherClassifier {

    private CO btClassifier;
    private Long GFA;
    public ExtinguisherInstallation classify(BuildTarget buildTarget) {

        btClassifier = make(buildTarget.classification, buildTarget.specification);
        GFA = buildTarget.getGFA();

        ExtinguisherInstallation extinguisherInstallation = new ExtinguisherInstallation();
        extinguisherInstallation.extinguisherApparatus = extinguisherApparatusCheck(buildTarget);
        extinguisherInstallation.HUAutomaticFireExtinguisherApparatus = HUAutomaticFireExtinguisherApparatusCheck(buildTarget);
        extinguisherInstallation.CEAutomaticFireExtinguisherApparatus = CEAutomaticFireExtinguisherApparatusCheck(buildTarget);
        extinguisherInstallation.IndoorFireHydrantApparatus = IndoorFireHydrantApparatusCheck(buildTarget);

        return extinguisherInstallation;
    }



    /**
     * 소화기구 설치 관련 (1-가)
     *
     * 연면적 33제곱미터 이상 -> (GFA>33)
     * 가스시설 -> 17-2 , 17-3, 17-4
     * 발전시설 중 전기저장시설 -> 23-5
     * 문화재 -> 29-1
     * 터널 -> 27-2
     * 지하구 -> 28--1
     *
     * * 투척용 소화용구 미포함
     * */
    public Boolean extinguisherApparatusCheck(BuildTarget bt) {

        List<CO> extinguisherList = Arrays.asList(make(17,2), make(17,3), make(17,4),make(23,5),make(27,2),make(28,-1),make(29,1));

        if(bt.GFA >= 33){
            return true;
        }
        return extinguisherList.contains(make(bt.classification, bt.specification));
    }

    /**
     * 주거용 자동소화장치(1-나-1)
     * 아파트등 -> 1-1
     * 오피스텔 -> 12-2 {일반업무시설(오피스텔) 신설}
     *
     * 일반업무시설 전반에 적용 가능한지에 대한 법리해석 요함
     */
    private Boolean HUAutomaticFireExtinguisherApparatusCheck(BuildTarget bt) {
        List<CO> HUAFE = Arrays.asList(make(1,1), make(12,2));
        return HUAFE.contains(make(bt.classification, bt.specification));

    }

    /**
     * 상업용 주방자동소화장치 설치(1-나-2)
     * 가)
     * 유통산업발전법 2조 3항 대규모점포 -> 5-2
     *
     * 나)
     *   집단급식소 관련 법률 하단 링크
     *   https://www.law.go.kr/lsSc.do?menuId=1&subMenuId=15&query=%EC%9C%A0%ED%86%B5%EC%82%B0%EC%97%85%EB%B0%9C%EC%A0%84%EB%B2%95&dt=20201211#undefined
     *
     *   집단급식소 정의
     *     가. 기숙사 -> 1-4
     *     나. 학교, 유치원, 어린이집 -> 8-1 , 9-2
     *     다. 병원 -> 7--1
     *     라. 「사회복지사업법」 제2조제4호의 사회복지시설 -> 9-6
     *     마. 산업체
     *     바. 국가, 지방자치단체 및 「공공기관의 운영에 관한 법률」 제4조제1항에 따른 공공기관
     *     사. 그 밖의 후생기관 등
     *     식품위생법 기준 항목: 라 - 사 미포함 -> 추가적인 법률 관련 해석 필요
    */
    private Boolean CEAutomaticFireExtinguisherApparatusCheck(BuildTarget bt) {
        List<CO> CEAFE = Arrays.asList(make(5,2), make(1,4),make(7,-1), make(8,1),make(9,2), make(9,6));
        return CEAFE.contains(make(bt.classification, bt.specification));
    }

    /**
     * 캐비닛형 자동소화장치, 가스자동 등등  (1-나-3)
     *
     * 미포함
     *
     */


    /**
     * 옥내소화전설비 설치 관련 (1-다)
     *
     * 무인변전소 원격 조정 소화 설비 미포함 가정
     *
     *
     */
    private Boolean IndoorFireHydrantApparatusCheck(BuildTarget bt) {
        List<CO> exclusionList = Arrays.asList(make(17,2), make(17,3), make(17,4), make(28,-1));
        CO btClassifier = make(bt.classification, bt.specification);
        if (exclusionList.contains(btClassifier)){
            return false;
        }
        if(bt.classification == 27 && bt.specification ==2) {
            return true;
        }
        if(bt.GFA >= 3000){
            return true;
        }
//        1 - 다 - 1 -나 제외 (무창층란 없음)
//        1 - 다 - 1 -다 제외 (층별 바닥면적란 없음)
        return true;

    }



}
