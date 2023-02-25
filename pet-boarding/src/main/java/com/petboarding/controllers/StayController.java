package com.petboarding.controllers;

import com.petboarding.controllers.utils.DateUtils;
import com.petboarding.models.*;
import com.petboarding.models.app.JsonStayService;
import com.petboarding.models.app.Module;
import com.petboarding.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

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
    private KennelRepository kennelRepository;

    @Autowired
    private PetServiceRepository serviceRepository;

    @Autowired
    private StayServiceRepository stayServiceRepository;

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
        hasErrors |= processDateValidation(newStay, model);
        if(hasErrors) {
            prepareAddFormModel(newStay, model);
            return "stays/form";
        }
        stayRepository.save(newStay);
        updateAdditionalServices(newStay);
        return "redirect:/stays";
    }

    @GetMapping("update/{id}")
    public String displayUpdateStayForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Stay> optStay = stayRepository.findById(id);
        if(optStay.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "The stay could not be found.");
            return "redirect:/stays";
        }
        prepareUpdateFormModel(optStay.get(), model);
        return "stays/form";
    }

    @PostMapping("update/{id}")
    public String processUpdateStayForm(@Valid Stay stay,
                                        Errors validation,
                                        Model model,
                                        RedirectAttributes redirectAttributes) {
        boolean hasErrors = validation.hasErrors();
        hasErrors |= processDateValidation(stay, model);
        if(hasErrors) {
            prepareUpdateFormModel(stay, model);
            return "stays/form";
        }
        stayRepository.save(stay);
        updateAdditionalServices(stay);
        redirectAttributes.addFlashAttribute("infoMessage", "The Stay information has been updated.");
        return "redirect:" + stay.getId();
    }

    @PostMapping("delete/{id}")
    public String processDeleteStay(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Stay> optStay = stayRepository.findById(id);
        if(optStay.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "The stay couldn't be found.");
        } else {
            Stay stay = optStay.get();
            if(stay.getInvoice() != null) {
                stay.setActive(false);
                stayRepository.save(stay);
                redirectAttributes.addFlashAttribute("infoMessage", "Stay <strong>#" + stay.getReservation().getConfirmation() +
                        "</strong> is linked to the Invoice #<strong>" + stay.getInvoice().getFormattedNumber() + "</strong>, so it will be set inactive.");
            } else {
                stayRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("infoMessage", "Stay was successfully deleted.");
            }
        }
        return "redirect:/stays/grid";
    }

    @GetMapping("checkout/{id}")
    public String processCheckoutAndShowInvoice(@PathVariable Integer id,
                                                Model model,
                                                RedirectAttributes redirectAttributes) {
        return "redirect: /invoices/update/"; // add invoice id
    }

    private void updateAdditionalServices(Stay stay) {
        for(StayService service: stay.getAdditionalServices()) {
            if(service.getService() == null) {
                stayServiceRepository.delete(service);
            } else {
                service.setStay(stay);
                stayServiceRepository.save(service);
            }
        }
    }

    private void prepareCommonFormModel(Stay stay, Model model) {
        model.addAttribute("kennels", kennelRepository.findAll());
        model.addAttribute("services", serviceRepository.findByStayService(true));
        model.addAttribute("statuses", stayStatusRepository.findAll());
        model.addAttribute("caretakers", employeeRepository.findByPositionName("caretaker"));
        model.addAttribute("additionalServices", serviceRepository.findByStayService(false));
        HashMap<Integer, JsonStayService> mapJsonStayServices = new HashMap<>();
        for(StayService service: stay.getAdditionalServices()) {
            mapJsonStayServices.put(service.getId(),
                    new JsonStayService(service));
        }
        model.addAttribute("mapStaysAdditionalServices", mapJsonStayServices);
    }
    private void prepareAddFormModel(Stay stay, Model model) {
        model.addAttribute("formTitle", FORM_NEW_TITLE);
        model.addAttribute("stay", stay);
        model.addAttribute("submitURL", "/stays/add");
        addLocation("New", model);
        prepareCommonFormModel(stay, model);
    }


    private void prepareUpdateFormModel(Stay stay, Model model) {
        model.addAttribute("formTitle", FORM_UPDATE_TITLE.replace("${confirmation}", stay.getReservation().getConfirmation()));
        model.addAttribute("stay", stay);
        model.addAttribute("submitURL", "/stays/update/" + stay.getId());
        addLocation("Update", model);
        prepareCommonFormModel(stay, model);
    }

    private boolean processDateValidation(Stay stay, Model model) {
        boolean hasErrors = false;
        if(!stay.getReservation().isDateRangeValid()) {
            hasErrors = true;
            model.addAttribute("errorMessage",
                    "The end date <strong>" + DateUtils.showFormatter.format(stay.getReservation().getEndDateTime()) +
                            "</strong> has to be on the same day or after <strong>" + DateUtils.showFormatter.format(stay.getReservation().getStartDateTime()) + "</strong>");
        }
        return hasErrors;
    }

    @ModelAttribute("activeModule")
    public Module addActiveModule() {
        return getActiveModule(this.getClass().getAnnotation(RequestMapping.class).value()[0]);
    }
}
