package org.example.models;

public class Sucursal {
    private Integer sucursalId;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String telefono;

    public Sucursal() {
    }

    // Constructor con todos los parámetros
    public Sucursal(Integer sucursalId, String nombre, String direccion, String ciudad, String telefono) {
        this.sucursalId = sucursalId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.telefono = telefono;
    }

    // Constructor sin ID (para inserciones)
    public Sucursal(String nombre, String direccion, String ciudad, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.telefono = telefono;
    }


    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }

    public String getNombre() {
        return nombre;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Sucursal{" +
                "sucursalId=" + sucursalId +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sucursal that = (Sucursal) o;
        return sucursalId != null && sucursalId.equals(that.sucursalId);
    }


    @Override
    public int hashCode() {
        return sucursalId != null ? sucursalId.hashCode() : 0;
    }

}