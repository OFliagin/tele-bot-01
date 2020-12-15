package com.example.tele.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
public class LocalMessageService {

    private final Locale locale;
    private final MessageSource messageSource;

    @Autowired
    public LocalMessageService(@Value("${bot.locale.lang:ru-RU}") String localeTag, MessageSource messageSource) {
        this.locale = Locale.forLanguageTag(localeTag);
        this.messageSource = messageSource;
    }

    public String getMessage(String message) {
        log.debug("get message [{}] for local {}", message ,locale.getLanguage());
        String res = messageSource.getMessage(message, null, locale);
        log.debug("got message [{}] for local {}", res ,locale.getLanguage());
        return res;
    }

    public String getMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, locale);
    }
}
