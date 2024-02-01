package com.FireFacilAuto.domain.entity.lawfields;

import com.FireFacilAuto.domain.Conditions;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 조건식으 저쟝하는 클래스
 */
@Data
@Entity
public class BuildingLawFields {
    @Column(nullable = false)
    @Positive
    public Integer majorCategoryCode; //주요시설법 식별코드
    @Column(nullable = false)
    @Positive
    public Integer minorCategoryCode; //세부시설법 식별코드
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer totalFloors; //건물 내 총 층수
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer undergroundFloors; //건물 내 총 지하층
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer overgroundFloors; //건물 내 총 지상층
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Long GFA; //연면적
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer buildingClassification; //건물 주용도
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer buildingSpecification; //건물 세부용도
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Long length; //터널 등 지하구 거리
    @Column(columnDefinition = "timestamp default '0001-01-01T00:00:00'")
    public LocalDateTime dateofApproval; //사용승인일


//    아직 미포함 정보
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer buildingHumanCapacity; //수용량


    @Id
    private Long id;

    @OneToMany
    public List<Conditions> conditionsList;

}
