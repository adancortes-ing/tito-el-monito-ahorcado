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
        setSize(1080, 720);
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
    }
}
