package com.titomonito.ui;

import com.titomonito.models.Jugador;
import com.titomonito.services.SesionManager;
import com.titomonito.utils.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.titomonito.config.Constantes.*;

public class VentanaLogin extends JFrame {

    private PanelFondoLogin fondo;
    private JButton btnSalir;
    private JButton btnJugar;
    private JButton btnCrear;
    private JComboBox<String> cmbJugadores;
    private JTextField txtNombre;
    private final Map<String, Integer> mapaJugadores = new HashMap<>();

    public VentanaLogin() {

        setSize(300, 550);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Objects.requireNonNull(Recursos.cargarImagen("icono.png")).getImage());

        initUI();
        cargarJugadoresEnCombo();
        new com.titomonito.controller.ControlLogin(this);
    }

    private void cargarJugadoresEnCombo() {
        List<Jugador> jugadores = SesionManager.getInstance().cargarJugadores();
        for (Jugador j : jugadores) {
            mapaJugadores.put(j.getNombre(), j.getId_jugador());
            cmbJugadores.addItem(j.getNombre());
        }
    }

    private void initUI() {

        fondo = new PanelFondoLogin();
        setContentPane(fondo);

        //Componentes de la ventana
        Box subTitulo = Box.createHorizontalBox();
        subTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblSeleccionar = new JLabel("Elegir Jugador");
        lblSeleccionar.setFont(lblSeleccionar.getFont().deriveFont(18.0f));
        lblSeleccionar.setAlignmentX(Component.CENTER_ALIGNMENT);
        subTitulo.add(lblSeleccionar);
        subTitulo.add(Box.createHorizontalGlue());

        cmbJugadores = new JComboBox<>();
        cmbJugadores.setAlignmentX(Component.CENTER_ALIGNMENT);
        cmbJugadores.setPreferredSize(new Dimension(220, 50));
        cmbJugadores.setMaximumSize(cmbJugadores.getPreferredSize());
        cmbJugadores.setFont(cmbJugadores.getFont().deriveFont(18.0f));

        final String placeholderCombo = "Selecciona jugador";
        final Color colorPlaceholder = Color.GRAY;
        final Color colorTextoCombo = cmbJugadores.getForeground();
        cmbJugadores.setEditable(false);
        JTextField editorCombo = (JTextField) cmbJugadores.getEditor().getEditorComponent();
        colocarPlaceHolder(placeholderCombo, colorPlaceholder, colorTextoCombo, editorCombo);

        Box subTitulo2 = Box.createHorizontalBox();
        subTitulo2.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblCrear = new JLabel("Crear Nuevo Jugador");
        lblCrear.setFont(lblCrear.getFont().deriveFont(18.0f));
        lblCrear.setAlignmentX(Component.CENTER_ALIGNMENT);
        subTitulo2.add(lblCrear);
        subTitulo2.add(Box.createHorizontalGlue());

        txtNombre = new JTextField();
        txtNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtNombre.setPreferredSize(new Dimension(200, 50));
        txtNombre.setMaximumSize(txtNombre.getPreferredSize());
        txtNombre.setFont(txtNombre.getFont().deriveFont(18.0f));

        final String placeholderTexto = "Ingresa tu nombre";
        final Color colorTextoNombre = txtNombre.getForeground();
        colocarPlaceHolder(placeholderTexto, colorPlaceholder, colorTextoNombre, txtNombre);

        btnJugar = crearBoton("JUGAR", COLOR_VERDE);
        btnCrear = crearBoton("CREAR y JUGAR", COLOR_AZUL);
        btnSalir = crearBoton("SALIR", COLOR_SALMON);

        fondo.add(subTitulo);
        fondo.add(Box.createVerticalStrut(5));
        fondo.add(cmbJugadores);
        fondo.add(btnJugar);
        fondo.add(Box.createVerticalStrut(10));
        fondo.add(subTitulo2);
        fondo.add(Box.createVerticalStrut(10));
        fondo.add(txtNombre);
        fondo.add(Box.createVerticalStrut(10));
        fondo.add(btnCrear);
        fondo.add(Box.createVerticalStrut(20));
        fondo.add(btnSalir);

    }

    public void addFondoListeners(MouseListener ml, MouseMotionListener mml) {
        fondo.addMouseListener(ml);
        fondo.addMouseMotionListener(mml);
    }

    public void addBotonesListeners(ActionListener al) {

        btnJugar.addActionListener(al);
        btnCrear.addActionListener(al);
        btnSalir.addActionListener(al);
    }

    public JComboBox<String> getCmbJugadores() { return cmbJugadores; }

    public JTextField getTxtNombre() { return txtNombre; }

    public Map<String, Integer> getMapaJugadores() { return mapaJugadores; }

    private void colocarPlaceHolder(String placeholderCombo, Color colorPlaceholder, Color colorTextoCombo, JTextField editorCombo) {
        editorCombo.setText(placeholderCombo);
        editorCombo.setForeground(colorPlaceholder);
        editorCombo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (editorCombo.getText().equals(placeholderCombo)) {
                    editorCombo.setText("");
                    editorCombo.setForeground(colorTextoCombo);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (editorCombo.getText().isEmpty()) {
                    editorCombo.setText(placeholderCombo);
                    editorCombo.setForeground(colorPlaceholder);
                }
            }
        });
    }

    private JButton crearBoton(String texto, Color color) {

        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setPreferredSize(new Dimension(180, 55));
        boton.setMargin(new Insets(0, 5, 0, 5));
        boton.setMaximumSize(boton.getPreferredSize());
        boton.setBackground(color);
        boton.setFont(Recursos.Fuentes.fuenteComic(Font.BOLD, 16));
        boton.setFocusPainted(false);
        return boton;
    }

    private static class PanelFondoLogin extends JPanel {

        public PanelFondoLogin() {

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(195, 30, 20, 30));
            setPreferredSize(new Dimension(300, 550));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Image fondo = Objects.requireNonNull(Recursos.cargarImagen("bg_login.png")).getImage();

            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
