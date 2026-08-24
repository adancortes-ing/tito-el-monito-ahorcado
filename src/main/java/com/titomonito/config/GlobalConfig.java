package com.titomonito.config;

import com.titomonito.utils.Recursos;

import javax.swing.*;
import java.awt.*;

public class GlobalConfig {

    public static final String VERSION_JUEGO = "version 0.1.0";
    public static final Dimension MEDIDA_VENTANA = new Dimension(1080, 750);

    public static void CargarConfig() {

        Font fuenteGlobal = Recursos.cargarFuente("IndieFlower-Regular.ttf", 24.0f);
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

}
