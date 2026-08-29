package com.titomonito.controller;

import com.titomonito.config.Constantes;
import com.titomonito.services.LogicaJuego;
import com.titomonito.ui.VentanaBase;
import com.titomonito.ui.vistas.PreGamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ControlPreGame {

    private final PreGamePanel panel;
    private final VentanaBase principal;

    public ControlPreGame(PreGamePanel panel, VentanaBase principal) {

        this.panel = panel;
        this.principal = principal;
        initListeners();
    }

    private void initListeners() {

        panel.addBotonesListeners(this::controlarBotones);
    }

    private void controlarBotones(ActionEvent e) {

        JButton boton = (JButton) e.getSource();
        String categoria = boton.getName();
        int id_categoria = (int) boton.getClientProperty("id_categoria");


        LogicaJuego.getInstance().newGame(id_categoria, categoria, panel.getValorDificultad());
        principal.cambiarVista(Constantes.JUEGO);
    }
}
