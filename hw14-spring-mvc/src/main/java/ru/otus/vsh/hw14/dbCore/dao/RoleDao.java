package ru.otus.vsh.hw14.dbCore.dao;

import ru.otus.vsh.hw14.dbCore.model.Role;

public interface RoleDao extends Dao<Role> {
    Role findByName(String name);
}
