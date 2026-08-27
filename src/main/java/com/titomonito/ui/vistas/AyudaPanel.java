package com.titomonito.ui.vistas;

import com.titomonito.utils.Recursos;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AyudaPanel extends JPanel{

    public AyudaPanel() {

        setLayout(new BorderLayout());
        JLabel titulo = new JLabel("AYUDA");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);
        JLabel etiqueta = new JLabel("[ PLACE HOLDER ]");
        etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
        etiqueta.setFont(etiqueta.getFont().deriveFont(Font.BOLD, 28.0f));
        add(etiqueta, BorderLayout.CENTER);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Image fondo = Objects.requireNonNull(Recursos.cargarImagen("bg_center.png")).getImage();

        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}
