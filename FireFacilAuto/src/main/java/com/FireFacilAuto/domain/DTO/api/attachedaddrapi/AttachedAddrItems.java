package com.FireFacilAuto.domain.DTO.api.attachedaddrapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonDeserialize(using = AttachedAddrItemsDeserializer.class)
@NoArgsConstructor
public class AttachedAddrItems {

    public List<AttachedAddrResponseItem> item;

    public AttachedAddrItems(List<AttachedAddrResponseItem> item) {
        this.item = item;
    }
}
