package com.sharom.wrm.service;

import com.sharom.wrm.common.context.LangContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {

    private final MessageSource messageSource;

    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String key) {

        String lang = LangContext.getLang();

        Locale locale = switch (lang) {
            case "ru" -> new Locale("ru");
            case "uz" -> new Locale("uz");
            default -> new Locale("en");
        };

        System.out.println("LANG = " + lang);
        System.out.println("MESSAGE = " + messageSource.getMessage(key, null, key, locale));

        return messageSource.getMessage(key, null, key, locale);
    }

}