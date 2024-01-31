package com.FireFacilAuto.domain.entity.lawfields;

import com.FireFacilAuto.domain.Conditions;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 조건식으 저쟝하는 클래스
 */
@Data
@Entity
public class BuildingLawFields {
    public Integer majorCategoryCode; //주요시설법 식별코드

    public Integer minorCategoryCode; //세부시설법 식별코드

    public Integer totalFloors; //건물 내 총 층수

    public Integer undergroundFloors; //건물 내 총 지하층

    public Integer overgroundFloors; //건물 내 총 지상층

    public Long GFA; //연면적

    public Integer buildingClassification; //건물 주용도

    public Integer buildingSpecification; //건물 세부용도

    public Long length; //터널 등 지하구 거리

    public LocalDateTime dateofApproval; //사용승인일


//    아직 미포함 정보

    public Integer buildingHumanCapacity; //수용량


    @Id
    private Long id;

    @OneToMany
    public List<Conditions> conditionsList;

}
