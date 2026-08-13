package com.titomonito.vista;

import com.titomonito.modelo.Categorias;
import com.titomonito.modelo.Recursos;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VistaCategorias extends JPanel {

    private JPanel pnlDificultades, pnlCategorias;
    private final List<JButton> listaBotones = new ArrayList<>();

    public VistaCategorias() {

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
        pnlDificultades.setPreferredSize(new Dimension(860, 70));
        pnlDificultades.setMaximumSize(pnlDificultades.getPreferredSize());
        pnlDificultades.setBorder(BorderFactory.createEmptyBorder(25, 70, 0, 10));
        pnlDificultades.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDificultad = new JLabel("Elige la dificultad:");
        JButton btnFacil = crearBoton("FÁCIL");
        JButton btnNormal = crearBoton("NORMAL");
        JButton btnDificil = crearBoton("DIFÍCIL");

        pnlDificultades.add(lblDificultad, 0);
        pnlDificultades.add(Box.createHorizontalStrut(30), 1);

        // sub-panel rejilla para los botones de categorías ------------------------------------------------------------
        pnlCategorias = new JPanel();
        pnlCategorias.setOpaque(false);
        pnlCategorias.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlCategorias.setBorder(BorderFactory.createEmptyBorder(0, 70, 130, 30));
        pnlCategorias.setLayout(new GridLayout(5, 4));

        JLabel lblCategoria = new JLabel("Elige la Categoria:");
        pnlCategorias.add(lblCategoria);

        crearBotonesCategorias();
        listaBotones.get(3).setEnabled(false);

        // =============================================================================================================
        contenedorCentro.add(pnlDificultades);
        contenedorCentro.add(Box.createVerticalStrut(30));
        contenedorCentro.add(pnlCategorias);
        add(contenedorCentro, BorderLayout.CENTER);
    }

    private JButton crearBoton(String etiqueta){

        JButton boton = new JButton(etiqueta);
        boton.setFont(Recursos.Fuentes.fuenteComic(14));
        boton.setPreferredSize(new Dimension(160, 45));
        boton.setMaximumSize(boton.getPreferredSize());

        pnlDificultades.add(boton);
        pnlDificultades.add(Box.createHorizontalStrut(15));

        return boton;
    }


    private void crearBotonesCategorias(){

        for (String[] cat : Categorias.categorias){

            JButton botonCat = new JButton(cat[0]);

            botonCat.setFont(Recursos.Fuentes.fuenteComic(12));
            botonCat.setIcon(Recursos.cargarImagenUI(cat[1]));
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

        Image fondo = Objects.requireNonNull(Recursos.cargarImagenUI("bg_center.png")).getImage();

        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}
