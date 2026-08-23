package com.titomonito.ui.vistas;

import com.titomonito.modelo.*;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PanelHeader extends JPanel {

    public PanelHeader() {

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(33, 270, 5, 15));
        setPreferredSize(new Dimension(1080, 120));

        initComponentes();
    }

    private void initComponentes() {

        JLabel lblIconoJugador = new JLabel("Jugador: ");
        lblIconoJugador.setFont(lblIconoJugador.getFont().deriveFont(Font.BOLD));
        lblIconoJugador.setAlignmentY(Component.TOP_ALIGNMENT);
        lblIconoJugador.setIcon(Recursos.cargarImagenUI("header_player.png"));
        lblIconoJugador.setVerticalTextPosition(SwingConstants.TOP);
        add(lblIconoJugador);

        JLabel lblValJugador = new JLabel("[Nombre del Jugador]");
        lblValJugador.setAlignmentY(Component.TOP_ALIGNMENT);
        lblValJugador.setMaximumSize(new Dimension(250, 35));
        lblValJugador.setPreferredSize(new Dimension(250, 35));
        add(lblValJugador);

        add(Box.createHorizontalStrut(50));

        JLabel lblIconoMonedas = new JLabel("Monedas: ");
        lblIconoMonedas.setFont(lblIconoMonedas.getFont().deriveFont(Font.BOLD));
        lblIconoMonedas.setAlignmentY(Component.TOP_ALIGNMENT);
        lblIconoMonedas.setIcon(Recursos.cargarImagenUI("header_monedas.png"));
        lblIconoMonedas.setVerticalTextPosition(SwingConstants.TOP);
        add(lblIconoMonedas);

        JLabel lblValMonedas = new JLabel("[$ 0000]");
        lblValMonedas.setAlignmentY(Component.TOP_ALIGNMENT);
        add(lblValMonedas);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image fondo = Objects.requireNonNull(Recursos.cargarImagenUI("bg_header.png")).getImage();

        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);

    }
}