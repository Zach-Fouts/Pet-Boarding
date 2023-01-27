package com.petboarding.controllers;

import com.petboarding.models.Role;
import com.petboarding.models.data.RoleRepository;
import com.petboarding.models.data.UserRepository;
import com.petboarding.models.dto.AddNewUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import com.petboarding.models.User;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("users")
public class UserManagementController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;



    @GetMapping("/addUserForm")
    public String addUserForm(Model model) {
        List<Role> listRoles = (List<Role>) roleRepository.findAll();
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("addNewUserDTO", new AddNewUserDTO());
        return "/users/addUserForm";
    }

    @PostMapping("/addUserForm")
    public String addUser(@ModelAttribute @Valid AddNewUserDTO addNewUserDTO, BindingResult result, Model model, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add User");
            return "/users/addUserForm";
        }

        User existingUser = userRepository.findByUsername(addNewUserDTO.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            model.addAttribute("title", "Add User");
            return "/users/addUserForm";
        }

        String password = addNewUserDTO.getPassword();
        String verifyPassword = addNewUserDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Register");
            return "/users/addUserForm";
        }

        User newUser = new User(addNewUserDTO.getUsername(), addNewUserDTO.getPassword(), addNewUserDTO.getRole());
        userRepository.save(newUser);

        return "redirect:/users/userList";
    }

    @GetMapping("/userList")
    public String listUsers(Model model) {
        List<User> listUsers = (List<User>) userRepository.findAll();
        model.addAttribute("listUsers", listUsers);

        return "/users/userList";
    }
    @GetMapping("/editUserForm/{id}")
    public String editUser(@PathVariable("id")int id, Model model) {
        Optional<User> user = userRepository.findById(id);
        List<Role> listRoles = (List<Role>) roleRepository.findAll();
        if (user.isPresent()) {
        model.addAttribute("user", user.get());
        model.addAttribute("listRoles", listRoles);
        }
        return "/users/editUserForm";
    }

    @PostMapping("/editUserForm/{id}")
    public String showEditForm(@RequestParam int id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
        model.addAttribute("user", user);
        }
        return "/users/editUserForm";
    }

    @Transactional
    @PostMapping("/save")
    public String submitSaveUser(@RequestParam int id,
                                 @RequestParam String username,
                                 @RequestParam Role role,
                                 @RequestParam(required = false) String password) {

       User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setUsername(username);
            user.setRole(role);
            userRepository.save(user);
        }
        return "redirect:/users/userList";
    }

    @GetMapping("/deleteUserForm/{id}")
    public String deleteUserForm(@PathVariable("id")int id, Model model) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()){
        model.addAttribute("user", user.get());
        }

        return "users/deleteUserForm";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(required = true) int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
        userRepository.delete(user.get());
        }


        return "redirect:/users/userList";
    }

}
