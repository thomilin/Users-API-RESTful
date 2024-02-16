package com.apirest.apirest.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column
    private long id;
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
