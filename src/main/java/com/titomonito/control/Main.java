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
            ControlVentana control = new ControlVentana(ventanaPrincipal);
            NavegacionInterna navegacion = new NavegacionInterna(ventanaPrincipal);
            navegacion.cambiarVista("INICIO");
            ventanaPrincipal.setVisible(true);
        });

    }

}
