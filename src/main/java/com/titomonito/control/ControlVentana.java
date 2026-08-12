package com.titomonito.control;

import com.titomonito.vista.VentanaBase;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControlVentana {

    private final VentanaBase ventanaPrincipal;

    public ControlVentana(VentanaBase ventanaPrincipal) {

        this.ventanaPrincipal = ventanaPrincipal;
        asignarControles();
    }

    private void asignarControles() {

        ControladorEventos controlador = new ControladorEventos();
        ventanaPrincipal.getPnlHeader().addMouseListener(controlador);
        ventanaPrincipal.getPnlHeader().addMouseMotionListener(controlador);
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
            ventanaPrincipal.setLocation(x, y);
        }
    }
}
