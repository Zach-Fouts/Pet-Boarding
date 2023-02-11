package com.petboarding.controllers;


import com.petboarding.models.*;
import com.petboarding.models.app.Module;
import com.petboarding.models.data.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("reservations")
public class ReservationController extends AppBaseController{
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("grid")
    public String displayReservations(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
        return "reservations/reservations";
    }
    @GetMapping
    public String displayCalendar(Model model) {
        List<Reservation> events = reservationRepository.findAll();
        for(Reservation event : events){
            if(event.getPet() != null){
                event.getPet().setOwner(null);
            }
        }
        model.addAttribute("reservations", events);
        return "reservations/calendarView";
    }
    @GetMapping("create")
    public String displayCreateReservationsForm(Model model, @RequestParam(required = false) Integer ownerId) {
        model.addAttribute("title", "Create Reservation");
        model.addAttribute(new Reservation());

        if(ownerId == null){
            model.addAttribute("pets", new ArrayList <Pet>());
        }else{
            Optional<Owner> result = ownerRepository.findById(ownerId);
            if(result.isEmpty()){
                model.addAttribute("pets", new ArrayList <Pet>());
            }else{
                model.addAttribute("pets", result.get().getPets());
                model.addAttribute("ownerId", ownerId );
            }
        }
        model.addAttribute("owners", ownerRepository.findAll());
        model.addAttribute("categories", reservationRepository.findAll());
        return "reservations/create";
    }

    @PostMapping("create")
    public String processCreateReservationsForm(@ModelAttribute @Valid Reservation newReservation,
                                         Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Event");
            return "reservations/create";
        }
        newReservation.assignConfirmationCode();
        reservationRepository.save(newReservation);
        return "redirect:";
    }

    @GetMapping("delete")
    public String displayDeleteReservationsForm(@RequestParam Integer reservationId, Model model) {

        Optional<Reservation> result = reservationRepository.findById(reservationId);
        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Reservation ID: " + reservationId);
        } else {
            Reservation reservation = result.get();
            model.addAttribute("title", reservation.getId() + " Details");
            model.addAttribute("reservation", reservation);
        }
        return "reservations/delete";
    }

    @PostMapping("delete")
    public String processDeleteReservationsForm(@RequestParam Integer reservationId, Model model) {

        if (reservationId != null) {
                reservationRepository.deleteById(reservationId);

        }

        return "redirect:";
    }

    @GetMapping("detail")
    public String displayReservationsDetails(@RequestParam Integer reservationId, Model model) {

        Optional<Reservation> result = reservationRepository.findById(reservationId);

        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Reservation ID: " + reservationId);
        } else {
            Reservation reservation = result.get();
            model.addAttribute("title", reservation.getId() + " Details");
            model.addAttribute("reservation", reservation);
        }

        return "reservations/detail";
    }
    @GetMapping("update")
    public String displayUpdateReservationsForm(@RequestParam Integer reservationId,@Valid Reservation reservation,Errors errors, Model model) {
        Optional<Reservation> result = reservationRepository.findById(reservationId);

        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Reservation ID: " + reservationId);
        } else {
            reservation = result.get();
            model.addAttribute("title", reservation.getId() + " Details");
            model.addAttribute("reservation", reservation);
        }
        return "reservations/update";
    }
    @PostMapping("update")
    public String processUpdateReservationsForm(@RequestParam Integer reservationId,@Valid Reservation updatedReservation,Errors errors, Model model) {
        Optional<Reservation> result = reservationRepository.findById(reservationId);

        if (result.isEmpty()) {
            model.addAttribute("title", "Invalid Reservation ID: " + reservationId);
        } else {
            Reservation reservation = result.get();
            model.addAttribute("title", reservation.getId() + " Details");
            model.addAttribute("reservation", reservation);

            //update the existing reservation
            reservation.setStartDateTime(updatedReservation.getStartDateTime());
            reservation.setEndDateTime(updatedReservation.getEndDateTime());
            reservation.setComments(updatedReservation.getComments());

            //save the updated reservation
            reservationRepository.save(reservation);
        }
        if (errors.hasErrors()) {
            return "reservation/update";
        }
        return "redirect:";
    }
    @ModelAttribute("activeModule")
    public Module addActiveModule(){
        return getActiveModule("reservations");
    }
}
