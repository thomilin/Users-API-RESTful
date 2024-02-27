package com.apirest.apirest;

import com.apirest.apirest.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.auditing.AuditingHandlerSupport;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ApiRestApplicationTests {

	@Test
	public void testcreateUser() {
		UserService userService = new UserService();

		User user = new User();

		user.setEmail("test@test.com");
		user.setPassword("MyS3cr3tP@ss");


	}

		/*
		Assertions.assertEquals(esperado, resultado); //Esto es igual a lo otro y falla si no
		Assertions.assertTrue(); //Dice si es verdader, si es falso falla
		Assertions.assertFalse(); //Si algo no es falso, falla
		Assertions.fail(); //Para que falle
		*/

}

//JaCoCo
//Verde = cubierto
//Amarillo = no cubierto en todos los casos
//Rojo = no esta cubierto
