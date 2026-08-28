package com.titomonito.services;

import com.titomonito.ui.vistas.JuegoPanel;

public class LogicaJuego {

    private static LogicaJuego instance;
    private JuegoPanel vistaJuego;

    private int id_categoria;
    private String categoria;

    public static LogicaJuego getInstance() {
        if (instance == null) {
            instance = new LogicaJuego();
        }
        return instance;
    }

    public void newGame(int id_categoria, String nombreCategoria) {
        this.id_categoria = id_categoria;
        this.categoria = nombreCategoria;

        initConfig();

    }

    private void initConfig(){

        vistaJuego.setLblValCategoria(categoria);
        vistaJuego.restablecerTeclado();
    }

    public void setVistaJuego(JuegoPanel vistaJuego) {
        this.vistaJuego = vistaJuego;
    }
}
