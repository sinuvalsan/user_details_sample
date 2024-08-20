package com.example.UserLogin.service;


import com.example.UserLogin.dto.UserDTO;
import com.example.UserLogin.entity.User;
import com.example.UserLogin.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    PasswordEncoder encoder =  new BCryptPasswordEncoder();

    //Create new user
    public User createUser(UserDTO userDto) {
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(encoder.encode(userDto.getPassword()));
        newUser.setActive(true);
        return userRepo.save(newUser);
    }

    //Get user
    public Optional<User> getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    // Read all users
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // Update user
    public User updateUser(UserDTO userDto) {
        Optional<User> user = userRepo.findByUsername(userDto.getUsername());
        //user.
        if(!userDto.getPassword().isEmpty()) {
            user.get().setPassword(encoder.encode(userDto.getPassword()));
        }
        if(!userDto.getActive().toString().isEmpty()){
            user.get().setActive(userDto.getActive());
        }
        return userRepo.save(user.get());
    }

    //Delete user
    public boolean deleteUser(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            userRepo.deleteByUsername(username);
            return true; // User found and deleted
        } else {
            return false; // User  not found
        }
    }

}
