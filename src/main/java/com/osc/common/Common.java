package com.osc.common;

import org.springframework.stereotype.Component;

/**
 * Created by Kerisnarendra on 15/04/2019.
 */
@Component
public class Common {
    public String RESOURCE_NOT_FOUND_EXCEPTION = "Resource not found";

    public String SERVICE_BASE_URL = "http://localhost:8080/api/v1/";
    public String SERVICE_BASE_URL_USER = "users";

    public static final int MIN_PRODUCT_RATE = 0;
    public static final int MAX_PRODUCT_RATE = 10;
}
