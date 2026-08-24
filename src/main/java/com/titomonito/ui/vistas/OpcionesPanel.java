package com.titomonito.ui.vistas;

import com.titomonito.utils.Recursos;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class OpcionesPanel extends JPanel {

    public OpcionesPanel() {

        add(new JLabel("OPCIONES"));
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Image fondo = Objects.requireNonNull(Recursos.cargarImagenUI("bg_center.png")).getImage();

        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}
