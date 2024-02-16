package com.crud.demo.model.entity;

import com.crud.demo.UserDetailsEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;
    private String password;
    private boolean status;
    private boolean enabled;
    @OneToOne(cascade = CascadeType.ALL)
    private UserDetailsEntity userDetailsEntity;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Arquivo> files = new ArrayList<>();
}
