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
 * Criteria class for the {@link com.access.myapp.domain.Job} entity. This class is used
 * in {@link com.access.myapp.web.rest.JobResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /jobs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JobCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter reportedBy;

    private StringFilter clientOrderRef;

    private StringFilter raiseDate;

    private StringFilter priority;

    private StringFilter fault;

    private StringFilter instructions;

    private StringFilter occupier;

    private StringFilter homeTel;

    private StringFilter workTel;

    private StringFilter mobileTel;

    private StringFilter tenantName;

    private StringFilter complete;

    private StringFilter position;

    private StringFilter invoice;

    private StringFilter invoiceDetails;

    private LongFilter commentsId;

    private LongFilter linesId;

    private LongFilter visitsId;

    private LongFilter clientId;

    public JobCriteria() {
    }

    public JobCriteria(JobCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.reportedBy = other.reportedBy == null ? null : other.reportedBy.copy();
        this.clientOrderRef = other.clientOrderRef == null ? null : other.clientOrderRef.copy();
        this.raiseDate = other.raiseDate == null ? null : other.raiseDate.copy();
        this.priority = other.priority == null ? null : other.priority.copy();
        this.fault = other.fault == null ? null : other.fault.copy();
        this.instructions = other.instructions == null ? null : other.instructions.copy();
        this.occupier = other.occupier == null ? null : other.occupier.copy();
        this.homeTel = other.homeTel == null ? null : other.homeTel.copy();
        this.workTel = other.workTel == null ? null : other.workTel.copy();
        this.mobileTel = other.mobileTel == null ? null : other.mobileTel.copy();
        this.tenantName = other.tenantName == null ? null : other.tenantName.copy();
        this.complete = other.complete == null ? null : other.complete.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.invoice = other.invoice == null ? null : other.invoice.copy();
        this.invoiceDetails = other.invoiceDetails == null ? null : other.invoiceDetails.copy();
        this.commentsId = other.commentsId == null ? null : other.commentsId.copy();
        this.linesId = other.linesId == null ? null : other.linesId.copy();
        this.visitsId = other.visitsId == null ? null : other.visitsId.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
    }

    @Override
    public JobCriteria copy() {
        return new JobCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(StringFilter reportedBy) {
        this.reportedBy = reportedBy;
    }

    public StringFilter getClientOrderRef() {
        return clientOrderRef;
    }

    public void setClientOrderRef(StringFilter clientOrderRef) {
        this.clientOrderRef = clientOrderRef;
    }

    public StringFilter getRaiseDate() {
        return raiseDate;
    }

    public void setRaiseDate(StringFilter raiseDate) {
        this.raiseDate = raiseDate;
    }

    public StringFilter getPriority() {
        return priority;
    }

    public void setPriority(StringFilter priority) {
        this.priority = priority;
    }

    public StringFilter getFault() {
        return fault;
    }

    public void setFault(StringFilter fault) {
        this.fault = fault;
    }

    public StringFilter getInstructions() {
        return instructions;
    }

    public void setInstructions(StringFilter instructions) {
        this.instructions = instructions;
    }

    public StringFilter getOccupier() {
        return occupier;
    }

    public void setOccupier(StringFilter occupier) {
        this.occupier = occupier;
    }

    public StringFilter getHomeTel() {
        return homeTel;
    }

    public void setHomeTel(StringFilter homeTel) {
        this.homeTel = homeTel;
    }

    public StringFilter getWorkTel() {
        return workTel;
    }

    public void setWorkTel(StringFilter workTel) {
        this.workTel = workTel;
    }

    public StringFilter getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(StringFilter mobileTel) {
        this.mobileTel = mobileTel;
    }

    public StringFilter getTenantName() {
        return tenantName;
    }

    public void setTenantName(StringFilter tenantName) {
        this.tenantName = tenantName;
    }

    public StringFilter getComplete() {
        return complete;
    }

    public void setComplete(StringFilter complete) {
        this.complete = complete;
    }

    public StringFilter getPosition() {
        return position;
    }

    public void setPosition(StringFilter position) {
        this.position = position;
    }

    public StringFilter getInvoice() {
        return invoice;
    }

    public void setInvoice(StringFilter invoice) {
        this.invoice = invoice;
    }

    public StringFilter getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(StringFilter invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public LongFilter getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(LongFilter commentsId) {
        this.commentsId = commentsId;
    }

    public LongFilter getLinesId() {
        return linesId;
    }

    public void setLinesId(LongFilter linesId) {
        this.linesId = linesId;
    }

    public LongFilter getVisitsId() {
        return visitsId;
    }

    public void setVisitsId(LongFilter visitsId) {
        this.visitsId = visitsId;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JobCriteria that = (JobCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(reportedBy, that.reportedBy) &&
            Objects.equals(clientOrderRef, that.clientOrderRef) &&
            Objects.equals(raiseDate, that.raiseDate) &&
            Objects.equals(priority, that.priority) &&
            Objects.equals(fault, that.fault) &&
            Objects.equals(instructions, that.instructions) &&
            Objects.equals(occupier, that.occupier) &&
            Objects.equals(homeTel, that.homeTel) &&
            Objects.equals(workTel, that.workTel) &&
            Objects.equals(mobileTel, that.mobileTel) &&
            Objects.equals(tenantName, that.tenantName) &&
            Objects.equals(complete, that.complete) &&
            Objects.equals(position, that.position) &&
            Objects.equals(invoice, that.invoice) &&
            Objects.equals(invoiceDetails, that.invoiceDetails) &&
            Objects.equals(commentsId, that.commentsId) &&
            Objects.equals(linesId, that.linesId) &&
            Objects.equals(visitsId, that.visitsId) &&
            Objects.equals(clientId, that.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        reportedBy,
        clientOrderRef,
        raiseDate,
        priority,
        fault,
        instructions,
        occupier,
        homeTel,
        workTel,
        mobileTel,
        tenantName,
        complete,
        position,
        invoice,
        invoiceDetails,
        commentsId,
        linesId,
        visitsId,
        clientId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (reportedBy != null ? "reportedBy=" + reportedBy + ", " : "") +
                (clientOrderRef != null ? "clientOrderRef=" + clientOrderRef + ", " : "") +
                (raiseDate != null ? "raiseDate=" + raiseDate + ", " : "") +
                (priority != null ? "priority=" + priority + ", " : "") +
                (fault != null ? "fault=" + fault + ", " : "") +
                (instructions != null ? "instructions=" + instructions + ", " : "") +
                (occupier != null ? "occupier=" + occupier + ", " : "") +
                (homeTel != null ? "homeTel=" + homeTel + ", " : "") +
                (workTel != null ? "workTel=" + workTel + ", " : "") +
                (mobileTel != null ? "mobileTel=" + mobileTel + ", " : "") +
                (tenantName != null ? "tenantName=" + tenantName + ", " : "") +
                (complete != null ? "complete=" + complete + ", " : "") +
                (position != null ? "position=" + position + ", " : "") +
                (invoice != null ? "invoice=" + invoice + ", " : "") +
                (invoiceDetails != null ? "invoiceDetails=" + invoiceDetails + ", " : "") +
                (commentsId != null ? "commentsId=" + commentsId + ", " : "") +
                (linesId != null ? "linesId=" + linesId + ", " : "") +
                (visitsId != null ? "visitsId=" + visitsId + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
            "}";
    }

}
