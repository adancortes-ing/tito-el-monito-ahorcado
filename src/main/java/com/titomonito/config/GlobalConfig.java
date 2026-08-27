package com.titomonito.config;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GlobalConfig {

    private static final Logger LOGGER = Logger.getLogger("com.titomonito");

    public static final String VERSION_JUEGO = "version 0.1.0";
    public static final Dimension MEDIDA_VENTANA = new Dimension(1080, 750);

    public static void CargarConfig() {

        Font fuenteGlobal = com.titomonito.utils.Recursos.cargarFuente("IndieFlower-Regular.ttf", 24.0f);
        Font fuenteComic = new Font("Comic Sans MS", Font.PLAIN, 16);

        // 1. Configuración de la UI nativa de FlatLaf
        UIManager.put("Button.arc", 10);
        UIManager.put("Button.background", Constantes.COLOR_AZUL_CLARO);

        // 2. Fuente global por defecto
        UIManager.put("defaultFont", fuenteGlobal);

        // 3. Fuentes específicas de JOptionPane
        UIManager.put("OptionPane.messageFont", fuenteComic.deriveFont(18.0f));
        UIManager.put("OptionPane.buttonFont", fuenteComic);

        // 4. CLAVES DE FLATLAF PARA LA BARRA DE TÍTULO (Esto cambia el título del diálogo/ventana)
        UIManager.put("TitlePane.font", fuenteComic);
        UIManager.put("TitlePane.titleFont", fuenteComic);
    }

    public static void configurarLoggers() {
        File directorioApp = ConfigDB.getDirectorio();

        try {
            File logDir = new File(directorioApp, "logs");
            if (!logDir.exists()) {
                boolean creado = logDir.mkdirs();
                if (!creado) {
                    JOptionPane.showMessageDialog(null, "No se pudo crear el directorio de logs.");
                }
            }

            FileHandler fileHandler = new FileHandler(logDir.getAbsolutePath() + File.separator + "error.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setUseParentHandlers(false); // Evitar duplicar logs en la consola si ya tienes handlers por defecto
        } catch (IOException e) {
            System.err.println("No se pudo inicializar el sistema de logs: " + e.getMessage());
        }
    }
}
