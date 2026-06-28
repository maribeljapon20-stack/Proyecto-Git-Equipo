package com.biblioteca.Controlador;

import com.biblioteca.Entidad.Usuario;
import com.biblioteca.Servicio.UsuarioServicio;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.net.URI;
import java.util.List;

@Path("/api/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Usuarios", description = "API para la gestión de usuarios")
public class UsuarioResource {
    
    @Inject
    UsuarioServicio usuarioServicio;
    
    @GET
    @Operation(summary = "Listar todos los usuarios")
    public List<Usuario> listarUsuarios() {
        return usuarioServicio.listarTodos();
    }
    
    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar usuario por ID")
    public Response buscarUsuarioPorId(@PathParam("id") Long id) {
        Usuario usuario = usuarioServicio.buscarPorId(id);
        return Response.ok(usuario).build();
    }
    
    @POST
    @Operation(summary = "Crear un nuevo usuario")
    public Response crearUsuario(@Valid Usuario usuario) {
        Usuario nuevoUsuario = usuarioServicio.crearUsuario(usuario);
        return Response.created(URI.create("/api/usuarios/" + nuevoUsuario.id))
                      .entity(nuevoUsuario)
                      .build();
    }
    
    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar un usuario")
    public Response actualizarUsuario(@PathParam("id") Long id, @Valid Usuario usuario) {
        Usuario usuarioActualizado = usuarioServicio.actualizarUsuario(id, usuario);
        return Response.ok(usuarioActualizado).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar un usuario")
    public Response eliminarUsuario(@PathParam("id") Long id) {
        usuarioServicio.eliminarUsuario(id);
        return Response.noContent().build();
    }
}