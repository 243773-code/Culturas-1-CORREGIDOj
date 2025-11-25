package org.example.services;

import org.example.models.ProductoTalla;
import org.example.repositories.ProductoTallaRepository;

import java.util.List;

public class ProductoTallaService {

    private final ProductoTallaRepository productoTallaRepository;

    public ProductoTallaService() {
        this.productoTallaRepository = new ProductoTallaRepository();
    }

    public List<ProductoTalla> getAll() {
        return productoTallaRepository.findAll();
    }

    public ProductoTalla getById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }

        ProductoTalla pt = productoTallaRepository.findById(id);
        if (pt == null) {
            throw new RuntimeException("Producto_talla no encontrado con ID: " + id);
        }
        return pt;
    }

    public ProductoTalla create(ProductoTalla pt) {
        validate(pt);
        return productoTallaRepository.save(pt);
    }

    public ProductoTalla update(int id, ProductoTalla pt) {
        ProductoTalla existing = getById(id); // valida existencia

        validate(pt);
        pt.setProductoTallaId(existing.getProductoTallaId());

        return productoTallaRepository.update(pt);
    }

    public boolean delete(int id) {
        getById(id); // lanza error si no existe
        return productoTallaRepository.deleteById(id);
    }

    public long count() {
        return productoTallaRepository.count();
    }

    public boolean updateCantidad(int id, int cantidad) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }

        // asegura que el registro existe
        getById(id);

        return productoTallaRepository.updateCantidad(id, cantidad);
    }

    private void validate(ProductoTalla pt) {
        if (pt == null) {
            throw new IllegalArgumentException("ProductoTalla no puede ser nulo");
        }
        if (pt.getProductoId() == null || pt.getProductoId() <= 0) {
            throw new IllegalArgumentException("producto_id es obligatorio");
        }
        if (pt.getTallaId() == null || pt.getTallaId() <= 0) {
            throw new IllegalArgumentException("talla_id es obligatorio");
        }
        if (pt.getCantidad() == null || pt.getCantidad() < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
    }
}
