package com.access.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.access.myapp.domain.Job} entity.
 */
public class JobDTO implements Serializable {
    
    private Long id;

    private String reportedBy;

    private String clientOrderRef;

    private String raiseDate;

    private String priority;

    private String fault;

    private String instructions;

    private String occupier;

    private String homeTel;

    private String workTel;

    private String mobileTel;

    private String tenantName;

    private String complete;

    private String position;

    private String invoice;

    private String invoiceDetails;


    private Long clientId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getClientOrderRef() {
        return clientOrderRef;
    }

    public void setClientOrderRef(String clientOrderRef) {
        this.clientOrderRef = clientOrderRef;
    }

    public String getRaiseDate() {
        return raiseDate;
    }

    public void setRaiseDate(String raiseDate) {
        this.raiseDate = raiseDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getOccupier() {
        return occupier;
    }

    public void setOccupier(String occupier) {
        this.occupier = occupier;
    }

    public String getHomeTel() {
        return homeTel;
    }

    public void setHomeTel(String homeTel) {
        this.homeTel = homeTel;
    }

    public String getWorkTel() {
        return workTel;
    }

    public void setWorkTel(String workTel) {
        this.workTel = workTel;
    }

    public String getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(String invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobDTO)) {
            return false;
        }

        return id != null && id.equals(((JobDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobDTO{" +
            "id=" + getId() +
            ", reportedBy='" + getReportedBy() + "'" +
            ", clientOrderRef='" + getClientOrderRef() + "'" +
            ", raiseDate='" + getRaiseDate() + "'" +
            ", priority='" + getPriority() + "'" +
            ", fault='" + getFault() + "'" +
            ", instructions='" + getInstructions() + "'" +
            ", occupier='" + getOccupier() + "'" +
            ", homeTel='" + getHomeTel() + "'" +
            ", workTel='" + getWorkTel() + "'" +
            ", mobileTel='" + getMobileTel() + "'" +
            ", tenantName='" + getTenantName() + "'" +
            ", complete='" + getComplete() + "'" +
            ", position='" + getPosition() + "'" +
            ", invoice='" + getInvoice() + "'" +
            ", invoiceDetails='" + getInvoiceDetails() + "'" +
            ", clientId=" + getClientId() +
            "}";
    }
}
