package com.titomonito.vista;

import com.titomonito.control.Recursos;

import javax.swing.*;
import java.awt.*;

public class PanelMenu extends JPanel {

    private final JButton btnInicio;
    private final JButton btnEstadisticas;
    private final JButton btnLogros;
    private final JButton btnAyuda;
    private final JButton btnOpciones;

    private final Dimension sizeBotones = new Dimension(200, 50);

    public PanelMenu() {

        setBackground(new Color(108, 222, 242));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
        setPreferredSize(new Dimension(220, 1000));

        btnInicio = crearBoton("INICIO", "menu_inicio.png");
        btnEstadisticas = crearBoton("ESTADÍSTICAS", "menu_estadisticas.png");
        btnLogros = crearBoton("LOGROS", "menu_logros.png");
        btnAyuda = crearBoton("AYUDA", "menu_ayuda.png");

        add(Box.createVerticalStrut(160));

        btnOpciones = crearBoton("OPCIONES", "menu_config.png");
        JButton btnSalir = crearBoton("SALIR", "menu_exit.png");
        btnSalir.setBackground(new Color(242, 129, 109));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.addActionListener(e -> {
            System.exit(0);
        });

        //Botones deshabilitados temporalmente hasta que funcionen correctamente
        btnEstadisticas.setEnabled(false);
        btnLogros.setEnabled(false);
        btnAyuda.setEnabled(false);
        btnOpciones.setEnabled(false);

    }

    private JButton crearBoton(String etiqueta, String icono) {
        final int separacionVertical = 15;

        JButton boton = new JButton(etiqueta);
        boton.setPreferredSize(sizeBotones);
        boton.setMaximumSize(sizeBotones);
        boton.setMinimumSize(sizeBotones);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFont(new Font("Comic Sans MS", Font.BOLD, 13));

        boton.setMargin(new Insets(0, 10, 0, 10));
        boton.setIcon(Recursos.cargarImagenUI(icono));
        boton.setHorizontalTextPosition(SwingConstants.RIGHT);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setIconTextGap(15);

        add(boton);
        add(Box.createVerticalStrut(separacionVertical));

        return boton;
    }
}