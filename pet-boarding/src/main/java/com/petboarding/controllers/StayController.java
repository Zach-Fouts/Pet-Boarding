package com.petboarding.controllers;

import com.petboarding.models.Employee;
import com.petboarding.models.Reservation;
import com.petboarding.models.Stay;
import com.petboarding.models.StayStatus;
import com.petboarding.models.app.Module;
import com.petboarding.models.data.ReservationRepository;
import com.petboarding.models.data.StayRepository;
import com.petboarding.models.data.StayStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private StayStatusRepository stayStatusRepository;

    @GetMapping
    public String displayStaysGrid(Model model) {
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

    private void prepareCommonFormModel(Model model) {
        model.addAttribute("status", stayStatusRepository.findAll());
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
