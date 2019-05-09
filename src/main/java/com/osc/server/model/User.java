package com.osc.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    private String imageUrl;
    
    @Column(nullable = false)
    private Boolean emailVerified = false;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

	/*Added by Syarif to accomodate Spring Security*/
	private String username;
	private String role;
	private boolean isEnabled;

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

//	public Set<Role> getRoles() {return roles;}
//	public void setRoles(Set<Role> roles) {this.roles = roles;}
	@OneToMany(mappedBy = "user")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Set<ProductReview> productReviews;
}
