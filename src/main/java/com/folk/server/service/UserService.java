package com.folk.server.service;

import com.folk.common.Common;
import com.folk.server.model.User;
import com.folk.server.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kerisnarendra on 15/04/2019.
*/
@RestController
@RequestMapping("/api/v1/users")
public class UserService extends BaseService<User> {
}

