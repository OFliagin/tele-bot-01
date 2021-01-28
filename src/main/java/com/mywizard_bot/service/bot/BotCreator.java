package com.mywizard_bot.service.bot;


import com.mywizard_bot.model.WizardTelegramBot;
import com.mywizard_bot.botapi.TelegramFacade;
import com.mywizard_bot.model.Bot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Component
@RequiredArgsConstructor
public class BotCreator {

    private final DefaultBotOptions options;
    private final TelegramFacade telegramFacade;

    public WizardTelegramBot create(Bot bot) {
        WizardTelegramBot wizardTelegramBot = new WizardTelegramBot(options, telegramFacade);
        wizardTelegramBot.setBotUserName(bot.getBotUserName());
        wizardTelegramBot.setBotToken(bot.getBotToken());
        wizardTelegramBot.setWebHookPath(bot.getWebHookPath());
        return wizardTelegramBot;
    }
}

