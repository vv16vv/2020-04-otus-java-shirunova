package ru.otus.vsh.hw16.hibernate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.otus.vsh.hw16.dbCore.dao.PlayerDao;
import ru.otus.vsh.hw16.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.vsh.hw16.domain.model.Player;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class PlayerDaoHibernate extends AbstractDaoHibernate<Player> implements PlayerDao {

    public PlayerDaoHibernate(SessionManagerHibernate sessionManager) {
        super(sessionManager, Player.class);
    }

    @Override
    public Optional<Player> findByLogin(String login) {
        try {
            var entityManager = sessionManager.getEntityManager();
            List<Player> players = entityManager
                    .createNamedQuery(Player.GET_PLAYER_BY_LOGIN, Player.class)
                    .setParameter("login", login)
                    .getResultList();
            if (players.isEmpty()) return Optional.empty();
            else return Optional.of(players.get(0));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public void insertOrUpdate(@Nonnull Player player) {
        if (player.getId() == 0)
            insert(player);
        else update(player);
    }

}
