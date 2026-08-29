package com.titomonito.services;

import java.util.HashMap;
import java.util.Map;

public class UtilsJuego {

    private static final int PREMIO_BASE = 10;

    private static final Map<Integer, Double> MULTIPLICADORES = new HashMap<>();

    static {
        MULTIPLICADORES.put(1, 1.0); // FÁCIL
        MULTIPLICADORES.put(2, 1.2); // NORMAL
        MULTIPLICADORES.put(3, 1.5); // DIFÍCIL
        MULTIPLICADORES.put(4, 1.8); // EXTREMO
        MULTIPLICADORES.put(5, 2.0); // IMPOSIBLE
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

        return dibujos[vidas];
    }

    public static int calcularPremioPotencial(int vidas, int letras, int dificultad) {
        double multiplicador = MULTIPLICADORES.getOrDefault(dificultad, 1.0);

        int premioBaseAjustado = (int) (PREMIO_BASE * multiplicador);
        int premioLetras = 2 * letras;
        int premioVidas = vidas;

        return premioBaseAjustado + premioLetras + premioVidas;
    }
}
