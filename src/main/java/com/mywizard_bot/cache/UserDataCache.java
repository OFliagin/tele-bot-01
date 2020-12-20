package com.mywizard_bot.cache;

import com.mywizard_bot.botapi.BotState;
import com.mywizard_bot.botapi.handlers.fillingprofile.UserProfileData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Component
@Slf4j
public class UserDataCache implements DataCache {
    private Map<Integer, BotState> usersBotState = new HashMap<>();
    private Map<Integer, UserProfileData> usersUserProfileData = new HashMap<>();

    @Override
    public void setUsersCurrentBotState(int userId, BotState bootState) {
        log.debug("setUsersCurrentBotState userId {}, bootState {}", userId, bootState.name());
        usersBotState.put(userId, bootState);
    }

    @Override
    public BotState getUsersCurrentBotState(int userId) {
        log.debug("getUsersCurrentBotState userId {}", userId);
        BotState bootState = usersBotState.get(userId);
        if (isNull(bootState)) {
            bootState = BotState.ASK_DESTINY;
        }
        return bootState;
    }

    @Override
    public void saveUserProfileData(int userId, UserProfileData userProfileData) {
        log.debug("saveUserProfileData userId {}, userProfileData {}", userId, userProfileData);

        usersUserProfileData.put(userId, userProfileData);
    }

    @Override
    public UserProfileData getUserProfileData(int userId) {
        log.debug("getUserProfileData userId {}", userId);

        UserProfileData userProfileData = usersUserProfileData.get(userId);
        return isNull(userProfileData)
                ? new UserProfileData()
                : userProfileData;
    }
}
