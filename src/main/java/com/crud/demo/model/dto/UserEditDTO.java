package com.crud.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEditDTO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private boolean status;
}
