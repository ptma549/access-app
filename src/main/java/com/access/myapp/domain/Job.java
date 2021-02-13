package com.access.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Job.
 */
@Entity
@Table(name = "job")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "reported_by")
    private String reportedBy;

    @Column(name = "client_order_ref")
    private String clientOrderRef;

    @Column(name = "raise_date")
    private String raiseDate;

    @Column(name = "priority")
    private String priority;

    @Column(name = "fault")
    private String fault;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "occupier")
    private String occupier;

    @Column(name = "home_tel")
    private String homeTel;

    @Column(name = "work_tel")
    private String workTel;

    @Column(name = "mobile_tel")
    private String mobileTel;

    @Column(name = "tenant_name")
    private String tenantName;

    @Column(name = "complete")
    private String complete;

    @Column(name = "position")
    private String position;

    @Column(name = "invoice")
    private String invoice;

    @Column(name = "invoice_details")
    private String invoiceDetails;

    @OneToMany(mappedBy = "job")
    private Set<JobComment> comments = new HashSet<>();

    @OneToMany(mappedBy = "job")
    private Set<JobLine> lines = new HashSet<>();

    @OneToMany(mappedBy = "job")
    private Set<JobVisit> visits = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "jobs", allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public Job reportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
        return this;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getClientOrderRef() {
        return clientOrderRef;
    }

    public Job clientOrderRef(String clientOrderRef) {
        this.clientOrderRef = clientOrderRef;
        return this;
    }

    public void setClientOrderRef(String clientOrderRef) {
        this.clientOrderRef = clientOrderRef;
    }

    public String getRaiseDate() {
        return raiseDate;
    }

    public Job raiseDate(String raiseDate) {
        this.raiseDate = raiseDate;
        return this;
    }

    public void setRaiseDate(String raiseDate) {
        this.raiseDate = raiseDate;
    }

    public String getPriority() {
        return priority;
    }

    public Job priority(String priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getFault() {
        return fault;
    }

    public Job fault(String fault) {
        this.fault = fault;
        return this;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public String getInstructions() {
        return instructions;
    }

    public Job instructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getOccupier() {
        return occupier;
    }

    public Job occupier(String occupier) {
        this.occupier = occupier;
        return this;
    }

    public void setOccupier(String occupier) {
        this.occupier = occupier;
    }

    public String getHomeTel() {
        return homeTel;
    }

    public Job homeTel(String homeTel) {
        this.homeTel = homeTel;
        return this;
    }

    public void setHomeTel(String homeTel) {
        this.homeTel = homeTel;
    }

    public String getWorkTel() {
        return workTel;
    }

    public Job workTel(String workTel) {
        this.workTel = workTel;
        return this;
    }

    public void setWorkTel(String workTel) {
        this.workTel = workTel;
    }

    public String getMobileTel() {
        return mobileTel;
    }

    public Job mobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
        return this;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    public String getTenantName() {
        return tenantName;
    }

    public Job tenantName(String tenantName) {
        this.tenantName = tenantName;
        return this;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getComplete() {
        return complete;
    }

    public Job complete(String complete) {
        this.complete = complete;
        return this;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getPosition() {
        return position;
    }

    public Job position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getInvoice() {
        return invoice;
    }

    public Job invoice(String invoice) {
        this.invoice = invoice;
        return this;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getInvoiceDetails() {
        return invoiceDetails;
    }

    public Job invoiceDetails(String invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
        return this;
    }

    public void setInvoiceDetails(String invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public Set<JobComment> getComments() {
        return comments;
    }

    public Job comments(Set<JobComment> jobComments) {
        this.comments = jobComments;
        return this;
    }

    public Job addComments(JobComment jobComment) {
        this.comments.add(jobComment);
        jobComment.setJob(this);
        return this;
    }

    public Job removeComments(JobComment jobComment) {
        this.comments.remove(jobComment);
        jobComment.setJob(null);
        return this;
    }

    public void setComments(Set<JobComment> jobComments) {
        this.comments = jobComments;
    }

    public Set<JobLine> getLines() {
        return lines;
    }

    public Job lines(Set<JobLine> jobLines) {
        this.lines = jobLines;
        return this;
    }

    public Job addLines(JobLine jobLine) {
        this.lines.add(jobLine);
        jobLine.setJob(this);
        return this;
    }

    public Job removeLines(JobLine jobLine) {
        this.lines.remove(jobLine);
        jobLine.setJob(null);
        return this;
    }

    public void setLines(Set<JobLine> jobLines) {
        this.lines = jobLines;
    }

    public Set<JobVisit> getVisits() {
        return visits;
    }

    public Job visits(Set<JobVisit> jobVisits) {
        this.visits = jobVisits;
        return this;
    }

    public Job addVisits(JobVisit jobVisit) {
        this.visits.add(jobVisit);
        jobVisit.setJob(this);
        return this;
    }

    public Job removeVisits(JobVisit jobVisit) {
        this.visits.remove(jobVisit);
        jobVisit.setJob(null);
        return this;
    }

    public void setVisits(Set<JobVisit> jobVisits) {
        this.visits = jobVisits;
    }

    public Client getClient() {
        return client;
    }

    public Job client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Job)) {
            return false;
        }
        return id != null && id.equals(((Job) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Job{" +
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
            "}";
    }
}
