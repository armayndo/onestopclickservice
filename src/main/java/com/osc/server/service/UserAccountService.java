package com.osc.server.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.osc.exception.ResourceNotFoundException;
import com.osc.server.model.User;
import com.osc.server.model.UserAccount;
import com.osc.server.repository.IUserAccountRepository;
import com.osc.server.repository.IUserRepository;

@RestController
@RequestMapping("/api/v1/useraccounts")
public class UserAccountService extends BaseService<UserAccount>{

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IUserAccountRepository userAccountRepository;
	
	@GetMapping("/balance")
	public UserAccount getUserAccountByUserName(@RequestParam("username") String username) {
		Optional<User> user = Optional.of(userRepository.findByUsername(username));
		if(!user.isPresent()) {
			throw new ResourceNotFoundException("User","username",username);
		}
		Optional<UserAccount> userAccount = userAccountRepository.findByUserId(user.get().getId());
		if(!userAccount.isPresent()) {
			UserAccount newUserAccount = new UserAccount();
			newUserAccount.setUser(user.get());
			newUserAccount.setBalance(BigDecimal.ZERO);
			return userAccountRepository.save(newUserAccount);
		}
		return userAccount.get();
	}
}
