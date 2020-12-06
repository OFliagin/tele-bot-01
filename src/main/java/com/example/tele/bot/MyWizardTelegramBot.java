package com.example.tele.bot;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyWizardTelegramBot extends TelegramWebhookBot {

    private String webHookPath;
    private String botUserName;
    private String botToken;

    public MyWizardTelegramBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            long chat_id = update.getMessage().getChatId();


            try {
                execute(new SendMessage(chat_id, getMessage(update.getMessage().getText())));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private String getMessage(String text) {
        if (StringUtils.equalsIgnoreCase(text, "hi")) {
            return "Hello my honey, it's your botLover";
        }
        if (StringUtils.endsWithIgnoreCase(text, "куся") || StringUtils.endsWithIgnoreCase(text, "Kucia"))  {
            return "I very miss you!!!";
        }
        return text;
    }


    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }
}
