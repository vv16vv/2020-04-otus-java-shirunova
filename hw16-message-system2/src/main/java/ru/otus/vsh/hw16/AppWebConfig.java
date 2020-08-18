package ru.otus.vsh.hw16;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ru.otus.vsh.hw16.model.domain.Player;
import ru.otus.vsh.hw16.hibernate.HibernateUtils;

@Configuration
@ComponentScan
@EnableWebMvc
public class AppWebConfig implements WebSocketMessageBrokerConfigurer {

    private final ApplicationContext applicationContext;

    public AppWebConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtils.buildSessionFactory("hibernate.cfg.xml", Player.class);
    }

}
