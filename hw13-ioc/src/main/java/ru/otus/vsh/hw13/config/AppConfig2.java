package ru.otus.vsh.hw13.config;

import ru.otus.vsh.hw13.appcontainer.api.AppComponent;
import ru.otus.vsh.hw13.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.vsh.hw13.services.*;

@AppComponentsContainerConfig(order = 1)
public class AppConfig2 {

    @AppComponent(order = 2, name = "gameProcessor")
    public GameProcessor gameProcessor(IOService ioService,
                                       PlayerService playerService,
                                       EquationPreparer equationPreparer) {
        return new GameProcessorImpl(ioService, equationPreparer, playerService);
    }

    @AppComponent(order = 0, name = "ioService")
    public IOService ioService() {
        return new IOServiceConsole(System.out, System.in);
    }

}
