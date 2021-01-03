package com.mywizard_bot.botapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BotStateContext {
    Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> messageHandlers) {
        messageHandlers.forEach(inputMessageHandler -> this.messageHandlers.put(inputMessageHandler.getHandlerName(), inputMessageHandler));
    }


    public InputMessageHandler findMessageHandler(BotState currentState) {
        log.debug("findMessageHandler for currentState {}", currentState);
        InputMessageHandler handler = null;
        if (isFillingProfileState(currentState)) {
            handler = messageHandlers.get(BotState.FILLING_PROFILE);
        } else {
            handler = messageHandlers.get(currentState);
        }

        log.debug("handler {}", handler.getHandlerName());
        return handler;
    }

    public SendMessage processInputMessage(BotState currentState, Message message) {
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }

    private boolean isFillingProfileState(BotState currentState) {
        switch (currentState) {
            case ASK_NAME:
            case ASK_AGE:
            case ASK_GENDER:
            case ASK_NUMBER:
            case ASK_MOVIE:
            case ASK_SONG:
            case ASK_COLOR:
            case FILLING_PROFILE:
            case PROFILE_FILLED:
                return true;
            default:
                return false;
        }
    }
}




