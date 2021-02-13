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
 * Criteria class for the {@link com.access.myapp.domain.Number} entity. This class is used
 * in {@link com.access.myapp.web.rest.NumberResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /numbers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NumberCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter value;

    private StringFilter building;

    private StringFilter postcode;

    private LongFilter positionsId;

    private LongFilter streetId;

    public NumberCriteria() {
    }

    public NumberCriteria(NumberCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.building = other.building == null ? null : other.building.copy();
        this.postcode = other.postcode == null ? null : other.postcode.copy();
        this.positionsId = other.positionsId == null ? null : other.positionsId.copy();
        this.streetId = other.streetId == null ? null : other.streetId.copy();
    }

    @Override
    public NumberCriteria copy() {
        return new NumberCriteria(this);
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

    public StringFilter getBuilding() {
        return building;
    }

    public void setBuilding(StringFilter building) {
        this.building = building;
    }

    public StringFilter getPostcode() {
        return postcode;
    }

    public void setPostcode(StringFilter postcode) {
        this.postcode = postcode;
    }

    public LongFilter getPositionsId() {
        return positionsId;
    }

    public void setPositionsId(LongFilter positionsId) {
        this.positionsId = positionsId;
    }

    public LongFilter getStreetId() {
        return streetId;
    }

    public void setStreetId(LongFilter streetId) {
        this.streetId = streetId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NumberCriteria that = (NumberCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(value, that.value) &&
            Objects.equals(building, that.building) &&
            Objects.equals(postcode, that.postcode) &&
            Objects.equals(positionsId, that.positionsId) &&
            Objects.equals(streetId, that.streetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        value,
        building,
        postcode,
        positionsId,
        streetId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NumberCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (building != null ? "building=" + building + ", " : "") +
                (postcode != null ? "postcode=" + postcode + ", " : "") +
                (positionsId != null ? "positionsId=" + positionsId + ", " : "") +
                (streetId != null ? "streetId=" + streetId + ", " : "") +
            "}";
    }

}
