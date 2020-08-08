package ru.otus.vsh.hw12.dbCore.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "street", nullable = false)
    private String street;

    public Address() {
    }

    public Address(long id, String street) {
        this.id = id;
        this.street = street;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address that = (Address) o;
        return id == that.id &&
                street.equals(that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street);
    }
}
