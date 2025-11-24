package org.example.config;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.Arrays;
import java.util.List;

/**
 * CorsConfig - Configuración de CORS (Cross-Origin Resource Sharing)
 *
 * ¿Qué es CORS?
 * - Mecanismo de seguridad de navegadores web
 * - Controla qué dominios pueden hacer peticiones a tu API
 * - Necesario cuando frontend y backend están en dominios diferentes
 *
 * Ejemplo:
 * - Frontend: http://localhost:3000 (React)
 * - Backend: http://localhost:7000 (Javalin)
 * - Sin CORS: El navegador bloquea las peticiones
 * - Con CORS: Permitimos peticiones desde localhost:3000
 *
 * IMPORTANTE:
 * - En desarrollo: Permitir orígenes locales
 * - En producción: Solo permitir tu dominio específico
 * - NUNCA usar "*" (todos los orígenes) en producción
 */
public class CorsConfig {

    // ===== CONFIGURACIÓN DE ORÍGENES PERMITIDOS =====

    /**
     * Orígenes permitidos para CORS
     *
     * DESARROLLO:
     * - http://localhost:3000 (React)
     * - http://localhost:8080 (Vue)
     * - http://localhost:4200 (Angular)
     *
     * PRODUCCIÓN:
     * - https://tudominio.com
     * - https://www.tudominio.com
     */
    private static final List<String> ALLOWED_ORIGINS = Arrays.asList(
            "http://localhost:3000",      // React dev
            "http://localhost:8080",      // Vue dev
            "http://localhost:4200",      // Angular dev
            "http://127.0.0.1:3000",      // React dev alternativo
            "http://127.0.0.1:8080",      // Vue dev alternativo
            "https://tudominio.com",      // Producción (cambiar por tu dominio)
            "https://www.tudominio.com"   // Producción con www
    );

    /**
     * Métodos HTTP permitidos
     */
    private static final List<String> ALLOWED_METHODS = Arrays.asList(
            "GET",
            "POST",
            "PUT",
            "DELETE",
            "PATCH",
            "OPTIONS"
    );

    /**
     * Headers permitidos
     * Estos son los headers que el frontend puede enviar
     */
    private static final List<String> ALLOWED_HEADERS = Arrays.asList(
            "Content-Type",
            "Authorization",
            "Accept",
            "Origin",
            "X-Requested-With",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers"
    );

    /**
     * Headers expuestos
     * Estos son los headers que el frontend puede leer de la respuesta
     */
    private static final List<String> EXPOSED_HEADERS = Arrays.asList(
            "Content-Type",
            "Authorization",
            "X-Total-Count"
    );

    /**
     * Tiempo máximo de cache para preflight requests (en segundos)
     * Preflight = petición OPTIONS que hace el navegador antes de la petición real
     */
    private static final int MAX_AGE = 3600; // 1 hora

    // ===== CONFIGURACIÓN PRINCIPAL =====

    /**
     * Configurar CORS en Javalin
     *
     * Uso en Main.java:
     * Javalin app = Javalin.create(config -> {
     *     CorsConfig.configureCors(config);
     * });
     *
     * @param app Instancia de Javalin
     */
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public static void configureCors(Javalin app) {
        // Manejar preflight requests (OPTIONS)
        app.before(ctx -> {
            String origin = ctx.header("Origin");

            // Verificar si el origen está permitido
            if (origin != null && isOriginAllowed(origin)) {
                // Configurar headers CORS
                ctx.header("Access-Control-Allow-Origin", origin);
                ctx.header("Access-Control-Allow-Credentials", "true");
                ctx.header("Access-Control-Allow-Methods", String.join(", ", ALLOWED_METHODS));
                ctx.header("Access-Control-Allow-Headers", String.join(", ", ALLOWED_HEADERS));
                ctx.header("Access-Control-Expose-Headers", String.join(", ", EXPOSED_HEADERS));
                ctx.header("Access-Control-Max-Age", String.valueOf(MAX_AGE));
            }
        });

        // Responder a peticiones OPTIONS (preflight)
        app.options("/*", ctx -> {
            ctx.status(204); // No Content
        });
    }

    /**
     * Agregar headers CORS a un contexto específico
     * Útil si quieres configurar CORS manualmente en algún endpoint
     *
     * @param ctx Contexto de Javalin
     */
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public static void addCorsHeaders(Context ctx) {
        String origin = ctx.header("Origin");

        if (origin != null && isOriginAllowed(origin)) {
            ctx.header("Access-Control-Allow-Origin", origin);
            ctx.header("Access-Control-Allow-Credentials", "true");
            ctx.header("Access-Control-Allow-Methods", String.join(", ", ALLOWED_METHODS));
            ctx.header("Access-Control-Allow-Headers", String.join(", ", ALLOWED_HEADERS));
            ctx.header("Access-Control-Expose-Headers", String.join(", ", EXPOSED_HEADERS));
        }
    }

