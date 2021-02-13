package com.access.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.access.myapp.domain.Town} entity.
 */
public class TownDTO implements Serializable {
    
    private Long id;

    private String value;


    private Long countyId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TownDTO)) {
            return false;
        }

        return id != null && id.equals(((TownDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TownDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", countyId=" + getCountyId() +
            "}";
    }
}
