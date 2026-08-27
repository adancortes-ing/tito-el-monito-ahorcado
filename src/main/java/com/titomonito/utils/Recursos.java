package com.titomonito.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

public class Recursos {

    public static class Fuentes {

        public static Font fuenteComic(int t) {
            return new Font("Comic Sans MS", Font.BOLD, t);
        }

        @SuppressWarnings("MagicConstant")
        public static Font fuenteComic(int estilo, int t) {
            return new Font("Comic Sans MS", estilo, t);
        }
    }

    public static ImageIcon cargarImagen(String archivo) {

        URL imagenURL = Recursos.class.getResource("/ui/" + archivo);

        if (imagenURL == null) {
            System.err.println("Advertencia: No se pudo encontrar la imagen en /ui/" + archivo);
            return crearImagenPorDefecto();
        }

        return new ImageIcon(imagenURL);
    }

    private static ImageIcon crearImagenPorDefecto() {
        int ancho = 32;
        int alto = 32;
        BufferedImage img = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, ancho, alto);

        g2d.setColor(Color.RED);
        g2d.drawLine(0, 0, ancho, alto);
        g2d.drawLine(0, alto, ancho, 0);

        g2d.dispose();
        return new ImageIcon(img);
    }

    public static Font cargarFuente(String font, float size) {

        try {
            InputStream is = Recursos.class.getResourceAsStream("/fonts/" + font);
            assert is != null;
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, is);
            return fuente.deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Comic Sans MS", Font.PLAIN, 20);
        }
    }
}
