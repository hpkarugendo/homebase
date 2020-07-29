package com.hpkarugendo.components;

import com.hpkarugendo.models.BlogPostCategory;
import com.hpkarugendo.models.Gallery;
import com.hpkarugendo.models.SiteAdmin;
import com.hpkarugendo.services.BlogPostService;
import com.hpkarugendo.services.HomebaseStorageService;
import com.hpkarugendo.services.SiteAdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class DataComponent implements CommandLineRunner {

    private SiteAdminService saService;
    private BCryptPasswordEncoder encoder;
    private HomebaseStorageService storageService;
    private EmailComponent emailComponent;
    private BlogPostService postService;

    public DataComponent(SiteAdminService saService, BCryptPasswordEncoder encoder, HomebaseStorageService storageService, EmailComponent emailComponent, BlogPostService postService) {
        this.saService = saService;
        this.encoder = encoder;
        this.storageService = storageService;
        this.emailComponent = emailComponent;
        this.postService = postService;
    }

    @Override
    public void run(String... args) throws Exception {

        //String email = new String("Hi Henry Patrick\nThe Application Homebase has been started or restarted!\nIf this was not you, you best go look into it.\n\nSystem.");

        //emailComponent.sendMessage(null, null, "System Start", email);

        //Delete all Users
        if(postService.listCategories().isEmpty()){
            List<BlogPostCategory> cats = new ArrayList<>();
            BlogPostCategory cat = new BlogPostCategory();
            cat.setName("Uncategorised");
            cats.add(cat);
            cat = new BlogPostCategory();
            cat.setName("Technology");
            cats.add(cat);
            cat = new BlogPostCategory();
            cat.setName("Travel");
            cats.add(cat);
            cat = new BlogPostCategory();
            cat.setName("Programming");
            cats.add(cat);
            cat = new BlogPostCategory();
            cat.setName("Social");
            cats.add(cat);
            cat = new BlogPostCategory();
            cat.setName("Movies");
            cats.add(cat);

            postService.saveAll(cats);
            System.out.println("SAVED CATEGORIES!!!");
        }

        SiteAdmin saToSave = new SiteAdmin("masterAdmin", "master.admin@hpkarugendo.com", encoder.encode("masterAdminPass"));

        saToSave.setAdminFullName("Master Administrator");

        if(saService.loadUserByUsername(saToSave.getAdminUsername()) == null){
            if(saService.saveUser(saToSave)){
                System.out.println("SAVED USER: " + saToSave.getAdminUsername() + "\nWITH PASS: " + saToSave.getAdminPassword());
            } else {
                System.out.println("Error Saving User!");
            }
        }

        for(Gallery g : storageService.listGalleries()){
            if(g.getPhotos().isEmpty()){
                storageService.deleteGallery(g.getId());
                storageService.deleteContainer(g.getName());
            }
        }

    }
}
