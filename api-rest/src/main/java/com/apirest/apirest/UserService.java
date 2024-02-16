package com.apirest.apirest;

import com.apirest.apirest.model.User;
import com.apirest.apirest.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User getUserById(Long id){
        //return userRepository.findById(id).orElseThrow(() -> {throw new RuntimeException();});
        Optional<User> optinalUser = userRepository.findById(id);
        return optinalUser.get();
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
