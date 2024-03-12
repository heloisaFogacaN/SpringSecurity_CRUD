package com.crud.demo;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final SecurityContextRepository repo; // armazena em memória o usuário da sessão
    private final FiltroAutenticacao filtroAutenticacao = new FiltroAutenticacao();

    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable); // gera um token para garantir se realemnte a requisição que está vindo do cliente é viável(autenticado) ou não, só está disable, para que ós pudessemos concluir com mais rapidez
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers(HttpMethod.GET, "/user").hasAuthority("Get") //
                        .requestMatchers(HttpMethod.GET, "/user/users").permitAll()
                        .anyRequest().authenticated());
        http.securityContext((context) -> context.securityContextRepository(repo)); // Manter a  sessão do usuário ativa

        http.formLogin(Customizer.withDefaults());
//        http.httpBasic(Customizer.withDefaults()); esse permite que ele fique no topo da página
        http.logout(Customizer.withDefaults());
        http.sessionManagement(config->{
            config.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        http.addFilterBefore(filtroAutenticacao, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain config(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttp)
//        http.authorizeHttpRequests(authorizeRequests ->
//                authorizeRequests
//                        .requestMatchers(HttpMethod.GET, "/user").hasAuthority("Get")
//                        .requestMatchers(HttpMethod.GET, "/user/users").permitAll()
//                        .anyRequest().authenticated()
//        );
//
//        http.formLogin(Customizer.withDefaults());
//        http.logout(Customizer.withDefaults());
//        return http.build();
//    }

    //    @Bean
//    public UserDetailsService userDetailsService(){
//        return autenticacaoService;
//    }


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth){
//        auth
//                .userDetailsService(autenticacaoService)
//                .passwordEncoder(passwordEncoder());
//    }
}
