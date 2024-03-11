package com.crud.demo;

import com.crud.demo.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@Entity
@Data
@NoArgsConstructor
@Builder
public class UserDetailsEntity implements UserDetails {
    // o que eu não for usar, deve retornar true e, caso seja o authorities deve retornar uma lista vazia
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, updatable = false)
    private String username; // essa é uma das opções, posso considerar essa ou apenas adicionar no construtor o user, questão de estética
    @Length(min = 6)
    private String password; // essa é uma das opções, posso considerar essa ou apenas adicionar no construtor o user, questão de estética
    private boolean enabled; // importante
    private Collection<Autorizacao> authorities; // importante
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    @OneToOne(mappedBy = "userDetailsEntity")
    @JsonIgnore // Isso permite que o spring security acesse os dados do meu user
    private User user;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @OneToOne
//    @NonNull
//    private Usuario usuario;
//    private boolean enabled;
//    private String password;
//    private GrantedAuthority authorities;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return usuario.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return usuario.getUsuario();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return usuario.isHabilitado();
//    }
}
