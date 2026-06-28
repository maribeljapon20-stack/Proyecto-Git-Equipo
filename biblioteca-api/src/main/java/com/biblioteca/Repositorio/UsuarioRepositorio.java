package com.biblioteca.Repositorio;


import com.biblioteca.Entidad.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UsuarioRepositorio implements PanacheRepository<Usuario> {
    
    public Optional<Usuario> findByCedula(String cedula) {
        return find("cedula", cedula).firstResultOptional();
    }
    
    public Optional<Usuario> findByCorreo(String correo) {
        return find("correo", correo).firstResultOptional();
    }
    
    public Optional<Usuario> findByCedulaAndPassword(String cedula, String password) {
        return find("cedula = ?1 and password = ?2", cedula, password).firstResultOptional();
    }
}
