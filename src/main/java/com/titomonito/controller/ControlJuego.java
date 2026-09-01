package com.titomonito.controller;

import com.titomonito.config.Constantes;
import com.titomonito.services.LogicaJuego;
import com.titomonito.ui.VentanaBase;
import com.titomonito.ui.vistas.JuegoPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ControlJuego {

    private final JuegoPanel juegoPanel;
    private final VentanaBase ventana;

    public ControlJuego(JuegoPanel juegoPanel, VentanaBase ventana) {

        this.juegoPanel = juegoPanel;
        this.ventana = ventana;
        this.juegoPanel.setTeclasActionListener(this::controlarTeclas);
        LogicaJuego.getInstance().setControlJuego(this);
    }

    public void mostrarResultado(String titulo, String mensaje, int id, String categoria, int dificultad) {

        String[] opciones = {"Volver a jugar", "Cambiar opciones", "Salir al menú"};
        int seleccion = JOptionPane.showOptionDialog(
                juegoPanel,
                mensaje,
                titulo,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        switch (seleccion) {
            case 0:
                LogicaJuego.getInstance().newGame(id, categoria, dificultad);
                break;
            case 1:
                ventana.cambiarVista(Constantes.PREGAME);
                break;
            default:
                ventana.cambiarVista(Constantes.INICIO);
                break;
        }
    }

    public void refrescarDatosJugador() {
        ventana.getPnlHeader().actualizarDatosJugador();
        ventana.getInicio().actualizarDatos();
    }

    private void controlarTeclas(ActionEvent e) {

        JButton boton = (JButton) e.getSource();

        char tecla = boton.getText().charAt(0);
        LogicaJuego.getInstance().probarLetra(tecla);
    }
}
