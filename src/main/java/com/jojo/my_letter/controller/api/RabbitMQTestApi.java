package com.jojo.my_letter.controller.api;

import com.jojo.my_letter.model.entity.AccessLog;
import com.jojo.my_letter.mq.consumer.ConsumerResult;
import com.jojo.my_letter.mq.consumer.RaceConditionMessage;
import com.jojo.my_letter.mq.producer.MqMessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RabbitMQTestApi {

    private final MqMessageService mqMessageService;

    @GetMapping("/send-message")
    public String sendMessage(HttpServletRequest request) {

        AccessLog accessLog = new AccessLog();
        accessLog.setHost(request.getRemoteHost());
        accessLog.setUri(request.getRequestURI());
        accessLog.setMethod(request.getMethod());
        accessLog.setUserAgent(request.getHeader("User-Agent"));
        accessLog.setReferer(request.getHeader("Referer"));
        accessLog.setClientIp(request.getRemoteAddr());
        accessLog.setElapsed(123L);

        mqMessageService.sendAccessLogMessage(accessLog);

        return "rabbitmq";
    }

    @GetMapping("/send-message-and-receive")
    public Object sendMessageAndReceive(HttpServletRequest request) {

        RaceConditionMessage message = new RaceConditionMessage();
        message.setSeq(1L);
        message.setName("아이브와 점심먹기 상품권 1장");
        message.setMessage("상품가격: 100만원");
        message.setUserAgent(request.getHeader("User-Agent"));

        final ConsumerResult consumerResult = mqMessageService.sendRaceConditionMessage(message);
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("result", consumerResult.getResult());

        return data;
    }
}
