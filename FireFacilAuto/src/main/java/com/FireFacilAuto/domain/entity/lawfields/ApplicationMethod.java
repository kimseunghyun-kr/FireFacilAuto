package com.FireFacilAuto.domain.entity.lawfields;

import jakarta.persistence.Embeddable;

@Embeddable
public enum ApplicationMethod {
    ALL,
    SINGLE,
    APPLICABLEONLY;
}
