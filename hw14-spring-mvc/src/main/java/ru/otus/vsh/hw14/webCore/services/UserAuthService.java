package ru.otus.vsh.hw14.webCore.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
