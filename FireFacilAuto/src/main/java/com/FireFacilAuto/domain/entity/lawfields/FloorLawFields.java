package com.FireFacilAuto.domain.entity.lawfields;

import com.FireFacilAuto.domain.Conditions;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class FloorLawFields {

    @Column(nullable = false)
    @Positive
    public Integer majorCategoryCode; //주요시설법 식별코드
    @Column(nullable = false)
    @Positive
    public Integer minorCategoryCode; //세부시설법 식별코드
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer floorNo; //충수
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Boolean isUnderGround; //지하여부
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer floorClassification; //층 주용도
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer floorSpecification; //층 세부용도
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Double floorArea; //층 바닥면적
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer floorMaterial; //층 재료

//    아직 미포함인 정보
    @Column(columnDefinition = "boolean default false")
    @Positive
    public Boolean floorWindowAvailability; //무창층 (무창층에만 1 / 아닐 시 0)

    @Id
    private Long id;

    @OneToMany
    public List<Conditions> conditionsList;

}
