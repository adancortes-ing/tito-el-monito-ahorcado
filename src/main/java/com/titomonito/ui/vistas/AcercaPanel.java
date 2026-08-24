package com.titomonito.ui.vistas;

import com.titomonito.ui.VentanaBase;

import javax.swing.*;

public class AcercaPanel extends JDialog {

    public AcercaPanel(VentanaBase owner) {

        super(owner, true);

        setTitle("Acerca de Tito el Monito Ahorcado");
        setSize(500, 600);
        setResizable(false);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JLabel("[ PLACE HOLDER ]"));
    }

}
