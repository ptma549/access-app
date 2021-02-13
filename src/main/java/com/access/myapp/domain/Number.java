package com.access.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Number.
 */
@Entity
@Table(name = "number")
public class Number implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "building")
    private String building;

    @Column(name = "postcode")
    private String postcode;

    @OneToMany(mappedBy = "number")
    private Set<Position> positions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "numbers", allowSetters = true)
    private Street street;

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

    public Number value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getBuilding() {
        return building;
    }

    public Number building(String building) {
        this.building = building;
        return this;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getPostcode() {
        return postcode;
    }

    public Number postcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public Number positions(Set<Position> positions) {
        this.positions = positions;
        return this;
    }

    public Number addPositions(Position position) {
        this.positions.add(position);
        position.setNumber(this);
        return this;
    }

    public Number removePositions(Position position) {
        this.positions.remove(position);
        position.setNumber(null);
        return this;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public Street getStreet() {
        return street;
    }

    public Number street(Street street) {
        this.street = street;
        return this;
    }

    public void setStreet(Street street) {
        this.street = street;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Number)) {
            return false;
        }
        return id != null && id.equals(((Number) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Number{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", building='" + getBuilding() + "'" +
            ", postcode='" + getPostcode() + "'" +
            "}";
    }
}
