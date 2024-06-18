package com.tss.controllers;

import com.tss.components.SessionQueryCounter;
import com.tss.entities.User;
import com.tss.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    SessionQueryCounter sessionUserCounter;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        sessionUserCounter.increaseCounter();
        model.addAttribute("counterValue", sessionUserCounter.getCounter());
        return "users.html";
    }

    @GetMapping("/showAddUserForm")
    public String showAddForm(User user) {
        return "addUserForm";
    }

    @PostMapping("/adduser")
    public String addUser(User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addUserForm";
        }

        userService.saveUser(user);

        sessionUserCounter.increaseCounter();
        model.addAttribute("counterValue", sessionUserCounter.getCounter());

        return "redirect:/users";
    }

    @GetMapping("/showEditUserForm/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);

        sessionUserCounter.increaseCounter();
        model.addAttribute("counterValue", sessionUserCounter.getCounter());

        return "editUserForm";
    }

    @PostMapping("/edituser/{id}")
    public String editUser(@PathVariable("id") long id, User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "editUserForm";
        }
        userService.saveUser(user);

        sessionUserCounter.increaseCounter();
        model.addAttribute("counterValue", sessionUserCounter.getCounter());

        return "redirect:/users";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userService.deleteUser(id);

        sessionUserCounter.increaseCounter();
        model.addAttribute("counterValue", sessionUserCounter.getCounter());

        return "redirect:/users";
    }

}
