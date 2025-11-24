package org.example.services;

import org.example.models.Usuario;
import org.example.repositories.UsuarioRepository;

import java.time.LocalDateTime;

/**
 * Servicio de Autenticación
 * Maneja login y registro de usuarios
 *
 * ⚠️ IMPORTANTE: Este código guarda passwords en TEXTO PLANO (INSEGURO)
 * Para producción debes implementar:
 * 1. Hasheo de passwords con BCrypt
 * 2. Tokens JWT para sesiones
 * 3. Refresh tokens
 * 4. Rate limiting
 *
 * Este es un ejemplo básico funcional para desarrollo.
 */
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public AuthService() {
        this.usuarioRepository = new UsuarioRepository();
        this.usuarioService = new UsuarioService();
    }

    /**
     * Login de usuario
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return Usuario si las credenciales son válidas
     * @throws RuntimeException si las credenciales son inválidas
     */
    public Usuario login(String email, String password) {
        // Validar que email y password no estén vacíos
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        // Buscar usuario por email
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // Verificar password
        // ⚠️ TODO: En producción usar BCrypt.checkpw(password, usuario.getPassword())
        if (!password.equals(usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // No retornar el password al cliente
        usuario.setPassword(null);

        return usuario;
    }

    /**
     * Registro de nuevo usuario
     * @param usuario Usuario a registrar
     * @return Usuario registrado (sin password en respuesta)
     */
    public Usuario register(Usuario usuario) {
        // Validar datos del usuario
        if (usuario == null) {
            throw new IllegalArgumentException("Los datos del usuario son obligatorios");
        }

        // Verificar que el email no esté registrado
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Establecer rol por defecto (2 = Cliente)
        if (usuario.getRolId() == 0) {
            usuario.setRolId(2); // Rol Cliente por defecto
        }

        // Establecer fecha de registro
        usuario.setFechaRegistro(LocalDateTime.now());

        // ⚠️ TODO: En producción hashear el password
        // String hashedPassword = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
        // usuario.setPassword(hashedPassword);

        // Crear usuario usando el servicio existente (que valida los datos)
        Usuario nuevoUsuario = usuarioService.createUsuario(usuario);

        // No retornar el password
        nuevoUsuario.setPassword(null);

        return nuevoUsuario;
    }

    /**
     * Verificar si un usuario existe por email
     * @param email Email a verificar
     * @return true si existe, false si no
     */
    public boolean existsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return usuarioRepository.existsByEmail(email);
    }

    /**
     * Obtener usuario por email (sin password)
     * @param email Email del usuario
     * @return Usuario encontrado (sin password)
     */
    public Usuario getUsuarioByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }

        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // No retornar password
        usuario.setPassword(null);

        return usuario;
    }
}