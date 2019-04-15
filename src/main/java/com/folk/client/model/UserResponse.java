package com.folk.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.folk.server.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kerisnarendra on 15/04/2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse extends BaseResponse<User> {
}
