package com.FireFacilAuto.domain.entity.building;

import com.FireFacilAuto.domain.entity.Address;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Building {

    @Id
    public Long UUID; //내부 시스템용 식별코드

    @Embedded
    public Address juso; //건물 주소

    @OneToMany
    public List<Floor> compositeFloors; //건물 내 층 정보

    public Integer totalFloors; //건물 내 총 층수

    public Integer undergroundFloors; //건물 내 총 지하층

    public Integer overgroundFloors; //건물 내 총 지상층

    public Double GFA; //연면적

    public Integer buildingClassification; //건물 주용도

    public Integer buildingSpecification; //건물 세부용도

    public Long length; //터널 등 지하구 거리

    public LocalDateTime dateofApproval; //사용승인일

//    아직 미포함 정보

    public Integer buildingHumanCapacity;


}
