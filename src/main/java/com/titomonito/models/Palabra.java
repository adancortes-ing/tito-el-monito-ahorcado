package com.titomonito.models;

public class Palabra {
    private int id_palabra;
    private int id_categoria;
    private String palabra;
    private String pista;

    public Palabra(int id_palabra, int id_categoria, String palabra, String pista) {
        this.id_palabra = id_palabra;
        this.id_categoria = id_categoria;
        this.palabra = palabra;
        this.pista = pista;
    }

    public int getId_palabra() { return id_palabra; }
    public int getId_categoria() { return id_categoria; }
    public String getPalabra() { return palabra; }
    public String getPista() { return pista; }
}
