package com.example.tele.bot.api.handlers.filingprofile;

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
public class FilingProfileHandler implements InputMessageHandler {
    private final DataCache cache;
    private final ReplyMessageService messageService;

    public FilingProfileHandler(DataCache cache, ReplyMessageService messageService) {
        this.cache = cache;
        this.messageService = messageService;
    }

    @Override
    public BootState getHandlerName() {
        return BootState.FILLING_PROFILE;
    }

    @Override
    public SendMessage handler(Message message) {
        if (cache.getUsersCurrentBotState(message.getFrom().getId()).equals(BootState.FILLING_PROFILE)) {
            cache.setUsersCurrentBotState(message.getFrom().getId(), BootState.ASK_NAME);
        }
        return processUsersInput(message);
    }

    private SendMessage processUsersInput(Message inputMessage) {
        String userAnswer = inputMessage.getText();
        int userId = inputMessage.getFrom().getId();
        long chatId = inputMessage.getChatId();
        UserProfileData profileData = cache.getUserProfileData(userId);
        BootState bootState = cache.getUsersCurrentBotState(userId);
        SendMessage replay = null;

        log.debug("userAnswer {}, userId {}, chatId {}, bootState {}", userAnswer, userId, chatId, bootState);

        if (bootState.equals(BootState.ASK_NAME)) {
            log.debug("state ASK_NAME");
            replay = messageService.getReplyMessage(chatId, "reply_askName");
            cache.setUsersCurrentBotState(userId, BootState.ASK_AGE);
        }


        if (bootState.equals(BootState.ASK_AGE)) {
            log.debug("state ASK_AGE");
            profileData.setName(userAnswer);
            replay = messageService.getReplyMessage(chatId, "reply_askAge");
            cache.setUsersCurrentBotState(userId, BootState.ASK_GENDER);
        }

        if (bootState.equals(BootState.ASK_GENDER)) {
            log.debug("state ASK_GENDER");
            profileData.setAge(Integer.parseInt(userAnswer));
            replay = messageService.getReplyMessage(chatId, "reply_askGender");
            cache.setUsersCurrentBotState(userId, BootState.ASK_NUMBER);
        }

        if (bootState.equals(BootState.ASK_NUMBER)) {
            log.debug("state ASK_NUMBER");
            profileData.setGender(userAnswer);
            replay = messageService.getReplyMessage(chatId, "reply_askNumber");
            cache.setUsersCurrentBotState(userId, BootState.ASK_COLOR);
        }
        if (bootState.equals(BootState.ASK_COLOR)) {
            log.debug("state ASK_COLOR");
            profileData.setNumber(Integer.parseInt(userAnswer));
            replay = messageService.getReplyMessage(chatId, "reply_askColor");
            cache.setUsersCurrentBotState(userId, BootState.ASK_MOVIE);
        }

        if (bootState.equals(BootState.ASK_MOVIE)) {
            log.debug("state ASK_MOVIE");
            profileData.setColor(userAnswer);
            replay = messageService.getReplyMessage(chatId, "reply_askMovie");
            cache.setUsersCurrentBotState(userId, BootState.ASK_SONG);
        }

        if (bootState.equals(BootState.ASK_SONG)) {
            log.debug("state ASK_SONG");
            profileData.setMovie(userAnswer);
            replay = messageService.getReplyMessage(chatId, "reply_askSong");
            cache.setUsersCurrentBotState(userId, BootState.PROFILE_FILLED);
        }
        if (bootState.equals(BootState.PROFILE_FILLED)) {
            log.debug("state PROFILE_FILLED");
            profileData.setSong(userAnswer);
            replay = new SendMessage(chatId, "Данные по вашей анкете " +  profileData.toString());
            cache.setUsersCurrentBotState(userId, BootState.ASK_SONG);
        }
        cache.saveUserProfileData(userId, profileData);

        log.debug("replay to user {}", replay);

        return replay;
    }
}
