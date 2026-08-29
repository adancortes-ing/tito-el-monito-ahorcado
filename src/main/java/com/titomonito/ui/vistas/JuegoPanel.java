package com.titomonito.ui.vistas;

import com.titomonito.services.LogicaJuego;
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
    private JLabel lblValPotencial;
    private JLabel lblValAsegurado;

    private List<JButton> teclas;
    private JButton btnSacapuntas, btnTijeras, btnGoma, btnPluma, btnMarcatextos;

    public JuegoPanel() {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 60, 10, 20));
        initUI();
        LogicaJuego.getInstance().setVistaJuego(this);
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
        Dimension sizeTiempo = new Dimension(60, 40);
        lblValTiempo.setPreferredSize(sizeTiempo);
        lblValTiempo.setMaximumSize(sizeTiempo);
        lblValTiempo.setMinimumSize(sizeTiempo);

        JLabel lblVidasRestantes = new JLabel("Salud de Tito: ");
        lblVidasRestantes.setAlignmentY(Component.CENTER_ALIGNMENT);

        lblValVidas = new JLabel("♥ ♥ ♥ ♥ ♥ ♥");
        lblValVidas.setAlignmentY(Component.CENTER_ALIGNMENT);
        lblValVidas.setFont(new Font("Segoe UI", Font.PLAIN, 24));

        pnlSuperior.add(lblTiempoRestante);
        pnlSuperior.add(Box.createHorizontalStrut(15));
        pnlSuperior.add(lblValTiempo);
        pnlSuperior.add(Box.createHorizontalStrut(155));
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

        Box sub1 = Box.createHorizontalBox();
        Box sub2 = Box.createHorizontalBox();
        sub1.setAlignmentX(Component.CENTER_ALIGNMENT);
        sub2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPotencial = new JLabel("Premio Potencial:  $ ");
        lblPotencial.setFont(lblPotencial.getFont().deriveFont(Font.PLAIN, 20.0F));
        lblPotencial.setToolTipText("Monedas que ganas si descubres la palabra.");
        lblValPotencial = new JLabel("");
        lblValPotencial.setFont(lblPotencial.getFont().deriveFont(Font.BOLD, 20.0F));

        JLabel lblTotalAcumulado = new JLabel("Total Asegurado:   $ ");
        lblTotalAcumulado.setFont(lblPotencial.getFont());
        lblTotalAcumulado.setToolTipText("Monedas que ganas si no descubres la palabra.");
        lblValAsegurado = new JLabel("0");
        lblValAsegurado.setFont(lblValPotencial.getFont());

        sub1.add(lblPotencial);
        sub1.add(lblValPotencial);
        sub1.add(Box.createHorizontalGlue());
        sub2.add(lblTotalAcumulado);
        sub2.add(lblValAsegurado);
        sub2.add(Box.createHorizontalGlue());

        JLabel lblInstrucciones = new JLabel("Descubre la palabra antes del trazo final, categoria:");
        lblInstrucciones.setFont(lblInstrucciones.getFont().deriveFont(18.0f));
        lblInstrucciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblValCategoria = new JLabel("[ CATEGORÍA ]");
        lblValCategoria.setFont(lblValCategoria.getFont().deriveFont(20.0f));
        lblValCategoria.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPalabra = new JLabel("[ PALABRA OCULTA ]");
        lblPalabra.setFont(lblPalabra.getFont().deriveFont(Font.BOLD, 28.0f));
        lblPalabra.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblValPista = new JLabel("Compra un marcatextos para resaltar y revelar la pista.");
        lblValPista.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblValPista.setFont(lblValPista.getFont().deriveFont(18.0f));

        pnlIzquierda.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlIzquierda.add(sub1);
        pnlIzquierda.add(sub2);
        pnlIzquierda.add(Box.createVerticalStrut(25));
        pnlIzquierda.add(lblInstrucciones);
        pnlIzquierda.add(Box.createVerticalStrut(5));
        pnlIzquierda.add(lblValCategoria);
        pnlIzquierda.add(Box.createVerticalStrut(25));
        pnlIzquierda.add(lblPalabra);
        pnlIzquierda.add(Box.createVerticalStrut(20));
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

    private void crearTecla(String letra) {

        JButton boton = new JButton(letra);
        boton.setName(letra);
        boton.setFocusable(false);
        boton.setFont(Recursos.Fuentes.fuenteComic(18));
        boton.setMargin(new Insets(2, 2, 2, 2));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        teclas.add(boton);
    }

    public void setLblValTiempo(int tiempo) {
        lblValTiempo.setText(String.valueOf(tiempo));

        if (tiempo <= 5) {
            lblValTiempo.setForeground(Color.RED);
        } else if (tiempo <= 10) {
            lblValTiempo.setForeground(new Color(255, 85, 0));
        } else {
            lblValTiempo.setForeground(Color.BLACK);
        }
    }

    public void setLblValCategoria(String lblValCategoria) {
        this.lblValCategoria.setText(lblValCategoria);
    }

    public void setLblPalabra(String p) {
        this.lblPalabra.setText(p);
    }

    public void setLblValVidas(String corazones) {
        this.lblValVidas.setText(corazones);
    }

    public void setTeclado(boolean estado) {
        for (JButton btn : teclas) {
            btn.setEnabled(estado);
        }
    }

    public void reiniciarPista() {
        lblValPista.setText("Compra un marcatextos para resaltar y revelar la pista.");
    }

    public void setTeclasActionListener(ActionListener al) {
        for (JButton btn : teclas) {
            btn.addActionListener(al);
        }
    }

    public void dibujarTito(String imagen) {

        lblIgmTito.setIcon(Recursos.cargarImagen(imagen));
    }

    public void setTeclaHabilitada(String letra, boolean habilitada) {
        for (JButton btn : teclas) {
            if (btn.getName().equalsIgnoreCase(letra)) {
                btn.setEnabled(habilitada);
                break;
            }
        }
    }

    public void setLblValPotencial(String p) {
        this.lblValPotencial.setText(p);
    }

    public void setLblValAsegurado(String p) {
        this.lblValAsegurado.setText(p);
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
