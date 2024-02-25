package com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder;

import com.FireFacilAuto.domain.Conditions;
import com.FireFacilAuto.domain.entity.installation.*;
import com.FireFacilAuto.domain.entity.lawfields.BuildingLawFields;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import org.hibernate.query.sqm.ComparisonOperator;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.FireFacilAuto.util.conditions.ConditionalComparator.isActivated;

public class TestLawObjectBuilder {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

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

        if(isActivated(Blaw.GFA)) {
            Conditions condition = this.conditionBuilder(Blaw, "GFA", COList.get(0));
            conditionsList.add(condition);
        }
        if(isActivated(Blaw.dateofApproval)) {
            Conditions condition = this.conditionBuilder(Blaw, "dateofApproval",COList.get(1));
            conditionsList.add(condition);
        }
        if(isActivated(Blaw.totalFloors)) {
            Conditions condition = this.conditionBuilder(Blaw, "totalFloors", COList.get(2));
            conditionsList.add(condition);
        }
        if(isActivated(Blaw.undergroundFloors)) {
            Conditions condition = this.conditionBuilder(Blaw, "undergroundFloors", COList.get(3));
            conditionsList.add(condition);
        }
        if(isActivated(Blaw.overgroundFloors)) {
            Conditions condition = this.conditionBuilder(Blaw, "overgroundFloors", COList.get(4));
            conditionsList.add(condition);
        }
        if(isActivated(Blaw.length)) {
            Conditions condition = this.conditionBuilder(Blaw, "length", COList.get(5));
            conditionsList.add(condition);
        }


        Blaw.setConditionsList(conditionsList);

