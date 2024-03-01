package com.apirest.apirest;

import com.apirest.apirest.model.Phone;
import com.apirest.apirest.model.User;
import com.apirest.apirest.repository.PhoneRepository;
import com.apirest.apirest.repository.UserRepository;
import com.apirest.apirest.util.UtilValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.auditing.AuditingHandlerSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiRestApplicationTests {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PhoneRepository phoneRepository;

	@Mock
	private UtilValidation utilValidation;

	@InjectMocks
	private UserService userService;

	@Test
	void testGetUserById_UserExists() {
		UUID id = UUID.randomUUID();
		User user = new User();
		user.setEmail("test@example.com");
		user.setPassword("MyS3cr3tP@ss");

		when(userRepository.findById(id)).thenReturn(Optional.of(user));

		User foundUser = userService.getUserById(id);

		assertEquals(user, foundUser); // verifica que el usuario encontrado sea igual al usuario creado.
	}

	@Test
	void testGetUserById_UserDoesNotExist() { // busca un usuario por su ID, pero no se encuentra en el repositorio.
		UUID id = UUID.randomUUID();

		when(userRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> userService.getUserById(id));
	}

	@Test
	void testGetAllUsers() {
		List<User> users = new ArrayList<>();
		users.add(new User());
		users.add(new User());

		when(userRepository.findAll()).thenReturn(users);

		List<User> foundUsers = userService.getAllUsers();

		assertEquals(users.size(), foundUsers.size());
		// verifica que la cantidad de usuarios encontrados sea igual a la cantidad de usuarios creados
	}

	@Test
	void testUpdateUser_UserExists() {
		UUID id = UUID.randomUUID();
		User existingUser = new User();
		existingUser.setEmail("test@example.com");
		existingUser.setPassword("existingPassword");

		User updatedUser = new User();
		updatedUser.setEmail("test@example.com");
		updatedUser.setPassword("updatedPassword");

		when(userRepository.findById(id)).thenReturn(Optional.of(existingUser)); // devuelve el objeto de usuario existente
		when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

		User resultUser = userService.updateUser(id, updatedUser);

		assertEquals(updatedUser.getEmail(), resultUser.getEmail());
		assertEquals(updatedUser.getPassword(), resultUser.getPassword());
		// verifica que el usuario actualizado tenga el mismo email y contraseña que el usuario existente
	}

	@Test
	void testUpdateUser_UserDoesNotExist() {
		UUID id = UUID.randomUUID();
		User updatedUser = new User();
		updatedUser.setEmail("test@example.com");
		updatedUser.setPassword("updatedPassword");

		when(userRepository.findById(id)).thenReturn(Optional.empty());
		// verifica el comportamiento cuando se intenta actualizar un usuario que no existe en el repositorio

		assertThrows(IllegalArgumentException.class, () -> userService.updateUser(id, updatedUser));
	}

	@Test
	void testDeleteUser() {
		UUID id = UUID.randomUUID();

		doNothing().when(userRepository).deleteById(id);

		userService.deleteUser(id);

		verify(userRepository, times(1)).deleteById(id);
		// Verifica que el metodo del repositorio se haya llamado solo una vez
	}

	@Test
	void testPatchUser_UserExists() {
		UUID id = UUID.randomUUID();
		User existingUser = new User();
		existingUser.setEmail("test@example.com");
		existingUser.setPassword("existingPassword");

		User updatedUser = new User();
		updatedUser.setEmail("test@example.com");
		updatedUser.setPassword("updatedPassword");

		when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
		when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

		User resultUser = userService.patchUser(id, updatedUser);

		assertEquals(updatedUser.getEmail(), resultUser.getEmail());
		// verifica que el email del usuario actualizado sea igual al email del usuario existente
		assertNotEquals(updatedUser.getPassword(), resultUser.getPassword());
		assertEquals(existingUser.getPassword(), resultUser.getPassword());
	}//  verifica que la contraseña del usuario actualizado no sea igual a la contraseña del usuario existente

	@Test
	void testPatchUser_UserDoesNotExist() {
		//  verifica el comportamiento cuando se intenta actualizar parcialmente un usuario que no existe en el repositorio
		UUID id = UUID.randomUUID();
		User updatedUser = new User();
		updatedUser.setEmail("test@example.com");
		updatedUser.setPassword("updatedPassword");

		when(userRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> userService.patchUser(id, updatedUser));
	}

	@Test
	void testCreateUser_EmailAlreadyExists() {
		// verifica el comportamiento cuando se intenta crear un usuario con una direccion de email que ya existe
		User user = new User();
		user.setEmail("test@example.com");
		user.setPassword("MyS3cr3tP@ss");

		when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

		assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
	}

	@Test
	void testCreateUser_InvalidEmail() {
		//  verifica el comportamiento cuando se intenta crear un usuario con un email no valido
		User user = new User();
		user.setEmail("invalidEmail");
		user.setPassword("MyS3cr3tP@ss");

		when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
		when(utilValidation.emailValido(user.getEmail())).thenReturn(false);

		assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
	}

	@Test
	void testCreateUser_InvalidPassword() {
		//  verifica el comportamiento cuando se intenta crear un usuario con una contrasena no valida
		User user = new User();
		user.setEmail("test@example.com");
		user.setPassword("invalidPassword");

		when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
		when(utilValidation.emailValido(user.getEmail())).thenReturn(true);
		when(utilValidation.claveValida(user.getPassword())).thenReturn(false);

		assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
	}

}

