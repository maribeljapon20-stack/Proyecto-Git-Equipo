package com.biblioteca.Controlador;

import com.biblioteca.Entidad.Usuario;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    
    private static List<Usuario> usuarios = new ArrayList<>();
    private static Long contadorId = 1L;
    
    @POST
    public Response crearUsuario(@Valid Usuario usuario) {
        usuario.setId(contadorId++);
        usuarios.add(usuario);
        return Response.status(Response.Status.CREATED)
                .entity(usuario)
                .build();
    }
    
    @GET
    public List<Usuario> listarUsuarios() {
        return usuarios;
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerUsuario(@PathParam("id") Long id) {
        Usuario usuario = usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
        
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuario no encontrado con ID: " + id)
                    .build();
        }
        
        return Response.ok(usuario).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response eliminarUsuario(@PathParam("id") Long id) {
        boolean eliminado = usuarios.removeIf(u -> u.getId().equals(id));
        
        if (!eliminado) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuario no encontrado con ID: " + id)
                    .build();
        }
        
        return Response.noContent().build();
    }
}