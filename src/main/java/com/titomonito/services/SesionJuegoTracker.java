package com.titomonito.services;

import java.util.HashSet;
import java.util.Set;

public class SesionJuegoTracker {

    private static SesionJuegoTracker instance;

    private int rachaGlobalSesion;
    private int rachaExtremoSesion;
    private int rachaImposibleSesion;
    private final Set<Integer> categoriasGanadasEnSesion;

    private SesionJuegoTracker() {
        rachaGlobalSesion = 0;
        rachaExtremoSesion = 0;
        rachaImposibleSesion = 0;
        categoriasGanadasEnSesion = new HashSet<>();
    }

    public static SesionJuegoTracker getInstance() {
        if (instance == null) {
            instance = new SesionJuegoTracker();
        }
        return instance;
    }

    public void registrarVictoria(int dificultad, int idCategoria) {
        rachaGlobalSesion++;
        if (dificultad == 4) {
            rachaExtremoSesion++;
        } else if (dificultad == 5) {
            rachaImposibleSesion++;
        }
        categoriasGanadasEnSesion.add(idCategoria);
    }

    public void registrarDerrotaOAbandono() {
        rachaGlobalSesion = 0;
        rachaExtremoSesion = 0;
        rachaImposibleSesion = 0;
    }

    public int getRachaGlobalSesion() {
        return rachaGlobalSesion;
    }

    public int getRachaExtremoSesion() {
        return rachaExtremoSesion;
    }

    public int getRachaImposibleSesion() {
        return rachaImposibleSesion;
    }

    public Set<Integer> getCategoriasGanadasEnSesion() {
        return new HashSet<>(categoriasGanadasEnSesion);
    }

    public int getCategoriasDistintasGanadas() {
        return categoriasGanadasEnSesion.size();
    }

    public void reiniciar() {
        rachaGlobalSesion = 0;
        rachaExtremoSesion = 0;
        rachaImposibleSesion = 0;
        categoriasGanadasEnSesion.clear();
    }
}
