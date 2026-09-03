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
                    if (confirmarAbandono()) {
                        LogicaJuego.getInstance().abandonarPartida();
                        principal.cambiarVista(Constantes.INICIO);
                    }
                } else principal.cambiarVista(Constantes.INICIO);

                break;
            case Constantes.PREGAME:
                if (LogicaJuego.getInstance().isJuegoActivo()) {
                    if (confirmarAbandono()) {
                        LogicaJuego.getInstance().abandonarPartida();
                        principal.cambiarVista(Constantes.PREGAME);
                        Jugador jugadorActual = SesionManager.getInstance().getJugadorActual();
                        if (jugadorActual != null) {
                            principal.getPreGame().actualizarEstadoCategorias(jugadorActual.getId_jugador());
                        }
                    }
                } else {
                    principal.cambiarVista(Constantes.PREGAME);
                    Jugador jugadorActual = SesionManager.getInstance().getJugadorActual();
                    if (jugadorActual != null) {
                        principal.getPreGame().actualizarEstadoCategorias(jugadorActual.getId_jugador());
                    }
                }
                break;
            case Constantes.ESTADISTICAS:
                if (LogicaJuego.getInstance().isJuegoActivo()) {
                    if (confirmarAbandono()) {
                        LogicaJuego.getInstance().abandonarPartida();
                        principal.cambiarVista(Constantes.ESTADISTICAS);
                        principal.getEstadisticas().refrescar();
                    }
                } else {
                    principal.cambiarVista(Constantes.ESTADISTICAS);
                    principal.getEstadisticas().refrescar();
                }
                break;
            case Constantes.LOGROS:
                if (LogicaJuego.getInstance().isJuegoActivo()) {
                    if (confirmarAbandono()) {
                        LogicaJuego.getInstance().abandonarPartida();
                        principal.cambiarVista(Constantes.LOGROS);
                    }
                } else principal.cambiarVista(Constantes.LOGROS);
                break;
            case Constantes.AYUDA:
                if (LogicaJuego.getInstance().isJuegoActivo()) {
                    if (confirmarAbandono()) {
                        LogicaJuego.getInstance().abandonarPartida();
                        principal.cambiarVista(Constantes.AYUDA);
                    }
                } else principal.cambiarVista(Constantes.AYUDA);
                break;
            case Constantes.OPCIONES:
                if (LogicaJuego.getInstance().isJuegoActivo()) {
                    if (confirmarAbandono()) {
                        LogicaJuego.getInstance().abandonarPartida();
                        principal.cambiarVista(Constantes.OPCIONES);
                    }
                } else principal.cambiarVista(Constantes.OPCIONES);
                break;
            case Constantes.ACERCA_DE:
                if (LogicaJuego.getInstance().isJuegoActivo()) {
                    if (confirmarAbandono()) {
                        LogicaJuego.getInstance().abandonarPartida();
                        AcercaPanel acerca = new AcercaPanel(principal);
                        acerca.setVisible(true);
                    }
                } else {
                    AcercaPanel acerca = new AcercaPanel(principal);
                    acerca.setVisible(true);
                }
                break;
            case Constantes.CAMBIAR:
                if (LogicaJuego.getInstance().isJuegoActivo()) {
                    if (confirmarAbandono()) {
                        LogicaJuego.getInstance().abandonarPartida();
                        cambiarJugador();
                    }
                } else cambiarJugador();
                break;
            case Constantes.SALIR:
                if (LogicaJuego.getInstance().isJuegoActivo()) {
                    if (confirmarAbandono()) {
                        LogicaJuego.getInstance().abandonarPartida();
                        System.exit(0);
                    }
                } else System.exit(0);
                break;

            default:
                break;
        }
    }

    private void cambiarJugador() {
        SesionManager.getInstance().cerrarSesion();
        principal.dispose();
        new Main().mostrarVentanaLogin();
    }

    private boolean confirmarAbandono() {

        Object[] respuestas = {Constantes.BTN_ABANDONAR_SI, Constantes.BTN_ABANDONAR_NO};

        return JOptionPane.showOptionDialog(principal,
                Constantes.MSJ_CONFIRMAR_ABANDONO,
                Constantes.TITULO_ABANDONO,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                respuestas,
                respuestas[1]) == JOptionPane.YES_OPTION;
    }
}
