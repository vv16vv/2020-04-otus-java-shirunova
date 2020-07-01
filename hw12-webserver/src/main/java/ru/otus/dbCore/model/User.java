package ru.otus.dbCore.model;


import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@NamedQuery(
        name = User.GET_USER_BY_LOGIN,
        query = "select u from users u where login = :login"
)
@NamedQuery(
        name = User.GET_USER_BY_ROLE,
        query = "select u from users u where role = :role"
)
@NamedQuery(
        name = User.GET_ALL_USERS,
        query = "select u from users u"
)
@Entity
@Table(name = "users")
public class User implements Model {
    public static final String GET_USER_BY_LOGIN = "get_user_by_login";
    public static final String GET_USER_BY_ROLE = "get_user_by_role";
    public static final String GET_ALL_USERS = "get_all_users";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Phone> phones;

    public User() {
    }

    public User(long id, String login, String name, String password, Address address, Role role, List<Phone> phones) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.password = password;
        this.address = address;
        this.role = role;
        this.phones = phones;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public void addPhone(@Nonnull Phone phone){
        phone.setUser(this);
        phones.add(phone);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                password.equals(user.password) &&
                login.equals(user.login) &&
                name.equals(user.name) &&
                address.equals(user.address) &&
                role.equals(user.role) &&
                phones.equals(user.phones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name, password, address, role, phones);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", password=" + password +
                ", address=" + address +
                ", role=" + role +
                ", phones=" + phones +
                '}';
    }

    public String smartToString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", role=" + role +
                ", phones=" + phones +
                '}';
    }
}
