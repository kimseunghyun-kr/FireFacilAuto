package com.FireFacilAuto.domain.entity.lawfields;

import com.FireFacilAuto.domain.Conditions;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 * 조건식으 저쟝하는 클래스
 */
@Data
@Entity
public class BuildingLawFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="building_law_field_id")
    private Long id;

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
    @Column(columnDefinition = "bigint default -1")
    @Positive
    public Double GFA; //연면적
    @Column(columnDefinition = "integer default -1")
    @Min(value = -1, message = "Value must be at least -1")
    public Integer buildingClassification; //건물 주용도
    @Column(columnDefinition = "integer default -1")
    @Min(value = -1, message = "Value must be at least -1")
    public Integer buildingSpecification; //건물 세부용도
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Double length; //터널 등 지하구 거리
    @Column(columnDefinition = "timestamp default '2023-01-01'")
    public LocalDate dateofApproval; //사용승인일


//    아직 미포함 정보
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer buildingHumanCapacity; //수용량

    @OneToMany(mappedBy = "buildingLawFields", cascade = CascadeType.ALL)
    public List<Conditions> conditionsList;


    /**
     * this is an option. not delving right now
     */
//    public Map<String, Object> getAllFieldValues() {
//        Map<String, Object> fieldValues = new HashMap<>();
//        Field[] fields = getClass().getDeclaredFields();
//
//        for (Field field : fields) {
//            try {
//                field.setAccessible(true);
//                Object value = field.get(this);
//                fieldValues.put(field.getName(), value);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace(); // Handle the exception as needed
//            }
//        }
//
//        return fieldValues;
//    }

}
