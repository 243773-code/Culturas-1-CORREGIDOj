package org.example.services;

import org.example.models.ProductoTalla;
import org.example.repositories.ProductoTallaRepository;

import java.util.List;

public class ProductoTallaService {

    private final ProductoTallaRepository productoTallaRepository;

    public ProductoTallaService() {
        this.productoTallaRepository = new ProductoTallaRepository();
    }

    public List<ProductoTalla> getAllProductoTallas() {
        return productoTallaRepository.findAll();
    }

    public ProductoTalla getProductoTallaById(int productoTallaId) {
        if (productoTallaId <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }

        ProductoTalla productoTalla = productoTallaRepository.findById(productoTallaId);

        if (productoTalla == null) {
            throw new RuntimeException("ProductoTalla no encontrado con ID: " + productoTallaId);
        }

        return productoTalla;
    }

    public List<ProductoTalla> getProductoTallasByProductoId(int productoId) {
        if (productoId <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser mayor a 0");
        }

        return productoTallaRepository.findByProductoId(productoId);
    }

    public ProductoTalla createProductoTalla(ProductoTalla productoTalla) {
        validateProductoTalla(productoTalla);
        return productoTallaRepository.save(productoTalla);
    }

    public ProductoTalla updateProductoTalla(int productoTallaId, ProductoTalla productoTalla) {
        ProductoTalla existing = productoTallaRepository.findById(productoTallaId);
        if (existing == null) {
            throw new RuntimeException("ProductoTalla no encontrado con ID: " + productoTallaId);
        }

        validateProductoTalla(productoTalla);

        productoTalla.setProductoTallaId(productoTallaId);
        return productoTallaRepository.update(productoTalla);
    }

    public boolean deleteProductoTalla(int productoTallaId) {
        if (productoTallaId <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }

        ProductoTalla productoTalla = productoTallaRepository.findById(productoTallaId);
        if (productoTalla == null) {
            throw new RuntimeException("ProductoTalla no encontrado con ID: " + productoTallaId);
        }

        return productoTallaRepository.deleteById(productoTallaId);
    }

    public long countProductoTallas() {
        return productoTallaRepository.count();
    }

    private void validateProductoTalla(ProductoTalla productoTalla) {
        if (productoTalla == null) {
            throw new IllegalArgumentException("ProductoTalla no puede ser nulo");
        }

        if (productoTalla.getProductoId() <= 0) {
            throw new IllegalArgumentException("El producto es obligatorio");
        }

        if (productoTalla.getTallaId() <= 0) {
            throw new IllegalArgumentException("La talla es obligatoria");
        }

        if (productoTalla.getCantidad() < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
    }
}