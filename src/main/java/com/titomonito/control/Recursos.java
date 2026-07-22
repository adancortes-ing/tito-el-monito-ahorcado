package com.titomonito.control;

import javax.swing.*;
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
}
