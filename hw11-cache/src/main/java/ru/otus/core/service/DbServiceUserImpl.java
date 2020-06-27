package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

    private final UserDao userDao;
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private final Optional<MyCache<String, User>> optionalCache;

    public DbServiceUserImpl(@Nonnull UserDao userDao){
        this(userDao, null);
    }

    public DbServiceUserImpl(@Nonnull UserDao userDao, @Nullable MyCache<String, User> cache) {
        this.userDao = userDao;
        this.optionalCache = Optional.ofNullable(cache);
        if (this.optionalCache.isPresent()) {
            HwListener<String, User> listener = new HwListener<>() {
                @Override
                public void notify(String key, User value, String action) {
                    logger.info("DbServiceUser: action {} on key {} with value {}", action, key, value);
                }
            };
            this.optionalCache.get().addListener(listener);
        }
    }

    @Override
    public long newUser(@Nonnull User user) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var userId = userDao.insertUser(user);
                sessionManager.commitSession();
                optionalCache.ifPresent(cache -> cache.put(String.valueOf(userId), user));

                logger.info("created user: {}", userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void editUser(User user) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.updateUser(user);
                sessionManager.commitSession();
                optionalCache.ifPresent(cache -> cache.put(String.valueOf(user.getId()), user));

                logger.info("updated user: {}", user.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void saveUser(User user) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.insertOrUpdate(user);
                sessionManager.commitSession();
                optionalCache.ifPresent(cache -> cache.put(String.valueOf(user.getId()), user));

                logger.info("added or changed user: {}", user.getId());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    private Optional<User> getUserFromDatabase(long id) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id);

                logger.info("user: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getUser(long id) {
        return optionalCache
                .flatMap(cache -> Optional.ofNullable(cache.get(String.valueOf(id))))
                .or(() -> getUserFromDatabase(id));
    }
}
