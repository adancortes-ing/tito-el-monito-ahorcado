package com.titomonito.ui.vistas;

import com.titomonito.modelo.Recursos;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class EstadisticasPanel extends JPanel {

    public EstadisticasPanel() {

        add(new JLabel("ESTADÍSTICAS"));
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Image fondo = Objects.requireNonNull(Recursos.cargarImagenUI("bg_center.png")).getImage();

        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}
