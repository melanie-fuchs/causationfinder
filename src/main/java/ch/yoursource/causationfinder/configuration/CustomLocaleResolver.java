package ch.yoursource.causationfinder.configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import io.micrometer.core.instrument.util.StringUtils;

public class CustomLocaleResolver extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {
    final List<Locale> LOCALES = Arrays.asList(new Locale("en"), new Locale("de"));
    
    @Override
    public Locale resolveLocale(HttpServletRequest request) { 
        if (StringUtils.isEmpty(request.getHeader("Accept-Language"))) {
            return new Locale("en");
        }
        
        List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));
        Locale locale = Locale.lookup(list, LOCALES);
        
        if (locale == null) {
            locale = new Locale("en");
        }
        
        return locale;
    }
}
