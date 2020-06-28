package com.hpkarugendo.controllers;

import com.hpkarugendo.models.Gallery;
import com.hpkarugendo.models.Photo;
import com.hpkarugendo.repositories.GalleryRepository;
import com.hpkarugendo.repositories.PhotoRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class GalleryController {

    private GalleryRepository gRepo;
    private PhotoRepository pRepo;

    public GalleryController(GalleryRepository gRepo, PhotoRepository photoRepository) {
        this.pRepo = photoRepository;
        this.gRepo = gRepo;
    }

    @GetMapping("/galleries")
    public String gallery(Model m){
        List<Gallery> galleries = gRepo.findAllByOrderById();

        m.addAttribute("galleryObject", new Gallery());

        if(galleries.isEmpty()){
            m.addAttribute("gYes", false);
        } else {
            m.addAttribute("gYes", true);
            m.addAttribute("galleries", galleries);
        }

        return "galleries";
    }

    @GetMapping("/admin/galleries/new")
    public String newGallery(Model m){
        if(!m.containsAttribute("galleryObject")){
            m.addAttribute("galleryObject", new Gallery());
        }

        return "galleries_new";
    }

    @PostMapping("/admin/galleries/add")
    public String saveGallery(Gallery galleryObject, RedirectAttributes ra){
        Gallery saved = gRepo.save(galleryObject);

        return "redirect:/admin/photos/new?id=" + saved.getId();
    }

    @GetMapping("/admin/photos/new")
    public String addPhotos(Model m, @RequestParam("id") int id){
        Optional<Gallery> go = gRepo.findById(id);

        Gallery g = go.get();

        m.addAttribute("galleryObject", g);

        return "photos_new";
    }

    @PostMapping("/admin/{id}/photos/add")
    public String savePhotos(@RequestParam("photos") MultipartFile[] files, @PathVariable("id") String id, RedirectAttributes ra){
        int noOfFiles = 0;

        Optional<Gallery> go = gRepo.findById(Integer.valueOf(id));

        Gallery gToSave = go.get();

        try {
            for(int i = 0; i < files.length; i++){
                if(files[i].getBytes().length > 10){
                    Photo toSave = new Photo();
                    toSave.setName(files[i].getOriginalFilename());
                    toSave.setData(files[i].getBytes());
                    gToSave.getPhotos().add(pRepo.save(toSave));
                    noOfFiles++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gallery saved = gRepo.save(gToSave);

        System.out.println(saved);

        if(noOfFiles > 0){
            ra.addFlashAttribute("mSg", "" + noOfFiles + " FILES UPLOADED!");
        } else {
            ra.addFlashAttribute("mSg", "ERROR UPLOADING!!");
        }

        return "redirect:/galleries";
    }

    @GetMapping("/galleries/{id}")
    public String serveGallery(@PathVariable("id") int id, Model m, RedirectAttributes ra){
        Optional<Gallery> go = gRepo.findById(id);

        if(go.isPresent()){
            Gallery g = go.get();
            m.addAttribute("g", g);
            return "galleries_view";
        }

        ra.addFlashAttribute("mSg", "That Gallery Is Not Found!");
        return "redirect:/galleries";
    }

    @GetMapping(value = "/db-images/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveImage(@PathVariable("id") int id, HttpServletResponse res) throws Exception {
        Optional<Photo> po = pRepo.findById(id);

        if(po.isPresent()){
            Photo p = po.get();
            StreamUtils.copy(p.getData(), res.getOutputStream());
        }

    }


}
