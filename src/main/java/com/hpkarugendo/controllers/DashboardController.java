package com.hpkarugendo.controllers;

import com.hpkarugendo.models.Gallery;
import com.hpkarugendo.models.Photo;
import com.hpkarugendo.models.SiteAdmin;
import com.hpkarugendo.repositories.GalleryRepository;
import com.hpkarugendo.repositories.PhotoRepository;
import com.hpkarugendo.repositories.SiteAdminRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DashboardController {
    private GalleryRepository gRepo;
    private SiteAdminRepository uRepo;
    private PhotoRepository pRepo;

    public DashboardController(GalleryRepository gRepo, SiteAdminRepository adminRepository, PhotoRepository repository) {
        this.gRepo = gRepo;
        this.uRepo = adminRepository;
        this.pRepo = repository;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model m){
        m.addAttribute("galleryObjects", gRepo.findTop5ByOrderByIdDesc());
        m.addAttribute("galleryObject", new Gallery());
        m.addAttribute("userObjects", uRepo.findAllByOrderByAdminIdDesc());
        m.addAttribute("photoObjects", pRepo.findAllByOrderById());

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
