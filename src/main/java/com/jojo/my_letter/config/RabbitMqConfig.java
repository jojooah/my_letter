package com.jojo.my_letter.config;

import com.jojo.my_letter.config.properties.RabbitServerProperties;
import com.jojo.my_letter.mq.consumer.AccessLogConsumer;
import com.jojo.my_letter.mq.consumer.RaceConditionConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.jojo.my_letter.utils.Utils.toJson;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {

    public static final String EXCHANGE_NAME = "letter";
    public static final String ACCESS_LOG_ROUTING_KEY = "access-log";
    public static final String RACE_CONDITION_ROUTING_KEY = "race-condition-log";

    private final RabbitServerProperties rabbitServerProperties;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitServerProperties.getHost());
        connectionFactory.setPort(rabbitServerProperties.getPort());
        connectionFactory.setUsername(rabbitServerProperties.getUsername());
        connectionFactory.setPassword(rabbitServerProperties.getPassword());
        return connectionFactory;
    }

    /**
     * RabbitTemplate을 생성하여 반환
     *
     * @param connectionFactory RabbitMQ와의 연결을 위한 ConnectionFactory 객체
     * @return RabbitTemplate 객체
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // JSON 형식의 메시지를 직렬화하고 역직렬할 수 있도록 설정
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * Jackson 라이브러리를 사용하여 메시지를 JSON 형식으로 변환하는 MessageConverter 빈을 생성
     *
     * @return MessageConverter 객체
     */
    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 메시지 컨버터 공통 메소드
     * @param object
     * @return
     */
    public Message buildSimpleConverterMessage(Object object)
    {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding("utf-8");
        messageProperties.setMessageId(java.util.UUID.randomUUID().toString());
        return new Message(toJson(object).getBytes(), messageProperties);
    }

    // part-1 : AGENT_RESULT_ROUTING_KEY

    @Bean
    public Queue accessLogQueue() {
        return new Queue(ACCESS_LOG_ROUTING_KEY);
    }

    @Bean
    public Binding accessLogBinding(DirectExchange exchange) {
        return BindingBuilder.bind(accessLogQueue()).to(exchange).with(ACCESS_LOG_ROUTING_KEY);
    }

    @Profile("consumer")
    @Bean
    public MessageListenerAdapter accessLogConsumerAdapter(AccessLogConsumer consumer) {
        return new MessageListenerAdapter(consumer, "handleMessage");
    }

    // part-2 : RACE_CONDITION_ROUTING_KEY

    @Bean
    public Queue raceConditionQueue() {
        return new Queue(RACE_CONDITION_ROUTING_KEY);
    }

    @Bean
    public Binding raceConditionBinding(DirectExchange exchange) {
        return BindingBuilder.bind(raceConditionQueue()).to(exchange).with(RACE_CONDITION_ROUTING_KEY);
    }

    @Profile("consumer")
    @Bean
    public MessageListenerAdapter raceConditionConsumerAdapter(RaceConditionConsumer consumer) {
        return new MessageListenerAdapter(consumer, "handleMessage");
    }

}