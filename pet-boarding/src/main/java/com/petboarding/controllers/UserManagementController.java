package com.petboarding.controllers;

import com.petboarding.models.Role;
import com.petboarding.models.app.Module;
import com.petboarding.models.data.RoleRepository;
import com.petboarding.models.data.UserRepository;
import com.petboarding.models.dto.AddNewUserDTO;
import net.bytebuddy.asm.Advice;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("users")
public class UserManagementController extends AppBaseController{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


// -----------------------------User Management------------------------------
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
            List<Role> listRoles = (List<Role>) roleRepository.findAll();
            model.addAttribute("listRoles", listRoles);

            return "/users/addUserForm";
        }

        User existingUser = userRepository.findByUsername(addNewUserDTO.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            model.addAttribute("title", "Add User");
            List<Role> listRoles = (List<Role>) roleRepository.findAll();
            model.addAttribute("listRoles", listRoles);
            return "/users/addUserForm";

        }

        String password = addNewUserDTO.getPassword();
        String verifyPassword = addNewUserDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Register");
            List<Role> listRoles = (List<Role>) roleRepository.findAll();
            model.addAttribute("listRoles", listRoles);
            return "/users/addUserForm";
        }

        User newUser = new User(addNewUserDTO.getUsername(), addNewUserDTO.getPassword(), addNewUserDTO.getRole());
        addNewUserDTO.getRole().users.add(newUser);
        userRepository.save(newUser);

        return "redirect:/users";
    }

    @GetMapping("")
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

    //Saves user profile changes in editUserForm
    @Transactional
    @PostMapping("/saveUser")
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
        return "redirect:/users";
    }

    @GetMapping("/deleteUserForm/{id}")
    public String deleteUserForm(@PathVariable("id")int id, Model model) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()){
        model.addAttribute("user", user.get());
        }

        return "users/deleteUserForm";
    }

    //Deletes user in deleteUserForm
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam(required = true) int id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
        userRepository.delete(user.get());
        }
        return "redirect:/users";
    }


    // -----------------------------Role Management------------------------------

    @GetMapping("roles/roleList")
    public String roleList(Model model){
        List<Role> listRoles = roleRepository.findAll();
        model.addAttribute("listRoles", listRoles);
        return "/users/roles/roleList";
    }

    @GetMapping("/roles/addRoleForm")
    public String addRoleForm(Model model) {
        model.addAttribute("role", new Role());
        return "/users/roles/addRoleForm";
    }

    //Saves role in addRoleForm
    @PostMapping("roles/saveRole")
    public String createRole(@RequestParam String name)  {
        Role newRole = new Role(name);
        roleRepository.save(newRole);
        return "redirect:/users/roles/roleList";
    }

    @GetMapping("roles/deleteRoleForm/{id}")
    public String deleteRoleForm(@PathVariable("id") int id, Model model){
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            model.addAttribute("role", role.get());
        }

        return "users/roles/deleteRoleForm";
    }

    @PostMapping("roles/deleteRoleForm")
    public String deleteRole(@RequestParam int id, Model model) {
            Role role = roleRepository.findById(id).get();

        List<User> users = role.getUsers();
        List<User> filteredUsers = users.stream().filter(user -> user.getRole().getId() == id).collect(Collectors.toList());
            if (!filteredUsers.isEmpty()) {
            model.addAttribute("errorMessage", "This role is currently assigned to one or more users and cannot be deleted.");
            model.addAttribute("role", role);

            return "/users/roles/deleteRoleForm";
        }
            else {
            roleRepository.deleteById(role.getId());
            return "redirect:/users/roles/roleList";
        }
    }

    @ModelAttribute("activeModule")
    public Module addActiveModule() {
        return getActiveModule("users");
    }
}
