package com.FireFacilAuto.domain.entity.installation;

import jakarta.persistence.*;
import lombok.Data;


/**
 * A base class for installations, providing common functionality and methods.
 */
@Data
@MappedSuperclass
public abstract class BaseInstallation {

    /**
     * Abstract method to be implemented by subclasses for setting boolean values.
     *
     * @param number The field to target.
     */
    public abstract void setBooleanValue(int number);

    /**
     * Gets the total number of fields in the implementing subclass.
     *
     * @return The total number of fields.
     */
    public static Integer getTotalFieldSize() {
        // Provide a default implementation or throw an exception if needed
        throw new UnsupportedOperationException("Not implemented");
    }
}
