package com.titomonito.vista;

import com.titomonito.control.Recursos;

import javax.swing.*;
import java.awt.*;

public class PanelHeader extends JPanel {

    public PanelHeader() {

        setLayout(null);
        setPreferredSize(new Dimension(1080, 120));
        JLabel imagenFondo = new JLabel();
        imagenFondo.setIcon(Recursos.cargarImagenUI("bg_header.png"));
        imagenFondo.setBounds(0, 0, 1080, 120);
        add(imagenFondo);

    }
}
