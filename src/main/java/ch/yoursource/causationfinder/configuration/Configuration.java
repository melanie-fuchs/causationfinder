package ch.yoursource.causationfinder.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

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
    
    @Bean
    public LocaleResolver localeResolver() {
        return new CustomLocaleResolver();
    }

    // interceptor will switch to a new locale based on value of the lang-parameter appended to a request
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    // register the LocaleChanceInterceptor-Bean to the application's interceptor 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(). // restrict access based on the HttpServletRequest
        antMatchers("/", "/causationfinder.png", "/favicon.ico").permitAll().
        antMatchers("/registration", "/user-registration/confirm-account").anonymous().
        anyRequest().authenticated() // any request: the user MUST be logged in! (authenticated)
        .and()
        .formLogin()
        .defaultSuccessUrl("/userhome", false)
        .and()
        .logout().permitAll() // add logout support for default URL /logout
        .logoutSuccessUrl("/");
    }
    
}
