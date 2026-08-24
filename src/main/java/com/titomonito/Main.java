package com.titomonito;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTGitHubIJTheme;
import com.titomonito.controller.ControlVentana;
import com.titomonito.config.GlobalConfig;
import com.titomonito.ui.VentanaBase;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Configuración del tema
        FlatMTGitHubIJTheme.setup();
        GlobalConfig.CargarConfig();

        SwingUtilities.invokeLater(() -> {
            VentanaBase ventanaPrincipal = new VentanaBase();

            new ControlVentana(ventanaPrincipal);
            ventanaPrincipal.setVisible(true);
        });

    }

}
