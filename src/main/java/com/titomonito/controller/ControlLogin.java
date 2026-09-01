package com.titomonito.controller;

import com.titomonito.Main;
import com.titomonito.dao.JugadorDAO;
import com.titomonito.models.Jugador;
import com.titomonito.services.SesionManager;
import com.titomonito.ui.VentanaLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class ControlLogin {

    private final VentanaLogin ventana;

    public ControlLogin(VentanaLogin ventana) {

        this.ventana = ventana;
        agregarListeners();
    }

    private void agregarListeners() {

        ControlLogin.ControladorEventos controlador = new ControlLogin.ControladorEventos();
        ventana.addFondoListeners(controlador, controlador);
        ventana.addBotonesListeners(this::controlarBotones);
    }

    private void controlarBotones(ActionEvent e) {

        JButton boton = (JButton) e.getSource();
        String texto = boton.getText();

        switch (texto) {
            case "SALIR":
                System.exit(0);
                break;
            case "JUGAR":
                manejarJugar();
                break;
            case "CREAR y JUGAR":
                manejarCrearYJugar();
                break;
        }
    }

    private void manejarJugar() {
        JComboBox<String> combo = ventana.getCmbJugadores();
        Object seleccionObj = combo.getSelectedItem();

        if (seleccionObj == null) {
            mostrarAviso("Selecciona un jugador de la lista o crea uno nuevo.");
            return;
        }

        String seleccion = seleccionObj.toString().trim();

        if (seleccion.isEmpty() || seleccion.equals("Selecciona jugador")) {
            mostrarAviso("Selecciona un jugador de la lista o crea uno nuevo.");
            return;
        }

        Map<String, Integer> mapa = ventana.getMapaJugadores();
        Integer idJugador = mapa.get(seleccion);

        if (idJugador == null) {
            mostrarAviso("Selección inválida.");
            return;
        }

        Jugador jugador = JugadorDAO.obtenerPorId(idJugador);
        if (jugador != null) {
            SesionManager.getInstance().iniciarSesion(jugador);
            ventana.dispose();
            new Main().mostrarVentanaPrincipal();
        } else {
            mostrarError("No se pudo cargar el jugador.");
        }
    }

    private void manejarCrearYJugar() {
        String nombre = ventana.getTxtNombre().getText().trim();

        if (nombre.isEmpty() || nombre.equals("Ingresa tu nombre")) {
            mostrarAviso("Escribe un nombre para crear el jugador.");
            return;
        }

        if (ventana.getMapaJugadores().containsKey(nombre)) {
            mostrarAviso("Ese nombre ya existe. Selecciónalo de la lista para jugar.");
            return;
        }

        Jugador nuevo = JugadorDAO.crear(nombre);
        if (nuevo != null) {
            SesionManager.getInstance().iniciarSesion(nuevo);
            ventana.dispose();
            new Main().mostrarVentanaPrincipal();
        } else {
            mostrarError("No se pudo crear el jugador. Intenta de nuevo.");
        }
    }

    private void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(ventana, mensaje, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(ventana, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private class ControladorEventos extends MouseAdapter {

        private int xMouse, yMouse;

        @Override
        public void mousePressed(MouseEvent e) {
            Point clickInicial = e.getPoint();
            xMouse = clickInicial.x;
            yMouse = clickInicial.y;
        }

        public void mouseDragged(MouseEvent e) {
            int x = e.getXOnScreen() - xMouse;
            int y = e.getYOnScreen() - yMouse;
            ventana.setLocation(x, y);
        }
    }
}
