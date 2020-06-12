package ru.otus.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class DbExecutorImpl<T> implements DbExecutor<T> {
    private static final Logger logger = LoggerFactory.getLogger(DbExecutorImpl.class);

    @Override
    public long executeInsert(
            @Nonnull Connection connection,
            @Nonnull String sql,
            @Nonnull List<Object> params) throws SQLException {
        Savepoint savePoint = connection.setSavepoint("insert");
        try (var pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int idx = 0; idx < params.size(); idx++) {
                pst.setObject(idx + 1, params.get(idx));
            }
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            connection.rollback(savePoint);
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public void executeUpdate(
            @Nonnull Connection connection,
            @Nonnull String sql,
            @Nonnull List<Object> params) throws SQLException {
        Savepoint savePoint = connection.setSavepoint("update");
        try (var pst = connection.prepareStatement(sql)) {
            for (int idx = 0; idx < params.size(); idx++) {
                pst.setObject(idx + 1, params.get(idx));
            }
            pst.executeUpdate();
        } catch (SQLException ex) {
            connection.rollback(savePoint);
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    @Nonnull
    public Optional<T> executeSelect(
            @Nonnull Connection connection,
            @Nonnull String sql,
            long id,
            @Nonnull Function<ResultSet, T> rsHandler) throws SQLException {
        try (var pst = connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (var rs = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(rs));
            }
        }
    }

}
