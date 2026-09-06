package com.titomonito.ui.vistas;

import com.titomonito.enums.NivelMedalla;
import com.titomonito.utils.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TarjetaMedalla extends JPanel {

    private final int idCategoria;
    private final String nombreCategoria;
    private final JLabel lblIcono;
    private final JLabel lblNombre;
    private final JLabel lblProgreso;

    private double[] datosProgreso;
    private boolean hover = false;

    public TarjetaMedalla(int idCategoria, String nombreCategoria, String iconoPath) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.datosProgreso = new double[]{0, 0, 0.0};

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(CENTER_ALIGNMENT);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        lblIcono = new JLabel();
        lblIcono.setAlignmentX(CENTER_ALIGNMENT);
        lblIcono.setPreferredSize(new Dimension(70, 70));
        lblIcono.setMaximumSize(new Dimension(70, 70));
        lblIcono.setMinimumSize(new Dimension(70, 70));

        lblNombre = new JLabel(nombreCategoria);
        lblNombre.setFont(lblNombre.getFont().deriveFont(Font.BOLD, 14));
        lblNombre.setAlignmentX(CENTER_ALIGNMENT);

        lblProgreso = new JLabel("0%");
        lblProgreso.setFont(lblProgreso.getFont().deriveFont(14f));
        lblProgreso.setAlignmentX(CENTER_ALIGNMENT);
        lblProgreso.setForeground(Color.GRAY);

        add(lblIcono);
        add(Box.createVerticalStrut(3));
        add(lblNombre);
        add(lblProgreso);

        actualizar(null);
        setToolTipText("");

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                hover = true;
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                hover = false;
                repaint();
            }
        });
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void actualizar(double[] datos) {
        this.datosProgreso = datos != null ? datos : new double[]{0, 0, 0.0};
        int descubiertas = (int) datosProgreso[0];
        int total = (int) datosProgreso[1];
        double porcentaje = datosProgreso[2];

        NivelMedalla nivel = NivelMedalla.fromProgreso(porcentaje);
        String sufijoNivel = nivel.name().toLowerCase();

        String nombreArchivo = "cat_" + idCategoria + "_" + sufijoNivel + ".png";
        ImageIcon icono = Recursos.cargarImagen(nombreArchivo);


        Image imgFinal;
        if (icono.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
            imgFinal = icono.getImage();
        } else {
            imgFinal = crearPlaceholder(70, 70);
        }

        lblIcono.setIcon(new ImageIcon(imgFinal));

        if (porcentaje >= 1.0) {
            lblProgreso.setText("¡Completada!");
            lblProgreso.setForeground(new Color(255, 215, 0));
        } else {
            lblProgreso.setText(descubiertas + " / " + total);
            lblProgreso.setForeground(Color.GRAY);
        }

        String tooltip = String.format(
                "<html><b>%s</b><br>Progreso: %d / %d (%.0f%%)<br>Nivel: %s</html>",
                nombreCategoria, descubiertas, total, porcentaje * 100, nivel.name().toLowerCase()
        );
        setToolTipText(tooltip);

        repaint();
    }

    private Image crearPlaceholder(int ancho, int alto) {
        BufferedImage img = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(200, 200, 200));
        g2.fillOval(5, 5, ancho - 10, alto - 10);
        g2.setColor(Color.DARK_GRAY);
        g2.drawString("?", ancho / 2 - 5, alto / 2 + 5);
        g2.dispose();
        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        NivelMedalla nivel = NivelMedalla.fromProgreso(datosProgreso[2]);
        Color bordeColor;
        switch (nivel) {
            case ORO:
                bordeColor = new Color(253, 191, 0);
                break;
            case PLATA:
                bordeColor = new Color(145, 145, 145);
                break;
            case BRONCE:
                bordeColor = new Color(185, 115, 55);
                break;
            default:
                bordeColor = new Color(180, 180, 180);
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (hover) {
            g2.setStroke(new BasicStroke(4));
            g2.setColor(new Color(253, 191, 0, 180));
            int arc = 12;
            g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, arc, arc);
        } else {
            g2.setStroke(new BasicStroke(3));
            g2.setColor(bordeColor);
            int arc = 12;
            g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, arc, arc);
        }
        g2.dispose();
    }
}
