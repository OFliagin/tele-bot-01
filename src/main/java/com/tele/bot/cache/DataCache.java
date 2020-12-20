package com.tele.bot.cache;

import com.tele.bot.api.BootState;
import com.tele.bot.api.handlers.filingprofile.UserProfileData;

public interface DataCache {
    void setUsersCurrentBotState(int userId, BootState bootState);
    BootState getUsersCurrentBotState(int userId);
    void saveUserProfileData(int userId, UserProfileData userProfileData);
    UserProfileData getUserProfileData(int userId);
}
