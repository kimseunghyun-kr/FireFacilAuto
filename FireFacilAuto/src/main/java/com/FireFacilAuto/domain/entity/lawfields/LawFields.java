package com.FireFacilAuto.domain.entity.lawfields;

import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "entity_type", discriminatorType = DiscriminatorType.STRING)
public abstract class LawFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Major category code representing the installation that the law is trying to enforce.
     */
    @Column(nullable = false)
    @Positive
    public Integer majorCategoryCode;

    @Column(nullable = false)
    @Positive
    public ApplicationMethod applicationMethod;

    /**
     * Minor category code representing the installation that the law is trying to enforce.
     */
    @Column(nullable = false)
    @Positive
    public Integer minorCategoryCode;


    //    @Convert(converter = ClauseListConverter.class)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "law_fields_id")  // Adjust the column name as needed
    public List<Clause> clauses;

}
