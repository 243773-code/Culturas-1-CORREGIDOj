package org.example;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import org.example.routers.*;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        // ═══════════════════════════════════════════════════════════════
        // CONFIGURACIÓN DE JAVALIN
        // ═══════════════════════════════════════════════════════════════

        Javalin app = Javalin.create(config -> {

            // CORS para desarrollo
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });

            // Logging de desarrollo
            config.bundledPlugins.enableDevLogging();

        }).start("0.0.0.0", 7070); // Puerto 7070

        // ═══════════════════════════════════════════════════════════════
        // LOG DE INICIO
        // ═══════════════════════════════════════════════════════════════

        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("🚀 SERVIDOR INICIADO - PROYECTO CULTURAS");
        System.out.println("📍 URL: http://localhost:7070");
        System.out.println("📍 API: http://localhost:7070/api/");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        // ═══════════════════════════════════════════════════════════════
        // REGISTRO DE RUTAS
        // ═══════════════════════════════════════════════════════════════

        new AuthRoutes().register(app);
        new Rolroutes().register(app);
        new Categoriaroutes().register(app);
        new Tallaroutes().register(app);
        new Sucursalroutes().register(app);
        new Proveedorroutes().register(app);
        new Estatusroutes().register(app);
        new UsuarioRoutes().register(app);
        new Empleadoroutes().register(app);
        new Direccionroutes().register(app);
        new ProductoRoutes().register(app);
        new Imagenproductoroutes().register(app);
        new Productotallaroutes().register(app);
        new Adquisicionroutes().register(app);
        new DetalleAdquisicionRoutes().register(app);
        new PedidoRoutes().register(app);
        new DetallePedidoRoutes().register(app);
        new PagoRoutes().register(app);
        new EnvioRoutes().register(app);
        new HistorialPrecioRoutes().register(app);

        // ═══════════════════════════════════════════════════════════════
        // ENDPOINTS DE PRUEBA
        // ═══════════════════════════════════════════════════════════════

        app.get("/", ctx -> {
            ctx.json(Map.of(
                    "success", true,
                    "message", "API Culturas - E-commerce de productos artesanales mexicanos",
                    "version", "1.0.0",
                    "endpoints", Map.of(
                            "auth", "/api/auth/*",
                            "api", "/api/*",
                            "docs", "Consulta el RESUMEN_FINAL_COMPLETO.txt"
                    )
            ));
        });

        app.get("/api/health", ctx -> {
            ctx.json(Map.of(
                    "success", true,
                    "status", "OK",
                    "message", "API funcionando correctamente",
                    "timestamp", System.currentTimeMillis()
            ));
        });

        // ═══════════════════════════════════════════════════════════════
        // MANEJO DE ERRORES
        // ═══════════════════════════════════════════════════════════════

        // app.error(404, ctx -> {
        //    ctx.json(Map.of(
        //           "success", false,
        //           "error", "Endpoint no encontrado",
        //           "path", ctx.path()
        //  ));
        //  });

        app.error(500, ctx -> {
            ctx.json(Map.of(
                    "success", false,
                    "error", "Error interno del servidor"
            ));
        });

        // ═══════════════════════════════════════════════════════════════
        // RESUMEN FINAL
        // ═══════════════════════════════════════════════════════════════

        System.out.println("✅ TODAS LAS RUTAS REGISTRADAS EXITOSAMENTE");
        System.out.println("📊 Total de entidades: 19");
        System.out.println("📊 Total de endpoints: ~80+");
        System.out.println("🎯 API lista para recibir peticiones");
        System.out.println("🎯 Frontend puede conectarse a http://localhost:7070");

        System.out.println("\n⚠️  RECORDATORIOS IMPORTANTES:");
        System.out.println("   1. Verifica DatabaseConfig.java con credenciales correctas");
        System.out.println("   2. Ejecuta el ALTER TABLE para DETALLE_PEDIDO");
        System.out.println("   3. Los passwords están sin hashear (implementar BCrypt)");
        System.out.println("   4. Implementar JWT para autenticación real");
    }
}