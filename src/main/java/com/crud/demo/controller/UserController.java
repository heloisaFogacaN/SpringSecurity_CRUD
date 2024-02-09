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

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;


    @PostMapping()
    public void create(@RequestBody UserDTO userDTO){
        userService.save(userDTO);
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

    @PatchMapping("/password/{id}")
    public void changePassword(@RequestParam Integer id, @RequestParam String password) throws Exception {
        userService.updatePassword(id, password);
    }

    @PatchMapping("/status/{id}")
    public void changeStatus(@RequestParam Integer id, @RequestParam boolean status) throws Exception {
        userService.updateStatus(id, status);
    }

    @PostMapping("/cadastrar")
    public void editarPatch(@RequestParam String user, @RequestParam MultipartFile file) throws Exception {
        userService.save(user, file);
    }
}
