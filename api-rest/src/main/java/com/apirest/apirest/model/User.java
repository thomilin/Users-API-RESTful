package com.apirest.apirest.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
//No me funciona AllArgsConstructor (No se por que D:)
//Ni @RequiredArgsContructor :C
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    @Column
    private String name;
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Email inválido")
    @Column
    private String email;
    @NotBlank(message = "La contraseña es obligatoria")
    @Column
    private String password;
    @Column
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Phone> phone;
    @Column
    private LocalDateTime created;
    @Column
    private LocalDateTime modified;
    @Column
    private LocalDateTime lastLogin;
    @Column
    private boolean active;
}
