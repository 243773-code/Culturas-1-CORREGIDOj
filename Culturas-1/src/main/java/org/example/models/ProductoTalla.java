package org.example.models;

import java.time.LocalDateTime;

public class ProductoTalla {
    private Integer productoTallaId;
    private Integer productoId;
    private Integer tallaId;
    private Integer cantidad;
    private LocalDateTime updatedAt;

    // Constructor vacío
    public ProductoTalla() {
    }

    // Constructor con todos los parámetros
    public ProductoTalla(Integer productoTallaId, Integer productoId, Integer tallaId,
                         Integer cantidad, LocalDateTime updatedAt) {
        this.productoTallaId = productoTallaId;
        this.productoId = productoId;
        this.tallaId = tallaId;
        this.cantidad = cantidad;
        this.updatedAt = updatedAt;
    }

    // Constructor sin ID (para inserciones)
    public ProductoTalla(Integer productoId, Integer tallaId, Integer cantidad) {
        this.productoId = productoId;
        this.tallaId = tallaId;
        this.cantidad = cantidad;
    }


    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public Integer getProductoTallaId() {
        return productoTallaId;
    }

    public void setProductoTallaId(Integer productoTallaId) {
        this.productoTallaId = productoTallaId;
    }

    public Integer getProductoId() {
        return productoId;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getTallaId() {
        return tallaId;
    }

    public void setTallaId(Integer tallaId) {
        this.tallaId = tallaId;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ProductoTalla{" +
                "productoTallaId=" + productoTallaId +
                ", productoId=" + productoId +
                ", tallaId=" + tallaId +
                ", cantidad=" + cantidad +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @SuppressWarnings("unused")
    public void setCreatedAt(LocalDateTime createdAt) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductoTalla that = (ProductoTalla) o;
        return productoTallaId != null && productoTallaId.equals(that.productoTallaId);
    }


    @Override
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public int hashCode() {
        return productoTallaId != null ? productoTallaId.hashCode() : 0;
    }

}