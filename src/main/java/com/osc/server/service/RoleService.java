package com.osc.server.service;

import com.osc.exception.ResourceNotFoundException;
import com.osc.server.model.Permission;
import com.osc.server.model.Role;
import com.osc.server.model.User;
import com.osc.server.repository.IPermissionRepository;
import com.osc.server.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Created by Kerisnarendra on 23/04/2019.
*/
@CrossOrigin(origins="http://localhost:3000") // added by Tommy 26/04/2019
@RestController
@RequestMapping("/api/v1/roles")
public class RoleService extends BaseService<Role> {
    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IPermissionRepository permissionRepository;

    @GetMapping("/{id}/users")
    public Set<User> getUsers(@PathVariable Long id){
        // Finds role by id and returns it's recorded users, otherwise throws exception
        return this.roleRepository.findById(id).map((role) -> {
            return role.getUsers();
        }).orElseThrow(() -> new ResourceNotFoundException("Role", id));
    }

    @GetMapping("/{roleId}/permissions")
    public Set<Permission> getPermissions(@PathVariable Long roleId){
        // Finds role by id and returns it's recorded permissions, otherwise throws exception
        return this.roleRepository.findById(roleId).map((role) -> {
            return role.getPermissions();
        }).orElseThrow(() -> new ResourceNotFoundException("Role", roleId));
    }

    @PostMapping("/{id}/permissions/{permissionId}") // Path variable names must match with method's signature variables.
    public Set<Permission> addPermission(@PathVariable Long id, @PathVariable Long permissionId){
        // Finds a persisted permission
        Permission permission = this.permissionRepository.findById(permissionId).orElseThrow(
                () -> new ResourceNotFoundException("Permission", permissionId)
        );

        // Finds a role and adds the given permission to the role's set.
        return this.roleRepository.findById(id).map((role) -> {
            role.getPermissions().add(permission);
            return this.roleRepository.save(role).getPermissions();
        }).orElseThrow(() -> new ResourceNotFoundException("Role", id));
    }

    @DeleteMapping("/{id}/permissions/{permissionId}") // Path variable names must match with method's signature variables.
    public Set<Permission> removePermission(@PathVariable Long id, @PathVariable Long permissionId){
        // Finds a persisted permission
        Permission permission = this.permissionRepository.findById(permissionId).orElseThrow(
                () -> new ResourceNotFoundException("Permission", permissionId)
        );

        // Finds a role and adds the given permission to the role's set.
        return this.roleRepository.findById(id).map((role) -> {
            role.getPermissions().remove(permission);
            return this.roleRepository.save(role).getPermissions();
        }).orElseThrow(() -> new ResourceNotFoundException("Role", id));
    }
}

