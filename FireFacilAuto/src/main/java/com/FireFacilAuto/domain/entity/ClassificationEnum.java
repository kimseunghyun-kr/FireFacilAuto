package com.FireFacilAuto.domain.entity;

import lombok.Getter;

@Getter
public enum ClassificationEnum {
    공동주택(1),
    근린생활시설(2),
    문화및집회(3),
    종교시설(4),
    판매시설(5),
    운수시설(6),
    의료시설(7),
    교육연구시설(8),
    노유자시설(9),
    수련시설(10),
    운동시설(11),
    업무시설(12),
    숙박시설(13),
    위락시설(14),
    공장(15),
    창고시설(16),
    위험물시설(17),
    항공기및자동차관련시설(18),
    동식물관련시설(19),
    자원순환관련시설(20),
    교정및군사시설(21),
    방송통신시설(22),
    발전시설(23),
    묘지관련시설(24),
    관광휴게시설(25),
    장례시설(26),
    지하가(27),
    지하구(28),
    문화재(29),
    복합건축물(30),
    특수가연물(31);

    private final int number;

    ClassificationEnum(int number) {
        this.number = number;
    }

//    public int getNumber() {
//        return number;
//    }
}
