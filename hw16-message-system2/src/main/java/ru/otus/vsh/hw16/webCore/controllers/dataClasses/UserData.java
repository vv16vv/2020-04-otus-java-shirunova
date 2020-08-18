package ru.otus.vsh.hw16.webCore.controllers.dataClasses;

import lombok.*;

@Data
@NoArgsConstructor
public class UserData {
    private String login = "";
    private String name = "";
    private String password = "";
}
