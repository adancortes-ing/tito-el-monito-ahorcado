package com.titomonito.vista;

import com.titomonito.control.Recursos;
import com.titomonito.modelo.GlobalConfig;

import javax.swing.*;
import java.awt.*;

public class VistaInicio extends JPanel {

    public VistaInicio() {

        setLayout(new FlowLayout(FlowLayout.LEFT, 60, 0));

        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BorderLayout());
        contenedor.setOpaque(false);
        contenedor.setBorder(BorderFactory.createLineBorder(GlobalConfig.COLOR_VERDE)); //al rato la quitamos
        contenedor.setPreferredSize(new Dimension(780, 220));
        add(contenedor);

        contenedor.add(crearCentro(), BorderLayout.CENTER);

    }

    private JPanel crearCentro() {

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JLabel lblBienvenida = new JLabel("¡Bienvenido a Tito el Monito Ahorcado!");
        lblBienvenida.setFont(lblBienvenida.getFont().deriveFont(Font.BOLD, 24f));
        lblBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUltimaPalabra = new JLabel("Última palabra descubierta");
        lblUltimaPalabra.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblValUltimapalabra = new JLabel("[ palabra ]");
        lblValUltimapalabra.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension medidaBoton = new Dimension(250, 70);
        JButton btnIniciarJuego = new JButton("Iniciar Juego");
        btnIniciarJuego.setFont(Recursos.Fuentes.fuenteComic(Font.BOLD, 22));
        btnIniciarJuego.setMaximumSize(medidaBoton);
        btnIniciarJuego.setPreferredSize(medidaBoton);
        btnIniciarJuego.setBackground(GlobalConfig.COLOR_VERDE);
        btnIniciarJuego.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIniciarJuego.setFocusable(false);

        center.add(lblBienvenida);
        center.add(Box.createVerticalStrut(20));
        center.add(lblUltimaPalabra);
        center.add(lblValUltimapalabra);
        center.add(Box.createVerticalGlue());
        center.add(btnIniciarJuego);
        return center;

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Image fondo = Recursos.cargarImagenUI("bg_inicio.png").getImage();
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);

        }

    }

}
