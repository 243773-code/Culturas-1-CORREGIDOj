    package org.example.models;

    import java.time.LocalDateTime;

    public class Usuario {
        private Integer usuarioId;
        private Integer rolId;
        private String nombre;
        private String apellido;
        private String email;
        private String password;
        private String telefono;
        private LocalDateTime fechaRegistro;
        private LocalDateTime ultimoLogin;

        public Usuario() {
        }

        // Constructor con todos los parámetros
        public Usuario(Integer usuarioId, Integer rolId, String nombre, String apellido, String email,
                       String password, String telefono, LocalDateTime fechaRegistro) {
            this.usuarioId = usuarioId;
            this.rolId = rolId;
            this.nombre = nombre;
            this.apellido = apellido;
            this.email = email;
            this.password = password;
            this.telefono = telefono;
            this.fechaRegistro = fechaRegistro;
            this.ultimoLogin = ultimoLogin;
        }

        // Constructor sin ID (para inserciones)
        public Usuario(Integer rolId, String nombre, String apellido, String email, String password,
                       String telefono, LocalDateTime fechaRegistro) {
            this.rolId = rolId;
            this.nombre = nombre;
            this.apellido = apellido;
            this.email = email;
            this.password = password;
            this.telefono = telefono;
            this.fechaRegistro = fechaRegistro;
        }

        @SuppressWarnings({"unchecked", "rawtypes", "unused"})
        public Integer getUsuarioId() {
            return usuarioId;
        }

        public void setUsuarioId(Integer usuarioId) {
            this.usuarioId = usuarioId;
        }

        public Integer getRolId() {
            return rolId;
        }

        @SuppressWarnings({"unchecked", "rawtypes", "unused"})
        public void setRolId(Integer rolId) {
            this.rolId = rolId;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        @SuppressWarnings({"unchecked", "rawtypes", "unused"})
        public String getApellido() {
            return apellido;
        }

        public void setApellido(String apellido) {
            this.apellido = apellido;
        }

        public String getEmail() {
            return email;
        }

        @SuppressWarnings({"unchecked", "rawtypes", "unused"})
        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @SuppressWarnings({"unchecked", "rawtypes", "unused"})
        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public LocalDateTime getFechaRegistro() {
            return fechaRegistro;
        }

        @SuppressWarnings({"unchecked", "rawtypes", "unused"})
        public void setFechaRegistro(LocalDateTime fechaRegistro) {
            this.fechaRegistro = fechaRegistro;
        }

        public LocalDateTime getUltimoLogin() {
            return ultimoLogin;
        }

        public void setUltimoLogin(LocalDateTime ultimoLogin) {
            this.ultimoLogin = ultimoLogin;
        }

        @Override
        @SuppressWarnings({"unchecked", "rawtypes", "unused"})
        public String toString() {
            return "Usuario{" +
                    "usuarioId=" + usuarioId +
                    ", rolId=" + rolId +
                    ", nombre='" + nombre + '\'' +
                    ", apellido='" + apellido + '\'' +
                    ", email='" + email + '\'' +
                    ", telefono='" + telefono + '\'' +
                    ", fechaRegistro=" + fechaRegistro +
                    ", ultimoLogin=" + ultimoLogin +
                    '}';
        }

        @Override
        @SuppressWarnings({"unchecked", "rawtypes", "unused"})
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Usuario that = (Usuario) o;
            return usuarioId != null && usuarioId.equals(that.usuarioId);
        }


        @Override
        public int hashCode() {
            return usuarioId != null ? usuarioId.hashCode() : 0;
        }

    }