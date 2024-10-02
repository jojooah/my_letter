package com.jojo.my_letter.mq.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.jojo.my_letter.config.RabbitMqConfig.RACE_CONDITION_ROUTING_KEY;
import static com.jojo.my_letter.utils.Utils.toJson;

@Profile("consumer")
@Slf4j
@Component
@RequiredArgsConstructor
public class RaceConditionConsumer extends MessageListenerAdapter {

    @RabbitListener(queues = RACE_CONDITION_ROUTING_KEY, concurrency = "1")
    public ConsumerResult handleMessage(RaceConditionMessage raceConditionMessage) {

        try {
            log.info("RaceConditionConsumer Received message: {}", toJson(raceConditionMessage));

            final ConsumerResult consumerResult = new ConsumerResult();
            consumerResult.setResult("success");
            return consumerResult;

        }catch (Exception e) {
            final ConsumerResult consumerResult = new ConsumerResult();
            consumerResult.setResult("failure");
            consumerResult.setMessage(e.getMessage());

            log.error("[error-skip] RaceConditionConsumer error. seq: {}", raceConditionMessage.getSeq(), e);

            return consumerResult;
        }
    }

}