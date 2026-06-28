package com.biblioteca.Servicio;

import com.biblioteca.Entidad.Libro;
import com.biblioteca.Entidad.Categoria;
import com.biblioteca.Repositorio.LibroRepositorio;
import com.biblioteca.Excepcion.RecursoDuplicadoException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;

@ApplicationScoped
public class LibroServicio {
    
    @Inject
    LibroRepositorio libroRepository;
    
    @Inject
    CategoriaServicio categoriaServicio;
    
    public List<Libro> listarTodos() {
        return libroRepository.listAll();
    }
    
    public Libro buscarPorId(Long id) {
        return libroRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
    }
    
    @Transactional
    public Libro crearLibro(@Valid Libro libro) {
        // Verificar si el ISBN ya existe
        if (libroRepository.findByIsbn(libro.isbn).isPresent()) {
            throw new RecursoDuplicadoException("Ya existe un libro con el ISBN: " + libro.isbn);
        }
        
        // Verificar que la categoría exista
        Categoria categoria = categoriaServicio.buscarPorId(libro.categoria.id);
        libro.categoria = categoria;
        
        libroRepository.persist(libro);
        return libro;
    }
    
    @Transactional
    public Libro actualizarLibro(Long id, @Valid Libro libroActualizado) {
        Libro libroExistente = buscarPorId(id);
        
        libroExistente.isbn = libroActualizado.isbn;
        libroExistente.titulo = libroActualizado.titulo;
        libroExistente.autor = libroActualizado.autor;
        libroExistente.stock = libroActualizado.stock;
        
        // Verificar y actualizar categoría si es necesario
        if (libroActualizado.categoria != null && libroActualizado.categoria.id != null) {
            Categoria categoria = categoriaServicio.buscarPorId(libroActualizado.categoria.id);
            libroExistente.categoria = categoria;
        }
        
        libroRepository.persist(libroExistente);
        return libroExistente;
    }
    
    @Transactional
    public void eliminarLibro(Long id) {
        Libro libro = buscarPorId(id);
        libroRepository.delete(libro);
    }
}