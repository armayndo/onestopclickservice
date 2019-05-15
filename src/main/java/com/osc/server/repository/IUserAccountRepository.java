package com.osc.server.repository;

import java.util.Optional;

import com.osc.server.model.UserAccount;

public interface IUserAccountRepository extends IBaseRepository<UserAccount, Long>{
	public Optional<UserAccount> findByUserId(Long userID);
}
