package com.mywizard_bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bot {
    private String webHookPath;
    private String botUserName;
    private String botToken;
}
