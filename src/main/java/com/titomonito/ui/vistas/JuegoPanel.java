package com.titomonito.ui.vistas;

import com.titomonito.utils.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import static com.titomonito.config.Constantes.*;

public class JuegoPanel extends JPanel {

    private JLabel lblValTiempo;
    private JLabel lblValVidas;
    private JLabel lblIgmTito;
    private JLabel lblPalabra;
    private JLabel lblValPista;
    private JLabel lblValCategoria;

    private List<JButton> teclas;
    private JButton btnSacapuntas, btnTijeras, btnGoma, btnPluma, btnMarcatextos;

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
        lblTiempoRestante.setIcon(Recursos.cargarImagen("reloj.gif"));
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
        pnlPrincipal.setOpaque(false);
        pnlPrincipal.setLayout(new BorderLayout());

        // Panel de la izquierda para la palabra y la pista-------------------------------------------------------------
        JPanel pnlIzquierda = new JPanel();
        pnlIzquierda.setLayout(new BoxLayout(pnlIzquierda, BoxLayout.Y_AXIS));
        pnlIzquierda.setOpaque(false);
        pnlIzquierda.setPreferredSize(new Dimension(500, 0));

        JLabel lblInstrucciones = new JLabel("Descubre la palabra antes del trazo final, categoria:");
        lblInstrucciones.setFont(lblInstrucciones.getFont().deriveFont(18.0f));
        lblInstrucciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblValCategoria = new JLabel("[ CATEGORÍA ]");
        lblValCategoria.setFont(lblValCategoria.getFont().deriveFont(20.0f));
        lblValCategoria.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPalabra = new JLabel("[ PALABRA OCULTA ]");
        lblPalabra.setFont(lblPalabra.getFont().deriveFont(Font.BOLD, 22.0f));
        lblPalabra.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblValPista = new JLabel("Compra un marcatextos para resaltar y revelar la pista.");
        lblValPista.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblValPista.setFont(lblValPista.getFont().deriveFont(18.0f));

        pnlIzquierda.add(Box.createRigidArea(new Dimension(0, 35)));
        pnlIzquierda.add(lblInstrucciones);
        pnlIzquierda.add(Box.createVerticalStrut(10));
        pnlIzquierda.add(lblValCategoria);
        pnlIzquierda.add(Box.createVerticalStrut(45));
        pnlIzquierda.add(lblPalabra);
        pnlIzquierda.add(Box.createVerticalStrut(50));
        pnlIzquierda.add(lblValPista);

        // Panel de la derecha para la imagen de tito ------------------------------------------------------------------
        JPanel pnlDerecha = new JPanel();
        pnlDerecha.setOpaque(false);
        lblIgmTito = new JLabel();
        lblIgmTito.setIcon(Recursos.cargarImagen("game_horca.png"));
        pnlDerecha.add(lblIgmTito);

        // Panel para el teclado ---------------------------------------------------------------------------------------
        JPanel pnlTeclado = new JPanel();
        pnlTeclado.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        pnlTeclado.setLayout(new GridLayout(3, 9, 10, 5));
        pnlTeclado.setPreferredSize(new Dimension(0, 170));
        pnlTeclado.setOpaque(false);

        teclas = new ArrayList<>();
        for (char c : ALFABETO.toCharArray()) {
            crearTecla(String.valueOf(c));
        }

        for (JButton btn : teclas) {
            pnlTeclado.add(btn);
        }

        pnlPrincipal.add(pnlIzquierda, BorderLayout.WEST);
        pnlPrincipal.add(pnlDerecha, BorderLayout.EAST);
        pnlPrincipal.add(pnlTeclado, BorderLayout.SOUTH);

        // Panel inferior con los botones de powerups ==================================================================
        JPanel pnlInferior = new JPanel();
        pnlInferior.setOpaque(false);
        pnlInferior.setLayout(new BoxLayout(pnlInferior, BoxLayout.X_AXIS));
        pnlInferior.setPreferredSize(new Dimension(0, 70));

        btnSacapuntas = crearBoton("Sacapuntas", "item_sharpener.png", PRECIO_SACAPUNTAS, "Añade 10 segundos al reloj actual.");
        btnTijeras = crearBoton("Tijeras", "item_cut.png", PRECIO_TIJERAS, "Corta la cuerda y aumenta la salud de Tito en +1.");
        btnGoma = crearBoton("Goma", "item_erase.png", PRECIO_GOMA, "Elimina 4 letras incorrectas del teclado.");
        btnPluma = crearBoton("Pluma", "item_pen.png", PRECIO_PLUMA, "Revela una letra correcta al azar que aún no haya sido descubierta.");
        btnMarcatextos = crearBoton("Marcatextos", "item_marker.png", PRECIO_MARCATEXTOS, "Revela la pista asociada a la palabra");

        pnlInferior.add(btnSacapuntas);
        pnlInferior.add(btnTijeras);
        pnlInferior.add(btnGoma);
        pnlInferior.add(btnPluma);
        pnlInferior.add(btnMarcatextos);

        // Construcción final de las diferentes áreas
        add(pnlSuperior, BorderLayout.NORTH);
        add(pnlPrincipal, BorderLayout.CENTER);
        add(pnlInferior, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, String icono, int precio, String ayuda) {

        JButton boton = new JButton("$" + precio + " " + texto);
        boton.setIcon(Recursos.cargarImagen(icono));
        boton.setToolTipText(ayuda);
        boton.setMargin(new Insets(0, 2, 0, 2));
        boton.setPreferredSize(new Dimension(160, 70));
        boton.setMaximumSize(boton.getPreferredSize());
        boton.setFocusable(false);
        boton.setFont(Recursos.Fuentes.fuenteComic(Font.PLAIN, 15));
        boton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        boton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        boton.setIconTextGap(0);

        return boton;
    }

    private JButton crearTecla(String letra) {

        JButton boton = new JButton(letra);
        boton.setName(letra);
        boton.setFocusable(false);
        boton.setFont(Recursos.Fuentes.fuenteComic(18));
        boton.setMargin(new Insets(2, 2, 2, 2));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        teclas.add(boton);
        return boton;
    }

    public void setTeclasActionListener(ActionListener al) {
        for (JButton btn : teclas) {
            btn.addActionListener(al);
        }
    }

    public void setTeclaHabilitada(String letra, boolean habilitada) {
        for (JButton btn : teclas) {
            if (btn.getName().equalsIgnoreCase(letra)) {
                btn.setEnabled(habilitada);
                break;
            }
        }
    }

    public void restablecerTeclado() {
        for (JButton btn : teclas) {
            btn.setEnabled(true);
        }
    }

    public List<JButton> getTeclas() {
        return teclas;
    }

    public JButton getTecla(String letra) {
        return teclas.stream()
                .filter(btn -> btn.getName().equalsIgnoreCase(letra))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Image fondo = Objects.requireNonNull(Recursos.cargarImagen("bg_contenedor.png")).getImage();
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }

}
