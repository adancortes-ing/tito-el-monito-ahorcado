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
    public static final int PRECIO_SACAPUNTAS = 20;
    public static final int PRECIO_TIJERAS = 35;
    public static final int PRECIO_GOMA = 30;
    public static final int PRECIO_PLUMA = 50;
    public static final int PRECIO_MARCATEXTOS = 65;

    // Tiers de utiles (Fase 9)
    public static final String TIER_BASICO = "BASICO";
    public static final String TIER_MEDIO = "MEDIO";
    public static final String TIER_CARO = "CARO";

    // Caps de gasto por partida (Fase 9)
    public static final int CAP_GASTO_FACIL = Integer.MAX_VALUE;
    public static final int CAP_GASTO_NORMAL = 120;
    public static final int CAP_GASTO_DIFICIL = 120;
    public static final int CAP_GASTO_EXTREMO = 105;
    public static final int CAP_GASTO_IMPOSIBLE = 55;

    public static String obtenerTier(String util) {
        if (UTIL_SACAPUNTAS.equals(util)) return TIER_BASICO;
        if (UTIL_GOMA.equals(util) || UTIL_TIJERAS.equals(util)) return TIER_MEDIO;
        if (UTIL_PLUMA.equals(util) || UTIL_MARCATEXTOS.equals(util)) return TIER_CARO;
        return null;
    }

    public static int obtenerPrecio(String util) {
        if (UTIL_SACAPUNTAS.equals(util)) return PRECIO_SACAPUNTAS;
        if (UTIL_GOMA.equals(util)) return PRECIO_GOMA;
        if (UTIL_TIJERAS.equals(util)) return PRECIO_TIJERAS;
        if (UTIL_PLUMA.equals(util)) return PRECIO_PLUMA;
        if (UTIL_MARCATEXTOS.equals(util)) return PRECIO_MARCATEXTOS;
        return Integer.MAX_VALUE;
    }

    public static int[] obtenerCapTierPorDificultad(int dificultad) {
        return switch (dificultad) {
            case 1 -> new int[]{Integer.MAX_VALUE, 1, 2};
            case 2 -> new int[]{Integer.MAX_VALUE, 1, 1};
            case 3 -> new int[]{1, 1, 1};
            case 4 -> new int[]{Integer.MAX_VALUE, 0, 1};
            case 5 -> new int[]{Integer.MAX_VALUE, 1, 0};
            default -> new int[]{0, 0, 0};
        };
    }

    public static int obtenerCapGastoPorDificultad(int dificultad) {
        return switch (dificultad) {
            case 1 -> CAP_GASTO_FACIL;
            case 2 -> CAP_GASTO_NORMAL;
            case 3 -> CAP_GASTO_DIFICIL;
            case 4 -> CAP_GASTO_EXTREMO;
            case 5 -> CAP_GASTO_IMPOSIBLE;
            default -> 0;
        };
    }

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
