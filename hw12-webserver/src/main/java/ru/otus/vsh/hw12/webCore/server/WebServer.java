package ru.otus.vsh.hw12.webCore.server;

public interface WebServer {
    void start() throws Exception;

    void join() throws Exception;

    void stop() throws Exception;
}
