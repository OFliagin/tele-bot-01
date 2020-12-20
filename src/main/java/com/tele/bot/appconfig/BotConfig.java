package com.tele.bot.appconfig;

import com.tele.bot.MyWizardTelegramBot;
import com.tele.bot.api.TelegramFacade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String botUserName;
    private String botToken;
    private String webHookPath;

    private DefaultBotOptions.ProxyType proxyType;
    private String proxyHost;
    private int proxyPort;

    private final TelegramFacade telegramFacade;

    @Autowired
    public BotConfig(TelegramFacade telegramFacade) {
        this.telegramFacade = telegramFacade;
    }

    @Bean
    public MyWizardTelegramBot mySuperTelegramBot() {
        DefaultBotOptions options = ApiContext
                .getInstance(DefaultBotOptions.class);


        MyWizardTelegramBot mySuperTelegramBot = new MyWizardTelegramBot(options, telegramFacade);
        mySuperTelegramBot.setBotUserName(botUserName);
        mySuperTelegramBot.setBotToken(botToken);
        mySuperTelegramBot.setWebHookPath(webHookPath);

        return mySuperTelegramBot;
    }
}
