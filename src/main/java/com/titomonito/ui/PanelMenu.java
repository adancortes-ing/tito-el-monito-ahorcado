package com.titomonito.ui;

import com.titomonito.control.NavegacionInterna;
import com.titomonito.modelo.*;

import javax.swing.*;
import java.awt.*;

public class PanelMenu extends JPanel {

    private JButton btnInicio;
    private JButton btnEstadisticas;
    private JButton btnLogros;
    private JButton btnAyuda;
    private JButton btnOpciones;
    private JButton btnAcerca;
    private JButton btnSalir;

    public PanelMenu() {

        //Propiedades del panel
        //==============================================================================================================
        setBackground(GlobalConfig.COLOR_AZUL);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
        setPreferredSize(new Dimension(220, 1000));

        initComponentes();
        asignarControles();
    }

    private void initComponentes() {

        btnInicio = crearBoton("INICIO", "menu_inicio.png");
        btnEstadisticas = crearBoton("ESTADÍSTICAS", "menu_estadisticas.png");
        btnLogros = crearBoton("LOGROS", "menu_logros.png");
        btnAyuda = crearBoton("AYUDA", "menu_ayuda.png");
        btnOpciones = crearBoton("OPCIONES", "menu_config.png");
        add(Box.createVerticalGlue());

        btnAcerca = crearBoton("ACERCA DE", "menu_acerca.png");

        btnSalir = crearBoton("SALIR", "menu_exit.png");
        btnSalir.setBackground(GlobalConfig.COLOR_SALMON);
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusable(false);
    }

    private void asignarControles() {

        NavegacionInterna.ManejarMenu manejarMenu = new NavegacionInterna.ManejarMenu();

        btnInicio.addActionListener(manejarMenu);
        btnAyuda.addActionListener(manejarMenu);
        btnEstadisticas.addActionListener(manejarMenu);
        btnAcerca.addActionListener(manejarMenu);
        btnOpciones.addActionListener(manejarMenu);
        btnLogros.addActionListener(manejarMenu);
        btnSalir.addActionListener(manejarMenu);
    }

    private JButton crearBoton(String etiqueta, String icono) {

        final Dimension sizeBotones = new Dimension(200, 50);
        final int separacionVertical = 15;

        JButton boton = new JButton(etiqueta);
        boton.setPreferredSize(sizeBotones);
        boton.setMaximumSize(sizeBotones);
        boton.setMinimumSize(sizeBotones);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFont(Recursos.Fuentes.fuenteComic(Font.BOLD, 13));

        boton.setMargin(new Insets(0, 10, 0, 5));
        boton.setIcon(Recursos.cargarImagenUI(icono));
        boton.setHorizontalTextPosition(SwingConstants.RIGHT);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setIconTextGap(10);

        add(boton);
        add(Box.createVerticalStrut(separacionVertical));

        return boton;
    }

}