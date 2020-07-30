package com.company.project.i18n;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author
 * 2020-04-21 20:15
 **/
@Component
public class MessageSources implements MessageSource {

    private MessageSource messageSource;

    public MessageSources(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private String get(String code) {
        return get(code, "");
    }

    private String get(String code, String args) {
        if (messageSource == null) {
            return code;
        }
        return messageSource.getMessage(code, new String[] {args}, null,
                LocaleContextHolder.getLocale());
    }

    public String get(String code, String... args) {
        if (messageSource == null) {
            return code;
        }
        return messageSource.getMessage(code, args, null, LocaleContextHolder.getLocale());
    }

    public String getMessage(String code, Object[] args, String defaultMessage) {
        if (messageSource == null) {
            return code;
        }
        //Locale.CHINESE
        return messageSource.getMessage(code, args, defaultMessage,
                LocaleContextHolder.getLocale());
    }

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        if (messageSource == null) {
            return code;
        }
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale)
            throws NoSuchMessageException {
        if (messageSource == null) {
            return code;
        }
        return messageSource.getMessage(code, args, locale);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale)
            throws NoSuchMessageException {
        return messageSource.getMessage(resolvable, locale);
    }

    public boolean hasCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return false;
        }
        return !code.equals(get(code));

    }

}
