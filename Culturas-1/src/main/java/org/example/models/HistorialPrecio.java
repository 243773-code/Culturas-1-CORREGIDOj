package org.example.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HistorialPrecio {

    private Integer historialId;
    private Integer productoId;
    private BigDecimal precioAnterior;
    private BigDecimal nuevoPrecio;   // campo interno
    private LocalDateTime fechaCambio;
    private Integer usuarioId;

    // Constructor vacío
    public HistorialPrecio() {
    }

    // Constructor con todos los parámetros
    public HistorialPrecio(Integer historialId,
                           Integer productoId,
                           BigDecimal precioAnterior,
                           BigDecimal nuevoPrecio,
                           LocalDateTime fechaCambio,
                           Integer usuarioId) {
        this.historialId = historialId;
        this.productoId = productoId;
        this.precioAnterior = precioAnterior;
        this.nuevoPrecio = nuevoPrecio;
        this.fechaCambio = fechaCambio;
        this.usuarioId = usuarioId;
    }

    // Constructor sin ID (para inserciones)
    public HistorialPrecio(Integer productoId,
                           BigDecimal precioAnterior,
                           BigDecimal nuevoPrecio,
                           LocalDateTime fechaCambio,
                           Integer usuarioId) {
        this.productoId = productoId;
        this.precioAnterior = precioAnterior;
        this.nuevoPrecio = nuevoPrecio;
        this.fechaCambio = fechaCambio;
        this.usuarioId = usuarioId;
    }

    // ===== GETTERS & SETTERS =====

    public Integer getHistorialId() {
        return historialId;
    }

    public void setHistorialId(Integer historialId) {
        this.historialId = historialId;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public BigDecimal getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(BigDecimal precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    // Getter “viejo” que ya tenías
    public BigDecimal getNuevoPrecio() {
        return nuevoPrecio;
    }

    public void setNuevoPrecio(BigDecimal nuevoPrecio) {
        this.nuevoPrecio = nuevoPrecio;
    }

    // Alias para que el REPO funcione: precioNuevo == nuevoPrecio
    public BigDecimal getPrecioNuevo() {
        return nuevoPrecio;
    }

    public void setPrecioNuevo(BigDecimal precioNuevo) {
        this.nuevoPrecio = precioNuevo;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public String toString() {
        return "HistorialPrecio{" +
                "historialId=" + historialId +
                ", productoId=" + productoId +
                ", precioAnterior=" + precioAnterior +
                ", nuevoPrecio=" + nuevoPrecio +
                ", fechaCambio=" + fechaCambio +
                ", usuarioId=" + usuarioId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistorialPrecio that = (HistorialPrecio) o;
        return historialId != null && historialId.equals(that.historialId);
    }

    @Override
    public int hashCode() {
        return historialId != null ? historialId.hashCode() : 0;
    }
}