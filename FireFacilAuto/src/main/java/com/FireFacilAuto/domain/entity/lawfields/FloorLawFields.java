package com.FireFacilAuto.domain.entity.lawfields;

import com.FireFacilAuto.domain.Conditions;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class FloorLawFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    public Boolean isUnderGround; //지하여부

    @Column(columnDefinition = "integer default -1")
    @Min(value = -1, message = "Value must be at least -1")
    public Integer floorClassification; //층 주용도
    @Column(columnDefinition = "integer default -1")
    @Min(value = -1, message = "Value must be at least -1")
    public Integer floorSpecification; //층 세부용도
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Double floorAreaSum; //층 바닥면적 합
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Double floorAreaThreshold; //층 바닥면적 합
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer floorMaterial; //층 재료

//    아직 미포함인 정보
    @Column(columnDefinition = "boolean default false")
    public Boolean floorWindowAvailability; //무창층 (무창층에만 1 / 아닐 시 0)

    @OneToMany(mappedBy = "floorLawFields", cascade = CascadeType.ALL)
    public List<Conditions> conditionsList;

}
