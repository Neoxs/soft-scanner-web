package org.example.softscannerweb.services;

import org.example.softscannerweb.model.User;
import org.example.softscannerweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUserWithDetails(User user) {
        return userRepository.save(user);
    }

    public User getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public User updateUser(String id, User updatedUser) {
        if (userRepository.existsById(id)) {
            updatedUser.setID(id);
            return userRepository.save(updatedUser);
        }
        return null;
    }
}