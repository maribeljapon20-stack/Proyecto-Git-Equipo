package com.biblioteca.Servicio;

import com.biblioteca.Entidad.Prestamo;
import com.biblioteca.Entidad.Libro;
import com.biblioteca.Entidad.Usuario;
import com.biblioteca.Repositorio.PrestamoRepositorio;  // ← Repositorio con mayúscula
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class PrestamoServicio {
    
    @Inject
    PrestamoRepositorio prestamoRepository;  // ← Clase con Repositorio, variable con repository
    
    @Inject
    LibroServicio libroServicio;
    
    @Inject
    UsuarioServicio usuarioServicio;
    
    public List<Prestamo> listarTodos() {
        return prestamoRepository.listAll();
    }
    
    public Prestamo buscarPorId(Long id) {
        return prestamoRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));
    }
    
    @Transactional
    public Prestamo crearPrestamo(@Valid Prestamo prestamo) {
        // Verificar que el libro exista y tenga stock
        Libro libro = libroServicio.buscarPorId(prestamo.libro.id);
        if (libro.stock <= 0) {
            throw new RuntimeException("No hay stock disponible para el libro: " + libro.titulo);
        }
        
        // Verificar que el usuario exista
        Usuario usuario = usuarioServicio.buscarPorId(prestamo.usuario.id);
        
        // Establecer la fecha de préstamo
        prestamo.fechaPrestamo = LocalDate.now();
        prestamo.libro = libro;
        prestamo.usuario = usuario;
        
        // Reducir el stock del libro
        libro.stock--;
        libroServicio.actualizarLibro(libro.id, libro);
        
        prestamoRepository.persist(prestamo);  // ← Usar prestamoRepository
        return prestamo;
    }
    
    @Transactional
    public Prestamo devolverLibro(Long id) {
        Prestamo prestamo = buscarPorId(id);
        
        if (prestamo.devuelto) {
            throw new RuntimeException("El libro ya ha sido devuelto");
        }
        
        prestamo.devuelto = true;
        prestamo.fechaDevolucionReal = LocalDate.now();
        
        // Aumentar el stock del libro
        Libro libro = libroServicio.buscarPorId(prestamo.libro.id);
        libro.stock++;
        libroServicio.actualizarLibro(libro.id, libro);
        
        prestamoRepository.persist(prestamo);  // ← Cambiado de prestamoRepositorio a prestamoRepository
        return prestamo;
    }
    
    @Transactional
    public void eliminarPrestamo(Long id) {
        Prestamo prestamo = buscarPorId(id);
        prestamoRepository.delete(prestamo);  // ← Cambiado de prestamoRepositorio a prestamoRepository
    }
}