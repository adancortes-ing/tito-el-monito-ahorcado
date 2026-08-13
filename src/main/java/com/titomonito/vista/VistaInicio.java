package com.titomonito.vista;

import com.titomonito.control.NavegacionInterna;
import com.titomonito.modelo.Recursos;
import com.titomonito.modelo.GlobalConfig;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class VistaInicio extends JPanel {

    public VistaInicio() {

        // Propiedades del panel =======================================================================================
        setLayout(new FlowLayout(FlowLayout.LEFT, 60, 0));

        initComponentes();
    }

    private void initComponentes() {

        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BorderLayout());
        contenedor.setOpaque(false);
        contenedor.setPreferredSize(new Dimension(780, 220));
        add(contenedor);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JLabel lblBienvenida = new JLabel("¡ Este es Tito, el monito ahorcado !");
        lblBienvenida.setFont(lblBienvenida.getFont().deriveFont(Font.BOLD, 24f));
        lblBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUltimaPalabra = new JLabel("Última palabra descubierta");
        lblUltimaPalabra.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblValUltimaPalabra = new JLabel("[ palabra ]");
        lblValUltimaPalabra.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnIniciarJuego = getBtnIniciarJuego();
        btnIniciarJuego.addActionListener(NavegacionInterna.ManejarVistas.INSTANCIA);

        center.add(lblBienvenida);
        center.add(Box.createVerticalStrut(20));
        center.add(lblUltimaPalabra);
        center.add(lblValUltimaPalabra);
        center.add(Box.createVerticalGlue());
        center.add(btnIniciarJuego);

        contenedor.add(center, BorderLayout.CENTER);
    }

    private static JButton getBtnIniciarJuego() {

        Dimension medidaBoton = new Dimension(250, 70);
        JButton btnIniciarJuego = new JButton("Iniciar Juego");
        btnIniciarJuego.setName("PLAY");
        btnIniciarJuego.setFont(Recursos.Fuentes.fuenteComic(Font.BOLD, 22));
        btnIniciarJuego.setMaximumSize(medidaBoton);
        btnIniciarJuego.setPreferredSize(medidaBoton);
        btnIniciarJuego.setBackground(GlobalConfig.COLOR_VERDE);
        btnIniciarJuego.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIniciarJuego.setFocusable(false);
        return btnIniciarJuego;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Image fondo = Objects.requireNonNull(Recursos.cargarImagenUI("bg_inicio.png")).getImage();

        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }

}
