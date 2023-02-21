package com.petboarding.controllers;

import com.petboarding.models.Kennel;
import com.petboarding.models.KennelSVGShape;
import com.petboarding.models.Pet;
import com.petboarding.models.Stay;
import com.petboarding.models.data.KennelRepository;
import com.petboarding.models.data.KennelSVGShapeRepository;
import com.petboarding.models.data.PetRepository;
import com.petboarding.models.data.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("kennels")
public class KennelController extends AppBaseController{

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private KennelSVGShapeRepository kennelSVGShapeRepository;

    @Autowired
    private KennelRepository kennelRepository;

    @Autowired
    private StayRepository stayRepository;


    @RequestMapping("")
    public String showKennelMap(Model model){
        Iterable<Pet> pets;
        pets = petRepository.findAll();

        Iterable<Stay> stays;
        stays = stayRepository.findAll();

        this.setActiveModule("kennels", model);
        model.addAttribute("pets", pets);
        model.addAttribute("stays", stays);
        return "kennels/kennelMap";
    }

    /*****************************************************************************/
    @RequestMapping("/testPage")
    public String showTestMap(Model model){
        Iterable<Kennel> kennels;
        kennels = kennelRepository.findAll();

        Iterable<KennelSVGShape> kennelSVGs;
        kennelSVGs = kennelSVGShapeRepository.findAll();


        Iterable<Pet> pets;
        pets = petRepository.findAll();

        Iterable<Stay> stays;
        stays = stayRepository.findAll();

        this.setActiveModule("kennels", model);
        model.addAttribute("kennels", kennels);
        model.addAttribute("kennelSVGs", kennelSVGs);
        model.addAttribute("pets", pets);
        model.addAttribute("stays", stays);
        return "kennels/testPage";
    }
    /*****************************************************************************/






    @RequestMapping("/grid")
    public String showKennelGrid(Model model){
        Iterable<Kennel> kennels;
        kennels = kennelRepository.findAll();

        this.setActiveModule("kennels", model);
        model.addAttribute("kennels", kennels);
        return "kennels/kennelGrid";
    }
}