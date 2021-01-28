package com.mywizard_bot.service.bot;

import com.google.gson.Gson;
import com.mywizard_bot.model.WizardTelegramBot;
import com.mywizard_bot.model.Bot;
import com.mywizard_bot.repository.BotTokenRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class BotService {
    private static final Gson gson = new Gson();
    private final BotTokenRepository tokenRepository;
    private final BotCreator creator;

    public WizardTelegramBot getBotByUUID(String uuid) {
        String botJson = tokenRepository.getBotOptionsJsonByUUID(uuid);
        if (StringUtils.isNotBlank(botJson)) {
            Bot bot = gson.fromJson(botJson, Bot.class);
            return creator.create(bot);
        }


        return null;
    }

    public WizardTelegramBot getMyWizardTelegramBot(Update update) {
        String botJson = tokenRepository.getBotJsonByUserId(update.getChannelPost().getMessageId());
        if (StringUtils.isNotBlank(botJson)) {
            Bot bot = gson.fromJson(botJson, Bot.class);
            return creator.create(bot);
        }
        return null;
    }
}


