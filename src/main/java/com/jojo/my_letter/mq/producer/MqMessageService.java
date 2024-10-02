package com.jojo.my_letter.mq.producer;

import com.jojo.my_letter.config.RabbitMqConfig;
import com.jojo.my_letter.model.entity.AccessLog;
import com.jojo.my_letter.mq.consumer.ConsumerResult;
import com.jojo.my_letter.mq.consumer.RaceConditionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.jojo.my_letter.config.RabbitMqConfig.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MqMessageService {

    private final RabbitMqConfig rabbitMqConfig;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Queue로 메시지를 발행
     *
     * @param accessLog 발행할 메시지의 DTO 객체
     */
    public void sendAccessLogMessage(AccessLog accessLog) {
        Message message = rabbitMqConfig.buildSimpleConverterMessage(accessLog);
        log.info("accessLog message sent: {}", new String(message.getBody()));
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ACCESS_LOG_ROUTING_KEY, message);
    }

    /**
     * Queue로 메시지를 발행
     *
     * @param raceConditionMessage 발행할 메시지의 DTO 객체
     */
    public ConsumerResult sendRaceConditionMessage(RaceConditionMessage raceConditionMessage) {
        Message message = rabbitMqConfig.buildSimpleConverterMessage(raceConditionMessage);
        log.info("raceConditionMessage message sent: {}", new String(message.getBody()));
        return (ConsumerResult) rabbitTemplate.convertSendAndReceive(EXCHANGE_NAME, RACE_CONDITION_ROUTING_KEY, message);
    }


}