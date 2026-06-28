package com.biblioteca.Controlador;

import com.biblioteca.Entidad.Libro;
import com.biblioteca.Servicio.LibroServicio;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.net.URI;
import java.util.List;

@Path("/api/libros")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Libros", description = "API para la gestión de libros")
public class LibroResource {
    
    @Inject
    LibroServicio libroServicio;
    
    @GET
    @Operation(summary = "Listar todos los libros")
    public List<Libro> listarLibros() {
        return libroServicio.listarTodos();
    }
    
    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar libro por ID")
    public Response buscarLibroPorId(@PathParam("id") Long id) {
        Libro libro = libroServicio.buscarPorId(id);
        return Response.ok(libro).build();
    }
    
    @POST
    @Operation(summary = "Crear un nuevo libro")
    public Response crearLibro(@Valid Libro libro) {
        Libro nuevoLibro = libroServicio.crearLibro(libro);
        return Response.created(URI.create("/api/libros/" + nuevoLibro.id))
                      .entity(nuevoLibro)
                      .build();
    }
    
    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar un libro")
    public Response actualizarLibro(@PathParam("id") Long id, @Valid Libro libro) {
        Libro libroActualizado = libroServicio.actualizarLibro(id, libro);
        return Response.ok(libroActualizado).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar un libro")
    public Response eliminarLibro(@PathParam("id") Long id) {
        libroServicio.eliminarLibro(id);
        return Response.noContent().build();
    }
}