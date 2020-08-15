package ru.otus.vsh.hw16.dbCore.dao;

import ru.otus.vsh.hw16.dbCore.model.Role;

public interface RoleDao extends Dao<Role> {
    Role findByName(String name);
}
