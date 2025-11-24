package org.example.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pedido {
    private Integer pedidoId;
    private Integer usuarioId;
    private Integer direccionId;
    private LocalDateTime fecha;
    private BigDecimal total;
    private String metodoPago;
    private String nota;
    private Integer estatusId;
    private String tipoEntrega;

    // Constructor vacío
    public Pedido() {
    }

    // Constructor con todos los parámetros
    public Pedido(Integer pedidoId, Integer usuarioId, Integer direccionId, LocalDateTime fecha,
                  BigDecimal total, String metodoPago, String nota, Integer estatusId, String tipoEntrega) {
        this.pedidoId = pedidoId;
        this.usuarioId = usuarioId;
        this.direccionId = direccionId;
        this.fecha = fecha;
        this.total = total;
        this.metodoPago = metodoPago;
        this.nota = nota;
        this.estatusId = estatusId;
        this.tipoEntrega = tipoEntrega;
    }

    // Constructor sin ID (para inserciones)
    public Pedido(Integer usuarioId, Integer direccionId, LocalDateTime fecha, BigDecimal total,
                  String metodoPago, String nota, Integer estatusId, String tipoEntrega) {
        this.usuarioId = usuarioId;
        this.direccionId = direccionId;
        this.fecha = fecha;
        this.total = total;
        this.metodoPago = metodoPago;
        this.nota = nota;
        this.estatusId = estatusId;
        this.tipoEntrega = tipoEntrega;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(Integer direccionId) {
        this.direccionId = direccionId;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public String toString() {
        return "Pedido{" +
                "pedidoId=" + pedidoId +
                ", usuarioId=" + usuarioId +
                ", direccionId=" + direccionId +
                ", fecha=" + fecha +
                ", total=" + total +
                ", metodoPago='" + metodoPago + '\'' +
                ", nota='" + nota + '\'' +
                ", estatusId=" + estatusId +
                ", tipoEntrega='" + tipoEntrega + '\'' +
                '}';
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido that = (Pedido) o;
        return pedidoId != null && pedidoId.equals(that.pedidoId);
    }


    @Override
    public int hashCode() {
        return pedidoId != null ? pedidoId.hashCode() : 0;
    }

}