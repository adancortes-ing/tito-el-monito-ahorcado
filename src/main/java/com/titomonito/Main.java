package com.titomonito;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTGitHubIJTheme;
import com.titomonito.config.ConfigDB;
import com.titomonito.config.GlobalConfig;
import com.titomonito.dao.GeneralDAO;
import com.titomonito.services.ServicioSonido;
import com.titomonito.ui.VentanaBase;
import com.titomonito.ui.VentanaLogin;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Configuración del tema
        FlatMTGitHubIJTheme.setup();

        // Configuración Global
        GlobalConfig.cargarConfig();
        GlobalConfig.configurarLoggers();

        try {
            ConfigDB.initDB();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "No se pudo inicializar la base de datos.\nLa aplicación se cerrará.\n\nDetalles: " + ex.getMessage(),
                    "Error de Base de Datos",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }

        GeneralDAO.obtenerCategorias();
        ServicioSonido.getInstance();

        new Main().mostrarVentanaLogin();
    }

    public void mostrarVentanaPrincipal() {
        SwingUtilities.invokeLater(() -> {
            VentanaBase ventanaPrincipal = new VentanaBase();
            ventanaPrincipal.setVisible(true);
        });
    }

    public void mostrarVentanaLogin () {
        SwingUtilities.invokeLater(() -> {
            VentanaLogin ventanaLogin = new VentanaLogin();
            ventanaLogin.setVisible(true);
        });
    }
}
