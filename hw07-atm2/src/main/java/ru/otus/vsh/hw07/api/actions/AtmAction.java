package ru.otus.vsh.hw07.api.actions;

import ru.otus.vsh.hw07.impl.Department;

public abstract class AtmAction {
    private AtmAction nextAction = null;

    public AtmAction next(AtmAction action) {
        this.nextAction = action;
        return this;
    }

    public abstract void action(Department department);

    public void process(Department department) {
        action(department);
        if (nextAction != null) nextAction.process(department);
    }
}
