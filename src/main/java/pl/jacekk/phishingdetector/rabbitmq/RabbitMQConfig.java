package pl.jacekk.phishingdetector.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue createSmsSourceQueue() {
        return new Queue("q.sms-source");
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue createSmsSourceFilteredQueue() {
        return new Queue("q.sms-source-filtered");
    }

    @Bean
    public Queue createSmsSourceRejectedQueue() {
        return new Queue("q.sms-source-rejected");
    }

}
