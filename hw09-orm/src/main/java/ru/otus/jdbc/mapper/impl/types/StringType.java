package ru.otus.jdbc.mapper.impl.types;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StringType implements FieldType {
    @Override
    @Nullable
    public Object getValue(@Nonnull ResultSet rs, @Nonnull String columnName) throws SQLException {
        return rs.getString(columnName);
    }
}
