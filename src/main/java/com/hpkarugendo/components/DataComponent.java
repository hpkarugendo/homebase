package com.hpkarugendo.components;

import com.hpkarugendo.models.Gallery;
import com.hpkarugendo.models.SiteAdmin;
import com.hpkarugendo.services.HomebaseStorageService;
import com.hpkarugendo.services.SiteAdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DataComponent implements CommandLineRunner {

    private SiteAdminService saService;
    private BCryptPasswordEncoder encoder;
    private HomebaseStorageService storageService;

    public DataComponent(SiteAdminService saService, BCryptPasswordEncoder encoder, HomebaseStorageService storageService) {
        this.saService = saService;
        this.encoder = encoder;
        this.storageService = storageService;
    }

    @Override
    public void run(String... args) throws Exception {

        SiteAdmin saToSave = new SiteAdmin("masterAdmin", "master.admin@hpkarugendo.com", encoder.encode("masterAdminPass"));

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
