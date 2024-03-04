package com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Law;

import com.FireFacilAuto.domain.Conditions;
import com.FireFacilAuto.domain.entity.installation.*;
import com.FireFacilAuto.domain.entity.lawfields.buildingLaw.BuildingLawFields;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.sqm.ComparisonOperator;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.LawObjectBuilderUtils.*;
import static com.FireFacilAuto.util.conditions.ConditionalComparator.isActivated;

@Slf4j
public class TestBuildingLawObjectBuilder {
    public List<BuildingLawFields> randomBuildingLawsBuilder(Integer numLaws) {
        List<BuildingLawFields> laws = new LinkedList<>();
        int totalLaws = numLaws != null ? numLaws : random.nextInt(1,3);

        for(int i = 0 ; i< totalLaws; i++) {
            BuildingLawFields Blaw = singularBuildingLawBuilder(random.nextInt(-1, 13)
                    ,random.nextInt(-1, 4)
                    ,random.nextInt(1,5)
                    ,randomPositiveIntWithDefault()
                    ,randomPositiveDoubleWithDefault()
                    ,randomLocalDateWithDefault()
                    ,randomPositiveIntWithDefault()
                    ,randomPositiveIntWithDefault()
                    ,randomPositiveIntWithDefault()
                    ,randomPositiveIntWithDefault()
                    ,List.of(new ComparisonOperator[]{
                            randomComparisonOperatorDie(), randomComparisonOperatorDie(),
                            randomComparisonOperatorDie(), randomComparisonOperatorDie(),
                            randomComparisonOperatorDie(), randomComparisonOperatorDie()})
            );
            laws.add(Blaw);
        }

        return laws;
    }

    public BuildingLawFields singularBuildingLawBuilder(int Classification, int Specification, int MajorCategoryCode, Integer MinorCategoryCode,
                                                        double GFA, LocalDate localDate, int totalFloors, int underGroundFloors,
                                                        int overgroundFloors, double length, List<ComparisonOperator> COList) {
        BuildingLawFields Blaw = new BuildingLawFields();
        List<Conditions> conditionsList = new LinkedList<>();

        // must include n
        Blaw.setBuildingClassification(Classification);
        Blaw.setBuildingSpecification(Specification);
        Blaw.setMajorCategoryCode(MajorCategoryCode);
        if(MinorCategoryCode == null) {
            switch (Blaw.majorCategoryCode) {
                case 1 -> Blaw.setMinorCategoryCode(random.nextInt(1, ExtinguisherInstallation.getTotalFieldSize()));
                case 2 -> Blaw.setMinorCategoryCode(random.nextInt(1, AlarmDeviceInstallation.getTotalFieldSize()));
                case 3 -> Blaw.setMinorCategoryCode(random.nextInt(1, FireServiceSupportDeviceInstallation.getTotalFieldSize()));
                case 4 -> Blaw.setMinorCategoryCode(random.nextInt(1, WaterSupplyInstallation.getTotalFieldSize()));
                case 5 -> Blaw.setMinorCategoryCode(random.nextInt(1, EscapeRescueInstallation.getTotalFieldSize()));
                // Add more cases as needed
                default -> throw new UnsupportedOperationException("majority code somehow got fked up");
            }
        } else {
            Blaw.setMinorCategoryCode(MinorCategoryCode);
        }

        //optional
        Blaw.setGFA(GFA);
        Blaw.setDateofApproval(localDate);
        Blaw.setTotalFloors(totalFloors);
        Blaw.setUndergroundFloors(underGroundFloors);
        Blaw.setOvergroundFloors(overgroundFloors);
        Blaw.setLength(length);

        if( COList != null &&  !COList.isEmpty()) {
            setConditionsListFromComparisonOperatorList(COList, Blaw, conditionsList);
        }

        return Blaw;
    }

    private void setConditionsListFromComparisonOperatorList(List<ComparisonOperator> COList, BuildingLawFields Blaw, List<Conditions> conditionsList) {
        if(isActivated(Blaw.GFA)) {
            log.info("test law GFA {}", Blaw.GFA);
            Conditions condition = this.conditionBuilder(Blaw, "GFA", COList.get(0));
            conditionsList.add(condition);
        }
        if(isActivated(Blaw.dateofApproval)) {
            log.info("test law dateofApproval {}", Blaw.dateofApproval);
            Conditions condition = this.conditionBuilder(Blaw, "dateofApproval", COList.get(1));
            conditionsList.add(condition);
        }
        if(isActivated(Blaw.totalFloors)) {
            log.info("test law totalFloors {}", Blaw.totalFloors);
            Conditions condition = this.conditionBuilder(Blaw, "totalFloors", COList.get(2));
            conditionsList.add(condition);
        }
        if(isActivated(Blaw.undergroundFloors)) {
            log.info("test law undergroundFloors {}", Blaw.undergroundFloors);
            Conditions condition = this.conditionBuilder(Blaw, "undergroundFloors", COList.get(3));
            conditionsList.add(condition);
        }
        if(isActivated(Blaw.overgroundFloors)) {
            log.info("test law overgroundFloors {}", Blaw.overgroundFloors);
            Conditions condition = this.conditionBuilder(Blaw, "overgroundFloors", COList.get(4));
            conditionsList.add(condition);
        }
        if(isActivated(Blaw.length)) {
            log.info("test law length {}", Blaw.length);
            Conditions condition = this.conditionBuilder(Blaw, "length", COList.get(5));
            conditionsList.add(condition);
        }


        Blaw.setConditionsList(conditionsList);
    }

    public Conditions conditionBuilder (BuildingLawFields blf, String field, ComparisonOperator co) {
        Conditions condition = new Conditions();
        condition.setBuildingLawFields(blf);
        condition.setFieldName(field);
        condition.setOperator(co);
        return condition;
    }



}
