package org.example.models;

import java.math.BigDecimal;

public class DetallePedido {

    private Integer detallePedidoId;
    private Integer pedidoId;
    private Integer productoTallaId;   // <-- USAMOS producto_talla_id
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    // Constructor vacío
    public DetallePedido() {
    }

    // Constructor con todos los parámetros
    public DetallePedido(Integer detallePedidoId,
                         Integer pedidoId,
                         Integer productoTallaId,
                         Integer cantidad,
                         BigDecimal precioUnitario,
                         BigDecimal subtotal) {
        this.detallePedidoId = detallePedidoId;
        this.pedidoId = pedidoId;
        this.productoTallaId = productoTallaId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    // Constructor sin ID (para inserciones)
    public DetallePedido(Integer pedidoId,
                         Integer productoTallaId,
                         Integer cantidad,
                         BigDecimal precioUnitario,
                         BigDecimal subtotal) {
        this.pedidoId = pedidoId;
        this.productoTallaId = productoTallaId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    // ======== GETTERS & SETTERS ========

    public Integer getDetallePedidoId() {
        return detallePedidoId;
    }

    public void setDetallePedidoId(Integer detallePedidoId) {
        this.detallePedidoId = detallePedidoId;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
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

    // ======== MÉTODOS DE SOPORTE ========

    @Override
    public String toString() {
        return "DetallePedido{" +
                "detallePedidoId=" + detallePedidoId +
                ", pedidoId=" + pedidoId +
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

        DetallePedido that = (DetallePedido) o;
        return detallePedidoId != null && detallePedidoId.equals(that.detallePedidoId);
    }

    @Override
    public int hashCode() {
        return detallePedidoId != null ? detallePedidoId.hashCode() : 0;
    }
}