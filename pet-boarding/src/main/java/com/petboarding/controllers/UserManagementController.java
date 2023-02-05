package com.petboarding.controllers;

import com.petboarding.models.Employee;
import com.petboarding.models.Role;
import com.petboarding.models.app.Module;
import com.petboarding.models.data.EmployeeRepository;
import com.petboarding.models.data.OwnerRepository;
import com.petboarding.models.data.RoleRepository;
import com.petboarding.models.data.UserRepository;
import com.petboarding.models.dto.AddNewUserDTO;
import com.petboarding.models.utilities.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import com.petboarding.models.User;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("users")
public class UserManagementController extends AppBaseController{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OwnerRepository ownerRepository;


    // -----------------------------User Management------------------------------
        public List<User> listUsers() {
            return userRepository.findAll();
        }
        public List<Employee> listEmployees() {
            return employeeRepository.findAll();
        }
        public List<Role> listRoles() {
            return roleRepository.findAll();
        }

        public List<Employee> filteredEmployeeList() {
//        TODO: use repository filter to find employees not already associated with a user, -> change in /addUserForm PostMapping as well
//       filters employees that are not already associated with a user
            Map<Integer, Integer> employeeIds = listUsers().stream().filter(user -> user.getEmployee() != null).collect(Collectors.toMap(user -> user.getEmployee().getId(), user -> user.getId()));
            List<Employee> filteredEmployees = new ArrayList<>();
            for (Employee employee : listEmployees()) {
                if (!employeeIds.containsKey(employee.getId())) {
                    filteredEmployees.add(employee);
                }
            }
            return filteredEmployees;
        }

    @GetMapping("/addUserForm")
    public String addUserForm(Model model) {
        model.addAttribute("listRoles", listRoles());
        model.addAttribute("listEmployees", filteredEmployeeList());
        model.addAttribute("addNewUserDTO", new AddNewUserDTO());
        return "/users/addUserForm";
    }

    @PostMapping("/addUserForm")
    public String addUser(@ModelAttribute @Valid AddNewUserDTO addNewUserDTO, BindingResult result, Model model, Errors errors) {

        boolean fieldError = false;
        User existingUser = userRepository.findByUsername(addNewUserDTO.getUsername());
        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            fieldError = true;
        }

        String password = addNewUserDTO.getPassword();
        String verifyPassword = addNewUserDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            fieldError = true;
        }

        if (errors.hasErrors() || fieldError) {
            model.addAttribute("listRoles", listRoles());
            model.addAttribute("listEmployees", filteredEmployeeList());
            return "/users/addUserForm";
        }

        User newUser = new User(addNewUserDTO.getUsername(), addNewUserDTO.getPassword(), addNewUserDTO.getRole(), addNewUserDTO.getEmployee());
        userRepository.save(newUser);
        return "redirect:/users";
    }

    @GetMapping("")
    public String listUsers(Model model) {
        model.addAttribute("listUsers", listUsers());
        return "/users/userList";
    }
    @GetMapping("/editUserForm/{id}")
    public String editUser(@PathVariable("id")int id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
        model.addAttribute("user", user.get());
        model.addAttribute("listRoles", listRoles());
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
    //    TODO: remove image upload from user, implement in employee
    @Transactional
    @PostMapping("/saveUser")
    public String submitSaveUser(@RequestParam int id, @RequestParam String username, @RequestParam Role role,
                                 @RequestParam(required = false) String password)  {

        User user = userRepository.findById(id).orElse(null);
            if (user != null) {
                user.setUsername(username);
                user.setRole(role);
            }
            return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            String username = user.get().getUsername();
            redirectAttributes.addFlashAttribute("infoMessage", "Role: <strong>" + username + "</strong>  was successfully deleted.");
        userRepository.delete(user.get());
        }
        return "redirect:/users";
    }


    // -----------------------------Role Management------------------------------

    @GetMapping("roles/roleList")
    public String roleList(Model model){
        model.addAttribute("listRoles", listRoles());
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

    @PostMapping("/roles/delete/{id}")
    public String deleteRole(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Role role = roleRepository.findById(id).get();
        List<User> users = role.getUsers();
            if (!users.isEmpty()) {
            model.addAttribute("errorMessage", "This role is currently assigned to one or more users and cannot be deleted.");
            model.addAttribute("role", role);
            return "/users/roles/deleteRoleForm";
        }
            else {
                String name = role.getName();
                redirectAttributes.addFlashAttribute("infoMessage", "User: <strong>" + name + "</strong>  was successfully deleted.");
            roleRepository.deleteById(role.getId());
            return "redirect:/users/roles/roleList";
        }
    }

    @ModelAttribute("activeModule")
    public Module addActiveModule() {
        return getActiveModule("users");
    }
}
