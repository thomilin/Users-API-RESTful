package com.apirest.apirest.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UtilValidation {

    @Value("${validation.password.regex}")
    private String passwordRegex;
    public boolean claveValida(String password){
        System.out.println(password.matches(passwordRegex));
        return password.matches(passwordRegex);
    }

    @Value("${validation.email.regex}")
    private String emailRegex;
    public boolean emailValido(String password){
        System.out.println(password.matches(emailRegex));
        return password.matches(emailRegex);
    }

}