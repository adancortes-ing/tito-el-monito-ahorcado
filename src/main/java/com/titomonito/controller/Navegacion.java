package com.titomonito.controller;

import com.titomonito.Main;
import com.titomonito.config.Constantes;
import com.titomonito.models.Jugador;
import com.titomonito.services.LogicaJuego;
import com.titomonito.services.SesionManager;
import com.titomonito.ui.*;
import com.titomonito.ui.vistas.AcercaPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Navegacion {

    private final PanelMenu view;
    private final InicioPanel inicio;
    private final VentanaBase principal;

    public Navegacion(PanelMenu view, InicioPanel inicio, VentanaBase owner) {

        this.view = view;
        this.inicio = inicio;
        this.principal = owner;
        initListeners();
    }

    private void initListeners() {

        view.addBotonesMenuListener(this::controlMenu);
        inicio.addIniciarListener(this::controlMenu);
    }

    private void controlMenu(ActionEvent e) {

        String opcion = e.getActionCommand();

        switch (opcion) {

            case Constantes.INICIO:
                if (LogicaJuego.getInstance().isJuegoActivo()) {
                    if (confirmarSalida()) {
                        principal.cambiarVista(Constantes.INICIO);
                    }
                } else principal.cambiarVista(Constantes.INICIO);

                break;
            case Constantes.PREGAME:
                principal.cambiarVista(Constantes.PREGAME);
                Jugador jugadorActual = SesionManager.getInstance().getJugadorActual();
                if (jugadorActual != null) {
                    principal.getPreGame().actualizarEstadoCategorias(jugadorActual.getId_jugador());
                }
                break;
            case Constantes.ESTADISTICAS:
                principal.cambiarVista(Constantes.ESTADISTICAS);
                principal.getEstadisticas().refrescar();
                break;
            case Constantes.LOGROS:
                principal.cambiarVista(Constantes.LOGROS);
                break;
            case Constantes.AYUDA:
                principal.cambiarVista(Constantes.AYUDA);
                break;
            case Constantes.OPCIONES:
                principal.cambiarVista(Constantes.OPCIONES);
                break;
            case Constantes.ACERCA_DE:
                AcercaPanel acerca = new AcercaPanel(principal);
                acerca.setVisible(true);
                break;
            case Constantes.CAMBIAR:
                if (LogicaJuego.getInstance().isJuegoActivo()) {
                    if (confirmarSalida()) {
                        cambiarJugador();
                    }
                } else cambiarJugador();
                break;
            case Constantes.SALIR:
                if (LogicaJuego.getInstance().isJuegoActivo()) {
                    if (confirmarSalida()) {
                        System.exit(0);
                    }
                } else System.exit(0);

            default:
                break;
        }
    }

    private void cambiarJugador() {
        SesionManager.getInstance().cerrarSesion();
        principal.dispose();
        new Main().mostrarVentanaLogin();
    }

    private boolean confirmarSalida() {

        Object[] respuestas = {"Sí, quiero salir", "Volver al juego"};

        return JOptionPane.showOptionDialog(principal,
                "¿Seguro que quieres abandonar a Tito?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                respuestas,
                respuestas[1]) == JOptionPane.YES_OPTION;
    }
}
