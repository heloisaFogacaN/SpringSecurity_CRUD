package com.crud.demo;

import com.crud.demo.model.entity.User;
import com.crud.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@AllArgsConstructor
public class DataBaseConfig {
    private UserRepository userRepository;

    @PostConstruct
    public void init(){
        User user = new User();
        user.setName("Teste");
        user.setUserDetailsEntity(UserDetailsEntity.builder()
                .user(user)
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .authorities(List.of(Autorizacao.GET))
                .username("teste")
                .password("teste123")
                .build());

        userRepository.save(user);
    }
}
