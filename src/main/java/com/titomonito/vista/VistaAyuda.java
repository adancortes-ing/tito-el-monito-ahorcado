package com.titomonito.vista;

import com.titomonito.modelo.Recursos;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class VistaAyuda extends JPanel{

    public VistaAyuda() {

        add(new JLabel("AYUDA"));
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Image fondo = Objects.requireNonNull(Recursos.cargarImagenUI("bg_center.png")).getImage();

        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}
