package ru.otus.vsh.hw13.config;

import ru.otus.vsh.hw13.appcontainer.api.AppComponent;
import ru.otus.vsh.hw13.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.vsh.hw13.services.*;

@AppComponentsContainerConfig(order = 1)
public class AppConfig {

    @AppComponent(order = 0, name = "equationPreparer")
    public EquationPreparer equationPreparer() {
        return new EquationPreparerImpl();
    }

    @AppComponent(order = 1, name = "playerService")
    public PlayerService playerService(IOService ioService) {
        return new PlayerServiceImpl(ioService);
    }

}
