package com.titomonito.services;

public class UtilsJuego {

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
}
