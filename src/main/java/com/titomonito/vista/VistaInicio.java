package com.titomonito.vista;

import com.titomonito.control.Recursos;
import com.titomonito.modelo.GlobalConfig;

import javax.swing.*;
import java.awt.*;

public class VistaInicio extends JPanel {

    public VistaInicio() {

        // Configuración del panel de inicio
        setLayout(null);
        setBounds(0, 0, 860, 600);

        JLabel lblBienvenida = new JLabel("¡Bienvenido a Tito el Monito Ahorcado!");
        lblBienvenida.setBounds(230, 1, 500, 50);
        lblBienvenida.setFont(lblBienvenida.getFont().deriveFont(24f));
        add(lblBienvenida);

        JButton btnIniciarJuego = new JButton("Iniciar Juego");
        btnIniciarJuego.setBounds(350, 100, 200, 50);
        btnIniciarJuego.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnIniciarJuego.setBackground(GlobalConfig.COLOR_VERDE);
        add(btnIniciarJuego);

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
