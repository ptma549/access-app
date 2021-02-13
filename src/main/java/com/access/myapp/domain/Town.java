package com.access.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Town.
 */
@Entity
@Table(name = "town")
public class Town implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value")
    private String value;

    @OneToMany(mappedBy = "town")
    private Set<Street> streets = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "towns", allowSetters = true)
    private County county;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public Town value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<Street> getStreets() {
        return streets;
    }

    public Town streets(Set<Street> streets) {
        this.streets = streets;
        return this;
    }

    public Town addStreets(Street street) {
        this.streets.add(street);
        street.setTown(this);
        return this;
    }

    public Town removeStreets(Street street) {
        this.streets.remove(street);
        street.setTown(null);
        return this;
    }

    public void setStreets(Set<Street> streets) {
        this.streets = streets;
    }

    public County getCounty() {
        return county;
    }

    public Town county(County county) {
        this.county = county;
        return this;
    }

    public void setCounty(County county) {
        this.county = county;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Town)) {
            return false;
        }
        return id != null && id.equals(((Town) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Town{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
