package com.mywizard_bot.repository.inner;

import com.google.gson.Gson;
import com.mywizard_bot.model.Bot;
import com.mywizard_bot.repository.BotTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@Slf4j
public class InnerBotTokenRepository implements BotTokenRepository {
    private static final Gson gson = new Gson();

    @Value("${telegrambot.webHookPath}")
    private String webHookPath;
    @Value("${telegrambot.botUserName}")
    private String botUserName;
    @Value("${telegrambot.botToken}")
    private String botToken;


    @Override
    public String getBotOptionsJsonByUUID(String uuid) {
        log.info("Get bot options from inmamory by uuid {}", uuid);
        return gson.toJson(Bot.builder()
                .botToken(botToken)
                .webHookPath(webHookPath)
                .botUserName(botUserName)
                .build());
    }

    @Override
    @Deprecated
    public String getBotJsonByUserId(Integer id) {
        if (Objects.equals(id, 12)) {
            return gson.toJson(Bot.builder()
                    .botToken(botToken)
                    .webHookPath(webHookPath)
                    .botUserName(botUserName)
                    .build());
        } else {
            return null;
        }
    }
}
