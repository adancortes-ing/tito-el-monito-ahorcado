package com.titomonito.vista;

import javax.swing.*;

public class VentanaBase extends JFrame {
    public VentanaBase() {
        setTitle("Tito el Monito Ahorcado");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
