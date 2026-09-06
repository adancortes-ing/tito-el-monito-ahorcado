package com.titomonito.services;

import com.titomonito.dao.JugadorDAO;
import com.titomonito.models.Jugador;

import java.util.List;

public class SesionManager {

    private static SesionManager instance;
    private Jugador jugadorActual;

    private SesionManager() {
    }

    public static SesionManager getInstance() {
        if (instance == null) {
            instance = new SesionManager();
        }
        return instance;
    }

    public List<Jugador> cargarJugadores() {

        return JugadorDAO.listarTodos();
    }

    public void iniciarSesion(Jugador jugador) {
        SesionJuegoTracker.getInstance().reiniciar();
        this.jugadorActual = jugador;
    }

    public Jugador getJugadorActual() {

        return jugadorActual;
    }

    public void cerrarSesion() {
        SesionJuegoTracker.getInstance().reiniciar();
        this.jugadorActual = null;
    }
}
