package com.titomonito.ui;

import com.titomonito.modelo.GlobalConfig;
import com.titomonito.modelo.Recursos;
import com.titomonito.ui.vistas.PanelHeader;
import com.titomonito.ui.vistas.PreGamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class VentanaBase extends JFrame {

    private CardLayout vistas;
    private JPanel contenedor;
    public PanelHeader pnlHeader;

    public VentanaBase() {

        //Propiedades de la ventana principal
        //==============================================================================================================
        setTitle("Tito el Monito Ahorcado");
        setIconImage(Objects.requireNonNull(Recursos.cargarImagenUI("icono.png")).getImage());
        setSize(GlobalConfig.MEDIDA_VENTANA);
        setLayout(new BorderLayout());
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {

        pnlHeader = new PanelHeader();
        add(pnlHeader, BorderLayout.NORTH);

        PanelMenu pnlMenu = new PanelMenu();
        add(pnlMenu, BorderLayout.WEST);

        vistas = new CardLayout();
        contenedor = new JPanel(vistas);
        add(contenedor, BorderLayout.CENTER);

        InicioPanel inicio = new InicioPanel();
        contenedor.add(inicio, "INICIO");
        contenedor.add(new PreGamePanel(), "PLAY");

        setPanelInferior();
    }

    private void setPanelInferior() {

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.X_AXIS));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        panelInferior.setBackground(GlobalConfig.COLOR_VERDE);

        Font footerFont = Recursos.cargarFuente("tahoma.ttf", 12.0f);

        JLabel lblVersion = new JLabel();
        lblVersion.setFont(footerFont);
        lblVersion.setForeground(GlobalConfig.COLOR_GRIS_OSCURO);
        lblVersion.setText("<html>Tito el Monito Ahorcado - <b>" + GlobalConfig.VERSION_JUEGO + "</b></html>");
        panelInferior.add(lblVersion);
        panelInferior.add(Box.createHorizontalGlue());

        JLabel lblDesarrollador = new JLabel();
        lblDesarrollador.setFont(footerFont);
        lblDesarrollador.setForeground(GlobalConfig.COLOR_GRIS_OSCURO);
        lblDesarrollador.setText("Developed by: CorLogic - Copyright © 2026");
        panelInferior.add(lblDesarrollador);
        add(panelInferior, BorderLayout.SOUTH);
    }

    public CardLayout getVistas() {
        return vistas;
    }

    public JPanel getContenedor() {
        return contenedor;
    }

    public final PanelHeader getPnlHeader() {
        return pnlHeader;
    }

}
