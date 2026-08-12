package com.titomonito.control;

import com.titomonito.vista.PanelMenu;
import com.titomonito.vista.VentanaBase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavegacionInterna {

    private final VentanaBase ventanaPrincipal;

    public NavegacionInterna(VentanaBase ventanaPrincipal) {

        this.ventanaPrincipal = ventanaPrincipal;
        asignarControles();
    }

    private void asignarControles() {

        // Botones del menu lateral ====================================================================================
        PanelMenu menu = ventanaPrincipal.getPnlMenu();
        ManejarMenu menus = new ManejarMenu();

        menu.getBtnInicio().addActionListener(menus);
        menu.getBtnAyuda().addActionListener(menus);
        menu.getBtnEstadisticas().addActionListener(menus);
        menu.getBtnLogros().addActionListener(menus);
        menu.getBtnOpciones().addActionListener(menus);
        menu.getBtnAcerca().addActionListener(menus);
        menu.getBtnSalir().addActionListener(menus);

        //VistaInicio inicio = (VistaInicio) ventanaPrincipal.getVistainicio();
        ventanaPrincipal.getVistaInicio().getBtnIniciarJuego().addActionListener(null);
        //inicio.getBtnIniciarJuego().addActionListener(this);
    }

    private class ManejarMenu implements ActionListener {

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

    public void cambiarVista(String vista) {

        ventanaPrincipal.getVistas().show(ventanaPrincipal.getContenedor(), vista);
    }

}
