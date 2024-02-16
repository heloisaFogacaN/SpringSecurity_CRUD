package com.crud.demo.controller;


import com.crud.demo.model.dto.UserDTO;
import com.crud.demo.model.dto.UserEditDTO;
import com.crud.demo.model.entity.User;
import com.crud.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;


    @PostMapping()
    public User create(@RequestBody UserDTO userDTO){
       return userService.save(userDTO);
    }

    @PutMapping()
    public void upDate(@RequestBody UserEditDTO userDTO){
        userService.update(userDTO);
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Integer id){
        return userService.findOne(id);
    }

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        userService.delete(id);

    }

    @PostMapping("/password/{id}")
    public void changePassword(@RequestParam Integer id, @RequestParam String password) throws Exception {
        userService.updatePassword(id, password);
    }

    @PostMapping("/status/{id}")
    public void changeStatus(@RequestParam Integer id, @RequestParam boolean status) throws IOException {

    }

    @PostMapping("/cadastrar")
    public User editarPatch(@RequestParam String user, @RequestParam MultipartFile file) throws Exception {
       return userService.save(user, file);
    }
}
