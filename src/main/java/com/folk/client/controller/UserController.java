package com.folk.client.controller;

import com.folk.client.model.UserResponse;
import com.folk.common.Common;
import com.folk.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kerisnarendra on 15/04/2019.
 */
@Controller
public class UserController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Common common;

    @GetMapping("/users")
    public String index(Model model) {
        ResponseEntity<UserResponse> response;
        String url = common.SERVICE_BASE_URL + common.SERVICE_BASE_URL_USER;
        RestTemplate restTemplate = new RestTemplate();
        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<UserResponse>(){});
            model.addAttribute("users", response.getBody().content);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        return "user/index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "user/add";
    }

    @PostMapping("/add")
    public String create(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/add";
        }

        String url = common.SERVICE_BASE_URL + common.SERVICE_BASE_URL_USER;
        restTemplate.postForLocation(url, user, User.class);

        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        String url = common.SERVICE_BASE_URL + common.SERVICE_BASE_URL_USER + "/" + id;
        restTemplate.delete(url);

        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") long id, Model model) {
        String url = common.SERVICE_BASE_URL + common.SERVICE_BASE_URL_USER + "/" + id;
        User user = restTemplate.getForObject(url, User.class);

        model.addAttribute("user", user);
        return "user/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") long id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "user/edit";
        }
        String url = common.SERVICE_BASE_URL + common.SERVICE_BASE_URL_USER + "/" + id;
        restTemplate.put(url, user);
        return "redirect:/users";
    }
}
