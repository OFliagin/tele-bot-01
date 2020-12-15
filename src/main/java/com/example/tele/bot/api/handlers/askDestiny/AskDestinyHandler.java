package com.example.tele.bot.api.handlers.askDestiny;

import com.example.tele.bot.api.BootState;
import com.example.tele.bot.api.InputMessageHandler;
import com.example.tele.bot.cache.DataCache;
import com.example.tele.bot.service.ReplyMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Slf4j
public class AskDestinyHandler implements InputMessageHandler {

    private final ReplyMessageService messageService;
    private final DataCache cache;

    public AskDestinyHandler(ReplyMessageService messageService, DataCache cache) {
        this.messageService = messageService;
        this.cache = cache;
    }

    @Override
    public SendMessage handler(Message inputMessage) {
        log.debug("handler {}", inputMessage);
        int userId = inputMessage.getFrom().getId();
        long chatId = inputMessage.getChatId();
        SendMessage replyToUser = messageService.getReplyMessage(chatId, "replay.askDestiny");
        log.debug("replyToUser {}", replyToUser);
        cache.setUsersCurrentBotState(userId, BootState.FILLING_PROFILE);
        return replyToUser;
    }

    @Override
    public BootState getHandlerName() {
        return BootState.ASK_DESTINY;
    }
}
