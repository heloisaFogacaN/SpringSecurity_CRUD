package com.crud.demo.service;

import com.crud.demo.model.dto.UserDTO;
import com.crud.demo.model.dto.UserEditDTO;
import com.crud.demo.model.entity.User;
import com.crud.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    private final ObjectMapper objectMapper;


    public void save(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        userRepository.save(user);
    }

    public void update(UserEditDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        userRepository.save(user);
    }

    public void updatePassword(Integer id, String password) throws Exception {
        User user;
        try {
            user = userRepository.findById(id).get();
            user.setPassword(password);
            userRepository.save(user);
        } catch (Exception e){
            throw new Exception("Usuário com id " + id + " não foi encontrado!");
        }
    }

    public User findOne(Integer id) {
       return userRepository.findById(id).get();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public void updateStatus(Integer id, boolean status) throws Exception {
        User user;
        try {
            user = userRepository.findById(id).get();
            user.setStatus(status);
            userRepository.save(user);
        } catch (Exception e){
            throw new Exception("Usuário com id " + id + " não foi encontrado!");
        }
    }

    public void save(String user, MultipartFile file) throws Exception {
        UserDTO userDTO = objectMapper.readValue(user, UserDTO.class);
        userDTO.setFiles(List.of(file));
        save(userDTO);
    }
}