        return Blaw;
    }


    public List<FloorLawFields> randomFloorLawsBuilder(Integer numLaws) {
        List<FloorLawFields> laws = new LinkedList<>();
        int totalLaws = numLaws != null ? numLaws : random.nextInt(1,3);

        for(int i = 0 ; i< totalLaws; i++) {
            FloorLawFields Flaw = singularFloorLawBuilder(random.nextInt(-1, 13)
                    ,random.nextInt(-1, 4)
                    ,random.nextInt(1,5)
                    ,randomPositiveIntWithDefault()
                    ,randomPositiveIntWithDefault()
                    ,randomthreeWayBooleanWithDefault()
                    ,randomPositiveIntWithDefault()
                    ,randomPositiveIntWithDefault()
                    ,randomPositiveIntWithDefault()
                    ,randomthreeWayBooleanWithDefault()
                    ,List.of(new ComparisonOperator[]{
                            randomComparisonOperatorDie(), randomComparisonOperatorDie(),
                            randomComparisonOperatorDie(), randomComparisonOperatorDie(),
                            randomComparisonOperatorDie(), randomComparisonOperatorDie()})
            );
            laws.add(Flaw);
        }

        return laws;
    }

    public FloorLawFields singularFloorLawBuilder(int Classification, int Specification, int MajorCategoryCode, Integer MinorCategoryCode,
                                                        int floorNo, Boolean isUnderGround, int floorMaterial, double floorAreaThreshold,
                                                        double floorAreaSum, Boolean floorWindowAvailability, List<ComparisonOperator> COList) {
        FloorLawFields Flaw = new FloorLawFields();
        List<Conditions> conditionsList = new LinkedList<>();
        // must include
        Flaw.setFloorClassification(Classification);
        Flaw.setFloorSpecification(Specification);
        Flaw.setMajorCategoryCode(MajorCategoryCode);
        if(MinorCategoryCode == null) {
            switch (Flaw.majorCategoryCode) {
                case 1 -> Flaw.setMinorCategoryCode(random.nextInt(1, ExtinguisherInstallation.getTotalFieldSize()));
                case 2 -> Flaw.setMinorCategoryCode(random.nextInt(1, AlarmDeviceInstallation.getTotalFieldSize()));
                case 3 -> Flaw.setMinorCategoryCode(random.nextInt(1, FireServiceSupportDeviceInstallation.getTotalFieldSize()));
                case 4 -> Flaw.setMinorCategoryCode(random.nextInt(1, WaterSupplyInstallation.getTotalFieldSize()));
                case 5 -> Flaw.setMinorCategoryCode(random.nextInt(1, EscapeRescueInstallation.getTotalFieldSize()));
                // Add more cases as needed
                default -> throw new UnsupportedOperationException("majority code somehow got fked up");
            }
        } else {
            Flaw.setMinorCategoryCode(MinorCategoryCode);
        }
        //optional
        Flaw.setFloorNo(floorNo);
        Flaw.setIsUnderGround(isUnderGround);
        Flaw.setFloorMaterial(floorMaterial);
        Flaw.setFloorAreaThreshold(floorAreaThreshold);
        Flaw.setFloorAreaSum(floorAreaSum);
        Flaw.setFloorWindowAvailability(floorWindowAvailability);

        if(isActivated(Flaw.floorNo)) {
            Conditions condition = this.conditionBuilder(Flaw, "floorNo", COList.get(0));
            conditionsList.add(condition);
        }
        if(isActivated(Flaw.isUnderGround)) {
            Conditions condition = this.conditionBuilder(Flaw, "isUnderGround", COList.get(1));
            conditionsList.add(condition);
        }
        if(isActivated(Flaw.floorMaterial)) {
            Conditions condition = this.conditionBuilder(Flaw, "floorMaterial", COList.get(2));
            conditionsList.add(condition);
        }
        if(isActivated(Flaw.floorAreaThreshold)) {
            Conditions condition = this.conditionBuilder(Flaw, "floorAreaThreshold", COList.get(3));
            conditionsList.add(condition);
        }
        if(isActivated(Flaw.floorAreaSum)) {
            Conditions condition = this.conditionBuilder(Flaw, "floorAreaSum", COList.get(4));
            conditionsList.add(condition);
        }
        if(isActivated(Flaw.floorWindowAvailability)) {
            Conditions condition = this.conditionBuilder(Flaw, "floorWindowAvailability", COList.get(5));
            conditionsList.add(condition);
        }
        Flaw.setConditionsList(conditionsList);

        return Flaw;
    }

    private int randomPositiveIntWithDefault() {
        return random.nextBoolean() ?
                ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE) :
                -1;
    }

    private Boolean randomthreeWayBooleanWithDefault() {
        return random.nextInt(1,3) == 1 ?
                ThreadLocalRandom.current().nextBoolean():
                null;
    }

    private double randomPositiveDoubleWithDefault() {
        return random.nextBoolean() ?
                ThreadLocalRandom.current().nextDouble(1, Double.MAX_VALUE) :
                -1;
    }

    private LocalDate randomLocalDateWithDefault() {
        return random.nextBoolean() ?
                LocalDate.of(ThreadLocalRandom.current().nextInt(2000, 2023), 1, 1) :
                LocalDate.now();
    }
    private ComparisonOperator randomComparisonOperatorDie () {
        int dice = random.nextInt(1, 7);  // Corrected to include 6
        ComparisonOperator CO;

        switch (dice) {
            case 1 -> CO = ComparisonOperator.GREATER_THAN_OR_EQUAL;
            case 2 -> CO = ComparisonOperator.GREATER_THAN;
            case 3 -> CO = ComparisonOperator.LESS_THAN_OR_EQUAL;
            case 4 -> CO = ComparisonOperator.LESS_THAN;
            case 5 -> CO = ComparisonOperator.EQUAL;
            case 6 -> CO = ComparisonOperator.NOT_EQUAL;
            default -> throw new UnsupportedOperationException("Something went wrong with the comparison operators");
        }
        return CO;
    }

    private Conditions conditionBuilder (BuildingLawFields blf, String field, ComparisonOperator co) {
        Conditions condition = new Conditions();
        condition.setBuildingLawFields(blf);
        condition.setFieldName(field);
        condition.setOperator(co);
        return condition;
    }

    private Conditions conditionBuilder (FloorLawFields flf, String field, ComparisonOperator co) {
        Conditions condition = new Conditions();
        condition.setFloorLawFields(flf);
        condition.setFieldName(field);
        condition.setOperator(co);
        return condition;
    }

}
