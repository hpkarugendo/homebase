package com.hpkarugendo.controllers;

import com.hpkarugendo.models.Gallery;
import com.hpkarugendo.models.Photo;
import com.hpkarugendo.models.SiteAdmin;
import com.hpkarugendo.repositories.GalleryRepository;
import com.hpkarugendo.repositories.PhotoRepository;
import com.hpkarugendo.repositories.SiteAdminRepository;
import com.hpkarugendo.services.HomebaseStorageService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DashboardController {
    private SiteAdminRepository uRepo;
    private HomebaseStorageService storageService;

    public DashboardController(SiteAdminRepository uRepo, HomebaseStorageService storageService) {
        this.uRepo = uRepo;
        this.storageService = storageService;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model m){
        m.addAttribute("galleryObjects", storageService.listTop5Galleries());
        m.addAttribute("galleryObject", new Gallery());
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
}
