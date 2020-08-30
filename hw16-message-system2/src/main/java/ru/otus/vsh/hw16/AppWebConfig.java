package ru.otus.vsh.hw16;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ru.otus.vsh.hw16.hibernate.HibernateUtils;
import ru.otus.vsh.hw16.domain.model.Player;
import ru.otus.vsh.hw16.domain.model.Session;
import ru.otus.vsh.hw16.webCore.server.Routes;

@Configuration
@ComponentScan
public class AppWebConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(Routes.API_TOPIC);
        config.setApplicationDestinationPrefixes(Routes.API);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(Routes.API_GAME_WS).withSockJS();
    }

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtils.buildSessionFactory("hibernate.cfg.xml", Player.class, Session.class);
    }

}
