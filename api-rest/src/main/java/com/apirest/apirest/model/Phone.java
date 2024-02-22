package com.apirest.apirest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Lazy;

import java.util.UUID;

@Entity
@Table(name = "phones")
@Getter @Setter
public class Phone {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column
    private UUID id;

    @Column
    private String number;

    @Column
    private String cityCode;

    @Column
    private String countryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;        // Llave Foranea user de la tabla Userss
}
