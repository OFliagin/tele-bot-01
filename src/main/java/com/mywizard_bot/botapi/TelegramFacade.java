package com.mywizard_bot.botapi;

import com.mywizard_bot.botapi.BotState;
import com.mywizard_bot.botapi.BotStateContext;

import com.mywizard_bot.cache.DataCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Component
@Slf4j
public class TelegramFacade {
    private final BotStateContext botStateContext;
    private final DataCache userDataCache;

    @Autowired
    public TelegramFacade(BotStateContext botStateContext, DataCache userDataCache) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
    }

    public SendMessage handleUpdate(Update update) {

        SendMessage replyMessage = null;

        Message message = update.getMessage();
        if (Objects.nonNull(message) && message.hasText()) {
            log.info("New message from user : {}, chatId : {}, with text : {}", message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        int userId = message.getFrom().getId();
        SendMessage replyMessage;
        BotState bootState;

        switch (inputMsg) {
            case "/start":
                bootState = BotState.ASK_DESTINY;
                break;
            case "Получить предсказание" :
                bootState = BotState.FILLING_PROFILE;
                break;
            case "Помощь" :
                bootState = BotState.SHOW_HELP_MENU;
                break;
            default:
                bootState = userDataCache.getUsersCurrentBotState(userId);
                break;
        }

        userDataCache.setUsersCurrentBotState(userId, bootState);
        replyMessage = botStateContext.processInputMessage(bootState, message);
        return replyMessage;
    }
}
