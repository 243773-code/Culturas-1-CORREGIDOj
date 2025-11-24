package org.example.models;

public class Direccion {
    private Integer direccionId;
    private Integer usuarioId;
    private String calle;
    private String numero;
    private String colonia;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    private String pais;
    private String tipo;

    // Constructor vacío
    public Direccion() {
    }

    // Constructor con todos los parámetros
    public Direccion(Integer direccionId, Integer usuarioId, String calle, String numero,
                     String colonia, String ciudad, String estado, String codigoPostal,
                     String pais, String tipo) {
        this.direccionId = direccionId;
        this.usuarioId = usuarioId;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.estado = estado;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.tipo = tipo;
    }

    // Constructor sin ID (para inserciones)
    public Direccion(Integer usuarioId, String calle, String numero, String colonia,
                     String ciudad, String estado, String codigoPostal, String pais, String tipo) {
        this.usuarioId = usuarioId;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.estado = estado;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.tipo = tipo;
    }


    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public Integer getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(Integer direccionId) {
        this.direccionId = direccionId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "direccionId=" + direccionId +
                ", usuarioId=" + usuarioId +
                ", calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", colonia='" + colonia + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", estado='" + estado + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", pais='" + pais + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direccion that = (Direccion) o;
        return direccionId != null && direccionId.equals(that.direccionId);
    }


    @Override
    public int hashCode() {
        return direccionId != null ? direccionId.hashCode() : 0;
    }

}