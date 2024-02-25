package com.FireFacilAuto.domain.entity.lawfields;

import com.FireFacilAuto.domain.Conditions;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


/**
 * Represents the fields related to building laws and regulations.
 */
@Data
@Entity
public class BuildingLawFields {

    /**
     * Internal system identification code for the building law fields.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_law_field_id")
    private Long id;

    /**
     * Major category code representing the installation that the law is trying to enforce.
     */
    @Column(nullable = false)
    @Positive
    public Integer majorCategoryCode;

    /**
     * Minor category code representing the installation that the law is trying to enforce.
     */
    @Column(nullable = false)
    @Positive
    public Integer minorCategoryCode;

    /**
     * Building classification code indicating the primary use of the building.
     */
    @Column(columnDefinition = "integer default -1")
    @Min(value = -1, message = "Value must be at least -1")
    public Integer buildingClassification;

    /**
     * Building specification code indicating the detailed use of the building.
     */
    @Column(columnDefinition = "integer default -1")
    @Min(value = -1, message = "Value must be at least -1")
    public Integer buildingSpecification;

    /**
     * Building materials code indicating the primary structural material of the building.
     */
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer buildingMaterials;

    /**
     * Total number of floors in the building.
     */
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer totalFloors;

    /**
     * Number of underground floors in the building.
     */
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer undergroundFloors;

    /**
     * Number of overground floors in the building.
     */
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer overgroundFloors;

    /**
     * Gross floor area of the building.
     */
    @Column(columnDefinition = "bigint default -1")
    @Positive
    public Double GFA;

    /**
     * Distance from the building to underground structures like tunnels.
     */
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Double length;

    /**
     * Date when the building received approval for use.
     */
    @Column(columnDefinition = "timestamp default '2023-01-01'")
    public LocalDate dateofApproval;

    // Unspecified information (you can update these comments as needed)

    /**
     * Capacity of humans within the building (not yet included).
     */
    @Column(columnDefinition = "integer default -1")
    @Positive
    public Integer buildingHumanCapacity;

    /**
     * List of conditions associated with the building law fields.
     */
    @OneToMany(mappedBy = "buildingLawFields", cascade = CascadeType.ALL)
    public List<Conditions> conditionsList;

    /**
     * This is an option for getting all field values. Not delving into it right now.
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

