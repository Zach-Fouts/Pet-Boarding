package com.petboarding.controllers;

import com.petboarding.models.Employee;
import com.petboarding.models.app.Module;
import com.petboarding.models.data.EmployeeRepository;
import com.petboarding.models.data.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("employees")
public class EmployeeController extends AppBaseController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    private final String FORM_NEW_TITLE = "New Employee";
    private final String FORM_UPDATE_TITLE = "Update: ${employeeName}";

    @GetMapping
    public String displayEmployeesGrid(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employees/index";
    }

    @GetMapping("add")
    public String displayAddEmployeeForm(Model model) {
        prepareAddFormModel(model);
        return "employees/form";
    }

    @PostMapping("add")
    public String processAddEmployeeRequest(@Valid @ModelAttribute Employee newEmployee, Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("positions", positionRepository.findAll());
            return "employees/form";
        }
        employeeRepository.save(newEmployee);
        return "employees/index";
    }

    @GetMapping("update/{id}")
    public String displayUpdateEmployeeForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Employee> optEmployee = employeeRepository.findById(id);
        if(optEmployee.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "The employee ID:" + id + " couldn't be found.");
            return "redirect:/employees";
        }
        prepareUpdateFormModel(optEmployee.get(), model);
        return "employees/form";
    }

    @PostMapping("update/{id}")
    public String processUpdateEmployeeRequest(@PathVariable Integer id, @Valid @ModelAttribute Employee employee, Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("positions", positionRepository.findAll());
            return "employees/form";
        }
        employeeRepository.save(employee);
        return "redirect:" + id;
    }

    @PostMapping("delete/{id}")
    public String processDeleteEmployeeRequest(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        if(!employeeRepository.existsById(id)) {
            redirectAttributes.addFlashAttribute("errorMessage", "The employee ID:" + id + " couldn't be found.");
        } else {
            employeeRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("infoMessage", "Employee was successfully deleted.");
        }
        return "redirect:/employees";
    }

    @ModelAttribute("activeModule")
    public Module addActiveModule() {
        return getActiveModule(this.getClass().getAnnotation(RequestMapping.class).value()[0]);
    }

    private void prepareCommonFormModel(Model model) {
        model.addAttribute("positions", positionRepository.findAll());
    }
    private void prepareAddFormModel(Model model) {
        model.addAttribute("formTitle", FORM_NEW_TITLE);
        model.addAttribute("employee", new Employee());
        model.addAttribute("submitURL", "/employees/add");
        model.addAttribute("submitMethod", "post");
        prepareCommonFormModel(model);
    }

    private void prepareUpdateFormModel(Employee employee, Model model) {
        model.addAttribute("formTitle", FORM_UPDATE_TITLE.replace("${employeeName}", employee.getFullName()));
        model.addAttribute("employee", employee);
        model.addAttribute("submitURL", "/employees/update/" + employee.getId());
        model.addAttribute("submitMethod", "post");
        prepareCommonFormModel(model);
    }
}
