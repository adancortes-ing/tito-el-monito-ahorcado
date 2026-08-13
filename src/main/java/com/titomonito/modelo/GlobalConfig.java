package com.titomonito.modelo;

import javax.swing.*;
import java.awt.*;

public class GlobalConfig {

    public static final String VERSION_JUEGO = "version 0.1.0";
    public static final Dimension MEDIDA_VENTANA = new Dimension(1080, 750);

    //Paleta de colores de la aplicacion
    public static final Color COLOR_AZUL = new Color(108, 222, 242);
    public static final Color COLOR_VERDE = new Color(178, 242, 109);
    public static final Color COLOR_SALMON = new Color(242, 129, 109);
    public static final Color COLOR_GRIS_OSCURO = new Color(80, 80, 80);
    public static final Color COLOR_AZUL_CLARO = new Color(206, 244, 250);

    public static void CargarConfig() {

        Font fuenteGlobal = Recursos.cargarFuente("IndieFlower-Regular.ttf", 24.0f);
        Font fuenteComic = new Font("Comic Sans MS", Font.PLAIN, 16);

        // 1. Configuración de la UI nativa de FlatLaf
        UIManager.put("Button.arc", 10);
        UIManager.put("Button.background", COLOR_AZUL_CLARO);

        // 2. Fuente global por defecto
        UIManager.put("defaultFont", fuenteGlobal);

        // 3. Fuentes específicas de JOptionPane
        UIManager.put("OptionPane.messageFont", fuenteComic.deriveFont(18.0f));
        UIManager.put("OptionPane.buttonFont", fuenteComic);

        // 4. CLAVES DE FLATLAF PARA LA BARRA DE TÍTULO (Esto cambia el título del diálogo/ventana)
        UIManager.put("TitlePane.font", fuenteComic);
        UIManager.put("TitlePane.titleFont", fuenteComic);
    }

}
