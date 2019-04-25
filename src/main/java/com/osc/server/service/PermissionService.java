package com.osc.server.service;

import com.osc.server.model.Permission;
import com.osc.server.model.Role;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Kerisnarendra on 24/04/2019.
*/
@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionService extends BaseService<Permission> {
}

