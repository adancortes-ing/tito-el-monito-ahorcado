package com.titomonito.control;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTGitHubIJTheme;
import com.titomonito.modelo.GlobalConfig;
import com.titomonito.vista.VentanaBase;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Configuración del tema
        FlatMTGitHubIJTheme.setup();
        GlobalConfig.CargarConfig();

        SwingUtilities.invokeLater(() -> {
            VentanaBase ventanaPrincipal = new VentanaBase();

            new ControlVentana(ventanaPrincipal);
            NavegacionInterna.setVentanaPrincipal(ventanaPrincipal);
            NavegacionInterna.cambiarVista("INICIO");

            ventanaPrincipal.setVisible(true);
        });

    }

}
