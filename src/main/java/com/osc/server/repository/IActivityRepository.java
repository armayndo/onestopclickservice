package com.osc.server.repository;

import java.util.List;

import com.osc.server.model.Activity;
import com.osc.server.model.User;

public interface IActivityRepository extends IBaseRepository<Activity, Long>{
	List<Activity> findById(Long id);
}

