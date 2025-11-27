package org.example.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class  Producto {
    private Integer productoId;
    private Integer categoriaId;
    private Integer estatusId;
    private String nombre;
    private String descripcion;
    private String sku;
    private BigDecimal precioBase;
    private LocalDateTime fechaCreacion;

    // Campos de inventario
    private Integer stockTotal;
    private Integer stockMinimo;
    private Integer puntoReorden;
    private LocalDateTime ultimaActualizacion;

    // URL de la imagen
    private String img_url;

    // Constructor vacío
    public Producto() {
    }

    // Constructor con todos los parámetros (incluyendo img_url)
    public Producto(Integer productoId, Integer categoriaId, Integer estatusId, String nombre,
                    String descripcion, String sku, BigDecimal precioBase, LocalDateTime fechaCreacion,
                    Integer stockTotal, Integer stockMinimo, Integer puntoReorden,
                    LocalDateTime ultimaActualizacion, String img_url) {
        this.productoId = productoId;
        this.categoriaId = categoriaId;
        this.estatusId = estatusId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.sku = sku;
        this.precioBase = precioBase;
        this.fechaCreacion = fechaCreacion;
        this.stockTotal = stockTotal;
        this.stockMinimo = stockMinimo;
        this.puntoReorden = puntoReorden;
        this.ultimaActualizacion = ultimaActualizacion;
        this.img_url = img_url;
    }

    // Constructor sin ID (para inserciones) incluyendo img_url
    public Producto(Integer categoriaId, Integer estatusId, String nombre, String descripcion,
                    String sku, BigDecimal precioBase, Integer stockTotal,
                    Integer stockMinimo, Integer puntoReorden, String img_url) {
        this.categoriaId = categoriaId;
        this.estatusId = estatusId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.sku = sku;
        this.precioBase = precioBase;
        this.stockTotal = stockTotal;
        this.stockMinimo = stockMinimo;
        this.puntoReorden = puntoReorden;
        this.img_url = img_url;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(Integer stockTotal) {
        this.stockTotal = stockTotal;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Integer getPuntoReorden() {
        return puntoReorden;
    }

    public void setPuntoReorden(Integer puntoReorden) {
        this.puntoReorden = puntoReorden;
    }

    public LocalDateTime getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public Number getPrecio() {
        return precioBase;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "productoId=" + productoId +
                ", categoriaId=" + categoriaId +
                ", estatusId=" + estatusId +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", sku='" + sku + '\'' +
                ", precioBase=" + precioBase +
                ", fechaCreacion=" + fechaCreacion +
                ", stockTotal=" + stockTotal +
                ", stockMinimo=" + stockMinimo +
                ", puntoReorden=" + puntoReorden +
                ", ultimaActualizacion=" + ultimaActualizacion +
                ", img_url='" + img_url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto that = (Producto) o;
        return productoId != null && productoId.equals(that.productoId);
    }

    @Override
    public int hashCode() {
        return productoId != null ? productoId.hashCode() : 0;
    }
}