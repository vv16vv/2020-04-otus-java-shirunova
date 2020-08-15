package ru.otus.vsh.hw16.webCore.controllers.dataClasses;

public class UserData {
    private String login = "";
    private String name = "";
    private String password = "";
    private String address = "";
    private String role = "";
    private String phone1 = "";
    private String phone2 = "";

    public UserData() {
    }

    public UserData(String login, String name, String password, String address, String role, String phone1, String phone2) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.address = address;
        this.role = role;
        this.phone1 = phone1;
        this.phone2 = phone2;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", role='" + role + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", phone2='" + phone2 + '\'' +
                '}';
    }
}

//http://localhost:63342/2020-04-otus-java-shirunova/
// 2020-04-otus-java-shirunova.hw14-spring-mvc.main/
// templates/new-user.html?
// login=vv16vv&
// name=%D0%92%D0%B8%D0%BA%D1%83%D1%88%D0%BA%D0%B0&
// password=aaa&
// address=%D0%A1%D0%B5%D1%80%D0%B5%D0%B1%D1%80%D0%B8%D1%81%D1%82%D1%8B%D0%B9+%D0%B1%D1%83%D0%BB%D1%8C%D0%B2%D0%B0%D1%80&
// role=admin&
// phone1=%2B1%28999%29888-77-66&
// phone2=%2B9%28111%29222-33-44