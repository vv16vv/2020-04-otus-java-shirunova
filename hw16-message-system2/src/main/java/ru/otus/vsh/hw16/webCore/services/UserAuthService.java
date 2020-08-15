package ru.otus.vsh.hw16.webCore.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
