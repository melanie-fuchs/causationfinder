package ch.yoursource.causationfinder.setup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
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
		BCryptPasswordEncoder passwordEncoder
	) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.predefinedParameterRepository = predefinedParameterRepository;
		this.passwordEncoder = passwordEncoder;
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
            predefinedParameters.add(new PredefinedParameter(ParameterType.NUMERIC, "Hours Slept Per Night", "Number of hours slept last night", 0.0, 24.0));
            predefinedParameters.add(new PredefinedParameter(ParameterType.NUMERIC, "Quality Of Sleep", "The quality of my sleep last night", 0.0, 10.0));
            predefinedParameters.add(new PredefinedParameter(ParameterType.NUMERIC, "Happiness", "My happiness today", 0.0, 10.0));
            predefinedParameters.add(new PredefinedParameter(ParameterType.BOOLEAN, "Gluten Eaten", "Did I eat anything that contains gluten today? Like Bread, Pasta, etc?", null, null));
            predefinedParameters.add(new PredefinedParameter(ParameterType.BOOLEAN, "Processed Sweets Eaten", "Did I eat any processed sweets like chocolate, lollipops, cookies etc today?", null, null));
            predefinedParameters.add(new PredefinedParameter(ParameterType.BOOLEAN, "Did Sports", "Did I do some kind of sports today?", null, null));
            predefinedParameters.add(new PredefinedParameter(ParameterType.STRING, "Diary", "Anything I want to share with my diary ^^", null, null));

            predefinedParameterRepository.saveAll((Iterable<PredefinedParameter>)predefinedParameters);
        }
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
