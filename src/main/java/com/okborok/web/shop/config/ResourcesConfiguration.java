package com.okborok.web.shop.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Created on 02.11.2017.
 */
@Configuration
public class ResourcesConfiguration {

    /**
     * @return message source bean
     */
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        final ReloadableResourceBundleMessageSource res = new ReloadableResourceBundleMessageSource();
        res.setDefaultEncoding("UTF-8");
        res.addBasenames("classpath:messages/messages", "classpath:messages/titles");
        return res;
    }

    /**
     * Set default locale.
     */
    @Bean
    public LocaleResolver localeResolver() {
        final SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("ru", "RU"));
        return localeResolver;
    }

    @Bean
    public MessageSourceAccessor getMessageSourceAccessor(final MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource);
    }
}
