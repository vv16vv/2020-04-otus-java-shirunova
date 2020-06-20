package ru.otus.jdbc.mapper.impl.types;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface FieldType {

    @Nullable
    Object getValue(@Nonnull ResultSet rs, @Nonnull String columnName) throws SQLException;

}
