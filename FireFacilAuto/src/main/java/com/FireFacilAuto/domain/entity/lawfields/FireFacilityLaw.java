package com.FireFacilAuto.domain.entity.lawfields;

import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Entity
@Slf4j
@Data
public class FireFacilityLaw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public ApplicationMethod applicationMethod;

    /**
     * Classification code for the primary purpose of the floor.
     */
    @Column(columnDefinition = "integer default -1")
    @Min(value = -1, message = "Value must be at least -1")
    public Integer classification;

    /**
     * Specification code for additional details about the floor.
     */
    @Column(columnDefinition = "integer default -1")
    @Min(value = -1, message = "Value must be at least -1")
    public Integer specification;


    //    @Convert(converter = ClauseListConverter.class)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "floor_law_fields_id")  // Adjust the column name as needed
    public List<Clause> clauses;

}
