package ru.otus.vsh.hw07.api;

import ru.otus.vsh.hw07.impl.Department;

import javax.annotation.Nonnull;

public class AtmInitiateAction extends AtmAction {
    private final String reason;

    public AtmInitiateAction(String reason) {
        this.reason = reason;
    }

    @Override
    public void action(@Nonnull Department department) {
        department.initiate(reason);
    }
}
