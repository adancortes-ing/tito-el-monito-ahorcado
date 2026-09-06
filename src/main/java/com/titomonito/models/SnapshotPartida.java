package com.titomonito.models;

public class SnapshotPartida {

    private final int idJugador;
    private final boolean gano;
    private final int vidasRestantes;
    private final int dificultad;
    private final boolean usoSacapuntas;
    private final boolean usoMarcatextos;
    private final int utilesCount;
    private final int monedasObtenidas;
    private final int tiempoRestanteAlFinal;
    private final int longitudPalabra;

    private SnapshotPartida(Builder b) {
        this.idJugador = b.idJugador;
        this.gano = b.gano;
        this.vidasRestantes = b.vidasRestantes;
        this.dificultad = b.dificultad;
        this.usoSacapuntas = b.usoSacapuntas;
        this.usoMarcatextos = b.usoMarcatextos;
        this.utilesCount = b.utilesCount;
        this.monedasObtenidas = b.monedasObtenidas;
        this.tiempoRestanteAlFinal = b.tiempoRestanteAlFinal;
        this.longitudPalabra = b.longitudPalabra;
    }

    public int getIdJugador() { return idJugador; }
    public boolean isGano() { return gano; }
    public int getVidasRestantes() { return vidasRestantes; }
    public int getDificultad() { return dificultad; }
    public boolean isUsoSacapuntas() { return usoSacapuntas; }
    public boolean isUsoMarcatextos() { return usoMarcatextos; }
    public int getUtilesCount() { return utilesCount; }
    public int getMonedasObtenidas() { return monedasObtenidas; }
    public int getTiempoRestanteAlFinal() { return tiempoRestanteAlFinal; }
    public int getLongitudPalabra() { return longitudPalabra; }
    public boolean isUsoAlgunUtil() { return utilesCount > 0; }

    public static class Builder {
        private int idJugador;
        private boolean gano;
        private int vidasRestantes;
        private int dificultad;
        private boolean usoSacapuntas;
        private boolean usoMarcatextos;
        private int utilesCount;
        private int monedasObtenidas;
        private int tiempoRestanteAlFinal;
        private int longitudPalabra;

        public Builder idJugador(int v) { this.idJugador = v; return this; }
        public Builder gano(boolean v) { this.gano = v; return this; }
        public Builder vidasRestantes(int v) { this.vidasRestantes = v; return this; }
        public Builder dificultad(int v) { this.dificultad = v; return this; }
        public Builder usoSacapuntas(boolean v) { this.usoSacapuntas = v; return this; }
        public Builder usoMarcatextos(boolean v) { this.usoMarcatextos = v; return this; }
        public Builder utilesCount(int v) { this.utilesCount = v; return this; }
        public Builder monedasObtenidas(int v) { this.monedasObtenidas = v; return this; }
        public Builder tiempoRestanteAlFinal(int v) { this.tiempoRestanteAlFinal = v; return this; }
        public Builder longitudPalabra(int v) { this.longitudPalabra = v; return this; }

        public SnapshotPartida build() {
            return new SnapshotPartida(this);
        }
    }
}
