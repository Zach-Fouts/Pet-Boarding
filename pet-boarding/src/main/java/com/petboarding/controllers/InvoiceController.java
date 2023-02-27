package com.petboarding.controllers;

import com.petboarding.controllers.utils.JsonService;
import com.petboarding.models.*;
import com.petboarding.models.app.Module;
import com.petboarding.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

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

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PetServiceRepository petServiceRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @GetMapping
    public String displayStaysGrid(@RequestParam(required = false, defaultValue = "false") Boolean showAll, Model model) {
        model.addAttribute("invoices", showAll ? invoiceRepository.findAll() : invoiceRepository.findByActive(true));
        model.addAttribute("showAll", showAll);
        return "invoices/index";
    }

    @GetMapping("test")
    public String test(Model model) {
        model.addAttribute("infoMessage", Float.parseFloat(configurationRepository.findByName("SALES_TAX").getValue()));
        return "index";
    }

    @GetMapping("add")
    public String displayAddStayForm(Model model) {
        prepareAddFormModel(new Invoice(), model);
        return "invoices/form";
    }

    @Transactional
    @PostMapping("add")
    public String processAddStayForm(@Valid Invoice newInvoice, Errors validation, Model model) {
        if(validation.hasErrors()) {
            prepareAddFormModel(newInvoice, model);
            return "stays/form";
        }
        newInvoice.setDate(new Date());
        BigDecimal nextNumber = invoiceRepository.findNextNumberByDate(newInvoice.getDate());
        newInvoice.setNumber(nextNumber == null ? 1 : nextNumber.intValue());
        invoiceRepository.save(newInvoice);
        updateDetailServices(newInvoice);
        return "redirect:/invoices";
    }

    @GetMapping("update/{id}")
    public String displayUpdateInvoiceForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Invoice> optInvoice = invoiceRepository.findById(id);
        if(optInvoice.isEmpty()){
            redirectAttributes.addFlashAttribute("errorMessage", "The invoice couldn't be foound.");
            return "redirect: /invoices";
        }
        prepareUpdateFormModel(optInvoice.get(), model);
        return "invoices/form";
    }

    private void updateDetailServices(Invoice invoice) {
        for(InvoiceDetail service: invoice.getDetails()) {
            if(service.getService() == null) {
                invoiceDetailRepository.delete(service);
            } else {
                service.setInvoice(invoice);
                invoiceDetailRepository.save(service);
            }
        }
    }

    private void prepareCommonFormModel(Invoice invoice, Model model) {
        HashMap<Integer, JsonService> mapJsonServices = new HashMap<>();
        for(InvoiceDetail service: invoice.getDetails()) {
            mapJsonServices.put(service.getId(),
                    new JsonService(service));
        }
        model.addAttribute("mapInvoiceDetails", mapJsonServices);
        model.addAttribute("statuses", invoiceStatusRepository.findAll());
        model.addAttribute("services", petServiceRepository.findAll());
    }

    private void prepareAddFormModel(Invoice invoice, Model model) {
        invoice.setTaxPercent(Float.parseFloat(configurationRepository.findByName("SALES_TAX").getValue()));
        invoice.setDate(new Date());
        model.addAttribute("formTitle", FORM_NEW_TITLE);
        model.addAttribute("invoice", invoice);
        model.addAttribute("submitURL", "/invoices/add");
        model.addAttribute("owners", ownerRepository.findByActive(true));
        addLocation("New", model);
        prepareCommonFormModel(invoice, model);
    }

    private void prepareUpdateFormModel(Invoice invoice, Model model) {
        List<Owner> owners = ownerRepository.findByActive(true);
        if(!invoice.getOwner().getActive() && !owners.contains(invoice.getOwner()))
            owners.add(invoice.getOwner());
        model.addAttribute("formTitle", FORM_UPDATE_TITLE.replace("${number}", invoice.getFullNumber()));
        model.addAttribute("invoice", invoice);
        model.addAttribute("submitURL", "/invoices/update/" + invoice.getId());
        model.addAttribute("owners", owners);
        addLocation("Update", model);
        prepareCommonFormModel(invoice, model);
    }


    @ModelAttribute("activeModule")
    public Module addActiveModule() {
        return getActiveModule(this.getClass().getAnnotation(RequestMapping.class).value()[0]);
    }

}
