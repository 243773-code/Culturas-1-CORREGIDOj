    package org.example.models;

    public class Proveedor {
        private Integer proveedorId;
        private String nombre;
        private String contacto;
        private String telefono;
        private String email;
        private String direccion;


        public Proveedor() {
        }

        // Constructor con todos los parámetros
        public Proveedor(Integer proveedorId, String nombre, String contacto, String telefono,
                         String email, String direccion) {
            this.proveedorId = proveedorId;
            this.nombre = nombre;
            this.contacto = contacto;
            this.telefono = telefono;
            this.email = email;
            this.direccion = direccion;
        }

        // Constructor sin ID (para inserciones)
        public Proveedor(String nombre, String contacto, String telefono, String email, String direccion) {
            this.nombre = nombre;
            this.contacto = contacto;
            this.telefono = telefono;
            this.email = email;
            this.direccion = direccion;
        }


        @SuppressWarnings({"unchecked", "rawtypes", "unused"})
        public Integer getProveedorId() {
            return proveedorId;
        }

        public void setProveedorId(Integer proveedorId) {
            this.proveedorId = proveedorId;
        }

        public String getNombre() {
            return nombre;
        }

        @SuppressWarnings({"unchecked", "rawtypes", "unused"})
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getContacto() {
            return contacto;
        }

        public void setContacto(String contacto) {
            this.contacto = contacto;
        }

        @SuppressWarnings({"unchecked", "rawtypes", "unused"})
        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getEmail() {
            return email;
        }

        @SuppressWarnings({"unchecked", "rawtypes", "unused"})
        public void setEmail(String email) {
            this.email = email;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        @Override
        @SuppressWarnings({"unchecked", "rawtypes", "unused"})
        public String toString() {
            return "Proveedor{" +
                    "proveedorId=" + proveedorId +
                    ", nombre='" + nombre + '\'' +
                    ", contacto='" + contacto + '\'' +
                    ", telefono='" + telefono + '\'' +
                    ", email='" + email + '\'' +
                    ", direccion='" + direccion + '\'' +
                    '}';
        }

        @Override
        @SuppressWarnings({"unchecked", "rawtypes", "unused"})
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Proveedor that = (Proveedor) o;
            return proveedorId != null && proveedorId.equals(that.proveedorId);
        }


        @Override
        public int hashCode() {
            return proveedorId != null ? proveedorId.hashCode() : 0;
        }

    }