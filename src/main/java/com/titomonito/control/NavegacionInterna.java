package com.titomonito.control;

import com.titomonito.ui.*;
import com.titomonito.ui.vistas.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavegacionInterna {

    private static boolean estadisticasCargado = false;
    private static boolean logrosCargado = false;
    private static boolean ayudaCargado = false;
    private static boolean opcionesCargado = false;
    private static VentanaBase ventanaPrincipal;

    public static class ManejarMenu implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String opcion = e.getActionCommand();

            switch (opcion) {
                case "INICIO":
                    cambiarVista("INICIO");
                    break;

                case "ESTADÍSTICAS":
                    if (!estadisticasCargado) {
                        ventanaPrincipal.getContenedor().add(new EstadisticasPanel(), "ESTADÍSTICAS");
                        ventanaPrincipal.getContenedor().revalidate();
                        estadisticasCargado = true;
                    }
                    cambiarVista("ESTADÍSTICAS");
                    break;

                case "LOGROS":
                    if (!logrosCargado) {
                        ventanaPrincipal.getContenedor().add(new LogrosPanel(), "LOGROS");
                        ventanaPrincipal.getContenedor().revalidate();
                        logrosCargado = true;
                    }
                    cambiarVista("LOGROS");
                    break;

                case "AYUDA":
                    if (!ayudaCargado) {
                        ventanaPrincipal.getContenedor().add(new AyudaPanel(), "AYUDA");
                        ventanaPrincipal.getContenedor().revalidate();
                        ayudaCargado = true;
                    }
                    cambiarVista("AYUDA");
                    break;

                case "OPCIONES":
                    if (!opcionesCargado) {
                        ventanaPrincipal.getContenedor().add(new OpcionesPanel(), "OPCIONES");
                        ventanaPrincipal.getContenedor().revalidate();
                        opcionesCargado = true;
                    }
                    cambiarVista("OPCIONES");
                    break;

                case "ACERCA DE":
                    AcercaPanel vistaAbout = new AcercaPanel(ventanaPrincipal);
                    vistaAbout.setVisible(true);
                    break;

                case "SALIR":
                    if (confirmarSalida()) {
                        System.exit(0);
                    }
            }
        }
    }

    public static class ManejarVistas implements ActionListener {

        public static final ManejarVistas INSTANCIA = new ManejarVistas();

        @Override
        public void actionPerformed(ActionEvent e) {

            JButton boton = (JButton) e.getSource();

            if (boton.getName().equals("PLAY")) {
                cambiarVista(boton.getName());
            }
        }
    }

    private static boolean confirmarSalida() {

        Object[] respuestas = {"Sí, quiero salir", "Volver al juego"};

        return JOptionPane.showOptionDialog(ventanaPrincipal,
                "¿Seguro que quieres abandonar a Tito?",
                "Confirmación de salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                respuestas,
                respuestas[1]) == JOptionPane.YES_OPTION;
    }

    private static void cambiarVista(String vista) {

        ventanaPrincipal.getVistas().show(ventanaPrincipal.getContenedor(), vista);
    }

    public static void setVentanaPrincipal(VentanaBase ventanaPrincipal) {
        NavegacionInterna.ventanaPrincipal = ventanaPrincipal;
    }

}
