package com.mywizard_bot.botapi.handlers.menu;

import com.mywizard_bot.botapi.BotState;
import com.mywizard_bot.botapi.InputMessageHandler;
import com.mywizard_bot.service.message.MainMenuService;
import com.mywizard_bot.service.message.ReplyMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class HelpMenuHandler implements InputMessageHandler {
    private final MainMenuService mainMenuService;
    private final ReplyMessagesService replyMessagesService;


    @Autowired
    public HelpMenuHandler(MainMenuService mainMenuService, ReplyMessagesService replyMessagesService) {
        this.mainMenuService = mainMenuService;
        this.replyMessagesService = replyMessagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        return mainMenuService.getMainMenuMessage(message.getChatId(),
                replyMessagesService.getReplyText("reply.showHelpMenu"));
    }


    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_HELP_MENU;
    }
}
