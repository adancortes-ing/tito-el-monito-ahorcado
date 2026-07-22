package com.titomonito.vista;

import javax.swing.*;
import java.awt.*;

public class PanelMenu extends JPanel {

    private final JButton btnInicio;
    private final JButton btnEstadisticas;
    private final JButton btnLogros;
    private final JButton btnAyuda;
    private final JButton btnOpciones;
    private final JButton btnSalir;

    public PanelMenu() {

        setBackground(new Color(108, 222, 242));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
        setPreferredSize(new Dimension(220, 1000));

        Dimension tamanioBotones = new Dimension(200, 45);
        int separacionVertical = 15;

        btnInicio = new JButton("Inicio");
        btnInicio.setMaximumSize(tamanioBotones);
        btnInicio.setAlignmentX(CENTER_ALIGNMENT);
        add(btnInicio);
        add(Box.createVerticalStrut(separacionVertical));

        btnEstadisticas = new JButton("Estadísticas");
        btnEstadisticas.setMaximumSize(tamanioBotones);
        btnEstadisticas.setAlignmentX(CENTER_ALIGNMENT);
        btnEstadisticas.setEnabled(false);
        add(btnEstadisticas);
        add(Box.createVerticalStrut(separacionVertical));

        btnLogros = new JButton("Logros");
        btnLogros.setMaximumSize(tamanioBotones);
        btnLogros.setAlignmentX(CENTER_ALIGNMENT);
        btnLogros.setEnabled(false);
        add(btnLogros);
        add(Box.createVerticalStrut(separacionVertical));

        btnAyuda = new JButton("Ayuda");
        btnAyuda.setMaximumSize(tamanioBotones);
        btnAyuda.setAlignmentX(CENTER_ALIGNMENT);
        btnAyuda.setEnabled(false);
        add(btnAyuda);
        add(Box.createVerticalStrut(180));

        btnOpciones = new JButton("Opciones");
        btnOpciones.setMaximumSize(tamanioBotones);
        btnOpciones.setAlignmentX(CENTER_ALIGNMENT);
        btnOpciones.setEnabled(false);
        add(btnOpciones);
        add(Box.createVerticalStrut(separacionVertical));

        btnSalir = new JButton("Salir");
        btnSalir.setMaximumSize(tamanioBotones);
        btnSalir.setAlignmentX(CENTER_ALIGNMENT);
        btnSalir.addActionListener(e -> {System.exit(0);});
        add(btnSalir);

    }
}