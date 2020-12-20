package com.mywizard_bot.controller;

import com.mywizard_bot.MyWizardTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;


@RestController
@Slf4j
public class WebHookController {
    private final MyWizardTelegramBot telegramBot;

    @Autowired
    public WebHookController(MyWizardTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostMapping(value = "/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        log.debug("sandet message");
        return telegramBot.onWebhookUpdateReceived(update);
    }
}
