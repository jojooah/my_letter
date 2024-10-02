package com.jojo.my_letter.mq.consumer;

import com.jojo.my_letter.model.entity.AccessLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.jojo.my_letter.config.RabbitMqConfig.ACCESS_LOG_ROUTING_KEY;
import static com.jojo.my_letter.utils.Utils.toJson;

@Profile("consumer")
@Slf4j
@Component
@RequiredArgsConstructor
public class AccessLogConsumer extends MessageListenerAdapter {

    @RabbitListener(queues = ACCESS_LOG_ROUTING_KEY, concurrency = "1")
    public void handleMessage(AccessLog accessLog) {

        try {
            log.info("AccessLogConsumer Received message: {}", toJson(accessLog));
        }catch (Exception e) {
            log.error("[error-skip] AccessLogConsumer error. seq: {}", accessLog.getUri(), e);

            // 에러의 심각도에 따라서 telegram으로 알림을 보낸다. 그리고 끝낸다.

            // or dead-queue 로 전송한다.
        }
    }

}