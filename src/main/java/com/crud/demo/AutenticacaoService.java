package com.crud.demo;

import com.crud.demo.model.entity.User;
import com.crud.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AutenticacaoService implements UserDetailsService {

    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<User> usuarioOptional = userRepository.findByUserDetailsEntity_Username(username);
        if (usuarioOptional.isPresent()){
            return usuarioOptional.get().getUserDetailsEntity();
        }
        throw new UsernameNotFoundException("Dados inválidos!");
    }

}
