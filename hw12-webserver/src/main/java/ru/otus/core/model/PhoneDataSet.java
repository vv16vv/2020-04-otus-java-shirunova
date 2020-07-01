package ru.otus.core.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phones")
public class PhoneDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "number", nullable = false)
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(long id, String number) {
        this.id = id;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneDataSet that = (PhoneDataSet) o;
        return id == that.id &&
                number.equals(that.number) &&
                user.getId() == that.user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, user.getId());
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", user=" + user.getName() +
                '}';
    }
}
