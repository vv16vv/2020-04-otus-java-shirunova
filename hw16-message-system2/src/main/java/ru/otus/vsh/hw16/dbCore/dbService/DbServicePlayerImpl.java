package ru.otus.vsh.hw16.dbCore.dbService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.otus.vsh.hw16.dbCore.dao.PlayerDao;
import ru.otus.vsh.hw16.dbCore.dbService.api.AbstractDbServiceImpl;
import ru.otus.vsh.hw16.model.domain.Player;

import java.util.Optional;

@Slf4j
@Repository
public class DbServicePlayerImpl extends AbstractDbServiceImpl<Player> implements DBServicePlayer {
    private final PlayerDao playerDao;

    public DbServicePlayerImpl(PlayerDao playerDao) {
        super(playerDao);
        this.playerDao = playerDao;
    }

    @Override
    public Optional<Player> findByLogin(String login) {
        return executeInSession((sm, loginName) -> {
            var user = playerDao.findByLogin(loginName);
            sm.commitSession();

            log.info("found user with login = {}: {}", loginName, user);
            return user;
        }, login);
    }

}
