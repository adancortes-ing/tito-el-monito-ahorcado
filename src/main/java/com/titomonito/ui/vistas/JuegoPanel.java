package com.titomonito.ui.vistas;

import com.titomonito.utils.Recursos;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class JuegoPanel extends JPanel {

    private JLabel lblValTiempo;
    private JLabel lblValVidas;

    public JuegoPanel() {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 60, 10, 20));
        initUI();
    }

    private void initUI() {

        // Panel Superior (tiempo restante y numero de vidas) ==========================================================
        JPanel pnlSuperior = new JPanel();
        pnlSuperior.setLayout(new BoxLayout(pnlSuperior, BoxLayout.X_AXIS));
        pnlSuperior.setOpaque(false);
        pnlSuperior.setPreferredSize(new Dimension(0, 50));

        JLabel lblTiempoRestante = new JLabel("Tiempo de restante");
        lblTiempoRestante.setIcon(Recursos.cargarImagen("reloj.png"));
        lblTiempoRestante.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblTiempoRestante.setAlignmentY(Component.CENTER_ALIGNMENT);

        lblValTiempo = new JLabel("00");
        lblValTiempo.setFont(lblValTiempo.getFont().deriveFont(36.0f));
        lblValTiempo.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel lblVidasRestantes = new JLabel("Salud de Tito: ");
        lblVidasRestantes.setAlignmentY(Component.CENTER_ALIGNMENT);

        lblValVidas = new JLabel("♥ ♥ ♥ ♥ ♥ ♥");
        lblValVidas.setAlignmentY(Component.CENTER_ALIGNMENT);
        lblValVidas.setFont(new Font("Segoe UI", Font.PLAIN, 24));

        pnlSuperior.add(lblTiempoRestante);
        pnlSuperior.add(Box.createHorizontalStrut(5));
        pnlSuperior.add(lblValTiempo);
        pnlSuperior.add(Box.createHorizontalStrut(180));
        pnlSuperior.add(lblVidasRestantes);
        pnlSuperior.add(Box.createHorizontalStrut(10));
        pnlSuperior.add(lblValVidas);

        // Panel central (izq la palabra oculta y la pista debajo; derecha la imagen de tito) ==========================
        JPanel pnlPrincipal = new JPanel();
        pnlPrincipal.setLayout(new BorderLayout());

        // Panel de la izquierda para la palabra y la pista-------------------------------------------------------------
        JPanel pnlIzquierda = new JPanel();
        pnlIzquierda.setBackground(Color.GREEN);
        pnlIzquierda.setPreferredSize(new Dimension(500, 0));

        // Panel para el teclado ---------------------------------------------------------------------------------------
        JPanel pnlTeclado = new JPanel();
        pnlTeclado.setBackground(Color.RED);
        pnlTeclado.setPreferredSize(new Dimension(0, 180));

        pnlPrincipal.add(pnlIzquierda, BorderLayout.WEST);
        pnlPrincipal.add(pnlTeclado, BorderLayout.SOUTH);

        // Panel inferior con los botones de powerups ==================================================================
        JPanel pnlInferior = new JPanel();
        pnlInferior.setLayout(new BoxLayout(pnlInferior, BoxLayout.X_AXIS));
        pnlInferior.setBackground(Color.BLUE);
        pnlInferior.setPreferredSize(new Dimension(0, 70));

        add(pnlSuperior, BorderLayout.NORTH);
        add(pnlPrincipal, BorderLayout.CENTER);
        add(pnlInferior, BorderLayout.SOUTH);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Image fondo = Objects.requireNonNull(Recursos.cargarImagen("bg_center.png")).getImage();
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }

}
