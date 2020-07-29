package com.hpkarugendo.services;

import com.hpkarugendo.models.SiteAdmin;
import com.hpkarugendo.repositories.SiteAdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SiteAdminService implements UserDetailsService {
    private SiteAdminRepository saRepo;

    public SiteAdminService(SiteAdminRepository saRepo) {
        this.saRepo = saRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<SiteAdmin> sao = saRepo.findByAdminUsername(s);

        if(sao.isPresent()){
            return sao.get();
        }

        return null;
    }

    public SiteAdmin getUserByUsername(String username){
        Optional<SiteAdmin> ao = saRepo.findByAdminUsername(username);

        if(ao.isPresent()){
            return ao.get();
        }

        return null;
    }

    public boolean saveUser(SiteAdmin user) throws Exception {
        boolean success = false;

        SiteAdmin toSave = saRepo.save(user);

        if(toSave != null){
            success = true;
        }

        return success;
    }

    public void disableUser(SiteAdmin user) {
        SiteAdmin toDisable = user;
        toDisable.setEnabled(false);
        saRepo.save(toDisable);
    }

    public void deleteAllUsers(){
        saRepo.deleteAll();
    }
}
