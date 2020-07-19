package com.hpkarugendo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;

@Entity
public class SiteAdmin implements UserDetails {
    @Id
    private final String adminId = UUID.randomUUID().toString();
    @Column(unique = true)
    private String adminUsername;
    @Column(unique = true)
    private String adminEmail;
    private String adminPassword;
    private Date adminJoined;
    private boolean isEnabled;

    public SiteAdmin() {
        this.adminJoined = new Date();
        this.isEnabled = true;
    }

    public SiteAdmin(String adminUsername, String adminEmail, String adminPassword) {
        this.adminUsername = adminUsername;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.adminJoined = new Date();
        this.isEnabled = true;
    }

    public Date getAdminJoined() {
        return adminJoined;
    }

    public void setAdminJoined(Date adminJoined) {
        this.adminJoined = adminJoined;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        return "SiteAdmin{" +
                "adminId='" + adminId + '\'' +
                ", adminUsername='" + adminUsername + '\'' +
                ", adminEmail='" + adminEmail + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ADMIN";
            }
        };

        authorities.add(authority);

        authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "USER";
            }
        };

        authorities.add(authority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return adminPassword;
    }

    @Override
    public String getUsername() {
        return adminUsername;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setEnabled(boolean value){
        this.isEnabled = value;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }


}
