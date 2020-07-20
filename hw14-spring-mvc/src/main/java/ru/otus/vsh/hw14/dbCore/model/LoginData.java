package ru.otus.vsh.hw14.dbCore.model;

import java.util.Objects;

public class LoginData {
    private String name;
    private String password;

    public LoginData() {
    }

    public LoginData(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginData loginData = (LoginData) o;
        return name.equals(loginData.name) &&
                password.equals(loginData.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }
}
