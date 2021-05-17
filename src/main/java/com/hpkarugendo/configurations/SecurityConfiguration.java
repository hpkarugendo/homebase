package com.hpkarugendo.configurations;

import com.hpkarugendo.services.SiteAdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private SiteAdminService siteAdminService;
    private final String AZURE_STORAGE_CONNECTION_STRING = "DefaultEndpointsProtocol=https;AccountName=homebase3113sa;AccountKey=7i6YduiGLByA0lCdVEq6tgG/BLgtErKHn29XNWaKpolt8D19nnGzIFxm7VVbkY4E4tSTASgAgYbSweOQjE5L9g==;EndpointSuffix=core.windows.net";
    private final String AZURE_STORAGE_CONTAINER_NAME = "homebase3113con";

    public SecurityConfiguration(SiteAdminService sa){
        this.siteAdminService = sa;
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return siteAdminService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/home", "/css/**", "/js/**", "/images/**", "/files/**", "/db-images/**", "/profile", "/projects/**", "/galleries/**", "/blog/**", "/error*").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
    }
}
