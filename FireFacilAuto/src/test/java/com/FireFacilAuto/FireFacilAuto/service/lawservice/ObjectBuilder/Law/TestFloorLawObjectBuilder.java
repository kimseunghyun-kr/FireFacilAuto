package com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Law;

import com.FireFacilAuto.domain.Conditions;
import com.FireFacilAuto.domain.entity.installation.*;
import com.FireFacilAuto.domain.entity.lawfields.FloorLawFields;
import org.hibernate.query.sqm.ComparisonOperator;

import java.util.LinkedList;
import java.util.List;

import static com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Law.LawObjectBuilderUtils.*;
import static com.FireFacilAuto.util.conditions.ConditionalComparator.isActivated;

public class TestFloorLawObjectBuilder {
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

        if( COList != null &&  !COList.isEmpty()) {
            setConditionsListFromComparisonOperatorList(COList, Flaw, conditionsList);
        }
        return Flaw;
    }

    private void setConditionsListFromComparisonOperatorList(List<ComparisonOperator> COList, FloorLawFields Flaw, List<Conditions> conditionsList) {
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
    }

    public Conditions conditionBuilder (FloorLawFields flf, String field, ComparisonOperator co) {
        Conditions condition = new Conditions();
        condition.setFloorLawFields(flf);
        condition.setFieldName(field);
        condition.setOperator(co);
        return condition;
    }
}
