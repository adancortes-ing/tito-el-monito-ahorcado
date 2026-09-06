package com.titomonito.services;

import com.titomonito.config.Constantes;

import java.util.HashMap;
import java.util.Map;

public class UtilsJuego {

    public static final int PREMIO_BASE = 10;

    protected static final Map<Integer, Double> MULTIPLICADORES = new HashMap<>();
    private static final Map<Integer, Integer> TIEMPOS = new HashMap<>();

    static {
        MULTIPLICADORES.put(1, 0.8); // FÁCIL
        MULTIPLICADORES.put(2, 1.0); // NORMAL
        MULTIPLICADORES.put(3, 1.6); // DIFÍCIL
        MULTIPLICADORES.put(4, 2.2); // EXTREMO
        MULTIPLICADORES.put(5, 3.0); // IMPOSIBLE
    }

    static {
        TIEMPOS.put(1, 20); // FÁCIL
        TIEMPOS.put(2, 15); // NORMAL
        TIEMPOS.put(3, 10); //DIFÍCIL
        TIEMPOS.put(4, 7); // EXTREMO
        TIEMPOS.put(5, 4); //IMPOSIBLE
    }

    private static final String[] dibujos = {
            "game_over.png", "game_vida1.png", "game_vida2.png", "game_vida3.png", "game_vida4.png", "game_vida5.png"};

    public static String construirPalabra(char[] palabra) {
        StringBuilder palabraPreview = new StringBuilder();
        for (char c : palabra) {
            palabraPreview.append(c).append(" ");
        }
        return palabraPreview.toString();
    }

    public static String calcularCorazones(int vidas) {

        LogicaJuego.getInstance().reiniciarCorazones();

        for (int i = 0; i < vidas; i++) {
            LogicaJuego.getInstance().setCorazones("♥");
        }
        return String.join(" ", LogicaJuego.getInstance().getCorazones());
    }

    public static String obtenerDibujo(int vidas) {

        return dibujos[Math.min(Math.max(vidas, 0), dibujos.length - 1)];
    }

    public static int calcularPremioPotencial(int premioVidas, int letras, int dificultad) {
        double multiplicador = MULTIPLICADORES.getOrDefault(dificultad, 1.0);

        int premioBaseAjustado = (int) (PREMIO_BASE * multiplicador);
        int premioLetras = Constantes.MONEDAS_POR_LETRA * letras;

        return premioBaseAjustado + premioLetras + premioVidas;
    }

    public static int calcularBonoDificultad(int dificultad) {

        double  multiplicador = MULTIPLICADORES.getOrDefault(dificultad, 1.0);
        int premioBaseAjustado = (int) (PREMIO_BASE * multiplicador);

        return premioBaseAjustado - PREMIO_BASE;
    }

    public static int getTiempoBase(int dificultad) {
        return TIEMPOS.getOrDefault(dificultad, 15);
    }
}
