package com.biblioteca.Repositorio;

import com.biblioteca.Entidad.Libro;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class LibroRepositorio implements PanacheRepository<Libro> {
    
    public Optional<Libro> findByIsbn(String isbn) {
        return find("isbn", isbn).firstResultOptional();
    }
    
    public List<Libro> findByCategoriaId(Long categoriaId) {
        return find("categoria.id", categoriaId).list();
    }
}