package org.example.models;

import java.time.LocalDateTime;

public class Envio {

    private Integer envioId;
    private Integer pedidoId;
    private Integer direccionId;
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaEstimada;
    private LocalDateTime fechaEntrega;
    private Integer estatusId;

    // Constructor vacío
    public Envio() {
    }

    // Constructor con todos los campos
    public Envio(Integer envioId,
                 Integer pedidoId,
                 Integer direccionId,
                 LocalDateTime fechaEnvio,
                 LocalDateTime fechaEstimada,
                 LocalDateTime fechaEntrega,
                 Integer estatusId) {
        this.envioId = envioId;
        this.pedidoId = pedidoId;
        this.direccionId = direccionId;
        this.fechaEnvio = fechaEnvio;
        this.fechaEstimada = fechaEstimada;
        this.fechaEntrega = fechaEntrega;
        this.estatusId = estatusId;
    }

    // Constructor sin ID (para inserts)
    public Envio(Integer pedidoId,
                 Integer direccionId,
                 LocalDateTime fechaEnvio,
                 LocalDateTime fechaEstimada,
                 LocalDateTime fechaEntrega,
                 Integer estatusId) {
        this.pedidoId = pedidoId;
        this.direccionId = direccionId;
        this.fechaEnvio = fechaEnvio;
        this.fechaEstimada = fechaEstimada;
        this.fechaEntrega = fechaEntrega;
        this.estatusId = estatusId;
    }

    // ===== GETTERS & SETTERS =====

    public Integer getEnvioId() {
        return envioId;
    }

    public void setEnvioId(Integer envioId) {
        this.envioId = envioId;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Integer getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(Integer direccionId) {
        this.direccionId = direccionId;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public LocalDateTime getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(LocalDateTime fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
    }

    @Override
    public String toString() {
        return "Envio{" +
                "envioId=" + envioId +
                ", pedidoId=" + pedidoId +
                ", direccionId=" + direccionId +
                ", fechaEnvio=" + fechaEnvio +
                ", fechaEstimada=" + fechaEstimada +
                ", fechaEntrega=" + fechaEntrega +
                ", estatusId=" + estatusId +
                '}';
    }
}