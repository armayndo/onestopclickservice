package com.osc.server.service;

import com.osc.exception.ResourceNotFoundException;
import com.osc.server.model.Permission;
import com.osc.server.model.Role;
import com.osc.server.model.User;
import com.osc.server.repository.IPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by Kerisnarendra on 25/04/2019.
*/

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionService extends BaseService<Permission> {
    @Autowired
    private IPermissionRepository permissionRepository;

    @GetMapping("/{id}/roles")
    public Set<Role> getRoles(@PathVariable Long id){
        // Finds permission by id and returns it's recorded roles, otherwise throws exception
        return this.permissionRepository.findById(id).map((permission) -> {
            return permission.getRoles();
        }).orElseThrow(() -> new ResourceNotFoundException("Permission", id));
    }
}

