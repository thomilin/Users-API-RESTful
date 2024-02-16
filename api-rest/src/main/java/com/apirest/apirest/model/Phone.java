package com.apirest.apirest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

@Entity
@Table(name = "phones")
public class Phone {
    @Id     //Convierte este atributo en un Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //Hace que sea auto incrementable
    @Getter @Setter @Column
    private Long id;
    @Getter @Setter @Column
    private String number;
    @Getter @Setter @Column
    private String cityCode;
    @Getter @Setter @Column
    private String countryCode;
    @Getter @Setter @Column
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user.id")
    private User user;        // Llave Foranea user de la tabla User
}
