package com.titomonito.vista;

import com.titomonito.control.*;
import com.titomonito.modelo.GlobalConfig;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class VentanaBase extends JFrame {

    public static CardLayout vistas;
    public static JPanel contenedor;

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

        //Integración de paneles a las diferentes zonas
        PanelHeader pnlHeader = new PanelHeader();
        add(pnlHeader, BorderLayout.NORTH);

        PanelMenu pnlMenu = new PanelMenu();
        add(pnlMenu, BorderLayout.WEST);

        vistas = new CardLayout();
        contenedor = new JPanel(vistas);
        add(contenedor, BorderLayout.CENTER);

        contenedor.add(new VistaInicio(), "INICIO");

        PanelInferior pnlFooter = new PanelInferior();
        add(pnlFooter, BorderLayout.SOUTH);
    }

}
