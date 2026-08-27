package com.titomonito.ui;

import com.titomonito.config.Constantes;
import com.titomonito.utils.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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
        setBackground(Constantes.COLOR_AZUL);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
        setPreferredSize(new Dimension(220, 1000));

        initComponentes();
    }

    public void addInicioListener(ActionListener l) {
        btnInicio.addActionListener(l);
    }

    public void addEstadisticasListener(ActionListener l) {
        btnEstadisticas.addActionListener(l);
    }

    public void addLogrosListener(ActionListener l) {
        btnLogros.addActionListener(l);
    }

    public void addAyudaListener(ActionListener l) {
        btnAyuda.addActionListener(l);
    }

    public void addOpcionesListener(ActionListener l) { btnOpciones.addActionListener(l); }

    public void addSalirListener(ActionListener l) {
        btnSalir.addActionListener(l);
    }

    public void addAcercaListener(ActionListener l) {
        btnAcerca.addActionListener(l);
    }

    private void initComponentes() {

        btnInicio = crearBoton(Constantes.INICIO, "menu_inicio.png");
        btnEstadisticas = crearBoton(Constantes.ESTADISTICAS, "menu_estadisticas.png");
        btnLogros = crearBoton(Constantes.LOGROS, "menu_logros.png");
        btnAyuda = crearBoton(Constantes.AYUDA, "menu_ayuda.png");
        btnOpciones = crearBoton(Constantes.OPCIONES, "menu_config.png");
        add(Box.createVerticalGlue());

        btnAcerca = crearBoton(Constantes.ACERCA_DE, "menu_about.png");
        btnSalir = crearBoton(Constantes.SALIR, "menu_exit.png");
        btnSalir.setBackground(Constantes.COLOR_SALMON);
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusable(false);
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
        boton.setIcon(Recursos.cargarImagen(icono));
        boton.setHorizontalTextPosition(SwingConstants.RIGHT);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setIconTextGap(10);

        add(boton);
        add(Box.createVerticalStrut(separacionVertical));

        return boton;
    }

}