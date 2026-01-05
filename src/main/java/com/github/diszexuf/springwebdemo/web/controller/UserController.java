package com.github.diszexuf.springwebdemo.web.controller;

import com.github.diszexuf.springwebdemo.model.User;
import com.github.diszexuf.springwebdemo.service.UserService;
import com.github.diszexuf.springwebdemo.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserDto> users = userService.findAll()
                .stream()
                .map(it -> new UserDto(it.getId(), it.getName(), it.getAge()))
                .toList();
        model.addAttribute("users", users);

        return "user/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new UserDto());

        return "user/add-form";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute UserDto user) {
        userService.create(new User(user.getId(), user.getName(), user.getAge()));
        return "redirect:/ui/users";
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.deleteById(id);

        return "redirect:/ui/users";
    }

}
