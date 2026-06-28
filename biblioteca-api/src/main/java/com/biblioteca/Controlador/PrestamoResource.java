package com.biblioteca.Controlador;

import com.biblioteca.Entidad.Prestamo;
import com.biblioteca.Servicio.PrestamoServicio;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.net.URI;
import java.util.List;

@Path("/api/prestamos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Préstamos", description = "API para la gestión de préstamos")
public class PrestamoResource {
    
    @Inject
    PrestamoServicio prestamoServicio;
    
    @GET
    @Operation(summary = "Listar todos los préstamos")
    public List<Prestamo> listarPrestamos() {
        return prestamoServicio.listarTodos();
    }
    
    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar préstamo por ID")
    public Response buscarPrestamoPorId(@PathParam("id") Long id) {
        Prestamo prestamo = prestamoServicio.buscarPorId(id);
        return Response.ok(prestamo).build();
    }
    
    @POST
    @Operation(summary = "Crear un nuevo préstamo")
    public Response crearPrestamo(@Valid Prestamo prestamo) {
        Prestamo nuevoPrestamo = prestamoServicio.crearPrestamo(prestamo);
        return Response.created(URI.create("/api/prestamos/" + nuevoPrestamo.id))
                      .entity(nuevoPrestamo)
                      .build();
    }
    
    @PUT
    @Path("/{id}/devolver")
    @Operation(summary = "Devolver un libro")
    public Response devolverLibro(@PathParam("id") Long id) {
        Prestamo prestamoDevuelto = prestamoServicio.devolverLibro(id);
        return Response.ok(prestamoDevuelto).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar un préstamo")
    public Response eliminarPrestamo(@PathParam("id") Long id) {
        prestamoServicio.eliminarPrestamo(id);
        return Response.noContent().build();
    }
}