package com.titomonito.ui.vistas;

import com.titomonito.models.Categorias;
import com.titomonito.utils.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

public class PreGamePanel extends JPanel {

    private JPanel pnlDificultades, pnlCategorias;
    private JSlider sliderDificultad;
    private final List<JButton> listaBotones = new ArrayList<>();

    public PreGamePanel() {

        // Propiedades del panel =======================================================================================
        setLayout(new BorderLayout());

        initComponentes();
    }

    private void initComponentes() {

        JLabel lblTitulo = new JLabel("Opciones de Partida");
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        // Contenedor central ==========================================================================================
        JPanel contenedorCentro = new JPanel();
        contenedorCentro.setLayout(new BoxLayout(contenedorCentro, BoxLayout.Y_AXIS));
        contenedorCentro.setOpaque(false);

        // sub-panel horizontal para dificultades ----------------------------------------------------------------------
        pnlDificultades = new JPanel();
        pnlDificultades.setOpaque(false);
        pnlDificultades.setLayout(new BoxLayout(pnlDificultades, BoxLayout.X_AXIS));
        pnlDificultades.setPreferredSize(new Dimension(860, 110));
        pnlDificultades.setMaximumSize(pnlDificultades.getPreferredSize());
        pnlDificultades.setBorder(BorderFactory.createEmptyBorder(10, 70, 0, 10));
        pnlDificultades.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDificultad = new JLabel("Elige la dificultad: ");
        lblDificultad.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        sliderDificultad = new JSlider(1, 5, 2);
        sliderDificultad.setPaintTicks(true);
        sliderDificultad.setSnapToTicks(true);
        sliderDificultad.setPaintLabels(true);
        sliderDificultad.setFont(lblDificultad.getFont().deriveFont(14.0f));

        Hashtable<Integer, JLabel> etiquetas = new Hashtable<>();
        etiquetas.put(1, new JLabel("Fácil"));
        etiquetas.put(2, new JLabel("Normal"));
        etiquetas.put(3, new JLabel("Difícil"));
        etiquetas.put(4, new JLabel("Extremo"));
        etiquetas.put(5, new JLabel("Imposible"));
        sliderDificultad.setLabelTable(etiquetas);

        pnlDificultades.add(lblDificultad);
        pnlDificultades.add(sliderDificultad);
        pnlDificultades.add(Box.createHorizontalStrut(30));

        // sub-panel rejilla para los botones de categorías ------------------------------------------------------------
        pnlCategorias = new JPanel();
        pnlCategorias.setOpaque(false);
        pnlCategorias.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlCategorias.setBorder(BorderFactory.createEmptyBorder(0, 70, 130, 30));
        pnlCategorias.setLayout(new GridLayout(5, 4));

        JLabel lblCategoria = new JLabel("Elige la Categoria:");
        pnlCategorias.add(lblCategoria);

        crearBotonesCategorias();

        // =============================================================================================================
        contenedorCentro.add(pnlDificultades);
        contenedorCentro.add(Box.createVerticalStrut(30));
        contenedorCentro.add(pnlCategorias);
        add(contenedorCentro, BorderLayout.CENTER);
    }

    private JButton crearBoton(String etiqueta) {

        JButton boton = new JButton(etiqueta);
        boton.setFont(Recursos.Fuentes.fuenteComic(14));
        boton.setPreferredSize(new Dimension(160, 45));
        boton.setMaximumSize(boton.getPreferredSize());

        pnlDificultades.add(boton);
        pnlDificultades.add(Box.createHorizontalStrut(15));

        return boton;
    }

    public int getValorDificultad () {
        return sliderDificultad.getValue();
    }

    public void actualizarEstadoCategorias(int idJugador) {
        for (JButton btn : listaBotones) {
            Integer idCategoria = (Integer) btn.getClientProperty("id_categoria");
            if (idCategoria == null) continue;
            if (com.titomonito.dao.JugadorDAO.categoriaCompletada(idCategoria, idJugador)) {
                btn.setEnabled(false);
            }
        }
    }

    public void addBotonesListeners(ActionListener al) {

        for (JButton btn : listaBotones) {
            btn.addActionListener(al);
        }
    }

    private void crearBotonesCategorias() {

        for (Categorias cat : Categorias.getListaCategorias()) {

            JButton botonCat = new JButton(cat.getNombre_categoria());
            botonCat.setName(cat.getNombre_categoria());
            botonCat.putClientProperty("id_categoria", cat.getId_categoria());
            botonCat.setFont(Recursos.Fuentes.fuenteComic(12));
            botonCat.setIcon(Recursos.cargarImagen(cat.getUrl_icono()));
            botonCat.setMargin(new Insets(0, 3, 0, 3));
            botonCat.setHorizontalTextPosition(SwingConstants.RIGHT);
            botonCat.setHorizontalAlignment(SwingConstants.LEFT);
            botonCat.setIconTextGap(5);

            listaBotones.add(botonCat);
            pnlCategorias.add(botonCat);
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Image fondo = Objects.requireNonNull(Recursos.cargarImagen("bg_center.png")).getImage();

        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}
