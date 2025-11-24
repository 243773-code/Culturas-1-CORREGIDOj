package org.example.services;

import org.example.models.ImagenProducto;
import org.example.repositories.ImagenProductoRepository;

import java.util.List;

public class ImagenProductoService {

    private final ImagenProductoRepository imagenProductoRepository;

    public ImagenProductoService() {
        this.imagenProductoRepository = new ImagenProductoRepository();
    }

    public List<ImagenProducto> getAllImagenes() {
        return imagenProductoRepository.findAll();
    }

    public ImagenProducto getImagenById(int imagenId) {
        if (imagenId <= 0) {
            throw new IllegalArgumentException("El ID de la imagen debe ser mayor a 0");
        }

        ImagenProducto imagen = imagenProductoRepository.findById(imagenId);

        if (imagen == null) {
            throw new RuntimeException("Imagen no encontrada con ID: " + imagenId);
        }

        return imagen;
    }

    public List<ImagenProducto> getImagenesByProductoId(int productoId) {
        if (productoId <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser mayor a 0");
        }

        return imagenProductoRepository.findByProductoId(productoId);
    }

    public ImagenProducto createImagen(ImagenProducto imagen) {
        validateImagen(imagen);
        return imagenProductoRepository.save(imagen);
    }

    public ImagenProducto updateImagen(int imagenId, ImagenProducto imagen) {
        ImagenProducto existingImagen = imagenProductoRepository.findById(imagenId);
        if (existingImagen == null) {
            throw new RuntimeException("Imagen no encontrada con ID: " + imagenId);
        }

        validateImagen(imagen);

        imagen.setImagenId(imagenId);
        return imagenProductoRepository.update(imagen);
    }

    public boolean deleteImagen(int imagenId) {
        if (imagenId <= 0) {
            throw new IllegalArgumentException("El ID de la imagen debe ser mayor a 0");
        }

        ImagenProducto imagen = imagenProductoRepository.findById(imagenId);
        if (imagen == null) {
            throw new RuntimeException("Imagen no encontrada con ID: " + imagenId);
        }

        return imagenProductoRepository.deleteById(imagenId);
    }

    public long countImagenes() {
        return imagenProductoRepository.count();
    }

    private void validateImagen(ImagenProducto imagen) {
        if (imagen == null) {
            throw new IllegalArgumentException("La imagen no puede ser nula");
        }

        if (imagen.getProductoId() <= 0) {
            throw new IllegalArgumentException("El producto de la imagen es obligatorio");
        }

        if (imagen.getUrl() == null || imagen.getUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("La URL de la imagen es obligatoria");
        }

        if (imagen.getUrl().length() > 255) {
            throw new IllegalArgumentException("La URL no puede exceder 255 caracteres");
        }
    }
}