package com.titomonito.config;

import java.awt.*;

public class Constantes {

    // Constantes para botones y navegacion
    public static final String INICIO = "INICIO";
    public static final String ESTADISTICAS = "ESTADÍSTICAS";
    public static final String LOGROS = "LOGROS";
    public static final String AYUDA = "AYUDA";
    public static final String OPCIONES = "OPCIONES";
    public static final String ACERCA_DE = "ACERCA DE";
    public static final String SALIR = "SALIR";
    public static final String PREGAME = "Iniciar Juego";
    public static final String JUEGO = "JUEGO";
    public static final String CAMBIAR = "CAMBIAR JUGADOR";

    // Constantes de dificultades
    public static final int DIFICULTAD_IMPOSIBLE = 5;

    // Constante de bonus del sacapuntas
    public static final int BONUS_SACAPUNTAS = 10;

    // Constantes de los utiles
    public static final String UTIL_SACAPUNTAS = "SACAPUNTAS";
    public static final String UTIL_TIJERAS = "TIJERAS";
    public static final String UTIL_GOMA = "GOMA";
    public static final String UTIL_PLUMA = "PLUMA";
    public static final String UTIL_MARCATEXTOS = "MARCATEXTOS";

    // Precios de los Powerups
    public static final int PRECIO_SACAPUNTAS = 15;
    public static final int PRECIO_TIJERAS = 20;
    public static final int PRECIO_GOMA = 30;
    public static final int PRECIO_PLUMA = 35;
    public static final int PRECIO_MARCATEXTOS = 45;

    //Paleta de colores de la aplicación
    public static final Color COLOR_AZUL = new Color(108, 222, 242);
    public static final Color COLOR_VERDE = new Color(178, 242, 109);
    public static final Color COLOR_SALMON = new Color(242, 129, 109);
    public static final Color COLOR_GRIS_OSCURO = new Color(80, 80, 80);
    public static final Color COLOR_AZUL_CLARO = new Color(206, 244, 250);

    public static final String ALFABETO =   "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";

    // Mensaje de confirmación al abandonar partida activa
    public static final String MSJ_CONFIRMAR_ABANDONO =
            "<html><div style='width:340px'>" +
            "Si abandonas la partida ahora:<br><br>" +
            "&bull; Se reiniciará tu <b>racha actual</b> a 0.<br>" +
            "&bull; <b>No recibirás</b> las monedas aseguradas en esta partida.<br><br>" +
            "¿Seguro que quieres salir?" +
            "</div></html>";

    public static final String TITULO_ABANDONO = "Confirmación de salida";
    public static final String BTN_ABANDONAR_SI = "Sí, abandonar";
    public static final String BTN_ABANDONAR_NO = "Volver al juego";
}
