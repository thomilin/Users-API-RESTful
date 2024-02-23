package com.apirest.apirest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data // para los @getter @setter
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column
    private UUID id_user;

    @NotBlank(message = "El nombre es obligatorio")
    @Column
    @Size(max = 35, message = "El nombre no puede tener mas de 35 caracteres")
    private String name;

    //@NotBlank(message = "El correo es obligatorio")   //APRENDER A USAR TRY CATCH EN USERSERVICE EN VEZ DE ESTO
    //@Email(message = "Email inválido")
    @Column(unique = true)
    private String email;

    //@NotBlank(message = "Formato de contraseña inválido (az AZ 0-9 !@#$%^&*()_+=;:,. {8,16})")
    @Column
    private String password;

    @Column
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Phone> phone;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date modified;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date lastLogin;

    @Column
    private boolean active;

    @PrePersist
    protected void onCreate(){
        created = new Date();
        modified = new Date();
        lastLogin = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        modified = new Date();
    }
}
