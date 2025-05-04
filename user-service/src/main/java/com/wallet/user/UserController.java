package com.wallet.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        return userService.create(userCreateRequest.to());
    }


    @GetMapping("/user")
    public User getUser(@RequestParam("id") int id) {
        return userService.getUserById(id);
    }



}
