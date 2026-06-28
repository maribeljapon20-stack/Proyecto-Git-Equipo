package com.biblioteca.Repositorio;

import com.biblioteca.Entidad.Prestamo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class PrestamoRepositorio implements PanacheRepository<Prestamo> {
    
    public List<Prestamo> findByUsuarioId(Long usuarioId) {
        return find("usuario.id", usuarioId).list();
    }
    
    public List<Prestamo> findPrestamosActivosByUsuarioId(Long usuarioId) {
        return find("usuario.id = ?1 and devuelto = false", usuarioId).list();
    }
}