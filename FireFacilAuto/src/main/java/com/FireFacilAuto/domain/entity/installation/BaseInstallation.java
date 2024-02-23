package com.FireFacilAuto.domain.entity.installation;

import com.FireFacilAuto.domain.entity.building.Floor;
import jakarta.persistence.*;
import lombok.Data;


@Data
@MappedSuperclass
public abstract class BaseInstallation {
    public abstract void setBooleanValue(int number);
}
