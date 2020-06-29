package com.hpkarugendo.components;

import com.hpkarugendo.models.SiteAdmin;
import com.hpkarugendo.services.SiteAdminService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataComponent implements CommandLineRunner {

    private SiteAdminService saService;
    private BCryptPasswordEncoder encoder;

    public DataComponent(SiteAdminService saService, BCryptPasswordEncoder b) {
        this.saService = saService;
        this.encoder = b;
    }

    @Override
    public void run(String... args) throws Exception {

        SiteAdmin saToSave = new SiteAdmin("masterAdmin", "master.admin@hpkarugendo.com", encoder.encode("masterAdminPass"));

        if(saService.saveUser(saToSave)){
            System.out.println("SAVED USER: " + saToSave.getAdminUsername() + "\nWITH PASS: " + saToSave.getAdminPassword());
        } else {
            System.out.println("Error Saving User!");
        }

    }
}
