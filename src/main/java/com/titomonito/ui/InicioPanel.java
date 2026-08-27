package com.titomonito.ui;

import com.titomonito.config.Constantes;
import com.titomonito.utils.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class InicioPanel extends JPanel {

    private JButton btnIniciarJuego;

    public InicioPanel() {

        setLayout(new FlowLayout(FlowLayout.LEFT, 75, 0));
        initComponentes();
    }

    private void initComponentes() {

        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BorderLayout());
        contenedor.setOpaque(false);
        contenedor.setPreferredSize(new Dimension(750, 220));
        contenedor.setMaximumSize(contenedor.getPreferredSize());

        JLabel lblBienvenida = new JLabel("¡ Este es Tito, el monito ahorcado !");
        lblBienvenida.setFont(lblBienvenida.getFont().deriveFont(Font.BOLD));
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        contenedor.add(lblBienvenida, BorderLayout.NORTH);

        // Panel central ===============================================================================================
        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JLabel lblUltimaPalabra = new JLabel("Última palabra descubierta");
        lblUltimaPalabra.setFont(lblUltimaPalabra.getFont().deriveFont(20.0f));
        lblUltimaPalabra.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblValUltimaPalabra = new JLabel("[ palabra ]");
        lblValUltimaPalabra.setFont(lblValUltimaPalabra.getFont().deriveFont(Font.BOLD));
        lblValUltimaPalabra.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnIniciarJuego = crearBtnIniciarJuego();

        center.add(Box.createVerticalStrut(20));
        center.add(lblUltimaPalabra);
        center.add(lblValUltimaPalabra);
        center.add(Box.createVerticalGlue());
        center.add(btnIniciarJuego);

        // Panel lateral izquierdo =====================================================================================
        JPanel west = new JPanel();
        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
        west.setOpaque(false);

        JLabel lblRacha = new JLabel("Racha de Victorias");
        lblRacha.setFont(lblRacha.getFont().deriveFont(18.0f));
        lblRacha.setIcon(Recursos.cargarImagen("flama.png"));
        lblRacha.setHorizontalTextPosition(SwingConstants.CENTER);
        lblRacha.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblRacha.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblValorRacha = new JLabel("[ 00 ]");
        lblValorRacha.setFont(lblValorRacha.getFont().deriveFont(Font.BOLD, 30.0f));
        lblValorRacha.setAlignmentX(Component.CENTER_ALIGNMENT);

        west.add(lblRacha);
        west.add(lblValorRacha);

        // Panel lateral derecho =====================================================================================
        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        east.setOpaque(false);

        JLabel lblTopMonedas = new JLabel("Record de Monedas");
        lblTopMonedas.setFont(lblTopMonedas.getFont().deriveFont(18.0f));
        lblTopMonedas.setIcon(Recursos.cargarImagen("money.png"));
        lblTopMonedas.setHorizontalTextPosition(SwingConstants.CENTER);
        lblTopMonedas.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblTopMonedas.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblValorTopMonedas = new JLabel("[ 000 ]");
        lblValorTopMonedas.setFont(lblValorRacha.getFont().deriveFont(Font.BOLD, 30.0f));
        lblValorTopMonedas.setAlignmentX(Component.CENTER_ALIGNMENT);

        east.add(lblTopMonedas);
        east.add(lblValorTopMonedas);

        contenedor.add(center, BorderLayout.CENTER);
        contenedor.add(east, BorderLayout.EAST);
        contenedor.add(west, BorderLayout.WEST);
        add(contenedor);
    }

    public void addIniciarListener(ActionListener l) {
        btnIniciarJuego.addActionListener(l);
    }

    private static JButton crearBtnIniciarJuego() {

        Dimension medidaBoton = new Dimension(250, 70);
        JButton btnIniciarJuego = new JButton(Constantes.PREGAME);
        btnIniciarJuego.setFont(Recursos.Fuentes.fuenteComic(Font.BOLD, 22));
        btnIniciarJuego.setMaximumSize(medidaBoton);
        btnIniciarJuego.setPreferredSize(medidaBoton);
        btnIniciarJuego.setBackground(Constantes.COLOR_VERDE);
        btnIniciarJuego.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIniciarJuego.setFocusable(false);
        btnIniciarJuego.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btnIniciarJuego;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Image fondo = Objects.requireNonNull(Recursos.cargarImagen("bg_inicio.png")).getImage();

        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }

}
