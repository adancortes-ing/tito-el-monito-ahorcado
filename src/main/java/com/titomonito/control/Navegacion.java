package com.titomonito.control;

import com.titomonito.vista.VentanaBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Navegacion implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String opcion = e.getActionCommand();

        switch (opcion) {
            case "INICIO":
                cambiarVista("INICIO");
                break;
            case "SALIR":
                System.exit(0);
        }

    }

    public static void cambiarVista(String vista) {
        VentanaBase.vistas.show(VentanaBase.contenedor, vista);
    }

}
