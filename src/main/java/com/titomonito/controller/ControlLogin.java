package com.titomonito.controller;

import com.titomonito.ui.VentanaLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControlLogin {

    private final VentanaLogin ventana;

    public ControlLogin(VentanaLogin ventana) {

        this.ventana = ventana;
        agregarListeners();
    }

    private void agregarListeners() {

        ControlLogin.ControladorEventos controlador = new ControlLogin.ControladorEventos();
        ventana.addFondoListeners(controlador, controlador);
        ventana.addBotonesListeners(this::controlarBotones);
    }

    private void controlarBotones(ActionEvent e) {

        JButton boton = (JButton) e.getSource();

        if (boton.getText().equals("SALIR")) {
            System.exit(0);
        }
    }

    private class ControladorEventos extends MouseAdapter {

        private int xMouse, yMouse;

        @Override
        public void mousePressed(MouseEvent e) {
            Point clickInicial = e.getPoint();
            xMouse = clickInicial.x;
            yMouse = clickInicial.y;
        }

        public void mouseDragged(MouseEvent e) {
            int x = e.getXOnScreen() - xMouse;
            int y = e.getYOnScreen() - yMouse;
            ventana.setLocation(x, y);
        }
    }
}
