package com.titomonito;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTGitHubIJTheme;
import com.titomonito.config.ConfigDB;
import com.titomonito.config.GlobalConfig;
import com.titomonito.dao.generalDAO;
import com.titomonito.ui.VentanaLogin;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Configuración del tema
        FlatMTGitHubIJTheme.setup();

        // Configuración Global
        GlobalConfig.CargarConfig();
        GlobalConfig.configurarLoggers();
        ConfigDB.initDB();
        generalDAO.obtenerCategorias();

        SwingUtilities.invokeLater(() -> {
            VentanaLogin ventanaLogin = new VentanaLogin();
            ventanaLogin.setVisible(true);
        });
    }
}
