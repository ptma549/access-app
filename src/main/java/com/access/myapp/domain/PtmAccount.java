package com.access.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PtmAccount.
 */
@Entity
@Table(name = "ptm_account")
public class PtmAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "colour")
    private String colour;

    @OneToMany(mappedBy = "account")
    private Set<Client> clients = new HashSet<>();

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

    public PtmAccount name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public PtmAccount colour(String colour) {
        this.colour = colour;
        return this;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public PtmAccount clients(Set<Client> clients) {
        this.clients = clients;
        return this;
    }

    public PtmAccount addClients(Client client) {
        this.clients.add(client);
        client.setAccount(this);
        return this;
    }

    public PtmAccount removeClients(Client client) {
        this.clients.remove(client);
        client.setAccount(null);
        return this;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PtmAccount)) {
            return false;
        }
        return id != null && id.equals(((PtmAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PtmAccount{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", colour='" + getColour() + "'" +
            "}";
    }
}
