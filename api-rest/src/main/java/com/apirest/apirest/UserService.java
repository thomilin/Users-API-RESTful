package com.apirest.apirest;

import com.apirest.apirest.model.User;
import com.apirest.apirest.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.util.UUID;

@Component
public class UserService {
    @Autowired //Revisar por que tira error  ---> Solucionado
    private UserRepository userRepository;

    public User createUser(User user){
        // Verifica si el correo esta dentroo
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Date now = new Date();
        user.setCreated(now);
        user.setModified(now);
        user.setLastLogin(now);
        user.setActive(true);

        return userRepository.save(user);
    }

    public User getUserById(UUID id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

    public void deleteUser(UUID id){

        userRepository.deleteById(id);
    }

    public User updateUser(UUID id, User updatedUser) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());

        return userRepository.save(existingUser);
    }

    //Falta hacer un PATCH para cambiar contrasena

}