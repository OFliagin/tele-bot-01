package com.mywizard_bot.repository;


public interface BotTokenRepository {

    String getBotJsonByUserId(Integer id);

    String getBotOptionsJsonByUUID(String uuid);
}
