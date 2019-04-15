package com.folk.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kerisnarendra on 15/04/2019.
 */
@Controller
public class DefaultController {
    @RequestMapping("/")
    public String index() {
        return "index.html";
    }
}
