package com.FireFacilAuto.domain.entity.lawfields.buildingLaw;

import com.FireFacilAuto.domain.entity.lawfields.ClauseListConverter;
import com.FireFacilAuto.domain.entity.lawfields.LawFields;
import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.ClauseValue;
import com.FireFacilAuto.domain.entity.lawfields.clause.valueWrappers.ClauseValueWrapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * Represents the fields related to building laws and regulations.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class BuildingLawFields extends LawFields {

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

}

