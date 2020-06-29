package com.hpkarugendo.controllers;

import com.hpkarugendo.models.Gallery;
import com.hpkarugendo.repositories.GalleryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private GalleryRepository gRepo;

    public DashboardController(GalleryRepository gRepo) {
        this.gRepo = gRepo;
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(Model m){
        m.addAttribute("galleryObjects", gRepo.findTop5ByOrderByIdDesc());
        m.addAttribute("galleryObject", new Gallery());

        return "dashboard";
    }
}
