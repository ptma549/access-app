package com.access.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.access.myapp.domain.Number} entity.
 */
public class NumberDTO implements Serializable {
    
    private Long id;

    private String value;

    private String building;

    private String postcode;


    private Long streetId;
    
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

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumberDTO)) {
            return false;
        }

        return id != null && id.equals(((NumberDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NumberDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", building='" + getBuilding() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", streetId=" + getStreetId() +
            "}";
    }
}
