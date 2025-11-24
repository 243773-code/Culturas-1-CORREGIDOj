package org.example.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Adquisicion {

    private Integer adquisicionId;
    private Integer proveedorId;
    private Integer empleadoId;
    private LocalDateTime fecha;
    private BigDecimal total;
    private Integer estatusId; // <-- NUEVO CAMPO

    // Constructor vacío
    public Adquisicion() {
    }

    // Constructor con TODOS los parámetros (incluye estatusId)
    public Adquisicion(Integer adquisicionId,
                       Integer proveedorId,
                       Integer empleadoId,
                       LocalDateTime fecha,
                       BigDecimal total,
                       Integer estatusId) {
        this.adquisicionId = adquisicionId;
        this.proveedorId = proveedorId;
        this.empleadoId = empleadoId;
        this.fecha = fecha;
        this.total = total;
        this.estatusId = estatusId;
    }

    // Constructor SIN ID (para inserciones)
    public Adquisicion(Integer proveedorId,
                       Integer empleadoId,
                       LocalDateTime fecha,
                       BigDecimal total,
                       Integer estatusId) {
        this.proveedorId = proveedorId;
        this.empleadoId = empleadoId;
        this.fecha = fecha;
        this.total = total;
        this.estatusId = estatusId;
    }

    // ===== GETTERS & SETTERS =====

    public Integer getAdquisicionId() {
        return adquisicionId;
    }

    public void setAdquisicionId(Integer adquisicionId) {
        this.adquisicionId = adquisicionId;
    }

    public Integer getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Integer proveedorId) {
        this.proveedorId = proveedorId;
    }

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
    }

    // ===== toString, equals, hashCode =====

    @Override
    public String toString() {
        return "Adquisicion{" +
                "adquisicionId=" + adquisicionId +
                ", proveedorId=" + proveedorId +
                ", empleadoId=" + empleadoId +
                ", fecha=" + fecha +
                ", total=" + total +
                ", estatusId=" + estatusId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Adquisicion that = (Adquisicion) o;
        return adquisicionId != null && adquisicionId.equals(that.adquisicionId);
    }

    @Override
    public int hashCode() {
        return adquisicionId != null ? adquisicionId.hashCode() : 0;
    }
}