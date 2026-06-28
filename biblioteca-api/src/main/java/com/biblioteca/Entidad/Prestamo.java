package com.biblioteca.Entidad;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Prestamo extends PanacheEntity {
    
    @NotNull(message = "El libro es obligatorio")
    @ManyToOne
    @JoinColumn(name = "libro_id")
    public Libro libro;
    
    @NotNull(message = "El usuario es obligatorio")
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    public Usuario usuario;
    
    @NotNull(message = "La fecha de préstamo es obligatoria")
    public LocalDate fechaPrestamo = LocalDate.now();
    
    @NotNull(message = "La fecha máxima de devolución es obligatoria")
    @Future(message = "La fecha de devolución debe ser futura")
    public LocalDate fechaDevolucionMax;
    
    public boolean devuelto = false;
    public LocalDate fechaDevolucionReal;
}