package com.apirest.apirest.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
//No me funciona AllArgsConstructor (No se por que D:)
//Ni @RequiredArgsContructor :C
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column
    private Long id;
    @Getter @Setter @Column
    private String name;
    @Getter @Setter @Column
    private String email;
    @Getter @Setter @Column
    private String password;
    @Getter @Setter @Column
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Phone> phones;
}
