package com.access.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.access.myapp.domain.Position} entity.
 */
public class PositionDTO implements Serializable {
    
    private Long id;

    private String value;


    private Long numberId;
    
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

    public Long getNumberId() {
        return numberId;
    }

    public void setNumberId(Long numberId) {
        this.numberId = numberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PositionDTO)) {
            return false;
        }

        return id != null && id.equals(((PositionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PositionDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", numberId=" + getNumberId() +
            "}";
    }
}
