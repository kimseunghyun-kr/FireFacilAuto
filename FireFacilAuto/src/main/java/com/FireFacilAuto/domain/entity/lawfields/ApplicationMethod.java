package com.FireFacilAuto.domain.entity.lawfields;

import jakarta.persistence.Embeddable;

@Embeddable
public enum ApplicationMethod {
    APPLYONALLFLOORS,
    EXCLUDE,
    APPLYONTARGETFLOOR,
    APPLYONINTERSECTIONSURVIVED

}
