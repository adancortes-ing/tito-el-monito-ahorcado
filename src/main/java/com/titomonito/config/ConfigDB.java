package com.titomonito.config;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.logging.*;
import java.util.stream.Collectors;

public class ConfigDB {

    private static final int CURRENT_DB_VERSION = 1; // Sube a 2 cuando agregues cambios

    private static final String DB_NAME = "tito_db.db";
    private static final String DB_NOMBRE_CARPETA = "Tito el Monito Ahorcado";
    private static final String dbPath;
    private static final File directorio;

    // Logger para enviar los errores al archivo error.log de GlobalConfig
    private static final Logger LOGGER = Logger.getLogger(ConfigDB.class.getName());

    static {
        String userHome = System.getProperty("user.home");
        File documentos = new File(userHome, "Documents");
        directorio = new File(documentos, DB_NOMBRE_CARPETA);

        if (!directorio.exists()) {
            boolean creado = directorio.mkdirs();
            if (!creado) {
                LOGGER.warning("No se pudo crear el directorio de la base de datos.");
            }
        }
        dbPath = new File(directorio, DB_NAME).getAbsolutePath();
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:" + dbPath;
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException ex) {
            // 1. Registrar en el archivo de log
            LOGGER.log(Level.SEVERE, "Error crítico al conectar con la base de datos en: " + dbPath, ex);

            // 2. Notificar gráficamente al usuario en el hilo de Swing
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                    null,
                    "No se pudo establecer conexión con la base de datos local.\nConsulta el archivo de registro para más detalles.",
                    "Error de Base de Datos",
                    JOptionPane.ERROR_MESSAGE
            ));
            // 3. Relanzar la excepción para que el flujo superior sepa que falló
            throw ex;
        }
    }

    public static void initDB() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Habilitar las llaves foráneas en SQLite
            stmt.execute("PRAGMA foreign_keys = ON;");

            // Obtener la versión actual de la base de datos
            int versionActual = 0;
            try (var rs = stmt.executeQuery("PRAGMA user_version;")) {
                if (rs.next()) {
                    versionActual = rs.getInt(1);
                }
            }

            // Si la version en PRAGMA es menor a la version definida en esta app, ejecutar ↓
            if (versionActual < CURRENT_DB_VERSION) {
                LOGGER.info("Inicializando la base de datos por primera vez...");

                ejecutarScriptSQL(stmt, "/sql/schema.sql");
                ejecutarScriptSQL(stmt, "/sql/seeds.sql");

                // Actualizar version en PRAGMA de la base de datos
                stmt.execute("PRAGMA user_version = " + CURRENT_DB_VERSION + ";");
                LOGGER.info("Base de datos inicializada y sembrada con éxito en la versión " + CURRENT_DB_VERSION);
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al inicializar la base de datos.", ex);
        }
    }

    private static void ejecutarScriptSQL(Statement stmt, String resourcePath) throws IOException, SQLException {
        try (InputStream is = ConfigDB.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                LOGGER.warning("No se encontró el recurso: " + resourcePath);
                return;
            }
            String contenido;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                contenido = reader.lines().collect(Collectors.joining("\n"));
            }
            for (String sql : contenido.split(";")) {
                String trimmed = sql.trim();
                if (!trimmed.isEmpty()) {
                    stmt.execute(trimmed);
                }
            }
        }
    }

    public static File getDirectorio() {
        return directorio;
    }
}