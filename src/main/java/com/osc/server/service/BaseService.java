package com.osc.server.service;

import com.osc.server.model.BaseModel;
import com.osc.server.repository.IBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Kerisnarendra on 15/04/2019.
 */

/**
 * Modified by Syarif Hidayat on 22/04/2019.
 * 
 * 1. Add Long data type in IBaseRepository
 */

public class BaseService<T extends BaseModel> extends CrossOriginService  {
    @Autowired
    private IBaseRepository<T, Long> repository;

    @GetMapping
    public Page<T> read(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @PostMapping
    public T create(@RequestBody T entity) {
        return repository.save(entity);
    }

    @PutMapping("/{id}")
    public T update(@PathVariable(value = "id") long id, @RequestBody T entity) {
        return repository.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(value = "id") long id) {
        repository.deleteById(id);
    }

    @GetMapping("/{id}")
    public T get(@PathVariable(value = "id") long id) {
        return repository.getOne(id);
    }
}
