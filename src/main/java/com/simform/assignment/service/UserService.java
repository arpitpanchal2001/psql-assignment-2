package com.simform.assignment.service;

import com.simform.assignment.entity.Order;
import com.simform.assignment.entity.User;
import com.simform.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public User getById(Long id ) {
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            User user = byId.get();
            return user;
        }
        else {
            throw new RuntimeException("User not found");
        }
    }
    public void update(Long id , User user){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            if( !user.getName().isBlank()) {
                existingUser.setName(user.getName());
            }
            if( !user.getAddress().isBlank()) {
                existingUser.setAddress(user.getAddress());
            }
            if(user.getOrders() instanceof Order) {
               existingUser.setOrders(user.getOrders());
            }
            userRepository.save(existingUser);
        }
    }
}
