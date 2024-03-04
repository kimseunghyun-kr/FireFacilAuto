package com.FireFacilAuto.FireFacilAuto.service.lawservice.ObjectBuilder.Building;

import org.hibernate.query.sqm.ComparisonOperator;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class LawObjectBuilderUtils {
    public static final ThreadLocalRandom random = ThreadLocalRandom.current();

    public static int randomPositiveIntWithDefault() {
        return random.nextBoolean() ?
                ThreadLocalRandom.current().nextInt(1, 50) :
                -1;
    }

    public static Boolean randomthreeWayBooleanWithDefault() {
        return random.nextInt(1,3) == 1 ?
                ThreadLocalRandom.current().nextBoolean():
                null;
    }

    public static double randomPositiveDoubleWithDefault() {
        return random.nextBoolean() ?
                ThreadLocalRandom.current().nextDouble(1, 10000.0) :
                -1;
    }

    public static LocalDate randomLocalDateWithDefault() {
        return random.nextBoolean() ?
                LocalDate.of(ThreadLocalRandom.current().nextInt(2000, 2023), 1, 1) :
                LocalDate.now();
    }
    public static ComparisonOperator randomComparisonOperatorDie () {
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


}
