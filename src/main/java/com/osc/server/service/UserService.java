package com.osc.server.service;

import com.osc.server.model.User;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Kerisnarendra on 15/04/2019.
*/
@RestController
@RequestMapping("/api/v1/users")
public class UserService extends BaseService<User> {
}

