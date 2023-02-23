package com.petboarding.controllers;

import com.petboarding.controllers.utils.DateUtils;
import com.petboarding.models.Employee;
import com.petboarding.models.Reservation;
import com.petboarding.models.Stay;
import com.petboarding.models.StayStatus;
import com.petboarding.models.app.Module;
import com.petboarding.models.data.EmployeeRepository;
import com.petboarding.models.data.ReservationRepository;
import com.petboarding.models.data.StayRepository;
import com.petboarding.models.data.StayStatusRepository;
import org.hibernate.validator.internal.util.stereotypes.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("stays")
public class StayController extends AppBaseController {

    private final String FORM_NEW_TITLE = "New stay";
    private final String FORM_UPDATE_TITLE = "Update: ${confirmation}";

    @Autowired
    private StayRepository stayRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private StayStatusRepository stayStatusRepository;


    @GetMapping
    public String displayStaysCalendar(Model model) {
        model.addAttribute("stays", stayRepository.findAll());
        return "stays/indexCalendar";
    }

    @GetMapping("grid")
    public String displayStaysGrid(@RequestParam(required = false, defaultValue = "false") Boolean showAll, Model model) {
        model.addAttribute("stays", showAll ? stayRepository.findAll() : stayRepository.findByActive(true));
        model.addAttribute("showAll", showAll);
        return "stays/indexGrid";
    }

    @GetMapping("add")
    public String displayAddStayForm(@RequestParam(required = true) Integer reservationId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Reservation> optReservation = reservationRepository.findById(reservationId);
        if(optReservation.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "The reservation could not be found.");
            return "redirect:/stays";
        }
        Stay stay = new Stay();
        stay.setReservation(optReservation.get());
        prepareAddFormModel(stay, model);
        return "stays/form";
    }

    @PostMapping("add")
    public String processAddStayForm(@Valid Stay newStay, @RequestParam(required = false) String endDateValue, Errors validation, Model model) {
        boolean hasErrors = validation.hasErrors();
        if(endDateValue != null) {
            try {
                Date newEndDate = DateUtils.parseFormatter.parse(endDateValue);
                if(newStay.getReservation().getStartDateTime().after(newEndDate)) {
                    hasErrors = true;
                    model.addAttribute("errorMessage",
                            "The new end date <strong>" + DateUtils.showFormatter.format(newEndDate) + "</strong> has to be equal or after <strong>" + DateUtils.showFormatter.format(newStay.getReservation().getStartDateTime()) + "</strong>");
                } else {
                    newStay.getReservation().setEndDateTime(newEndDate);
                }
            } catch(ParseException exception) {
                hasErrors = true;
                model.addAttribute("errorMessage",
                        "There was an unexpected error trying to assign the new end date.");
            }
        }
        if(hasErrors) {
            prepareCommonFormModel(model);
            return "stays/form";
        }
        //TODO Revise to add Kennel and Service provided
        reservationRepository.save(newStay.getReservation());
        stayRepository.save(newStay);
        return "redirect:/stays";
    }

    private void prepareCommonFormModel(Model model) {
        model.addAttribute("statuses", stayStatusRepository.findAll());
        model.addAttribute("caretakers", employeeRepository.findByPositionName("caretaker"));
    }
    private void prepareAddFormModel(Stay stay, Model model) {
        model.addAttribute("formTitle", FORM_NEW_TITLE);
        model.addAttribute("stay", stay);
        model.addAttribute("submitURL", "/stays/add");
        addLocation("New", model);
        prepareCommonFormModel(model);
    }


    private void prepareUpdateFormModel(Stay stay, Model model) {
        model.addAttribute("formTitle", FORM_UPDATE_TITLE.replace("${confirmation}", stay.getReservation().getConfirmation()));
        model.addAttribute("stay", stay);
        model.addAttribute("submitURL", "/stays/update/" + stay.getId());
        addLocation("Update", model);
        prepareCommonFormModel(model);
    }

    @ModelAttribute("activeModule")
    public Module addActiveModule() {
        return getActiveModule(this.getClass().getAnnotation(RequestMapping.class).value()[0]);
    }
}
