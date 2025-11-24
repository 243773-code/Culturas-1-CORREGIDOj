package org.example.models;

public class Categoria {
    private Integer categoriaId;
    private String nombre;
    private String descripcion;

    // Constructor vacío
    public Categoria() {
    }

    // Constructor con todos los parámetros
    public Categoria(Integer categoriaId, String nombre, String descripcion) {
        this.categoriaId = categoriaId;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Constructor sin ID (para inserciones)
    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }


    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
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
        return "Categoria{" +
                "categoriaId=" + categoriaId +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categoria that = (Categoria) o;
        return categoriaId != null && categoriaId.equals(that.categoriaId);
    }


    @Override
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public int hashCode() {
        return categoriaId != null ? categoriaId.hashCode() : 0;
    }

}