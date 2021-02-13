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
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the {@link com.access.myapp.domain.JobLine} entity. This class is used
 * in {@link com.access.myapp.web.rest.JobLineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /job-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JobLineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter material;

    private IntegerFilter quantity;

    private BigDecimalFilter unitCost;

    private LongFilter jobId;

    public JobLineCriteria() {
    }

    public JobLineCriteria(JobLineCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.material = other.material == null ? null : other.material.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.unitCost = other.unitCost == null ? null : other.unitCost.copy();
        this.jobId = other.jobId == null ? null : other.jobId.copy();
    }

    @Override
    public JobLineCriteria copy() {
        return new JobLineCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMaterial() {
        return material;
    }

    public void setMaterial(StringFilter material) {
        this.material = material;
    }

    public IntegerFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(IntegerFilter quantity) {
        this.quantity = quantity;
    }

    public BigDecimalFilter getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimalFilter unitCost) {
        this.unitCost = unitCost;
    }

    public LongFilter getJobId() {
        return jobId;
    }

    public void setJobId(LongFilter jobId) {
        this.jobId = jobId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JobLineCriteria that = (JobLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(material, that.material) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(unitCost, that.unitCost) &&
            Objects.equals(jobId, that.jobId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        material,
        quantity,
        unitCost,
        jobId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (material != null ? "material=" + material + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (unitCost != null ? "unitCost=" + unitCost + ", " : "") +
                (jobId != null ? "jobId=" + jobId + ", " : "") +
            "}";
    }

}
