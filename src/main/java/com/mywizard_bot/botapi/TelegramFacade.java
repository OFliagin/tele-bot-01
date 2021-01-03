package com.mywizard_bot.botapi;

import com.mywizard_bot.botapi.handlers.fillingprofile.UserProfileData;
import com.mywizard_bot.cache.DataCache;
import com.mywizard_bot.service.MainMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Component
@Slf4j
public class TelegramFacade {
    private final BotStateContext botStateContext;
    private final DataCache userDataCache;
    private final MainMenuService mainMenuService;

    @Autowired
    public TelegramFacade(BotStateContext botStateContext, DataCache userDataCache, MainMenuService mainMenuService) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.mainMenuService = mainMenuService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {

        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            CallbackQuery callvackQuery = update.getCallbackQuery();

            log.info("New callbackQuery from user {}, userId {}, with data {}",
                    update.getCallbackQuery().getFrom().getUserName(),
                    update.getCallbackQuery().getFrom().getId(),
                    update.getCallbackQuery().getData());

            return processCallbackQuery(callvackQuery);
        }

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
            case "Получить предсказание":
                bootState = BotState.FILLING_PROFILE;
                break;
            case "Помощь":
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

    private BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final int userId = buttonQuery.getFrom().getId();

        BotApiMethod<?> callBackAnswer = mainMenuService.getMainMenuMessage(chatId, "Воспользуйтесь главным меню.");
        if (buttonQuery.getData().equals("buttonYes")) {
            callBackAnswer = new SendMessage(chatId, "как тебя зовут");
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_AGE);
        } else if (buttonQuery.getData().equals("buttonNo")) {
            callBackAnswer = sendAnswerCallbackQuery("Возвращайся когда будешь готов", false, buttonQuery);
        } else if (buttonQuery.getData().equals("buttonIwillThink")) {
            callBackAnswer = sendAnswerCallbackQuery("Данная кнопка не поддерживается", true, buttonQuery);
        }

        if (buttonQuery.getData().equals("buttonMan")) {
            UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
            userProfileData.setGender("М");
            userDataCache.saveUserProfileData(userId, userProfileData);
            callBackAnswer = new SendMessage(chatId, "Твоя любимая цифра");


            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_COLOR);
        } else if (buttonQuery.getData().equals("buttonWoman")) {
            UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
            userProfileData.setGender("Ж");
            userDataCache.saveUserProfileData(userId, userProfileData);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_COLOR);
            callBackAnswer = new SendMessage(chatId, "Твоя любимая цифра");

        } else {
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        }

        return callBackAnswer;
    }

    private AnswerCallbackQuery sendAnswerCallbackQuery(String text, boolean alert, CallbackQuery callbackquery) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
        answerCallbackQuery.setShowAlert(alert);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }
}
