package com.FireFacilAuto.util;


import com.FireFacilAuto.domain.configImport.Classification;
import com.FireFacilAuto.domain.configImport.ClassificationList;
import com.FireFacilAuto.domain.configImport.Specification;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Data
@Service
@Slf4j
public class CO {
    private Integer field1;
    private Integer field2;
    private ClassificationList classificationList;

    @Autowired
    public CO (ClassificationList classificationList) {
        this.classificationList = classificationList;
    }

    private CO(int field1, int field2) {
        this.field1 = field1;
        this.field2 = field2;
        validate();
    }


    public static CO make(Integer field1, Integer field2) {
        return new CO(field1, field2);
    }

    public static CO def(Integer field1) {
        return new CO(field1 , -1);
    }

    private void validate() {
        if (field1 != null && field2 != null) {
            boolean isValid = classificationList.getClassifications().stream()
                    .anyMatch(classification ->
                            classification.getNumber().equals(field1) && ( field2 == -1 ||
                                    classification.getSpecifications().stream()
                                            .anyMatch(specification -> specification.getNumber().equals(field2)))
                    );

            if (!isValid) {
                throw new IllegalArgumentException("Invalid CO instance: " + this.toString());
            }
            if(field2 == -1) {
                log.info("CO is {}", classificationList.getClassifications().get(field1 -1).name);
            } else {
                log.info("CO is {}, {}", classificationList.getClassifications().get(field1 - 1).name, classificationList.getClassifications().get(field1 - 1).getSpecifications().get(field2 - 1).name);
            }

        }
    }

    public CO nameMake(String name1, String name2) {
        Classification cat = classificationList.getClassifications().stream().filter(obj -> obj.name.equals(name1)).findFirst().orElseThrow();
        if (name2 != null) {
            Specification spec = cat.getSpecifications().stream().filter(obj -> obj.name.equals(name2)).findFirst().orElseThrow();
            return make(cat.number, spec.number);
        }
        return make(cat.number, -1);

    }

    public String identifyAsString (CO obj) {
        Classification c = classificationList.getClassifications().get(obj.field1 -1);
        Specification s = c.getSpecifications().get(obj.field2 -1);

        StringBuilder sb = new StringBuilder(c.name);
        sb.append("   ");
        sb.append(s.name);

        return sb.toString();
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
