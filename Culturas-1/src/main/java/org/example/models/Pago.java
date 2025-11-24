package org.example.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pago {

    private Integer pagoId;
    private Integer pedidoId;
    private LocalDateTime fecha;
    private BigDecimal monto;
    private String metodo;     // <-- este es tu metodo_pago
    private Integer estatusId;

    // Constructor vacío
    public Pago() {
    }

    // Constructor con todos los parámetros
    public Pago(Integer pagoId,
                Integer pedidoId,
                LocalDateTime fecha,
                BigDecimal monto,
                String metodo,
                Integer estatusId) {

        this.pagoId = pagoId;
        this.pedidoId = pedidoId;
        this.fecha = fecha;
        this.monto = monto;
        this.metodo = metodo;
        this.estatusId = estatusId;
    }

    // Constructor sin ID (para inserciones)
    public Pago(Integer pedidoId,
                LocalDateTime fecha,
                BigDecimal monto,
                String metodo,
                Integer estatusId) {

        this.pedidoId = pedidoId;
        this.fecha = fecha;
        this.monto = monto;
        this.metodo = metodo;
        this.estatusId = estatusId;
    }

    // ========================
    // GETTERS & SETTERS
    // ========================

    public Integer getPagoId() {
        return pagoId;
    }

    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {       // <-- este es el getter CORRECTO
        return metodo;
    }

    public void setMetodo(String metodo) {  // <-- setter correcto
        this.metodo = metodo;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "pagoId=" + pagoId +
                ", pedidoId=" + pedidoId +
                ", fecha=" + fecha +
                ", monto=" + monto +
                ", metodo='" + metodo + '\'' +
                ", estatusId=" + estatusId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pago)) return false;
        Pago that = (Pago) o;
        return pagoId != null && pagoId.equals(that.pagoId);
    }

    @Override
    public int hashCode() {
        return pagoId != null ? pagoId.hashCode() : 0;
    }


}