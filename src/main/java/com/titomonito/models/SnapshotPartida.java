package com.titomonito.models;

public class SnapshotPartida {

    private final int idJugador;
    private final boolean gano;
    private final int vidasRestantes;
    private final int errores;
    private final int dificultad;
    private final int idCategoria;
    private final boolean usoSacapuntas;
    private final boolean usoTijeras;
    private final boolean usoGoma;
    private final boolean usoPluma;
    private final boolean usoMarcatextos;
    private final int utilesCount;
    private final int monedasObtenidas;
    private final int tiempoRestanteAlFinal;
    private final int letrasCorrectas;
    private final int longitudPalabra;

    private SnapshotPartida(Builder b) {
        this.idJugador = b.idJugador;
        this.gano = b.gano;
        this.vidasRestantes = b.vidasRestantes;
        this.errores = b.errores;
        this.dificultad = b.dificultad;
        this.idCategoria = b.idCategoria;
        this.usoSacapuntas = b.usoSacapuntas;
        this.usoTijeras = b.usoTijeras;
        this.usoGoma = b.usoGoma;
        this.usoPluma = b.usoPluma;
        this.usoMarcatextos = b.usoMarcatextos;
        this.utilesCount = b.utilesCount;
        this.monedasObtenidas = b.monedasObtenidas;
        this.tiempoRestanteAlFinal = b.tiempoRestanteAlFinal;
        this.letrasCorrectas = b.letrasCorrectas;
        this.longitudPalabra = b.longitudPalabra;
    }

    public int getIdJugador() { return idJugador; }
    public boolean isGano() { return gano; }
    public int getVidasRestantes() { return vidasRestantes; }
    public int getErrores() { return errores; }
    public int getDificultad() { return dificultad; }
    public int getIdCategoria() { return idCategoria; }
    public boolean isUsoSacapuntas() { return usoSacapuntas; }
    public boolean isUsoTijeras() { return usoTijeras; }
    public boolean isUsoGoma() { return usoGoma; }
    public boolean isUsoPluma() { return usoPluma; }
    public boolean isUsoMarcatextos() { return usoMarcatextos; }
    public int getUtilesCount() { return utilesCount; }
    public int getMonedasObtenidas() { return monedasObtenidas; }
    public int getTiempoRestanteAlFinal() { return tiempoRestanteAlFinal; }
    public int getLetrasCorrectas() { return letrasCorrectas; }
    public int getLongitudPalabra() { return longitudPalabra; }
    public boolean isUsoAlgunUtil() { return utilesCount > 0; }

    public static class Builder {
        private int idJugador;
        private boolean gano;
        private int vidasRestantes;
        private int errores;
        private int dificultad;
        private int idCategoria;
        private boolean usoSacapuntas;
        private boolean usoTijeras;
        private boolean usoGoma;
        private boolean usoPluma;
        private boolean usoMarcatextos;
        private int utilesCount;
        private int monedasObtenidas;
        private int tiempoRestanteAlFinal;
        private int letrasCorrectas;
        private int longitudPalabra;

        public Builder idJugador(int v) { this.idJugador = v; return this; }
        public Builder gano(boolean v) { this.gano = v; return this; }
        public Builder vidasRestantes(int v) { this.vidasRestantes = v; return this; }
        public Builder errores(int v) { this.errores = v; return this; }
        public Builder dificultad(int v) { this.dificultad = v; return this; }
        public Builder idCategoria(int v) { this.idCategoria = v; return this; }
        public Builder usoSacapuntas(boolean v) { this.usoSacapuntas = v; return this; }
        public Builder usoTijeras(boolean v) { this.usoTijeras = v; return this; }
        public Builder usoGoma(boolean v) { this.usoGoma = v; return this; }
        public Builder usoPluma(boolean v) { this.usoPluma = v; return this; }
        public Builder usoMarcatextos(boolean v) { this.usoMarcatextos = v; return this; }
        public Builder utilesCount(int v) { this.utilesCount = v; return this; }
        public Builder monedasObtenidas(int v) { this.monedasObtenidas = v; return this; }
        public Builder tiempoRestanteAlFinal(int v) { this.tiempoRestanteAlFinal = v; return this; }
        public Builder letrasCorrectas(int v) { this.letrasCorrectas = v; return this; }
        public Builder longitudPalabra(int v) { this.longitudPalabra = v; return this; }

        public SnapshotPartida build() {
            return new SnapshotPartida(this);
        }
    }
}