    // ===== VALIDACIÓN DE ORÍGENES =====

    /**
     * Verificar si un origen está permitido
     *
     * @param origin Origen a verificar
     * @return true si está permitido
     */
    @SuppressWarnings({"unchecked", "unused"})
    private static boolean isOriginAllowed(String origin) {
        // En desarrollo, podrías permitir todos los orígenes localhost
        // if (origin.startsWith("http://localhost") || origin.startsWith("http://127.0.0.1")) {
        //     return true;
        // }

        return ALLOWED_ORIGINS.contains(origin);
    }

    /**
     * Verificar si un método está permitido
     *
     * @param method Método HTTP
     * @return true si está permitido
     */
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public static boolean isMethodAllowed(String method) {
        return ALLOWED_METHODS.contains(method.toUpperCase());
    }

    /**
     * Verificar si un header está permitido
     *
     * @param header Nombre del header
     * @return true si está permitido
     */
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public static boolean isHeaderAllowed(String header) {
        return ALLOWED_HEADERS.stream()
                .anyMatch(h -> h.equalsIgnoreCase(header));
    }

    // ===== CONFIGURACIÓN ALTERNATIVA (PERMISIVA PARA DESARROLLO) =====

    /**
     * Configurar CORS permisivo (SOLO PARA DESARROLLO)
     * NUNCA usar en producción
     *
     * Permite:
     * - Cualquier origen
     * - Cualquier método
     * - Cualquier header
     *
     * @param app Instancia de Javalin
     */
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public static void configurePermissiveCors(Javalin app) {
        System.err.println("⚠️  ADVERTENCIA: CORS permisivo activado - SOLO para desarrollo");

        app.before(ctx -> {
            ctx.header("Access-Control-Allow-Origin", "*");
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "*");
            ctx.header("Access-Control-Expose-Headers", "*");
            ctx.header("Access-Control-Max-Age", "3600");
        });

        app.options("/*", ctx -> {
            ctx.status(204);
        });
    }

    // ===== UTILIDADES =====

    /**
     * Agregar origen permitido dinámicamente
     * Útil si necesitas agregar orígenes en runtime
     *
     * @param origin Origen a agregar
     */
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public static void addAllowedOrigin(String origin) {
        if (origin != null && !origin.isEmpty()) {
            ALLOWED_ORIGINS.add(origin);
            System.out.println("✅ Origen agregado a CORS: " + origin);
        }
    }

    /**
     * Remover origen permitido
     *
     * @param origin Origen a remover
     */
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public static void removeAllowedOrigin(String origin) {
        if (ALLOWED_ORIGINS.remove(origin)) {
            System.out.println("✅ Origen removido de CORS: " + origin);
        }
    }

    /**
     * Obtener lista de orígenes permitidos
     *
     * @return Lista de orígenes
     */
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public static List<String> getAllowedOrigins() {
        return ALLOWED_ORIGINS;
    }

    /**
     * Imprimir configuración actual de CORS
     */
    public static void printConfig() {
        System.out.println("\n========================================");
        System.out.println("🌐 CONFIGURACIÓN DE CORS");
        System.out.println("========================================");
        System.out.println("Orígenes permitidos:");
        for (String origin : ALLOWED_ORIGINS) {
            System.out.println("  - " + origin);
        }
        System.out.println("\nMétodos permitidos:");
        System.out.println("  " + String.join(", ", ALLOWED_METHODS));
        System.out.println("\nHeaders permitidos:");
        for (String header : ALLOWED_HEADERS) {
            System.out.println("  - " + header);
        }
        System.out.println("\nMax Age: " + MAX_AGE + " segundos");
        System.out.println("========================================\n");
    }

    // ===== MIDDLEWARE DE SEGURIDAD ADICIONAL =====

    /**
     * Agregar headers de seguridad generales
     * Complemento a CORS para mejorar seguridad
     *
     * @param ctx Contexto de Javalin
     */
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public static void addSecurityHeaders(Context ctx) {
        // Prevenir ataques XSS
        ctx.header("X-Content-Type-Options", "nosniff");
        ctx.header("X-XSS-Protection", "1; mode=block");

        // Prevenir clickjacking
        ctx.header("X-Frame-Options", "DENY");

        // Política de contenido estricta (opcional, ajustar según necesidad)
        // ctx.header("Content-Security-Policy", "default-src 'self'");

        // Forzar HTTPS en producción (descomentar si usas HTTPS)
        // ctx.header("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
    }

    /**
     * Configurar CORS y seguridad completa
     *
     * @param app Instancia de Javalin
     */
    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    public static void configureComplete(Javalin app) {
        configureCors(app);

        // Agregar headers de seguridad a todas las respuestas
        app.after(CorsConfig::addSecurityHeaders);

        System.out.println("✅ CORS y headers de seguridad configurados");
    }
}


