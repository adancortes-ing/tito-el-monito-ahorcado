package com.titomonito.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Recursos {

    private static final Logger LOGGER = Logger.getLogger(Recursos.class.getName());
    private static final Map<String, String> cacheURLImagen = new HashMap<>();

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
            LOGGER.warning("No se pudo encontrar la imagen en /ui/" + archivo);
            return crearImagenPorDefecto();
        }

        return new ImageIcon(imagenURL);
    }

    /**
     * Devuelve una URL file:// válida de un recurso de imagen, extrayéndolo a un
     * archivo temporal para que pueda usarse en el HTML ligero de Swing (que no
     * soporta recursos dentro del JAR).
     */
    public static String imagenURL(String archivo) {
        String url = cacheURLImagen.get(archivo);
        if (url != null) return url;

        try {
            File tmp = File.createTempFile("tito_", ".png");
            tmp.deleteOnExit();
            try (InputStream is = Recursos.class.getResourceAsStream("/ui/" + archivo)) {
                if (is == null) {
                    LOGGER.warning("No se pudo encontrar la imagen en /ui/" + archivo);
                    return "";
                }
                Files.copy(is, tmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            url = tmp.toURI().toURL().toExternalForm();
            cacheURLImagen.put(archivo, url);
            return url;
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, "No se pudo extraer la imagen: " + archivo, ex);
            return "";
        }
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

        try (InputStream is = Recursos.class.getResourceAsStream("/fonts/" + font)) {
            if (is == null) {
                LOGGER.warning("No se encontró la fuente: " + font);
                return new Font("Comic Sans MS", Font.PLAIN, 20);
            }
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, is);
            return fuente.deriveFont(size);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "No se pudo cargar la fuente: " + font, e);
            return new Font("Comic Sans MS", Font.PLAIN, 20);
        }
    }
}