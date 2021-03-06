package com.hpkarugendo.controllers;

import com.hpkarugendo.models.Gallery;
import com.hpkarugendo.models.Photo;
import com.hpkarugendo.repositories.GalleryRepository;
import com.hpkarugendo.repositories.PhotoRepository;
import com.hpkarugendo.services.HomebaseStorageService;
import com.microsoft.azure.storage.StorageException;
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
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Controller
public class GalleryController {

    private HomebaseStorageService storageService;

    public GalleryController(HomebaseStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/galleries")
    public String gallery(Model m){
        List<Gallery> galleries = storageService.listGalleries();

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
        Gallery toSave = galleryObject;
        toSave.setName(toSave.getName().toLowerCase());
        Gallery found = storageService.getGalleryByName(toSave.getName());

        if(found == null){
            storageService.saveGallery(toSave);
            found = storageService.getGalleryByName(toSave.getName());
            ra.addFlashAttribute("mSg", "GALLERY SAVED!");
        } else {
            ra.addFlashAttribute("mSg", "!! GALLERY ALREADY EXISTS !!");
            return "redirect:/admin/galleries/new";
        }

        return "redirect:/admin/photos/new?id=" + found.getId();
    }

    @GetMapping("/admin/photos/new")
    public String addPhotos(Model m, @RequestParam("id") String id){

        Gallery g = storageService.getGalleryById(id);

        m.addAttribute("galleryObject", g);

        return "photos_new";
    }

    @PostMapping("/admin/{id}/photos/add")
    public String savePhotos(@RequestParam("photos") MultipartFile[] files, @PathVariable("id") String id, RedirectAttributes ra) throws URISyntaxException, StorageException {
        int noOfFiles = 0;

        Gallery gToSave = storageService.getGalleryById(id);

        boolean ans = false;

        if(gToSave != null){
            for(int a = 0; a < files.length; a++){
                if(files[a].getSize() > 10){
                    Photo toSave = new Photo();
                    toSave.setName(files[a].getOriginalFilename().replace("_", ""));
                    toSave.setGallery(gToSave);
                    String url = storageService.getSavedImageUrl(gToSave.getName(), files[a]).toString();
                    if(url != null){
                        toSave.setUrl(url);
                    } else {
                        noOfFiles = 0;
                        break;
                    }
                    gToSave.getPhotos().add(storageService.savePhoto(toSave));
                    noOfFiles++;
                }
            }
            storageService.saveGallery(gToSave);
        }

        if(noOfFiles > 0){
            ra.addFlashAttribute("mSg", "" + noOfFiles + " FILES UPLOADED!");
        } else {
            ra.addFlashAttribute("mSg", "ERROR UPLOADING!!");
        }

        return "redirect:/galleries";
    }

    @GetMapping("/galleries/{id}")
    public String serveGallery(@PathVariable("id") String id, Model m, RedirectAttributes ra){
        Gallery g = storageService.getGalleryById(id);

        if(g != null){
            m.addAttribute("g", g);
            return "galleries_view";
        }

        ra.addFlashAttribute("mSg", "That Gallery Is Not Found!");
        return "redirect:/galleries";
    }

    @GetMapping("admin/galleries/delete/{id}")
    public String deleteGallery(@PathVariable("id") String id, RedirectAttributes ra){
        Gallery toDelete = storageService.getGalleryById(id);

        if(toDelete == null){
            ra.addFlashAttribute("mSg", "GALLERY NOT FOUND!");
            return "redirect:/admin/dashboard";
        }

        storageService.deleteContainer(toDelete.getName());
        storageService.deleteGallery(toDelete.getId());

        ra.addFlashAttribute("mSg", "GALLERY - " + toDelete.getName() + " - DELETED!");

        return "redirect:/admin/dashboard";
    }

    @GetMapping("admin/photos/delete/{id}")
    public String deletePhoto(@PathVariable("id") String id, RedirectAttributes ra){
        Photo toDelete = storageService.getPhoto(id);

        if(toDelete == null){
            ra.addFlashAttribute("mSg", "PHOTO NOT FOUND!");
            return "redirect:/admin/dashboard";
        }

        storageService.deletePhoto(toDelete.getId());

        ra.addFlashAttribute("mSg","PHOTO - " + toDelete.getName() + " - DELETED FROM DB AND CLOUD CONTAINER!");

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/galleries/images/{id}")
    public String viewImage(@PathVariable("id") String id, Model m){
        Photo photo = storageService.getPhoto(id);
        m.addAttribute("photo", photo);

        return "image-view";
    }


}
