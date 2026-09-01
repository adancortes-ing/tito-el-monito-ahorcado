package com.titomonito.models;

public class Jugador {

    private int id_jugador;
    private String nombre;
    private int monedas_actuales;
    private int monedas_maximas;
    private int racha_actual;
    private int racha_maxima;

    public Jugador(int id_jugador, String nombre, int monedas_actuales,
                   int monedas_maximas, int racha_actual, int racha_maxima) {
        this.id_jugador = id_jugador;
        this.nombre = nombre;
        this.monedas_actuales = monedas_actuales;
        this.monedas_maximas = monedas_maximas;
        this.racha_actual = racha_actual;
        this.racha_maxima = racha_maxima;
    }

    public int getId_jugador() {
        return id_jugador;
    }

    public String getNombre() {
        return nombre;
    }

    public int getMonedas_actuales() {
        return monedas_actuales;
    }

    public void setMonedas_actuales(int monedas_actuales) {
        this.monedas_actuales = monedas_actuales;
    }

    public int getMonedas_maximas() {
        return monedas_maximas;
    }

    public void setMonedas_maximas(int monedas_maximas) {
        this.monedas_maximas = monedas_maximas;
    }

    public int getRacha_actual() {
        return racha_actual;
    }

    public void setRacha_actual(int racha_actual) {
        this.racha_actual = racha_actual;
    }

    public int getRacha_maxima() {
        return racha_maxima;
    }

    public void setRacha_maxima(int racha_maxima) {
        this.racha_maxima = racha_maxima;
    }
}
