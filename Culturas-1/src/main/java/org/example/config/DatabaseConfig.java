package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConfig - Configuración SIMPLE de conexión a MySQL
 *
 * VERSIÓN SIMPLIFICADA (sin HikariCP)
 * Usa conexiones directas con DriverManager
 *
 * IMPORTANTE: Actualiza las credenciales antes de usar
 */
public class DatabaseConfig {

    // ===== CONFIGURACIÓN DE LA BASE DE DATOS =====

    // CAMBIAR ESTOS VALORES POR LOS TUYOS
    private static final String DB_HOST = "52.22.74.214";  // ⚠️ CAMBIAR
    private static final String DB_PORT = "3306";          // ✅ Igual
    public static final String DB_NAME = "ECOMMERCE_CULTURAS";  // ✅ Igual (o el nombre que tenga en AWS)
    public static final String DB_USER = "richie";        // ⚠️ CAMBIAR
    private static final String DB_PASSWORD = "12345";     // ✅ Igual

    // URL completa de conexión
    public static final String DB_URL = String.format(
            "jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=America/Mexico_City&allowPublicKeyRetrieval=true",
            DB_HOST, DB_PORT, DB_NAME
    );

    /**
     * Bloque estático - Cargar driver de MySQL
     */
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println(" Driver MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ ERROR: No se pudo cargar el driver de MySQL");
            System.err.println("Asegúrate de tener mysql-connector-java en pom.xml");
            e.printStackTrace();
            throw new RuntimeException("Driver MySQL no encontrado", e);
        }
    }

    /**
     * Obtener una conexión a la base de datos
     *
     * USO:
     * try (Connection conn = DatabaseConfig.getConnection()) {
     *     // Usar la conexión...
     * } // Se cierra automáticamente
     *
     * @return Conexión activa a MySQL
     * @throws SQLException Si no se puede conectar
     */
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            return conn;
        } catch (SQLException e) {
            System.err.println("❌ ERROR al conectar a MySQL:");
            System.err.println("URL: " + DB_URL);
            System.err.println("Usuario: " + DB_USER);
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Verificar si la conexión funciona
     *
     * @return true si puede conectar
     */
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public static boolean isHealthy() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed() && conn.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Cerrar recursos (no hace nada en esta versión simple)
     */
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public static void close() {
        System.out.println("✅ Recursos de base de datos cerrados");
    }

    /**
     * Imprimir configuración actual
     */
    public static void printConfig() {
        System.out.println("\n========================================");
        System.out.println("📊 CONFIGURACIÓN DE BASE DE DATOS");
        System.out.println("========================================");
        System.out.println("Host: " + DB_HOST);
        System.out.println("Puerto: " + DB_PORT);
        System.out.println("Base de datos: " + DB_NAME);
        System.out.println("Usuario: " + DB_USER);
        System.out.println("URL: " + DB_URL);
        System.out.println("========================================\n");
    }

    /**
     * Test de conexión (ejecutar para verificar)
     */
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public static void main(String[] args) {
        System.out.println("🔍 Probando conexión a MySQL...\n");

        printConfig();

        if (isHealthy()) {
            System.out.println("✅ ¡CONEXIÓN EXITOSA!");
            System.out.println("La base de datos está funcionando correctamente.");
        } else {
            System.err.println("❌ ERROR: No se puede conectar a MySQL");
            System.err.println("\nVerifica:");
            System.err.println("1. MySQL está corriendo");
            System.err.println("2. La base de datos '" + DB_NAME + "' existe");
            System.err.println("3. Usuario y contraseña son correctos");
            System.err.println("4. El puerto " + DB_PORT + " está abierto");
        }
    }
}