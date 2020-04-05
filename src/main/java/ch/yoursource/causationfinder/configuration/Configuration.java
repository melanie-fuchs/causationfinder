package ch.yoursource.causationfinder.configuration;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@EnableWebSecurity
@org.springframework.context.annotation.Configuration
public class Configuration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

//	TODO implement to allow multiple languages
//	//default locale set to US (if key not found in messages_XX.properties-files,
//	// messages.properties will be used as fallback
//	@Bean
//	public LocaleResolver localeResolver() {
//		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
//		sessionLocaleResolver.setDefaultLocale(Locale.US);
//		return sessionLocaleResolver;
//	}
//	
//	// interceptor will switch to a new locale based on value of the lang-parameter appended to a request
//	@Bean
//	public LocaleChangeInterceptor localeChangeInterceptor() {
//		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//		localeChangeInterceptor.setParamName("lang");
//		return localeChangeInterceptor;
//	}
//	
//	// register the LocaleChanceInterceptor-Bean to the application's interceptor 
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(localeChangeInterceptor());
//	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring()
	    .antMatchers("/registration")
	    .antMatchers("/");
	}
	
}
