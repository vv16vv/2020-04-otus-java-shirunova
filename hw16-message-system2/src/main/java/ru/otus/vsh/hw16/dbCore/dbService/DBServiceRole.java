package ru.otus.vsh.hw16.dbCore.dbService;

import ru.otus.vsh.hw16.dbCore.dbService.api.DBService;
import ru.otus.vsh.hw16.dbCore.model.Role;

public interface DBServiceRole extends DBService<Role> {
    Role findByName(String name);
}
