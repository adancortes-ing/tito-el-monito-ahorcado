package com.titomonito.control;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class Recursos {

    public static ImageIcon cargarImagenUI(String nombreArchivo) {

        //Ruta hacia la carpeta interna dentro del main
        URL imgURL = Recursos.class.getResource("/ui/" + nombreArchivo);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            return null;
        }
    }

    public static Font cargarFuente(String font, float tamanio) {
        try {
            InputStream is = Recursos.class.getResourceAsStream("/fonts/" + font);
            assert is != null;
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, is);
            return fuente.deriveFont(tamanio);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Comic Sans MS", Font.PLAIN, 13);
        }
    }
}
