package com.hpkarugendo.controllers;

import com.hpkarugendo.models.Gallery;
import com.hpkarugendo.models.Photo;
import com.hpkarugendo.models.SiteAdmin;
import com.hpkarugendo.repositories.GalleryRepository;
import com.hpkarugendo.repositories.PhotoRepository;
import com.hpkarugendo.repositories.SiteAdminRepository;
import com.hpkarugendo.services.HomebaseStorageService;
import com.hpkarugendo.services.SiteAdminService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class DashboardController {
    private SiteAdminRepository uRepo;
    private HomebaseStorageService storageService;
    private SiteAdminService saService;

    public DashboardController(SiteAdminRepository uRepo, HomebaseStorageService storageService, SiteAdminService siteAdminService) {
        this.uRepo = uRepo;
        this.storageService = storageService;
        this.saService = siteAdminService;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model m){
        m.addAttribute("galleryObjects", storageService.listTop5Galleries());
        m.addAttribute("userObjects", uRepo.findAllByOrderByAdminIdDesc());
        m.addAttribute("photoObjects", storageService.listTop5Photos());

        return "dashboard";
    }

    @GetMapping("admin/users/new")
    public String newUser(Model m){
        m.addAttribute("userObject", new SiteAdmin());

        return "users_new";
    }

    @PostMapping("admin/users/add")
    public String addUser(SiteAdmin userObject, RedirectAttributes ra){
        SiteAdmin toSave = userObject;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String ePass = encoder.encode(toSave.getAdminPassword());
        toSave.setAdminPassword(ePass);
        uRepo.save(toSave);

        ra.addFlashAttribute("mSg", "User Added Successfully!");

        return "redirect:/admin/dashboard";
    }

    @GetMapping("admin/users/edisable/{id}")
    public String disableUser(@PathVariable("id") String id, RedirectAttributes ra) throws Exception {
        Optional<SiteAdmin> so = uRepo.findById(id);
        SiteAdmin toDisable = so.get();

        if(toDisable != null){
            if(toDisable.isEnabled()){
                toDisable.setEnabled(false);
                ra.addFlashAttribute("mSg", "USER DISABLED!");
            } else {
                toDisable.setEnabled(true);
                ra.addFlashAttribute("mSg", "USER IS ENABLED!");
            }
            saService.saveUser(toDisable);
            return "redirect:/admin/dashboard";
        }

        ra.addFlashAttribute("mSg", "ERROR DISABLING USER");
        return "redirect:/admin/dashboard";
    }

}
