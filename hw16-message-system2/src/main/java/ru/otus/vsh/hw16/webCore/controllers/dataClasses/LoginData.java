package ru.otus.vsh.hw16.webCore.controllers.dataClasses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginData {
    private String login = "vitkus";
    private String password = "12345";
}
