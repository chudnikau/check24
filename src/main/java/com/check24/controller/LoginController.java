package com.check24.controller;

import com.check24.domain.User;
import com.check24.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final User currUser;

    private final UserService userService;

    @Autowired
    public LoginController(User currUser, UserService userService) {
        this.currUser = currUser;
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute("currentUser") User user) {
        currUser.setUserId(user.getUserId());
        currUser.setUserName(user.getUserName());
        return "redirect:/films/all";
    }

    @GetMapping(value = "/logout")
    public String logout() {
        currUser.setUserId(null);
        currUser.setUserName(null);
        return "redirect:/login/users";
    }

    @GetMapping(value = "/users")
    public String allUsers(Model model) {
        model.addAttribute("users",
                userService.allUsers());
        model.addAttribute("currentUser", new User());
        return "login";
    }

}
