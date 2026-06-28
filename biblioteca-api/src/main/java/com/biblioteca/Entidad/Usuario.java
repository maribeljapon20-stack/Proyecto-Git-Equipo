package com.biblioteca.Entidad;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Usuario extends PanacheEntity {
    
    @NotBlank(message = "La cédula es obligatoria")
    @Column(unique = true)
    public String cedula;
    
    @NotBlank(message = "El nombre es obligatorio")
    public String nombre;
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    @Column(unique = true)
    public String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    public String password;
}