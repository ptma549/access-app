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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.access.myapp.domain.JobVisit} entity. This class is used
 * in {@link com.access.myapp.web.rest.JobVisitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /job-visits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JobVisitCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter arrived;

    private ZonedDateTimeFilter departed;

    private BigDecimalFilter charge;

    private StringFilter workCarriedOut;

    private LongFilter jobId;

    public JobVisitCriteria() {
    }

    public JobVisitCriteria(JobVisitCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.arrived = other.arrived == null ? null : other.arrived.copy();
        this.departed = other.departed == null ? null : other.departed.copy();
        this.charge = other.charge == null ? null : other.charge.copy();
        this.workCarriedOut = other.workCarriedOut == null ? null : other.workCarriedOut.copy();
        this.jobId = other.jobId == null ? null : other.jobId.copy();
    }

    @Override
    public JobVisitCriteria copy() {
        return new JobVisitCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getArrived() {
        return arrived;
    }

    public void setArrived(ZonedDateTimeFilter arrived) {
        this.arrived = arrived;
    }

    public ZonedDateTimeFilter getDeparted() {
        return departed;
    }

    public void setDeparted(ZonedDateTimeFilter departed) {
        this.departed = departed;
    }

    public BigDecimalFilter getCharge() {
        return charge;
    }

    public void setCharge(BigDecimalFilter charge) {
        this.charge = charge;
    }

    public StringFilter getWorkCarriedOut() {
        return workCarriedOut;
    }

    public void setWorkCarriedOut(StringFilter workCarriedOut) {
        this.workCarriedOut = workCarriedOut;
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
        final JobVisitCriteria that = (JobVisitCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(arrived, that.arrived) &&
            Objects.equals(departed, that.departed) &&
            Objects.equals(charge, that.charge) &&
            Objects.equals(workCarriedOut, that.workCarriedOut) &&
            Objects.equals(jobId, that.jobId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        arrived,
        departed,
        charge,
        workCarriedOut,
        jobId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobVisitCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (arrived != null ? "arrived=" + arrived + ", " : "") +
                (departed != null ? "departed=" + departed + ", " : "") +
                (charge != null ? "charge=" + charge + ", " : "") +
                (workCarriedOut != null ? "workCarriedOut=" + workCarriedOut + ", " : "") +
                (jobId != null ? "jobId=" + jobId + ", " : "") +
            "}";
    }

}
