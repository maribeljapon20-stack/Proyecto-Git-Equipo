package com.biblioteca.Entidad;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Libro extends PanacheEntity {
    
    @NotBlank(message = "El ISBN es obligatorio")
    @Column(unique = true)
    public String isbn;
    
    @NotBlank(message = "El título es obligatorio")
    public String titulo;
    
    @NotBlank(message = "El autor es obligatorio")
    public String autor;
    
    @PositiveOrZero(message = "El stock debe ser mayor o igual a 0")
    public int stock;
    
    @NotNull(message = "La categoría es obligatoria")
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    public Categoria categoria;
}