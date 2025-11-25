package org.example.models;

import java.time.LocalDateTime;

public class ProductoTalla {
    private Integer productoTallaId;
    private Integer productoId;
    private Integer tallaId;
    private Integer cantidad;
    private LocalDateTime updatedAt;

    public ProductoTalla() {
    }

    public ProductoTalla(Integer productoTallaId, Integer productoId, Integer tallaId,
                         Integer cantidad, LocalDateTime updatedAt) {
        this.productoTallaId = productoTallaId;
        this.productoId = productoId;
        this.tallaId = tallaId;
        this.cantidad = cantidad;
        this.updatedAt = updatedAt;
    }

    public Integer getProductoTallaId() {
        return productoTallaId;
    }

    public void setProductoTallaId(Integer productoTallaId) {
        this.productoTallaId = productoTallaId;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getTallaId() {
        return tallaId;
    }

    public void setTallaId(Integer tallaId) {
        this.tallaId = tallaId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
