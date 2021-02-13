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
 * Criteria class for the {@link com.access.myapp.domain.Street} entity. This class is used
 * in {@link com.access.myapp.web.rest.StreetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /streets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StreetCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter value;

    private LongFilter numbersId;

    private LongFilter townId;

    public StreetCriteria() {
    }

    public StreetCriteria(StreetCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.numbersId = other.numbersId == null ? null : other.numbersId.copy();
        this.townId = other.townId == null ? null : other.townId.copy();
    }

    @Override
    public StreetCriteria copy() {
        return new StreetCriteria(this);
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

    public LongFilter getNumbersId() {
        return numbersId;
    }

    public void setNumbersId(LongFilter numbersId) {
        this.numbersId = numbersId;
    }

    public LongFilter getTownId() {
        return townId;
    }

    public void setTownId(LongFilter townId) {
        this.townId = townId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StreetCriteria that = (StreetCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(value, that.value) &&
            Objects.equals(numbersId, that.numbersId) &&
            Objects.equals(townId, that.townId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        value,
        numbersId,
        townId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StreetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (numbersId != null ? "numbersId=" + numbersId + ", " : "") +
                (townId != null ? "townId=" + townId + ", " : "") +
            "}";
    }

}
