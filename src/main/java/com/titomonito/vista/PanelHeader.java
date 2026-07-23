package com.titomonito.vista;

import com.titomonito.control.Recursos;

import javax.swing.*;
import java.awt.*;

public class PanelHeader extends JPanel {

    private final Image fondo;

    public PanelHeader() {

        setLayout(null);
        setPreferredSize(new Dimension(1080, 120));

        fondo = Recursos.cargarImagenUI("bg_header.png").getImage();

        JLabel lblIconoJugador = new JLabel("Jugador: ");
        lblIconoJugador.setIcon(Recursos.cargarImagenUI("header_player.png"));
        lblIconoJugador.setBounds(290, 33, 160, 60);
        lblIconoJugador.setVerticalTextPosition(SwingConstants.TOP);
        add(lblIconoJugador);

        JLabel lblIconoMonedas = new JLabel("Monedas: ");
        lblIconoMonedas.setIcon(Recursos.cargarImagenUI("header_monedas.png"));
        lblIconoMonedas.setBounds(750, 33, 165, 60);
        lblIconoMonedas.setVerticalTextPosition(SwingConstants.TOP);
        add(lblIconoMonedas);

        //<editor-fold defaultstate="collapsed" desc="Posiblemente, estas etiquetas deban ser declaradas en el ámbito de clase">
        JLabel lblJugador = new JLabel("[ nombre ]");
        lblJugador.setBounds(450, 33, 200, 35);
        lblJugador.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblJugador.setFont(lblJugador.getFont().deriveFont(Font.BOLD));
        add(lblJugador);

        JLabel lblMonedas = new JLabel("[ 9999 ]");
        lblMonedas.setBounds(915, 33, 100, 35);
        lblMonedas.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblMonedas.setFont(lblMonedas.getFont().deriveFont(Font.BOLD));
        add(lblMonedas);
        //</editor-fold>
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
