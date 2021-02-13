package com.access.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Street.
 */
@Entity
@Table(name = "street")
public class Street implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value")
    private String value;

    @OneToMany(mappedBy = "street")
    private Set<Number> numbers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "streets", allowSetters = true)
    private Town town;

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

    public Street value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<Number> getNumbers() {
        return numbers;
    }

    public Street numbers(Set<Number> numbers) {
        this.numbers = numbers;
        return this;
    }

    public Street addNumbers(Number number) {
        this.numbers.add(number);
        number.setStreet(this);
        return this;
    }

    public Street removeNumbers(Number number) {
        this.numbers.remove(number);
        number.setStreet(null);
        return this;
    }

    public void setNumbers(Set<Number> numbers) {
        this.numbers = numbers;
    }

    public Town getTown() {
        return town;
    }

    public Street town(Town town) {
        this.town = town;
        return this;
    }

    public void setTown(Town town) {
        this.town = town;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Street)) {
            return false;
        }
        return id != null && id.equals(((Street) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Street{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
