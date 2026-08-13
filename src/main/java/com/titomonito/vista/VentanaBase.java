package com.titomonito.vista;

import com.titomonito.modelo.GlobalConfig;
import com.titomonito.modelo.Recursos;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class VentanaBase extends JFrame {

    private CardLayout vistas;
    private JPanel contenedor;
    private PanelHeader pnlHeader;

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

        initComponentes();
    }

    private void initComponentes() {

        pnlHeader = new PanelHeader();
        add(pnlHeader, BorderLayout.NORTH);

        PanelMenu pnlMenu = new PanelMenu();
        add(pnlMenu, BorderLayout.WEST);

        vistas = new CardLayout();
        contenedor = new JPanel(vistas);
        add(contenedor, BorderLayout.CENTER);

        VistaInicio vistaInicio = new VistaInicio();
        contenedor.add(vistaInicio, "INICIO");
        contenedor.add(new VistaCategorias(), "PLAY");

        PanelInferior pnlFooter = new PanelInferior();
        add(pnlFooter, BorderLayout.SOUTH);
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
