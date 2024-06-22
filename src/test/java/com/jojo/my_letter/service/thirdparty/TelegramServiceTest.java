package com.jojo.my_letter.service.thirdparty;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TelegramServiceTest {

    @Autowired
    TelegramService telegramService;

    @Test
    public void test() throws Exception {

        telegramService.sendTelegram("안녕!");
    }
}
