package com.osc.server.repository;

import com.osc.server.model.Token;
import com.osc.server.model.User;

/**
 * Created by Syarif Hidayat on 03/05/2019.
 */

public interface ITokenRepository extends IBaseRepository<Token, Long>{
	Token findByUsername(String username);
}
