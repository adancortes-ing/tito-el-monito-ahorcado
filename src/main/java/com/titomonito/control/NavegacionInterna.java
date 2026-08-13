package com.titomonito.control;

import com.titomonito.vista.VentanaBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavegacionInterna {

    private static VentanaBase ventanaPrincipal;

    public static void setVentanaPrincipal(VentanaBase ventanaPrincipal) {
        NavegacionInterna.ventanaPrincipal = ventanaPrincipal;
    }

    public static class ManejarMenu implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String opcion = e.getActionCommand();

            switch (opcion) {
                case "INICIO":
                    cambiarVista("INICIO");
                    break;
                case "ACERCA DE":
                    cambiarVista("ACERCA");
                    break;
                case "SALIR":
                    System.exit(0);
            }
        }
    }

    public static class ManejarVistas implements ActionListener {

        public static final ManejarVistas INSTANCIA =  new ManejarVistas();

        @Override
        public void actionPerformed(ActionEvent e) {

            JButton boton = (JButton) e.getSource();

            if (boton.getName().equals("PLAY")) {
                cambiarVista(boton.getName());
            }
        }
    }

    public static void cambiarVista(String vista) {

        ventanaPrincipal.getVistas().show(ventanaPrincipal.getContenedor(), vista);
    }

}
