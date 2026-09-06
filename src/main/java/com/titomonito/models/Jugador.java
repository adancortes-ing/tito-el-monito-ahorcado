package com.titomonito.models;

public class Jugador {

    private final int idJugador;
    private final String nombre;
    private int monedasActuales;
    private int monedasMaximas;
    private int rachaActual;
    private int rachaMaxima;

    public Jugador(int idJugador, String nombre, int monedasActuales,
                   int monedasMaximas, int rachaActual, int rachaMaxima) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.monedasActuales = monedasActuales;
        this.monedasMaximas = monedasMaximas;
        this.rachaActual = rachaActual;
        this.rachaMaxima = rachaMaxima;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public int getMonedasActuales() {
        return monedasActuales;
    }

    public void setMonedasActuales(int monedasActuales) {
        this.monedasActuales = monedasActuales;
    }

    public int getMonedasMaximas() {
        return monedasMaximas;
    }

    public void setMonedasMaximas(int monedasMaximas) {
        this.monedasMaximas = monedasMaximas;
    }

    public int getRachaActual() {
        return rachaActual;
    }

    public void setRachaActual(int rachaActual) {
        this.rachaActual = rachaActual;
    }

    public int getRachaMaxima() {
        return rachaMaxima;
    }

    public void setRachaMaxima(int rachaMaxima) {
        this.rachaMaxima = rachaMaxima;
    }
}