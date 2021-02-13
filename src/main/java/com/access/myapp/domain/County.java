package com.access.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A County.
 */
@Entity
@Table(name = "county")
public class County implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "county")
    private Set<Town> towns = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "counties", allowSetters = true)
    private Client client;

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

    public County name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Town> getTowns() {
        return towns;
    }

    public County towns(Set<Town> towns) {
        this.towns = towns;
        return this;
    }

    public County addTown(Town town) {
        this.towns.add(town);
        town.setCounty(this);
        return this;
    }

    public County removeTown(Town town) {
        this.towns.remove(town);
        town.setCounty(null);
        return this;
    }

    public void setTowns(Set<Town> towns) {
        this.towns = towns;
    }

    public Client getClient() {
        return client;
    }

    public County client(Client client) {
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
        if (!(o instanceof County)) {
            return false;
        }
        return id != null && id.equals(((County) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "County{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
