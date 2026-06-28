package com.biblioteca.Repositorio;

import com.biblioteca.Entidad.Categoria;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class CategoriaRepositorio implements PanacheRepository<Categoria> {
    
    public Optional<Categoria> findByNombre(String nombre) {
        return find("nombre", nombre).firstResultOptional();
    }
}