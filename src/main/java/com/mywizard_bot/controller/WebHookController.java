package com.mywizard_bot.controller;

import com.mywizard_bot.service.bot.BotService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;


@RestController
@Slf4j
public class WebHookController {
    private final BotService botService;

    @Autowired
    public WebHookController(BotService botService) {
        this.botService = botService;
    }

    @PostMapping(value = "/{uuid}")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update, @PathVariable String uuid) {
        log.debug("sandet message");
        val bot = botService.getBotByUUID(uuid);
        if (Objects.nonNull(bot)) {
            return bot.onWebhookUpdateReceived(update);
        } else {
            return null;
        }
    }
}
