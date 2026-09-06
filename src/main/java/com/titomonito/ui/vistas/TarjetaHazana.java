package com.titomonito.ui.vistas;

import com.titomonito.enums.LogroId;
import com.titomonito.utils.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TarjetaHazana extends JPanel {

    private final LogroId logroId;
    private final JLabel lblIcono;
    private final JLabel lblNombre;
    private final JLabel lblNuevo;

    private boolean desbloqueado;
    private boolean esNuevo;
    private boolean hover = false;

    public TarjetaHazana(LogroId logroId) {
        this.logroId = logroId;
        this.desbloqueado = false;
        this.esNuevo = false;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(CENTER_ALIGNMENT);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setPreferredSize(new Dimension(130, 160));
        setMaximumSize(new Dimension(130, 160));
        setMinimumSize(new Dimension(130, 160));

        lblIcono = new JLabel();
        lblIcono.setAlignmentX(CENTER_ALIGNMENT);
        lblIcono.setPreferredSize(new Dimension(80, 80));
        lblIcono.setMaximumSize(new Dimension(80, 80));
        lblIcono.setMinimumSize(new Dimension(80, 80));

        lblNombre = new JLabel("<html><center>" + logroId.getNombre() + "</center></html>");
        lblNombre.setFont(lblNombre.getFont().deriveFont(Font.BOLD, 14));
        lblNombre.setAlignmentX(CENTER_ALIGNMENT);
        lblNombre.setMaximumSize(new Dimension(120, 36));
        lblNombre.setPreferredSize(new Dimension(120, 36));

        lblNuevo = new JLabel();
        lblNuevo.setAlignmentX(RIGHT_ALIGNMENT);
        lblNuevo.setVisible(false);

        add(lblIcono);
        add(Box.createVerticalStrut(3));
        add(lblNombre);
        add(lblNuevo);

        ImageIcon nuevoIcono = Recursos.cargarImagen("logro_bandera_nuevo.png");
        if (nuevoIcono.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
            Image img = nuevoIcono.getImage().getScaledInstance(40, 20, Image.SCALE_DEFAULT);
            lblNuevo.setIcon(new ImageIcon(img));
        }

        actualizar(false, false);
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

    public LogroId getLogroId() {
        return logroId;
    }

    public String getCodigo() {
        return logroId.getCodigo();
    }

    public boolean isDesbloqueado() {
        return desbloqueado;
    }

    public void setDesbloqueado(boolean desbloqueado) {
        this.desbloqueado = desbloqueado;
    }

    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public void actualizar(boolean desbloqueado, boolean esNuevo) {
        this.desbloqueado = desbloqueado;
        this.esNuevo = esNuevo;

        ImageIcon icono = Recursos.cargarImagen(logroId.getIconoPath());

        if (icono.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
            Image img = icono.getImage();
            if (!desbloqueado) {
                img = crearImagenDeshabilitada(img);
            }
            lblIcono.setIcon(new ImageIcon(img));
        } else {
            lblIcono.setIcon(crearIconoPlaceholder(desbloqueado));
        }

        lblNuevo.setVisible(esNuevo);

        if (desbloqueado) {
            lblNombre.setForeground(new Color(50, 50, 50));
        } else {
            lblNombre.setForeground(Color.GRAY);
        }

        String tooltip = String.format(
                "<html><b>%s</b><br>%s<br><br><i>Estado: %s</i></html>",
                logroId.getNombre(),
                logroId.getDescripcion(),
                desbloqueado ? (esNuevo ? "¡NUEVO!" : "Desbloqueado") : "Bloqueado"
        );
        setToolTipText(tooltip);

        repaint();
    }

    private Image crearImagenDeshabilitada(Image img) {
        BufferedImage bi = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.80f));
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, 80, 80);
        g2.dispose();
        return bi;
    }

    private ImageIcon crearIconoPlaceholder(boolean habilitado) {
        BufferedImage img = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(habilitado ? new Color(200, 200, 200) : new Color(150, 150, 150));
        g2.fillOval(10, 10, 60, 60);
        g2.setColor(Color.DARK_GRAY);
        g2.drawString("?", 35, 48);
        g2.dispose();
        return new ImageIcon(img);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color bordeColor = desbloqueado
                ? new Color(253, 191, 0)
                : new Color(180, 180, 180);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (hover) {
            g2.setStroke(new BasicStroke(5));
            g2.setColor(new Color(253, 191, 0, 180));
        } else {
            g2.setStroke(new BasicStroke(desbloqueado ? 6 : 1));
            g2.setColor(bordeColor);
        }
        int arc = 10;
        g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, arc, arc);
        g2.dispose();
    }
}
