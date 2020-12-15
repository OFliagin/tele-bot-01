package com.example.tele.bot.api;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    Map<BootState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(inputMessageHandler -> this.messageHandlers.put(inputMessageHandler.getHandlerName(), inputMessageHandler));
    }


    public InputMessageHandler findMessageHandler(BootState currentState) {
        if (isFillingProfileState(currentState)) {
            return messageHandlers.get(BootState.FILLING_PROFILE);
        }

        return messageHandlers.get(currentState);
    }

    public SendMessage processInputMessage(BootState currentState, Message message) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handler(message);
    }

    private boolean isFillingProfileState(BootState currentState) {
        switch (currentState) {
            case ASK_DESTINY:
            case FILLING_PROFILE:
                case ASK_AGE:
                case ASK_NAME:
                case ASK_GENDER:
                case ASK_NUMBER:
                case ASK_MOVIE:
                case ASK_SONG:
                case ASK_COLOR:
                case PROFILE_FILLED:
                    return true;
            default: return false;
        }
    }

}
