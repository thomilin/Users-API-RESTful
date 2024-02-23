package com.apirest.apirest;

import com.apirest.apirest.model.User;
import com.apirest.apirest.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
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

    /* public User updateUser(UUID id, User updatedUser) {  // (User user) que reciba el usuario completo

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        existingUser.setName(updatedUser.getName());  //existinguser.usuariocompleto
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setActive(updatedUser.isActive());

        return userRepository.save(existingUser);
    } //Asi mismo esta funcionando como un PATCH, la idea es que se actualice completamente, que reciba todos los objetos del usuario
    //Falta hacer un PATCH para cambiar contrasena    /   O dejarlo para cambiar Name y Email como tengo en POST */

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