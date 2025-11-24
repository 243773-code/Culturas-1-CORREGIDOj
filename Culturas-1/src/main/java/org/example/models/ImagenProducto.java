package org.example.models;

public class ImagenProducto {
    private Integer imagenId;
    private Integer productoId;
    private String url;

    // Constructor vacío
    public ImagenProducto() {
    }

    // Constructor con todos los parámetros
    public ImagenProducto(Integer imagenId, Integer productoId, String url) {
        this.imagenId = imagenId;
        this.productoId = productoId;
        this.url = url;
    }

    // Constructor sin ID (para inserciones)
    public ImagenProducto(Integer productoId, String url) {
        this.productoId = productoId;
        this.url = url;
    }


    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public Integer getImagenId() {
        return imagenId;
    }

    public void setImagenId(Integer imagenId) {
        this.imagenId = imagenId;
    }

    public Integer getProductoId() {
        return productoId;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public String toString() {
        return "ImagenProducto{" +
                "imagenId=" + imagenId +
                ", productoId=" + productoId +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImagenProducto that = (ImagenProducto) o;
        return imagenId != null && imagenId.equals(that.imagenId);
    }


    @Override
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public int hashCode() {
        return imagenId != null ? imagenId.hashCode() : 0;
    }

}