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
 * Criteria class for the {@link com.access.myapp.domain.Position} entity. This class is used
 * in {@link com.access.myapp.web.rest.PositionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /positions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PositionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter value;

    private LongFilter numberId;

    public PositionCriteria() {
    }

    public PositionCriteria(PositionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.numberId = other.numberId == null ? null : other.numberId.copy();
    }

    @Override
    public PositionCriteria copy() {
        return new PositionCriteria(this);
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

    public LongFilter getNumberId() {
        return numberId;
    }

    public void setNumberId(LongFilter numberId) {
        this.numberId = numberId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PositionCriteria that = (PositionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(value, that.value) &&
            Objects.equals(numberId, that.numberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        value,
        numberId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PositionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (value != null ? "value=" + value + ", " : "") +
                (numberId != null ? "numberId=" + numberId + ", " : "") +
            "}";
    }

}
