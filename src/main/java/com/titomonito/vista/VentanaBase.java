package com.titomonito.vista;

import com.titomonito.control.Recursos;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class VentanaBase extends JFrame {
    public VentanaBase() {
        //Propiedades de la ventana principal
        setTitle("Tito el Monito Ahorcado");
        setIconImage(Objects.requireNonNull(Recursos.cargarImagenUI("icono.png")).getImage());
        setSize(1080, 750);
        setLayout(new BorderLayout());
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Menu Principal
        PanelMenu pnlMenu = new PanelMenu();
        add(pnlMenu, BorderLayout.WEST);

        PanelHeader pnlHeader = new PanelHeader();
        add(pnlHeader, BorderLayout.NORTH);

        //Panel inferior -- Barra de estado
        PanelInferior pnlFooter = new PanelInferior();
        add(pnlFooter, BorderLayout.SOUTH);
    }
}

class PanelInferior extends JPanel {
    public PanelInferior() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        setBackground(new Color(178, 242, 109));
        setPreferredSize(new Dimension(1080, 30));

        Color grisDark = new Color(80, 80, 80);
        Font footerFont = Recursos.cargarFuente("tahoma.ttf", 12.0f);

        JLabel lblVersion = new JLabel();
        lblVersion.setFont(footerFont);
        lblVersion.setForeground(grisDark);
        lblVersion.setText("<html>Tito el Monito Ahorcado - <b>Version 0.1</b></html>");
        add(lblVersion,  BorderLayout.WEST);

        JLabel lblDesarrollador = new JLabel();
        lblDesarrollador.setFont(footerFont);
        lblDesarrollador.setForeground(grisDark);
        lblDesarrollador.setText("Developed by: Corlogic - Copyright © 2026");
        add(lblDesarrollador, BorderLayout.EAST);
    }
}
