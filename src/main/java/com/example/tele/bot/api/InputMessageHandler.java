package com.example.tele.bot.api;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface InputMessageHandler {

    SendMessage handler(Message message);
    BootState getHandlerName();
}
