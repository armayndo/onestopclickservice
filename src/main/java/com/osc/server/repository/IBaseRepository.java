package com.osc.server.repository;

import com.osc.server.model.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by Kerisnarendra on 15/04/2019.
 */

/**
 * Modified by Syarif Hidayat on 22/04/2019.
 * 
 * 1. Add Long data type in template
 */

@Repository
public interface IBaseRepository<T extends BaseModel, Long> extends JpaRepository<T, Serializable> {
	
}
