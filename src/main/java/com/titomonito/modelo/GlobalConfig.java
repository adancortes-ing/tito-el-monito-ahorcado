package com.titomonito.modelo;

import com.titomonito.control.Recursos;

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

        //Configuración de la UI
        UIManager.put("Button.arc", 10);
        UIManager.put("Button.background", COLOR_AZUL_CLARO);
        Font fuenteGlobal = Recursos.cargarFuente("IndieFlower-Regular.ttf", 24.0f);
        UIManager.put("defaultFont", fuenteGlobal);

    }

}
