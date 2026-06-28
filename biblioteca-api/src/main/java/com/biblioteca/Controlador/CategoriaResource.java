package com.biblioteca.Controlador;

import com.biblioteca.Entidad.Categoria;
import com.biblioteca.Servicio.CategoriaServicio;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.net.URI;
import java.util.List;

@Path("/api/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Categorías", description = "API para la gestión de categorías")
public class CategoriaResource {
    
    @Inject
    CategoriaServicio categoriaServicio;
    
    @GET
    @Operation(summary = "Listar todas las categorías")
    public List<Categoria> listarCategorias() {
        return categoriaServicio.listarTodos();
    }
    
    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar categoría por ID")
    public Response buscarCategoriaPorId(@PathParam("id") Long id) {
        Categoria categoria = categoriaServicio.buscarPorId(id);
        return Response.ok(categoria).build();
    }
    
    @POST
    @Operation(summary = "Crear una nueva categoría")
    public Response crearCategoria(@Valid Categoria categoria) {
        Categoria nuevaCategoria = categoriaServicio.crearCategoria(categoria);
        return Response.created(URI.create("/api/categorias/" + nuevaCategoria.id))
                      .entity(nuevaCategoria)
                      .build();
    }
    
    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar una categoría")
    public Response actualizarCategoria(@PathParam("id") Long id, @Valid Categoria categoria) {
        Categoria categoriaActualizada = categoriaServicio.actualizarCategoria(id, categoria);
        return Response.ok(categoriaActualizada).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar una categoría")
    public Response eliminarCategoria(@PathParam("id") Long id) {
        categoriaServicio.eliminarCategoria(id);
        return Response.noContent().build();
    }
}