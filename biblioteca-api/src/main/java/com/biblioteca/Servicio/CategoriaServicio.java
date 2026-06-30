package com.biblioteca.Servicio;
esta linea es un error :)
import com.biblioteca.Entidad.Categoria;
import com.biblioteca.Repositorio.CategoriaRepositorio;
import com.biblioteca.Excepcion.RecursoDuplicadoException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;

@ApplicationScoped
public class CategoriaServicio {
    
    @Inject
    CategoriaRepositorio categoriaRepository;
    
    public List<Categoria> listarTodos() {
        return categoriaRepository.listAll();
    }
    
    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
    }
    
    @Transactional
    public Categoria crearCategoria(@Valid Categoria categoria) {
        if (categoriaRepository.findByNombre(categoria.nombre).isPresent()) {
            throw new RecursoDuplicadoException("Ya existe una categoría con el nombre: " + categoria.nombre);
        }
        categoriaRepository.persist(categoria);
        return categoria;
    }
    
    @Transactional
    public Categoria actualizarCategoria(Long id, @Valid Categoria categoriaActualizada) {
        Categoria categoriaExistente = buscarPorId(id);
        categoriaExistente.nombre = categoriaActualizada.nombre;
        categoriaRepository.persist(categoriaExistente);
        return categoriaExistente;
    }
    
    @Transactional
    public void eliminarCategoria(Long id) {
        Categoria categoria = buscarPorId(id);
        categoriaRepository.delete(categoria);
    }
}