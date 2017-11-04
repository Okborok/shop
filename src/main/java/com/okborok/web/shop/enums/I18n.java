package com.okborok.web.shop.enums;

import lombok.AllArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created on 02.11.2017.
 */
public enum I18n {
    ;

    private static MessageSourceAccessor messages;

    public static String getMessage(final String code) {
        return messages.getMessage(code);
    }

    @AllArgsConstructor
    @Component
    public static class InternationalizationInjector {
        private final MessageSourceAccessor messageSourceAccessor;

        @PostConstruct
        public void postConstruct() {
            messages = messageSourceAccessor;
        }
    }
}
