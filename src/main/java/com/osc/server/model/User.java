package com.osc.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kerisnarendra on 15/04/2019.
 */

/**
 * Modified by Syarif Hidayat on 22/04/2019.
 * 1. add username, role and isEnabled field
 * 2. add getter/setter methods.
 */

@Setter
@Getter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

	@ManyToMany(
			fetch = FetchType.LAZY,
			cascade = {CascadeType.MERGE, CascadeType.PERSIST}
	)
	@JoinTable(
			name = "user_roles",
			joinColumns = {@JoinColumn(name = "user_id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id")}
	)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Set<Role> roles = new HashSet<>();
   
    
    /*Added by Syarif to accomodate Spring Security*/
    private String username;
    private String role;
    private boolean isEnabled;
    
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	public Set<Role> getRoles() {return roles;}
	public void setRoles(Set<Role> roles) {this.roles = roles;}
    
}
