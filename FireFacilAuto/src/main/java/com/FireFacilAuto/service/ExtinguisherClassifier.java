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

    private BuildTarget bt;
    private CO btClassifier;
    private Long GFA;
    private Integer c;
    private Integer s;

    public ExtinguisherInstallation classify(BuildTarget buildTarget) {
        this.bt = buildTarget;
        c = buildTarget.classification;
        s = buildTarget.specification;
        btClassifier = make(buildTarget.classification, buildTarget.specification);
        GFA = buildTarget.getGFA();

        ExtinguisherInstallation extinguisherInstallation = new ExtinguisherInstallation();
        extinguisherInstallation.extinguisherApparatus = extinguisherApparatusCheck();
        extinguisherInstallation.HUAutomaticFireExtinguisherApparatus = HUAutomaticFireExtinguisherApparatusCheck();
        extinguisherInstallation.CEAutomaticFireExtinguisherApparatus = CEAutomaticFireExtinguisherApparatusCheck();
        extinguisherInstallation.IndoorFireHydrantApparatus = IndoorFireHydrantApparatusCheck();
        extinguisherInstallation.SprinklerApparatus = SprinklerApparatusCheck();
        extinguisherInstallation.SimplfiedSprinklerApparatus = SimplifiedSprinklerApparatusCheck();

        return extinguisherInstallation;
    }

    private Boolean SimplifiedSprinklerApparatusCheck() {

    }

    /**
     * 소화기구 설치 관련 ( 1 - 가 )
     * <p>
     * 연면적 33제곱미터 이상 -> (GFA>33)
     * 가스시설 -> 17-2 , 17-3, 17-4
     * 발전시설 중 전기저장시설 -> 23-5
     * 문화재 -> 29-1
     * 터널 -> 27-2
     * 지하구 -> 28--1
     * <p>
     * * 투척용 소화용구 미포함
     */
    public Boolean extinguisherApparatusCheck() {

        List<CO> extinguisherList = Arrays.asList(make(17, 2), make(17, 3), make(17, 4), make(23, 5), make(27, 2), make(28, -1), make(29, 1));

        if (GFA >= 33) {
            return true;
        }
        return extinguisherList.contains(btClassifier);
    }

    /**
     * 주거용 자동소화장치( 1 - 나 - 1 )
     * 아파트등 -> 1-1
     * 오피스텔 -> 12-2 {일반업무시설(오피스텔) 신설}
     * <p>
     * 일반업무시설 전반에 적용 가능한지에 대한 법리해석 요함
     */
    private Boolean HUAutomaticFireExtinguisherApparatusCheck() {
        List<CO> HUAFE = Arrays.asList(make(1, 1), make(12, 2));
        return HUAFE.contains(btClassifier);

    }

    /**
     * 상업용 주방자동소화장치 설치( 1 - 나 - 2 )
     * <p>
     * 가)
     * 유통산업발전법 2조 3항 대규모점포 -> 5-2
     * <p>
     * 나)
     * 집단급식소 관련 법률 하단 링크
     * https://www.law.go.kr/lsSc.do?menuId=1&subMenuId=15&query=%EC%9C%A0%ED%86%B5%EC%82%B0%EC%97%85%EB%B0%9C%EC%A0%84%EB%B2%95&dt=20201211#undefined
     * <p>
     * 집단급식소 정의
     * 가. 기숙사 -> 1-4
     * 나. 학교, 유치원, 어린이집 -> 8-1 , 9-2
     * 다. 병원 -> 7--1
     * 라. 「사회복지사업법」 제2조제4호의 사회복지시설 -> 9-6
     * 마. 산업체
     * 바. 국가, 지방자치단체 및 「공공기관의 운영에 관한 법률」 제4조제1항에 따른 공공기관
     * 사. 그 밖의 후생기관 등
     * 식품위생법 기준 항목: 라 - 사 미포함 -> 추가적인 법률 관련 해석 필요
     */
    private Boolean CEAutomaticFireExtinguisherApparatusCheck() {
        List<CO> CEAFE = Arrays.asList(make(5, 2), make(1, 4), make(7, -1), make(8, 1), make(9, 2), make(9, 6));
        return CEAFE.contains(btClassifier);
    }

    /**
     * 캐비닛형 자동소화장치, 가스자동 등등  (1-나-3)
     *
     * 미포함
     *
     */


    /**
     * 옥내소화전설비 설치 관련 (1-다)
     * <p>
     * 무인변전소 원격 조정 소화 설비 미포함 가정
     * 1 - 다 - 1 -나 제외 (무창층란 없음)
     * 1 - 다 - 1 -다 제외 (층별 바닥면적란 없음)
     * <p>
     * 1 - 다 - 2 - 나 제외  지하층ㆍ무창층으로서 바닥면적이 300㎡ 이상인 층이 있는 것
     * 1 - 다 - 2 - 다 제외  층수가 4층 이상인 것 중 바닥면적이 300㎡ 이상인 층이 있는 것
     * <p>
     * 1-다-3 제외
     * 건축물의 옥상에 설치된 차고ㆍ주차장으로서 사용되는 면적이 200㎡ 이상인 경우 해당 부분
     * <p>
     * 1 - 다 - 4 - 나 제외
     * 예상교통량, 경사도 등 터널의 특성을 고려하여 행정안전부령으로 정하는 터널
     * <p>
     * 1 - 다 - 5 제외
     * 1) 및 2)에 해당하지 않는 공장 또는 창고시설로서 「화재의 예방 및 안전관
     * 리에 관한 법률 시행령」 별표 2에서 정하는 수량의 750배 이상의 특수가연
     * 물을 저장ㆍ취급하는 것
     */
    private Boolean IndoorFireHydrantApparatusCheck() {
        List<CO> exclusionList = Arrays.asList(make(17, 2), make(17, 3), make(17, 4), make(28, -1));
        if (exclusionList.contains(btClassifier)) {
            return false;
        }

//        1 - 다 - 1 - 가
        if (GFA >= 3000 && !(s == 27 && c == 2)) {
            return true;
        }

//        1 - 다 - 1 -나 제외 (무창층란 없음)
//        1 - 다 - 1 -다 제외 (층별 바닥면적란 없음)

//        1 - 다 - 2 ( 1에 해당하지 않는 시설 )

        List<CO> inclusionList = Arrays.asList(make(2, -1), make(5, -1), make(6, -1), make(7, -1), make(8, -1), make(9, -1),
                make(12, -1), make(13, -1), make(14, -1), make(15, -1),
                make(16, -1), make(18, -1), make(21, 11), make(22, -1), make(23, -1), make(26, -1), make(30, -1));

        if (inclusionList.contains(btClassifier)) {
            // 1-다-2-가
            if (GFA >= 1500) {
                return true;
            }

//            1 - 다 - 2 - 나 제외  지하층ㆍ무창층으로서 바닥면적이 300㎡ 이상인 층이 있는 것
//            1 - 다 - 2 - 다 제외  층수가 4층 이상인 것 중 바닥면적이 300㎡ 이상인 층이 있는 것
        }

//        1-다-3 제외  건축물의 옥상에 설치된 차고ㆍ주차장으로서 사용되는 면적이 200㎡ 이상인 경우 해당 부분

//        1 - 다 - 4  지하가 중 터널로서 다음에 해당하는 터널
        if (c == 27 && s == 2) {
            if (bt.length >= 1000) {
                return true;
            }

//       1 - 다 - 4 - 나 제외 예상교통량, 경사도 등 터널의 특성을 고려하여 행정안전부령으로 정하는 터널
        }


//        1 - 다 - 5 제외
        if (c == 15 || c == 16) {
// 1) 및 2)에 해당하지 않는 공장 또는 창고시설로서 「화재의 예방 및 안전관
//리에 관한 법률 시행령」 별표 2에서 정하는 수량의 750배 이상의 특수가연
//물을 저장ㆍ취급하는 것
        }

        return false;
    }


    /**
     * 스프링클러 설치 관련 ( 1 - 라 )
     */
    private Boolean SprinklerApparatusCheck() {
        List<CO> exclusionList = Arrays.asList(make(17, 2), make(17, 3), make(17, 4));
        if (exclusionList.contains(btClassifier)) {
            return false;
        }

        if (bt.floor > 6) {
//            1 - 라 - 1 - 가 제외
//            주택 관련 법령에 따라 기존의 아파트등을 리모델링하는 경우로서 건축
//            물의 연면적 및 층의 높이가 변경되지 않는 경우. 이 경우 해당 아파트등
//            의 사용검사 당시의 소방시설의 설치에 관한 대통령령 또는 화재안전기준
//            을 적용한다

//            1 - 라 - 1 - 나 제외
//            스프링클러설비가 없는 기존의 특정소방대상물을 용도변경하는 경우. 다
//            만, 2)부터 6)까지 및 9)부터 12)까지의 규정에 해당하는 특정소방대상물로
//            용도변경하는 경우에는 해당 규정에 따라 스프링클러설비를 설치한다.
        }
//            1 - 라 - 2
//            기숙사(교육연구시설ㆍ수련시설 내에 있는 학생 수용을 위한 것을 말한다)
//            또는 복합건축물로서 연면적 5천㎡ 이상인 경우에는 모든 층
        if (GFA > 5000 && ((c == 1 && s == 4) || (c == 31))) {
            return true;
        }
//             1 - 라 - 3
//             문화 및 집회시설(동ㆍ식물원은 제외한다), 종교시설(주요구조부가 목조인 것
//             은 제외한다), 운동시설(물놀이형 시설 및 바닥이 불연재료이고 관람석이 없
//             는 운동시설은 제외한다)로서 다음의 어느 하나에 해당하는 경우에는 모든 층
        if ((c == 3 && s != 5) || c == 4 || (c == 11 && s != 10)) {
//                불연재료, 주요구조부 등등 필요
//                가)  수용인원이 100명 이상인 것 제외
//                나) 영화상영관의 용도로 쓰는 층의 바닥면적이 지하층 또는 무창층인 경우
//                    에는 500㎡ 이상, 그 밖의 층의 경우에는 1천㎡ 이상인 것 제외
//                다) 무대부가 지하층ㆍ무창층 또는 4층 이상의 층에 있는 경우에는 무대부의
//                    면적이 300㎡ 이상인 것 제외
//                라) 무대부가 다) 외의 층에 있는 경우에는 무대부의 면적이 500㎡ 이상인 것 제외
        }

//            1 - 라 - 4
//            판매시설, 운수시설 및 창고시설(물류터미널로 한정한다)로서
        if (c == 5 || c == 6 || (c == 16 && s == 3)) {
//            바닥면적의 합계가 5천㎡ 이상이거나
            if (GFA >= 5000) {
                return true;
            }
//            수용인원이 500명 이상인 경우에는 모든 층 제외
        }

//            1 - 라 - 5
//             다음의 어느 하나에 해당하는 용도로 사용되는 시설의 바닥면적의 합계가 600㎡ 이상인 것은 모든 층
        if (GFA > 600) {
//                가) 근린생활시설 중 조산원
            if ((c == 2 && (s == 18 || s == 19))) {
                return true;
            }
//                나) 의료시설 중 정신의료기관
//                다) 의료시설 중 종합병원, 병원, 치과병원, 한방병원 및 요양병원
            if (c == 7) {
                return true;
            }

//                라) 노유자 시설
            if (c == 9) {
                return true;
            }
//
//                마) 숙박이 가능한 수련시설
            if (c == 10) {
                return true;
            }

//                바) 숙박시설
            if (c == 13) {
                return true;
            }
        }

//            1 - 라 -6
//            6) 창고시설(물류터미널은 제외한다)로서 바닥면적 합계가 5천㎡ 이상인 경우에는 모든 층
        if (c == 16 && s != 3) {
            return GFA >= 5000;
        }

//            1 - 라 - 7
//            7) 특정소방대상물의 지하층ㆍ무창층(축사는 제외한다) 또는 층수가 4층 이상인
//            층으로서 바닥면적이 1천㎡ 이상인 층이 있는 경우에는 해당 층
//            제외


//            1 - 라 - 8
//            8) 랙식 창고(rack warehouse): 랙(물건을 수납할 수 있는 선반이나 이와 비슷
//            한 것을 말한다. 이하 같다)을 갖춘 것으로서 천장 또는 반자(반자가 없는 경
//            우에는 지붕의 옥내에 면하는 부분을 말한다)의 높이가 10m를 초과하고, 랙이
//            설치된 층의 바닥면적의 합계가 1천5백㎡ 이상인 경우에는 모든 층
//            제외


//            1 - 라 - 9
//            9) 공장 또는 창고시설로서 다음의 어느 하나에 해당하는 시설
        if (c == 15) {
//                가) 「화재의 예방 및 안전관리에 관한 법률 시행령」 별표 2에서 정하는 수
//                량의 1천 배 이상의 특수가연물을 저장ㆍ취급하는 시설
//                제외

//                나) 「원자력안전법 시행령」 제2조제1호에 따른 중ㆍ저준위방사성폐기물(이
//                하 “중ㆍ저준위방사성폐기물”이라 한다)의 저장시설 중 소화수를 수집ㆍ처
//                리하는 설비가 있는 저장시설
//                제외
        }

//            1 - 라 - 10
//            10) 지붕 또는 외벽이 불연재료가 아니거나 내화구조가 아닌 공장 또는 창고시
//            설로서 다음의 어느 하나에 해당하는 것
        if (c == 15 || c == 16) {
//            가) 창고시설(물류터미널로 한정한다) 중 4)에 해당하지 않는 것으로서 바닥
//            면적의 합계가 2천5백㎡ 이상이거나 수용인원이 250명 이상인 경우에는
//            모든 층
//            미완(제외)
            if ((c == 16 && s == 3) && GFA >= 2500) {
            }
//            나) 창고시설(물류터미널은 제외한다) 중 6)에 해당하지 않는 것으로서 바닥
//            면적의 합계가 2천5백㎡ 이상인 경우에는 모든 층
//            제외

//            다) 공장 또는 창고시설 중 7)에 해당하지 않는 것으로서 지하층ㆍ무창층 또
//            는 층수가 4층 이상인 것 중 바닥면적이 500㎡ 이상인 경우에는 모든 층
//            제외

//            라) 랙식 창고 중 8)에 해당하지 않는 것으로서 바닥면적의 합계가 750㎡ 이
//            상인 경우에는 모든 층
//            제외

//            마) 공장 또는 창고시설 중 9)가)에 해당하지 않는 것으로서 「화재의 예방
//            및 안전관리에 관한 법률 시행령」 별표 2에서 정하는 수량의 500배 이상
//            의 특수가연물을 저장ㆍ취급하는 시설
//            제외
        }

//            1 - 라 - 11
//            11) 교정 및 군사시설 중 다음의 어느 하나에 해당하는 경우에는 해당 장소
//
        if (c == 21) {
//             가) 보호감호소, 교도소, 구치소 및 그 지소, 보호관찰소, 갱생보호시설, 치료
//            감호시설, 소년원 및 소년분류심사원의 수용거실
            if (s == 1 || s == 2 || s == 3 || s == 4 || s == 5 || s == 6 || s == 7 || s == 8) {
                return true;
            }
//            나) 「출입국관리법」 제52조제2항에 따른 보호시설(외국인보호소의 경우에는
//            보호대상자의 생활공간으로 한정한다. 이하 같다)로 사용하는 부분. 다만,
//            보호시설이 임차건물에 있는 경우는 제외한다. - 제외
            if (s == 9) {
                return true;
            }
//            다) 「경찰관 직무집행법」 제9조에 따른 유치장
            if (s == 10) {
                return true;
            }
        }

//        1 - 라 - 12
//        12) 지하가(터널은 제외한다)로서 연면적 1천㎡ 이상인 것
        if(c == 27 && s != 2) {
            return GFA >= 1000;
        }

//        1 - 라 - 13
//        13) 발전시설 중 전기저장시설
        if (c == 23 && s == 5) {
            return true;
        }

//        14) 1)부터 13)까지의 특정소방대상물에 부속된 보일러실 또는 연결통로 등 - 제외
        return false;
    }
}





