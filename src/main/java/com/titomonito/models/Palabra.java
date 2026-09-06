package com.titomonito.models;

public class Palabra {
    private final int idPalabra;
    private final String palabra;
    private final String pista;

    public Palabra(int idPalabra, String palabra, String pista) {
        this.idPalabra = idPalabra;
        this.palabra = palabra;
        this.pista = pista;
    }

    public int getIdPalabra() { return idPalabra; }
    public String getPalabra() { return palabra; }
    public String getPista() { return pista; }
}