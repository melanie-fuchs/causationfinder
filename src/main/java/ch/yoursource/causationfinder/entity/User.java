package ch.yoursource.causationfinder.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "user_table")
public class User {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@NotEmpty
	@NotNull
	@Size(min=3, max=20)
	@Column(name = "username")
	private String username;

	@NotEmpty
	@NotNull
	@Size(min=8, max=64)
	@Column(name = "password")
	private String password;
	
	@Transient
	private String passwordConfirm;

	@NotEmpty
	@NotNull
	@Column(name = "first_name")
	private String firstName;

	@NotEmpty
	@NotNull
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "birthdate")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date birthdate;
	
	@NotNull
	@NotEmpty
	@Column(name = "email")
	private String email;

    @ManyToMany
    @JoinTable( 
        name = "users_roles",
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
    
	public User() {}


	public User(@NotNull int id, @NotNull String username, @NotNull String password, String firstName, String lastName,
			Date birthdate, @NotNull String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.email = email;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Date getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", birthdate=" + birthdate + ", email=" + email + "]";
	}


}
