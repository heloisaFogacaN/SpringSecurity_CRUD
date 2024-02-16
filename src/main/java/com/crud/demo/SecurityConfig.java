package com.crud.demo;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final AutenticacaoService autenticacaoService;

    @Autowired
    public void config(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(autenticacaoService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers(HttpMethod.GET, "/teste").hasAuthority("Get")
//                        .requestMatchers("/teste").permitAll()
                        .anyRequest().authenticated()
        );

        http.formLogin(Customizer.withDefaults());
        return http.build();
    }

    //    @Bean
//    public UserDetailsService userDetailsService(){
//        return autenticacaoService;
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }

//    @Bean
//    public UserDetailsService inMemoryUser(){
//        UserDetails user = User.withDefaultPasswordEncoder().username("mi72").password("M!dois").build();
//        return new InMemoryUserDetailsManager(user);
//    }
}
