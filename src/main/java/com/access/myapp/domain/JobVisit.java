package com.access.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * A JobVisit.
 */
@Entity
@Table(name = "job_visit")
public class JobVisit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "arrived")
    private ZonedDateTime arrived;

    @Column(name = "departed")
    private ZonedDateTime departed;

    @Column(name = "charge", precision = 21, scale = 2)
    private BigDecimal charge;

    @Column(name = "work_carried_out")
    private String workCarriedOut;

    @ManyToOne
    @JsonIgnoreProperties(value = "visits", allowSetters = true)
    private Job job;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getArrived() {
        return arrived;
    }

    public JobVisit arrived(ZonedDateTime arrived) {
        this.arrived = arrived;
        return this;
    }

    public void setArrived(ZonedDateTime arrived) {
        this.arrived = arrived;
    }

    public ZonedDateTime getDeparted() {
        return departed;
    }

    public JobVisit departed(ZonedDateTime departed) {
        this.departed = departed;
        return this;
    }

    public void setDeparted(ZonedDateTime departed) {
        this.departed = departed;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public JobVisit charge(BigDecimal charge) {
        this.charge = charge;
        return this;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public String getWorkCarriedOut() {
        return workCarriedOut;
    }

    public JobVisit workCarriedOut(String workCarriedOut) {
        this.workCarriedOut = workCarriedOut;
        return this;
    }

    public void setWorkCarriedOut(String workCarriedOut) {
        this.workCarriedOut = workCarriedOut;
    }

    public Job getJob() {
        return job;
    }

    public JobVisit job(Job job) {
        this.job = job;
        return this;
    }

    public void setJob(Job job) {
        this.job = job;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobVisit)) {
            return false;
        }
        return id != null && id.equals(((JobVisit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobVisit{" +
            "id=" + getId() +
            ", arrived='" + getArrived() + "'" +
            ", departed='" + getDeparted() + "'" +
            ", charge=" + getCharge() +
            ", workCarriedOut='" + getWorkCarriedOut() + "'" +
            "}";
    }
}
