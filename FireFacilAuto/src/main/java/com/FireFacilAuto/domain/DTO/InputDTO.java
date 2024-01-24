package com.FireFacilAuto.domain.DTO;

import com.FireFacilAuto.domain.entity.Address;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class InputDTO {

    public Address juso;

    public Integer floor;

    public Long GFA;

    public Integer classification;

    public LocalDateTime dateOfConstruction;
}
