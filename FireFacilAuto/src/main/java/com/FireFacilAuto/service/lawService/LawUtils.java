package com.FireFacilAuto.service.lawService;

import com.FireFacilAuto.domain.entity.lawfields.clause.Clause;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class LawUtils {
    public static <T extends Comparable<T>> void BooleanExpressionEvaluator(List<Clause<T>> clauses) {
        clauses.sort(Comparator.comparingInt(Clause::getPriority));
    }
}
