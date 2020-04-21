package ch.yoursource.causationfinder.setup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import ch.yoursource.causationfinder.entity.PredefinedParameter;
import ch.yoursource.causationfinder.entity.Role;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.repository.PredefinedParameterRepository;
import ch.yoursource.causationfinder.repository.RoleRepository;
import ch.yoursource.causationfinder.repository.UserRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PredefinedParameterRepository predefinedParameterRepository;
	private BCryptPasswordEncoder passwordEncoder;
	private MessageSource messageSource;
	
	private boolean setupCompleted = false;
	
    @Value("${admin.password}") 
    private String adminPassword;
    
    @Value("${admin.userName}") 
    private String adminUserName;
    
    @Value("${admin.email}") 
    private String adminEmail;
    
    @Value("${setup.roles.user}")
    private String userRole;
    
    @Value("${setup.roles.admin}")
    private String adminRole;
	
	@Autowired
	public SetupDataLoader(
		UserRepository userRepository,
		RoleRepository roleRepository,
		PredefinedParameterRepository predefinedParameterRepository,
		BCryptPasswordEncoder passwordEncoder,
		MessageSource messageSource
	) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.predefinedParameterRepository = predefinedParameterRepository;
		this.passwordEncoder = passwordEncoder;
		this.messageSource = messageSource;
	}
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (setupCompleted) {
			return;
		}
		
		Role userRole = this.createRoleIfNotFound(this.userRole);
		Role adminRole = this.createRoleIfNotFound(this.adminRole);
		
		Set<Role> userAdminRoles = new HashSet<Role>();
		userAdminRoles.add(userRole);
		userAdminRoles.add(adminRole);
		
		if (userRepository.findByUsername(this.adminUserName) == null) {
			createAdminUser(userAdminRoles);
		}
		
		this.definePrefedinedParameter();

		setupCompleted = true;		
	}
	
    private void definePrefedinedParameter() {
        
        int tableSize = predefinedParameterRepository.findAll().size();
        
        if(tableSize == 0) {
            List<PredefinedParameter> predefinedParameters = new ArrayList<PredefinedParameter>();
            predefinedParameters.add(new PredefinedParameter(
                    ParameterType.NUMERIC,
                    this.getGermanMessage("messages.predefined.hoursSlept.name"),
                    this.getGermanMessage("messages.predefined.hoursSlept.description"),
                    this.getEnglishMessage("messages.predefined.hoursSlept.name"),
                    this.getEnglishMessage("messages.predefined.hoursSlept.description"),
                    0.0,
                    24.0
            ));
            
            predefinedParameters.add(new PredefinedParameter(
                    ParameterType.NUMERIC,
                    this.getGermanMessage("messages.predefined.quality.name"),
                    this.getGermanMessage("messages.predefined.quality.description"),
                    this.getEnglishMessage("messages.predefined.quality.name"),
                    this.getEnglishMessage("messages.predefined.quality.description"),
                    0.0,
                    10.0
            ));

            predefinedParameters.add(new PredefinedParameter(
                    ParameterType.NUMERIC,
                    this.getGermanMessage("messages.predefined.happiness.name"),
                    this.getGermanMessage("messages.predefined.happiness.description"),
                    this.getEnglishMessage("messages.predefined.happiness.name"),
                    this.getEnglishMessage("messages.predefined.happiness.description"),
                    0.0,
                    10.0
            ));

            predefinedParameters.add(new PredefinedParameter(
                    ParameterType.BOOLEAN,
                    this.getGermanMessage("messages.predefined.gluten.name"),
                    this.getGermanMessage("messages.predefined.gluten.description"),
                    this.getEnglishMessage("messages.predefined.gluten.name"),
                    this.getEnglishMessage("messages.predefined.gluten.description"),
                    null,
                    null
            ));

            predefinedParameters.add(new PredefinedParameter(
                    ParameterType.BOOLEAN,
                    this.getGermanMessage("messages.predefined.sweets.name"),
                    this.getGermanMessage("messages.predefined.sweets.description"),
                    this.getEnglishMessage("messages.predefined.sweets.name"),
                    this.getEnglishMessage("messages.predefined.sweets.description"),
                    null,
                    null
            ));

            predefinedParameters.add(new PredefinedParameter(
                    ParameterType.BOOLEAN,
                    this.getGermanMessage("messages.predefined.sports.name"),
                    this.getGermanMessage("messages.predefined.sports.description"),
                    this.getEnglishMessage("messages.predefined.sports.name"),
                    this.getEnglishMessage("messages.predefined.sports.description"),
                    null,
                    null
            ));

            predefinedParameters.add(new PredefinedParameter(
                    ParameterType.BOOLEAN,
                    this.getGermanMessage("messages.predefined.dairy.name"),
                    this.getGermanMessage("messages.predefined.dairy.description"),
                    this.getEnglishMessage("messages.predefined.dairy.name"),
                    this.getEnglishMessage("messages.predefined.dairy.description"),
                    null,
                    null
            ));

            predefinedParameters.add(new PredefinedParameter(
                    ParameterType.STRING,
                    this.getGermanMessage("messages.predefined.diary.name"),
                    this.getGermanMessage("messages.predefined.diary.description"),
                    this.getEnglishMessage("messages.predefined.diary.name"),
                    this.getEnglishMessage("messages.predefined.diary.description"),
                    null,
                    null
            ));           
            
            predefinedParameterRepository.saveAll((Iterable<PredefinedParameter>)predefinedParameters);
        }
    }
    
    private String getGermanMessage(String key) {
        return this.messageSource.getMessage(new DefaultMessageSourceResolvable(key), Locale.GERMAN);
    }
    
    private String getEnglishMessage(String key) {
        return this.messageSource.getMessage(new DefaultMessageSourceResolvable(key), Locale.ENGLISH);
    }

    private Role createRoleIfNotFound(String name) {
		Role role = roleRepository.findByName(name);
		
		if (role == null) {
			role = new Role();
			role.setName(name);
			roleRepository.save(role);
		}
		
		return role;
	}
	
	private void createAdminUser(Set<Role> roles) {
		User user = new User();
		user.setUsername(this.adminUserName);
		user.setPassword(passwordEncoder.encode(this.adminPassword));
		user.setEmail(this.adminEmail);
		user.setRoles(roles);
		user.setEnabled(true);
		userRepository.save(user);
	}
}
