package com.petboarding.controllers;

import com.petboarding.models.*;
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

    private final String FORM_NEW_TITLE = "New Invoice";
    private final String FORM_UPDATE_TITLE = "Update: ${number}";

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired

    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private InvoiceStatusRepository invoiceStatusRepository;

    @GetMapping
    public String displayStaysGrid(@RequestParam(required = false, defaultValue = "false") Boolean showAll, Model model) {
        model.addAttribute("invoices", showAll ? invoiceRepository.findAll() : invoiceRepository.findByActive(true));
        model.addAttribute("showAll", showAll);
        return "invoices/index";
    }

    @GetMapping("add")
    public String displayAddStayForm(Model model) {
        prepareAddFormModel(new Invoice(), model);
        return "stays/form";
    }

    private void prepareCommonFormModel(Invoice invoice, Model model) {
//        HashMap<Integer, JsonStayService> mapJsonStayServices = new HashMap<>();
//        for(StayService service: stay.getAdditionalServices()) {
//            mapJsonStayServices.put(service.getId(),
//                    new JsonStayService(service));
//        }
//        model.addAttribute("mapStaysAdditionalServices", mapJsonStayServices);
    }
    private void prepareAddFormModel(Invoice invoice, Model model) {
        model.addAttribute("formTitle", FORM_NEW_TITLE);
        model.addAttribute("invoice", invoice);
        model.addAttribute("submitURL", "/invoices/add");
        addLocation("New", model);
        prepareCommonFormModel(invoice, model);
    }

    private void prepareUpdateFormModel(Invoice invoice, Model model) {
        model.addAttribute("formTitle", FORM_UPDATE_TITLE.replace("${number}", invoice.getFullNumber()));
        model.addAttribute("invoice", invoice);
        model.addAttribute("submitURL", "/invoices/update/" + invoice.getId());
        addLocation("Update", model);
        prepareCommonFormModel(invoice, model);
    }


    @ModelAttribute("activeModule")
    public Module addActiveModule() {
        return getActiveModule(this.getClass().getAnnotation(RequestMapping.class).value()[0]);
    }

}
