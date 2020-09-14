package ru.otus.vsh.hw16.domain.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder(buildMethodName = "get")
@ToString(exclude = {"password"})
@Data
@NamedQuery(
        name = Player.GET_PLAYER_BY_LOGIN,
        query = "select u from Player u where login = :login"
)
@Entity
@Table(name = "users")
public class Player implements Model {
    public static final String GET_PLAYER_BY_LOGIN = "get_player_by_login";
    public static final String GET_ALL_PLAYERS = "get_all_players";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    long id;

    @Column(name = "login", unique = true, nullable = false)
    String login;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "password", nullable = false)
    String password;
}

