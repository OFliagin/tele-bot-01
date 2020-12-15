package com.example.tele.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@Slf4j
public class ReplyMessageService {

    private final LocalMessageService localMessageService;

    @Autowired
    public ReplyMessageService(LocalMessageService localMessageService) {
        this.localMessageService = localMessageService;
    }

    public SendMessage getReplyMessage(long chatId, String replyMessage) {
        log.debug("getReplyMessage for chatId {}, replyMessage {}", chatId, replyMessage);
        return new SendMessage(chatId, localMessageService.getMessage(replyMessage));
    }

    public SendMessage getReplyMessage(long chatId, String replyMessage, Object... args) {
        return new SendMessage(chatId, localMessageService.getMessage(replyMessage, args));
    }
}
