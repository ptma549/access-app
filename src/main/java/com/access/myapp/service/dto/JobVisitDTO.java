package com.access.myapp.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.access.myapp.domain.JobVisit} entity.
 */
public class JobVisitDTO implements Serializable {
    
    private Long id;

    private ZonedDateTime arrived;

    private ZonedDateTime departed;

    private BigDecimal charge;

    private String workCarriedOut;


    private Long jobId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getArrived() {
        return arrived;
    }

    public void setArrived(ZonedDateTime arrived) {
        this.arrived = arrived;
    }

    public ZonedDateTime getDeparted() {
        return departed;
    }

    public void setDeparted(ZonedDateTime departed) {
        this.departed = departed;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public String getWorkCarriedOut() {
        return workCarriedOut;
    }

    public void setWorkCarriedOut(String workCarriedOut) {
        this.workCarriedOut = workCarriedOut;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobVisitDTO)) {
            return false;
        }

        return id != null && id.equals(((JobVisitDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobVisitDTO{" +
            "id=" + getId() +
            ", arrived='" + getArrived() + "'" +
            ", departed='" + getDeparted() + "'" +
            ", charge=" + getCharge() +
            ", workCarriedOut='" + getWorkCarriedOut() + "'" +
            ", jobId=" + getJobId() +
            "}";
    }
}
