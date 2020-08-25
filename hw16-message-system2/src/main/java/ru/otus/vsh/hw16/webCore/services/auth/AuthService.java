package ru.otus.vsh.hw16.webCore.services.auth;

public interface AuthService {
    boolean authenticate(String login, String password);
}
