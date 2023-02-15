package com.petboarding.controllers;

import com.petboarding.models.app.Module;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("stays")
public class StaysController extends AppBaseController {

    @GetMapping
    public String displayStaysGrid(Model model) {
        return "stays/indexGrid";
    }

    @ModelAttribute("activeModule")
    public Module addActiveModule() {
        return getActiveModule(this.getClass().getAnnotation(RequestMapping.class).value()[0]);
    }
}
