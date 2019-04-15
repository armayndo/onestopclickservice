package com.folk.server.repository;

import com.folk.server.model.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by Kerisnarendra on 15/04/2019.
 */
@Repository
public interface IBaseRepository<T extends BaseModel> extends JpaRepository<T, Serializable> {
}
