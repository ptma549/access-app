package com.access.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.access.myapp.domain.Street} entity.
 */
public class StreetDTO implements Serializable {
    
    private Long id;

    private String value;


    private Long townId;
    
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

    public Long getTownId() {
        return townId;
    }

    public void setTownId(Long townId) {
        this.townId = townId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StreetDTO)) {
            return false;
        }

        return id != null && id.equals(((StreetDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StreetDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", townId=" + getTownId() +
            "}";
    }
}
