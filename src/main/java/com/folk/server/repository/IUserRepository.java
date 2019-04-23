package com.folk.server.repository;

import com.folk.server.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

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
}
