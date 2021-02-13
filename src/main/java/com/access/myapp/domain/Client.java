package com.access.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "fax")
    private String fax;

    @OneToMany(mappedBy = "client")
    private Set<County> counties = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private Set<Job> jobs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "clients", allowSetters = true)
    private PtmAccount account;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Client name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public Client address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public Client postcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTelephone() {
        return telephone;
    }

    public Client telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public Client fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Set<County> getCounties() {
        return counties;
    }

    public Client counties(Set<County> counties) {
        this.counties = counties;
        return this;
    }

    public Client addCounties(County county) {
        this.counties.add(county);
        county.setClient(this);
        return this;
    }

    public Client removeCounties(County county) {
        this.counties.remove(county);
        county.setClient(null);
        return this;
    }

    public void setCounties(Set<County> counties) {
        this.counties = counties;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public Client jobs(Set<Job> jobs) {
        this.jobs = jobs;
        return this;
    }

    public Client addJobs(Job job) {
        this.jobs.add(job);
        job.setClient(this);
        return this;
    }

    public Client removeJobs(Job job) {
        this.jobs.remove(job);
        job.setClient(null);
        return this;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }

    public PtmAccount getAccount() {
        return account;
    }

    public Client account(PtmAccount ptmAccount) {
        this.account = ptmAccount;
        return this;
    }

    public void setAccount(PtmAccount ptmAccount) {
        this.account = ptmAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", fax='" + getFax() + "'" +
            "}";
    }
}
