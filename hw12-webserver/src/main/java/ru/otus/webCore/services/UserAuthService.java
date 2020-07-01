package ru.otus.webCore.services;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
