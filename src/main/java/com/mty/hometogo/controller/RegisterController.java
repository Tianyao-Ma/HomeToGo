package com.mty.hometogo.controller;

import com.mty.hometogo.model.User;
import com.mty.hometogo.model.UserRole;
import com.mty.hometogo.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// @controller + @ResponseBody => return the response as Json
@RestController
public class RegisterController {
    private RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register/guest")
    public void addGuest(@RequestBody User user) {
        registerService.add(user, UserRole.ROLE_GUEST);
    }

    @PostMapping("/register/host")
        public void addHost(@RequestBody User user) {
            registerService.add(user, UserRole.ROLE_HOST);
        }
    }

