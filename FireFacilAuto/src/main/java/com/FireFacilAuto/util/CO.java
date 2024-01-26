package com.FireFacilAuto.util;


import lombok.Data;

import java.util.Objects;

@Data
public class CO {
    private Integer field1;
    private Integer field2;

    private CO(int field1, int field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public static CO make(Integer field1, Integer field2) {
        return new CO(field1, field2);
    }

    public static CO def(Integer field1) {
        return new CO(field1 , -1);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        CO other = (CO) obj;

        if (field2 == -1 || other.field2 == -1) {
            return field1.equals(other.field1);
        }

        return field1.equals(other.field1) && field2.equals(other.field2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field1, field2);
    }

}
