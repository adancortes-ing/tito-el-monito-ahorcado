package com.titomonito.vista;

import com.titomonito.control.Recursos;
import com.titomonito.modelo.GlobalConfig;

import javax.swing.*;
import java.awt.*;

public class PanelInferior extends JPanel {

    public PanelInferior() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        setBackground(GlobalConfig.COLOR_VERDE);
        //setPreferredSize(new Dimension(1080, 30));

        Font footerFont = Recursos.cargarFuente("tahoma.ttf", 12.0f);

        JLabel lblVersion = new JLabel();
        lblVersion.setFont(footerFont);
        lblVersion.setForeground(GlobalConfig.COLOR_GRIS_OSCURO);
        lblVersion.setText("<html>Tito el Monito Ahorcado - <b>" + GlobalConfig.VERSION_JUEGO + "</b></html>");
        add(lblVersion);
        add(Box.createHorizontalGlue());

        JLabel lblDesarrollador = new JLabel();
        lblDesarrollador.setFont(footerFont);
        lblDesarrollador.setForeground(GlobalConfig.COLOR_GRIS_OSCURO);
        lblDesarrollador.setText("Developed by: Corlogic - Copyright © 2026");
        add(lblDesarrollador);
    }
}
