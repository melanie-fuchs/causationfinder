package ch.yoursource.causationfinder;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
public class CausationfinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CausationfinderApplication.class, args);
	}

	@Bean
	public MessageSource messageSource() {
	    Locale.setDefault(Locale.ENGLISH);
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.addBasenames("classpath:/messages");
		//messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
