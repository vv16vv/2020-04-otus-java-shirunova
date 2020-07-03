package ru.otus.vsh.hw13;

/*
В классе AppComponentsContainerImpl реализовать обработку, полученной в конструкторе конфигурации,
основываясь на разметке аннотациями из пакета appcontainer. Так же необходимо реализовать методы getAppComponent.
В итоге должно получиться работающее приложение. Менять можно только класс AppComponentsContainerImpl.

PS Приложение представляет из себя тренажер таблицы умножения)
*/

import ru.otus.vsh.hw13.appcontainer.AppComponentsContainerImpl;
import ru.otus.vsh.hw13.appcontainer.api.AppComponentsContainer;
import ru.otus.vsh.hw13.config.AppConfig;
import ru.otus.vsh.hw13.services.GameProcessor;

public class App {

    public static void main(String[] args) throws Exception {
        AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);
        GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
//        GameProcessor gameProcessor = container.getAppComponent("gameProcessor");
        gameProcessor.startGame();
    }
}
