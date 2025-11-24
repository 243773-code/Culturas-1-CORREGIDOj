package org.example.services;

import org.example.models.Producto;
import org.example.repositories.ProductoRepository;

import java.math.BigDecimal;
import java.util.List;

public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService() {
        this.productoRepository = new ProductoRepository();
    }

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto getProductoById(int productoId) {
        if (productoId <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser mayor a 0");
        }

        Producto producto = productoRepository.findById(productoId);

        if (producto == null) {
            throw new RuntimeException("Producto no encontrado con ID: " + productoId);
        }

        return producto;
    }

    public Producto getProductoBySku(String sku) {
        if (sku == null || sku.trim().isEmpty()) {
            throw new IllegalArgumentException("El SKU no puede estar vacío");
        }

        Producto producto = productoRepository.findBySku(sku);

        if (producto == null) {
            throw new RuntimeException("Producto no encontrado con SKU: " + sku);
        }

        return producto;
    }

    public List<Producto> getProductosByCategoriaId(int categoriaId) {
        if (categoriaId <= 0) {
            throw new IllegalArgumentException("El ID de categoría debe ser mayor a 0");
        }

        return productoRepository.findByCategoriaId(categoriaId);
    }

    public Producto createProducto(Producto producto) {
        validateProducto(producto);

        if (productoRepository.existsBySku(producto.getSku())) {
            throw new RuntimeException("Ya existe un producto con el SKU: " + producto.getSku());
        }

        return productoRepository.save(producto);
    }

    public Producto updateProducto(int productoId, Producto producto) {
        Producto existingProducto = productoRepository.findById(productoId);
        if (existingProducto == null) {
            throw new RuntimeException("Producto no encontrado con ID: " + productoId);
        }

        validateProducto(producto);

        Producto productoWithSameSku = productoRepository.findBySku(producto.getSku());
        if (productoWithSameSku != null && productoWithSameSku.getProductoId() != productoId) {
            throw new RuntimeException("Ya existe otro producto con el SKU: " + producto.getSku());
        }

        producto.setProductoId(productoId);
        producto.setFechaCreacion(existingProducto.getFechaCreacion());
        return productoRepository.update(producto);
    }

    public boolean deleteProducto(int productoId) {
        if (productoId <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser mayor a 0");
        }

        Producto producto = productoRepository.findById(productoId);
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado con ID: " + productoId);
        }

        return productoRepository.deleteById(productoId);
    }

    public long countProductos() {
        return productoRepository.count();
    }

    private void validateProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        if (producto.getCategoriaId() <= 0) {
            throw new IllegalArgumentException("La categoría del producto es obligatoria");
        }

        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }

        if (producto.getSku() == null || producto.getSku().trim().isEmpty()) {
            throw new IllegalArgumentException("El SKU del producto es obligatorio");
        }

        if (producto.getPrecioBase() == null || producto.getPrecioBase().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio del producto debe ser mayor a 0");
        }

        if (producto.getStockTotal() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        if (producto.getNombre().length() > 100) {
            throw new IllegalArgumentException("El nombre no puede exceder 100 caracteres");
        }

        if (producto.getSku().length() > 50) {
            throw new IllegalArgumentException("El SKU no puede exceder 50 caracteres");
        }
    }
}