package ch.yoursource.causationfinder.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

    // TODO implement to allow multiple languages
    //default locale set to US (if key not found in messages_XX.properties-files,
    // messages.properties will be used as fallback
//  @Bean
//  public LocaleResolver localeResolver() {
//      SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
//      sessionLocaleResolver.setDefaultLocale(Locale.US);
//      return sessionLocaleResolver;
//  }
    

//  
//  @Bean
//  public LocalValidatorFactoryBean getValidator() {
//      LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
//      bean.setValidationMessageSource(messageSource());
//      return bean;
//  }
//  
//  
    //
//  // interceptor will switch to a new locale based on value of the lang-parameter appended to a request
//  @Bean
//  public LocaleChangeInterceptor localeChangeInterceptor() {
//      LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//      localeChangeInterceptor.setParamName("lang");
//      return localeChangeInterceptor;
//  }
//  
//  // register the LocaleChanceInterceptor-Bean to the application's interceptor 
//  @Override
//  public void addInterceptors(InterceptorRegistry registry) {
//      registry.addInterceptor(localeChangeInterceptor());
//  }
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(). // restrict access based on the HttpServletRequest
        antMatchers("/").permitAll().
        antMatchers("/registration").anonymous(). 
        anyRequest().authenticated() // any request: the user MUST be logged in! (authenticated)
        .and()
        .formLogin()
        .defaultSuccessUrl("/userhome", false);
        //.loginProcessingUrl("/authenticateTheUser") // login form should POST data to this url for processing (check user IF and PW) --> NO CONTROLLER REQUEST MAPPING REQUIRED FOR THIS! SPRING MAGIC! But the name must be exactly written like that! /authenticateTheUser
        //.permitAll() // makes that everyone can see this login page (its not hidden to anyone)
        //.and()
        //.logout().permitAll(); // add logout support for default URL /logout
    }
    
}