@ExtendWith(MockitoExtension.class)
class UserTest {
	@Test
	void testUser() {
		com.apirest.apirest.model.User user = new com.apirest.apirest.model.User();
		user.setName("Test User");
		user.setEmail("test@example.com");
		user.setPassword("MyS3cr3tP@ss");

		assertEquals("Test User", user.getName());
		assertEquals("test@example.com", user.getEmail());
		assertEquals("MyS3cr3tP@ss", user.getPassword());
	}

	@Test
	void testUser_IdUser() {
		User user = new User();
		UUID id = UUID.randomUUID();
		user.setId_user(id);

		assertEquals(id, user.getId_user());
	}

	@Test
	void testUser_Phone() {
		User user = new User();
		List<Phone> phones = new ArrayList<>();
		Phone phone = new Phone();
		phone.setNumber("1234567890");
		phone.setCityCode("1");
		phone.setCountryCode("1");
		phones.add(phone);
		user.setPhone(phones);

		assertEquals(phones, user.getPhone());
	}

	@Test
	void testUser_CreatedModifiedLastLogin() {
		User user = new User();
		user.onCreate();

		assertNotNull(user.getCreated());
		assertNotNull(user.getModified());
		assertNotNull(user.getLastLogin());
		// verifica que no sean nulos
		assertEquals(user.getCreated(), user.getModified());
		assertEquals(user.getCreated(), user.getLastLogin());
		// verifica que created sea igual
	}

	@Test
	void testUser_OnUpdate() {
		User user = new User();
		user.onCreate();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		user.onUpdate();

		assertNotNull(user.getModified());
		// verifica que no sea nulo
		assertNotEquals(user.getCreated(), user.getModified());
		// y que no sea igual
	}

	@Test
	void testUser_Active() {
		User user = new User();
		user.setActive(true);

		assertTrue(user.isActive());
	}
}

@ExtendWith(MockitoExtension.class)
class PhoneTest{
	@Test
	void testPhone() {
		Phone phone = new Phone();
		phone.setNumber("1234567890");
		phone.setCityCode("1");
		phone.setCountryCode("1");

		assertEquals("1234567890", phone.getNumber());
		assertEquals("1", phone.getCityCode());
		assertEquals("1", phone.getCountryCode());
	}
}

//JaCoCo
//Verde = cubierto
//Amarillo = no cubierto en todos los casos
//Rojo = no esta cubierto
