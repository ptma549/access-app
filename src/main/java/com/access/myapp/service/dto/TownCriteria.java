package com.access.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.access.myapp.domain.Town} entity. This class is used
 * in {@link com.access.myapp.web.rest.TownResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /towns?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TownCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter value;

    private LongFilter streetsId;

    private LongFilter countyId;

    public TownCriteria() {
    }

    public TownCriteria(TownCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.streetsId = other.streetsId == null ? null : other.streetsId.copy();
        this.countyId = other.countyId == null ? null : other.countyId.copy();
    }

    @Override
    public TownCriteria copy() {
        return new TownCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }

    public LongFilter getStreetsId() {
        return streetsId;
    }

    public void setStreetsId(LongFilter streetsId) {
        this.streetsId = streetsId;
    }

    public LongFilter getCountyId() {
        return countyId;
    }

    public void setCountyId(LongFilter countyId) {
        this.countyId = countyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TownCriteria that = (TownCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(value, that.value) &&
            Objects.equals(streetsId, that.streetsId) &&
            Objects.equals(countyId, that.countyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        value,
        streetsId,
        countyId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TownCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (streetsId != null ? "streetsId=" + streetsId + ", " : "") +
                (countyId != null ? "countyId=" + countyId + ", " : "") +
            "}";
    }

}
