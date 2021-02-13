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
 * Criteria class for the {@link com.access.myapp.domain.PtmAccount} entity. This class is used
 * in {@link com.access.myapp.web.rest.PtmAccountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ptm-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PtmAccountCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter colour;

    private LongFilter clientsId;

    public PtmAccountCriteria() {
    }

    public PtmAccountCriteria(PtmAccountCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.colour = other.colour == null ? null : other.colour.copy();
        this.clientsId = other.clientsId == null ? null : other.clientsId.copy();
    }

    @Override
    public PtmAccountCriteria copy() {
        return new PtmAccountCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getColour() {
        return colour;
    }

    public void setColour(StringFilter colour) {
        this.colour = colour;
    }

    public LongFilter getClientsId() {
        return clientsId;
    }

    public void setClientsId(LongFilter clientsId) {
        this.clientsId = clientsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PtmAccountCriteria that = (PtmAccountCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(colour, that.colour) &&
            Objects.equals(clientsId, that.clientsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        colour,
        clientsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PtmAccountCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (colour != null ? "colour=" + colour + ", " : "") +
                (clientsId != null ? "clientsId=" + clientsId + ", " : "") +
            "}";
    }

}
