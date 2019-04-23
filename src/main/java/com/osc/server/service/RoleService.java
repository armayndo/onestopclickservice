package com.osc.server.service;

import com.osc.server.model.Role;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Kerisnarendra on 23/04/2019.
*/
@RestController
@RequestMapping("/api/v1/roles")
public class RoleService extends BaseService<Role> {
}

