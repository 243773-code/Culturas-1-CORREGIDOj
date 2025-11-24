package org.example.services;

import org.example.models.Usuario;
import org.example.repositories.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(int usuarioId) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("El ID del usuario debe ser mayor a 0");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId);

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
        }

        return usuario;
    }

    public Usuario getUsuarioByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }

        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con email: " + email);
        }

        return usuario;
    }

    public List<Usuario> getUsuariosByRolId(int rolId) {
        if (rolId <= 0) {
            throw new IllegalArgumentException("El ID del rol debe ser mayor a 0");
        }

        return usuarioRepository.findByRolId(rolId);
    }

    public Usuario createUsuario(Usuario usuario) {
        validateUsuario(usuario);

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + usuario.getEmail());
        }

        // Establecer fecha de registro si no viene
        if (usuario.getFechaRegistro() == null) {
            usuario.setFechaRegistro(LocalDateTime.now());
        }

        // TODO: Aquí deberías hashear el password antes de guardar
        // Por ahora se guarda tal cual (INSEGURO - mejorar después)

        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(int usuarioId, Usuario usuario) {
        Usuario existingUsuario = usuarioRepository.findById(usuarioId);
        if (existingUsuario == null) {
            throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
        }

        validateUsuario(usuario);

        Usuario usuarioWithSameEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioWithSameEmail != null && usuarioWithSameEmail.getUsuarioId() != usuarioId) {
            throw new RuntimeException("Ya existe otro usuario con el email: " + usuario.getEmail());
        }

        usuario.setUsuarioId(usuarioId);
        usuario.setFechaRegistro(existingUsuario.getFechaRegistro()); // Mantener fecha original

        return usuarioRepository.update(usuario);
    }

    public boolean deleteUsuario(int usuarioId) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("El ID del usuario debe ser mayor a 0");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
        }

        return usuarioRepository.deleteById(usuarioId);
    }

    public long countUsuarios() {
        return usuarioRepository.count();
    }

    public boolean existsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return usuarioRepository.existsByEmail(email);
    }

    private void validateUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }

        if (usuario.getRolId() <= 0) {
            throw new IllegalArgumentException("El rol del usuario es obligatorio");
        }

        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del usuario es obligatorio");
        }

        if (usuario.getApellido() == null || usuario.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido del usuario es obligatorio");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email del usuario es obligatorio");
        }

        if (!usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("El email no es válido");
        }

        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        if (usuario.getPassword().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }

        if (usuario.getNombre().length() > 100) {
            throw new IllegalArgumentException("El nombre no puede exceder 100 caracteres");
        }

        if (usuario.getApellido().length() > 100) {
            throw new IllegalArgumentException("El apellido no puede exceder 100 caracteres");
        }
    }
}