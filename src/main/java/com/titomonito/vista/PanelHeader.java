package com.titomonito.vista;

import com.titomonito.control.Recursos;

import javax.swing.*;
import java.awt.*;

public class PanelHeader extends JPanel {

    private final Image fondo;

    public PanelHeader() {

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createEmptyBorder(33, 290, 5, 15));
        setPreferredSize(new Dimension(1080, 120));

        fondo = Recursos.cargarImagenUI("bg_header.png").getImage();

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
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
