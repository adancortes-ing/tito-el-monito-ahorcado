package com.titomonito.controller;

import com.titomonito.config.Constantes;
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

        view.addAcercaListener(this::controlMenu);
        view.addAyudaListener(this::controlMenu);
        view.addSalirListener(this::controlMenu);
        view.addInicioListener(this::controlMenu);
        view.addEstadisticasListener(this::controlMenu);
        view.addLogrosListener(this::controlMenu);
        view.addOpcionesListener(this::controlMenu);

        inicio.addIniciarListener(this::controlMenu);
    }

    private void controlMenu(ActionEvent e) {

        String opcion = e.getActionCommand();

        switch (opcion) {

            case Constantes.INICIO:
                principal.cambiarVista(Constantes.INICIO);
                break;
            case Constantes.PREGAME:
                principal.cambiarVista(Constantes.PREGAME);
                break;
            case Constantes.ESTADISTICAS:
                principal.cambiarVista(Constantes.ESTADISTICAS);
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
            case Constantes.SALIR:
                if (confirmarSalida()) {
                    System.exit(0);
                }
            default:
                break;
        }
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
