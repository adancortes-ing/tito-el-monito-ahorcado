package com.titomonito.vista;

import javax.swing.*;

public class VistaAcerca extends JDialog {

    public VistaAcerca(VentanaBase ventanaBase) {

        super(ventanaBase, true);

        setTitle("Acerca de Tito el Monito Ahorcado");
        setSize(500, 600);
        setResizable(false);
        setLocationRelativeTo(ventanaBase);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JLabel("[ PLACE HOLDER ]"));
    }

}
