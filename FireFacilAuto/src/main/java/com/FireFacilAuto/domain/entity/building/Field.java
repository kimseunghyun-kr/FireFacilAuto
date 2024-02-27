package com.FireFacilAuto.domain.entity.building;

import jakarta.persistence.Embeddable;
import lombok.Data;

import lombok.extern.slf4j.Slf4j;

@Embeddable
@Slf4j
public record Field<T>(String fieldName, T value) {
}
