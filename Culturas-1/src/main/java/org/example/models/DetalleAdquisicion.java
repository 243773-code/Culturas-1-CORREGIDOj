package org.example.models;

import java.math.BigDecimal;

public class DetalleAdquisicion {

    private Integer detalleId;
    private Integer adquisicionId;
    private Integer productoTallaId;   // <-- CAMBIO: en vez de productoId + tallaId
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    // Constructor vacío
    public DetalleAdquisicion() {
    }

    // Constructor con todos los parámetros
    public DetalleAdquisicion(Integer detalleId,
                              Integer adquisicionId,
                              Integer productoTallaId,
                              Integer cantidad,
                              BigDecimal precioUnitario,
                              BigDecimal subtotal) {
        this.detalleId = detalleId;
        this.adquisicionId = adquisicionId;
        this.productoTallaId = productoTallaId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    // Constructor sin ID (para inserciones)
    public DetalleAdquisicion(Integer adquisicionId,
                              Integer productoTallaId,
                              Integer cantidad,
                              BigDecimal precioUnitario,
                              BigDecimal subtotal) {
        this.adquisicionId = adquisicionId;
        this.productoTallaId = productoTallaId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    // ===== GETTERS & SETTERS =====

    public Integer getDetalleId() {
        return detalleId;
    }

    public void setDetalleId(Integer detalleId) {
        this.detalleId = detalleId;
    }

    public Integer getAdquisicionId() {
        return adquisicionId;
    }

    public void setAdquisicionId(Integer adquisicionId) {
        this.adquisicionId = adquisicionId;
    }

    public Integer getProductoTallaId() {
        return productoTallaId;
    }

    public void setProductoTallaId(Integer productoTallaId) {
        this.productoTallaId = productoTallaId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "DetalleAdquisicion{" +
                "detalleId=" + detalleId +
                ", adquisicionId=" + adquisicionId +
                ", productoTallaId=" + productoTallaId +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subtotal=" + subtotal +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleAdquisicion that = (DetalleAdquisicion) o;
        return detalleId != null && detalleId.equals(that.detalleId);
    }

    @Override
    public int hashCode() {
        return detalleId != null ? detalleId.hashCode() : 0;
    }
}