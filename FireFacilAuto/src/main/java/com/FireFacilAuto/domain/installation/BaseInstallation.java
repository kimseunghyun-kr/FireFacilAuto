package com.FireFacilAuto.domain.installation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class BaseInstallation {
    @Id
    public Long UUID;
}
