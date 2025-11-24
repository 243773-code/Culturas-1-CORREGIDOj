package org.example.services;

import org.example.models.Categoria;
import org.example.repositories.CategoriaRepository;

import java.util.List;

/**
 * CategoriaService - Lógica de negocio para CATEGORIA
 */
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService() {
        this.categoriaRepository = new CategoriaRepository();
    }

    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria getCategoriaById(int categoriaId) {
        if (categoriaId <= 0) {
            throw new IllegalArgumentException("El ID de la categoría debe ser mayor a 0");
        }

        Categoria categoria = categoriaRepository.findById(categoriaId);

        if (categoria == null) {
            throw new RuntimeException("Categoría no encontrada con ID: " + categoriaId);
        }

        return categoria;
    }

    public Categoria getCategoriaByNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío");
        }

        Categoria categoria = categoriaRepository.findByNombre(nombre);

        if (categoria == null) {
            throw new RuntimeException("Categoría no encontrada con nombre: " + nombre);
        }

        return categoria;
    }

    public Categoria createCategoria(Categoria categoria) {
        validateCategoria(categoria);

        if (categoriaRepository.existsByNombre(categoria.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con el nombre: " + categoria.getNombre());
        }

        return categoriaRepository.save(categoria);
    }

    public Categoria updateCategoria(int categoriaId, Categoria categoria) {
        Categoria existingCategoria = categoriaRepository.findById(categoriaId);
        if (existingCategoria == null) {
            throw new RuntimeException("Categoría no encontrada con ID: " + categoriaId);
        }

        validateCategoria(categoria);

        Categoria categoriaWithSameName = categoriaRepository.findByNombre(categoria.getNombre());
        if (categoriaWithSameName != null && categoriaWithSameName.getCategoriaId() != categoriaId) {
            throw new RuntimeException("Ya existe otra categoría con el nombre: " + categoria.getNombre());
        }

        categoria.setCategoriaId(categoriaId);
        return categoriaRepository.update(categoria);
    }

    public boolean deleteCategoria(int categoriaId) {
        if (categoriaId <= 0) {
            throw new IllegalArgumentException("El ID de la categoría debe ser mayor a 0");
        }

        Categoria categoria = categoriaRepository.findById(categoriaId);
        if (categoria == null) {
            throw new RuntimeException("Categoría no encontrada con ID: " + categoriaId);
        }

        return categoriaRepository.deleteById(categoriaId);
    }

    public boolean existsByNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        return categoriaRepository.existsByNombre(nombre);
    }

    public long countCategorias() {
        return categoriaRepository.count();
    }

    private void validateCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría no puede ser nula");
        }

        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio");
        }

        if (categoria.getNombre().length() > 100) {
            throw new IllegalArgumentException("El nombre de la categoría no puede exceder 100 caracteres");
        }

        if (categoria.getDescripcion() != null && categoria.getDescripcion().length() > 255) {
            throw new IllegalArgumentException("La descripción de la categoría no puede exceder 255 caracteres");
        }
    }
}