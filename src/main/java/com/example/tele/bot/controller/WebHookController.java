package com.example.tele.bot.controller;

import com.example.tele.bot.MyWizardTelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WebHookController {
    private final MyWizardTelegramBot telegramBot;

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update){
        log.debug("update {}", update);
        return telegramBot.onWebhookUpdateReceived(update);
    }
}
