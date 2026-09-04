package com.titomonito.ui.vistas;

import com.titomonito.enums.NivelMedalla;
import com.titomonito.utils.Recursos;

import javax.swing.*;
import java.awt.*;

public class TarjetaMedalla extends JPanel {

    private final int idCategoria;
    private final String nombreCategoria;
    private final JLabel lblIcono;
    private final JLabel lblNombre;
    private final JLabel lblProgreso;

    private double[] datosProgreso;

    public TarjetaMedalla(int idCategoria, String nombreCategoria, String iconoPath) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.datosProgreso = new double[]{0, 0, 0.0};

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(CENTER_ALIGNMENT);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        lblIcono = new JLabel();
        lblIcono.setAlignmentX(CENTER_ALIGNMENT);
        lblIcono.setPreferredSize(new Dimension(80, 80));
        lblIcono.setMaximumSize(new Dimension(80, 80));
        lblIcono.setMinimumSize(new Dimension(80, 80));

        lblNombre = new JLabel(nombreCategoria);
        lblNombre.setFont(lblNombre.getFont().deriveFont(Font.BOLD, 13));
        lblNombre.setAlignmentX(CENTER_ALIGNMENT);

        lblProgreso = new JLabel("0%");
        lblProgreso.setFont(lblProgreso.getFont().deriveFont(12f));
        lblProgreso.setAlignmentX(CENTER_ALIGNMENT);
        lblProgreso.setForeground(Color.GRAY);

        add(lblIcono);
        add(Box.createVerticalStrut(3));
        add(lblNombre);
        add(lblProgreso);

        actualizar(null);
        setToolTipText("");
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

        ImageIcon iconoCat = Recursos.cargarImagen("cat_" + idCategoria + ".png");
        ImageIcon marcoIcono = Recursos.cargarImagen(nivel.getMarcoPath());

        if (iconoCat != null) {
            //Image img = iconoCat.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
            lblIcono.setIcon(new ImageIcon(iconoCat.getImage()));
        }

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        NivelMedalla nivel = NivelMedalla.fromProgreso(datosProgreso[2]);
        Color bordeColor;
        switch (nivel) {
            case ORO:
                bordeColor = new Color(255, 215, 0);
                break;
            case PLATA:
                bordeColor = new Color(192, 192, 192);
                break;
            case BRONCE:
                bordeColor = new Color(205, 127, 50);
                break;
            default:
                bordeColor = new Color(180, 180, 180);
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(6));
        g2.setColor(bordeColor);
        int arc = 12;
        g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, arc, arc);
        g2.dispose();
    }
}
