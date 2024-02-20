package com.apirest.apirest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

@Entity
@Table(name = "phones")
@Getter @Setter
public class Phone {
    @Id     //Convierte este atributo en un Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //Hace que sea auto incrementable
    @Column
    private Long id;

    @Column
    private String number;

    @Column
    private String cityCode;

    @Column
    private String countryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;        // Llave Foranea user de la tabla Userss
}
