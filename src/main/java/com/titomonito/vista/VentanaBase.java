package com.titomonito.vista;

import com.titomonito.control.Recursos;
import com.titomonito.modelo.GlobalConfig;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class VentanaBase extends JFrame {

    public VentanaBase() {
        //Propiedades de la ventana principal
        setTitle("Tito el Monito Ahorcado");
        setIconImage(Objects.requireNonNull(Recursos.cargarImagenUI("icono.png")).getImage());
        setSize(GlobalConfig.MEDIDA_VENTANA);
        setLayout(new BorderLayout());
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Integración de paneles a las diferentes zonas
        PanelHeader pnlHeader = new PanelHeader();
        add(pnlHeader, BorderLayout.NORTH);

        PanelMenu pnlMenu = new PanelMenu();
        add(pnlMenu, BorderLayout.WEST);

        CardLayout cardLayout = new CardLayout();
        JPanel pnlContenedor = new JPanel(cardLayout);
        add(pnlContenedor, BorderLayout.CENTER);

        pnlContenedor.add(new VistaInicio(), "Inicio");

        PanelInferior pnlFooter = new PanelInferior();
        add(pnlFooter, BorderLayout.SOUTH);
    }
}
