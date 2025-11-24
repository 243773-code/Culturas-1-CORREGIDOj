package org.example.models;

public class Talla {
    private Integer tallaId;
    private String codigo;
    private String nombre;
    private Integer orden;

    public Talla() {
    }

    // Constructor con todos los parámetros
    public Talla(Integer tallaId, String codigo, String nombre, Integer orden) {
        this.tallaId = tallaId;
        this.codigo = codigo;
        this.nombre = nombre;
        this.orden = orden;
    }

    // Constructor sin ID (para inserciones)
    public Talla(String codigo, String nombre, Integer orden) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.orden = orden;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public Integer getTallaId() {
        return tallaId;
    }

    public void setTallaId(Integer tallaId) {
        this.tallaId = tallaId;
    }

    public String getCodigo() {
        return codigo;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @Override
    public String toString() {
        return "Talla{" +
                "tallaId=" + tallaId +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", orden=" + orden +
                '}';
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Talla that = (Talla) o;
        return tallaId != null && tallaId.equals(that.tallaId);
    }


    @Override
    public int hashCode() {
        return tallaId != null ? tallaId.hashCode() : 0;
    }

}