package com.apirest.apirest;

import com.apirest.apirest.model.User;
import com.apirest.apirest.repository.UserRepository;
import com.apirest.apirest.util.UtilValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UtilValidation utilValidation;

    public User createUser(User user){
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Date now = new Date();
        user.setCreated(now);
        user.setModified(now);
        user.setLastLogin(now);
        user.setActive(true);

        if ( !utilValidation.emailValido(user.getEmail())) {
            throw new IllegalArgumentException("El email es invalido");
        }

        if ( !utilValidation.claveValida(user.getPassword())) {
            throw new IllegalArgumentException("La clave es invalida");
        }

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

        Field[] fields = User.class.getDeclaredFields(); // obtiene todos los campos de la clase User

        for (Field field : fields) {   // itera sobre cada campo y actualiza su valor si se proporciona en el objeto actualizado

            field.setAccessible(true);             // esto hace que el campo sea accesible, ya que podria ser privado
            try {
                Object updatedValue = field.get(updatedUser);  // obtiene el valor del campo del objeto actualizado
                if (updatedValue != null) {
                    field.set(existingUser, updatedValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace(); // imprime la traza de la pila de la excepción en la consola
                throw new IllegalArgumentException("Error al actualizar el usuario: " + e.getMessage());
            }
        }

        Date now = new Date();
        updatedUser.setCreated(now);
        updatedUser.setModified(now);
        updatedUser.setLastLogin(now);
        updatedUser.setActive(true);

        return userRepository.save(existingUser);
    }

    public User patchUser(UUID id, User updatedUser) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());

        return userRepository.save(existingUser);
    }
}