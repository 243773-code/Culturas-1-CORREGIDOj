package org.example.models;

public class Rol {
    private Integer rolId;
    private String nombre;
    private String descripcion;

    // Constructor vacío
    public Rol() {
    }

    // Constructor con todos los parámetros
    public Rol(Integer rolId, String nombre, String descripcion) {
        this.rolId = rolId;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Constructor sin ID (para inserciones)
    public Rol(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
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

    @Override
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public String toString() {
        return "Rol{" +
                "rolId=" + rolId +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rol that = (Rol) o;
        return rolId != null && rolId.equals(that.rolId);
    }


    @Override
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public int hashCode() {
        return rolId != null ? rolId.hashCode() : 0;
    }

}