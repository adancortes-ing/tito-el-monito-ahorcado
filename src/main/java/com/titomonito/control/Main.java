package com.titomonito.control;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTGitHubIJTheme;
import com.titomonito.vista.VentanaBase;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        FlatMTGitHubIJTheme.setup();

        UIManager.put( "Button.arc", 10 );
        UIManager.put( "Button.background", new Color(206, 244, 250));

        Font fuenteGlobal = Recursos.cargarFuente("IndieFlower-Regular.ttf", 24.0f);
        UIManager.put( "defaultFont", fuenteGlobal);

        VentanaBase ventanaPrincipal = new VentanaBase();
        ventanaPrincipal.setVisible(true);
    }
}

