package com.osc.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.osc.server.model.User;

/**
 * Created by Kerisnarendra on 15/04/2019.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse extends BaseResponse<User> {
}
