package org.example.models;

public class Estatus {
    private Integer estatusId;
    private String nombre;
    private String descripcion;
    private String tabla;
    private Integer orden;

    // Constructor vacío
    public Estatus() {
    }

    // Constructor con todos los parámetros
    public Estatus(Integer estatusId, String nombre, String descripcion, String tabla, Integer orden) {
        this.estatusId = estatusId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tabla = tabla;
        this.orden = orden;
    }

    // Constructor sin ID (para inserciones)
    public Estatus(String nombre, String descripcion, String tabla, Integer orden) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tabla = tabla;
        this.orden = orden;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
    }

    public String getNombre() {
        return nombre;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public Integer getOrden() {
        return orden;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @Override
    public String toString() {
        return "Estatus{" +
                "estatusId=" + estatusId +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tabla='" + tabla + '\'' +
                ", orden=" + orden +
                '}';
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estatus that = (Estatus) o;
        return estatusId != null && estatusId.equals(that.estatusId);
    }


    @Override
    public int hashCode() {
        return estatusId != null ? estatusId.hashCode() : 0;
    }

}