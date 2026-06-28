package com.biblioteca.Servicio;

import com.biblioteca.Entidad.Usuario;
import com.biblioteca.Repositorio.UsuarioRepositorio;
import com.biblioteca.Excepcion.RecursoDuplicadoException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;

@ApplicationScoped
public class UsuarioServicio {
    
    @Inject
    UsuarioRepositorio usuarioRepository;
    
    public List<Usuario> listarTodos() {
        return usuarioRepository.listAll();
    }
    
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }
    
    @Transactional
    public Usuario crearUsuario(@Valid Usuario usuario) {
        if (usuarioRepository.findByCedula(usuario.cedula).isPresent()) {
            throw new RecursoDuplicadoException("Ya existe un usuario con la cédula " + usuario.cedula);
        }
        if (usuarioRepository.findByCorreo(usuario.correo).isPresent()) {
            throw new RecursoDuplicadoException("Ya existe un usuario con el correo " + usuario.correo);
        }
        usuarioRepository.persist(usuario);
        return usuario;
    }
    
    @Transactional
    public Usuario actualizarUsuario(Long id, @Valid Usuario usuarioActualizado) {
        Usuario usuarioExistente = buscarPorId(id);
        usuarioExistente.cedula = usuarioActualizado.cedula;
        usuarioExistente.nombre = usuarioActualizado.nombre;
        usuarioExistente.correo = usuarioActualizado.correo;
        usuarioExistente.password = usuarioActualizado.password;
        usuarioRepository.persist(usuarioExistente);
        return usuarioExistente;
    }
    
    @Transactional
    public void eliminarUsuario(Long id) {
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }
    
    // ⬇️⬇️⬇️ NUEVO MÉTODO AQUÍ ⬇️⬇️⬇️
    public Usuario autenticar(String cedula, String password) {
        return usuarioRepository.find("cedula = ?1 and password = ?2", cedula, password)
                .firstResultOptional()
                .orElse(null);
    }
}