package com.mywizard_bot.cache;

import com.mywizard_bot.botapi.BotState;
import com.mywizard_bot.botapi.handlers.fillingprofile.UserProfileData;


public interface DataCache {
    void setUsersCurrentBotState(int userId, BotState botState);

    BotState getUsersCurrentBotState(int userId);

    UserProfileData getUserProfileData(int userId);

    void saveUserProfileData(int userId, UserProfileData userProfileData);
}
