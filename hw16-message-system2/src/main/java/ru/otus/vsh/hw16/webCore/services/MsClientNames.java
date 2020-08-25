package ru.otus.vsh.hw16.webCore.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Accessors
public enum MsClientNames {
    DATA_BASE("DataBaseMSClient"),
    GAME_PROCESSOR("GameProcessorMSClient"),
    AUTH_SERVICE("AuthServiceMSClient"),
    GAME_CONTROLLER("GameControllerMSClient"),
    LOGIN_CONTROLLER("LoginControllerMSClient"),
    NEW_PLAYER_CONTROLLER("NewPlayerControllerMSClient"),
    ;

    @Getter
    private final String name;
}