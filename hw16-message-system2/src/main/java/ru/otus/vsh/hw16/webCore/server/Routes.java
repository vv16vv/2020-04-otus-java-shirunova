package ru.otus.vsh.hw16.webCore.server;

import javax.annotation.Nonnull;

public final class Routes {

    public static final String ROOT = "/";

    public static final String PLAYERS = "/players";
    public static final String NEW_PLAYER = "/new-player";

    public static final String GAME_TEMPLATE = "/game/{sessionId}";
    private static final String SESSION_ID_PARAM = "{sessionId}";

    public static final String API = "/api";
    public static final String API_GAME_WS = API + "/game-ws";
    public static final String API_GAME_START = "/game-start.{sessionId}";
    public static final String API_ANSWER = "/answer.{gameId}";

    public static final String API_TOPIC = "/topic";
    public static final String API_TOPIC_EQUATION = API_TOPIC + "/equation";
    public static final String API_TOPIC_RESULT = API_TOPIC + "/result";
    public static final String API_TOPIC_CORRECT = API_TOPIC + "/correct";

    @Nonnull
    public static String gameUrl(@Nonnull String sessionId) {
        return GAME_TEMPLATE.replace(SESSION_ID_PARAM, sessionId);
    }

}
