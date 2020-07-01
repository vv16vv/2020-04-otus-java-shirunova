package ru.otus.vsh.hw12.webCore.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
