package ch.yoursource.causationfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CausationfinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CausationfinderApplication.class, args);
	}

//	@Bean
//	public MessageSource messageSource() {
//	    Locale.setDefault(Locale.ENGLISH);
//		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//		messageSource.addBasenames("messages");
//	    messageSource.setUseCodeAsDefaultMessage(true);
//		messageSource.setDefaultEncoding("UTF-8");
//		return messageSource;
//	}
}
