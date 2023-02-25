package com.petboarding.controllers;

import com.petboarding.models.InvoiceStatus;
import com.petboarding.models.app.Module;
import com.petboarding.models.data.InvoiceDetailRepository;
import com.petboarding.models.data.InvoiceRepository;
import com.petboarding.models.data.InvoiceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("invoices")
public class InvoiceController extends AppBaseController{

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired

    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private InvoiceStatusRepository invoiceStatusRepository;

    @GetMapping
    public String displayStaysGrid(@RequestParam(required = false, defaultValue = "false") Boolean showAll, Model model) {
        model.addAttribute("stays", showAll ? invoiceRepository.findAll() : invoiceRepository.findByActive(true));
        model.addAttribute("showAll", showAll);
        return "invoices/index";
    }

    @ModelAttribute("activeModule")
    public Module addActiveModule() {
        return getActiveModule(this.getClass().getAnnotation(RequestMapping.class).value()[0]);
    }

}
