package com.example.tele.bot.cache;

import com.example.tele.bot.api.BootState;
import com.example.tele.bot.api.handlers.filingprofile.UserProfileData;

public interface DataCache {
    void setUsersCurrentBotState(int userId, BootState bootState);
    BootState getUsersCurrentBotState(int userId);
    void saveUserProfileData(int userId, UserProfileData userProfileData);
    UserProfileData getUserProfileData(int userId);
}
