package com.FireFacilAuto.domain.entity.lawfields;

import com.FireFacilAuto.domain.Conditions;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.List;

@Data
@Entity
public class FloorLawFields {

    public Integer majorCategoryCode; //주요시설법 식별코드

    public Integer minorCategoryCode; //세부시설법 식별코드

    public Integer floorNo; //충수

    public Boolean isUnderGround; //지하여부

    public Integer floorClassification; //층 주용도

    public Integer floorSpecification; //층 세부용도

    public Double floorArea; //층 바닥면적

    public Integer floorMaterial; //층 재료

//    아직 미포함인 정보

    public Boolean floorWindowAvailability; //무창층

    @Id
    private Long id;

    @OneToMany
    public List<Conditions> conditionsList;

}
