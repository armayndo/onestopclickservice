package com.osc.server.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;

import com.osc.server.model.Role;
import com.osc.server.model.User;

/**
 * Created by Kerisnarendra on 15/04/2019.
 */

/**
 * Modified by Syarif Hidayat on 22/04/2019.
 * 
 * 1. Add findByUsername method
 */

public interface IUserRepository extends IBaseRepository<User, Long>{
	User findByUsername(String username);
	Optional<User> findByEmail(String email);
}
