package ru.otus.vsh.hw16.model.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder(buildMethodName = "get")
@Data
@NamedQuery(
        name = Session.GET_BY_SESSION_ID,
        query = "select s from Session s where sessionId = :sessionId"
)
@Entity
@Table(name = "sessions")
public class Session implements Model {
    public static final String GET_BY_SESSION_ID = "get_by_session_id";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    long id;

    @Column(name = "sessionId", unique = true, nullable = false)
    String sessionId;

    @Column(name = "login", nullable = false)
    String login;

}

