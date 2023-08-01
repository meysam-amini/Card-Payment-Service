package com.meysam.transferservice.configurations;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfigurations {

        @Value("${spring.rabbitmq.host}")
        private String host;
        @Value("${spring.rabbitmq.username}")
        private String username;
        @Value("${spring.rabbitmq.password}")
        private String password;


//        @Bean
//        public CachingConnectionFactory connectionFactory() {
//            CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
//            cachingConnectionFactory.setUsername(username);
//            cachingConnectionFactory.setPassword(password);
//            return cachingConnectionFactory;
//        }

        @Bean
        public MessageConverter jsonMessageConverter() {
            return new Jackson2JsonMessageConverter();
        }

        @Bean
        public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
            final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
            rabbitTemplate.setMessageConverter(jsonMessageConverter());
            return rabbitTemplate;
        }

}
