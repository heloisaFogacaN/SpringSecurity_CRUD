package com.crud.demo;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@AllArgsConstructor
public class BeanConfigs {
    private AutenticacaoService autenticacaoService;

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("POST"));
        corsConfiguration.setAllowCredentials(true);
        
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();  // provider
//        dao.setPasswordEncoder(new BCryptPasswordEncoder());
        dao.setUserDetailsService(autenticacaoService);
        return new ProviderManager(dao);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

//   @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth, AutenticacaoService autenticacaoService) throws Exception{
//        auth.userDetailsService(autenticacaoService).passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }

//    @Bean
//    public UserDetailsService userDetailsService(AutenticacaoService autenticacaoService){
//        return autenticacaoService;
//    }