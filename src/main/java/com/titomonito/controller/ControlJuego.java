package com.titomonito.controller;

import com.titomonito.config.Constantes;
import com.titomonito.enums.LogroId;
import com.titomonito.services.LogicaJuego;
import com.titomonito.ui.VentanaBase;
import com.titomonito.ui.vistas.JuegoPanel;
import com.titomonito.ui.vistas.LogrosPanel;
import com.titomonito.utils.Recursos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ControlJuego {

    private final JuegoPanel juegoPanel;
    private final VentanaBase ventana;

    public ControlJuego(JuegoPanel juegoPanel, VentanaBase ventana) {

        this.juegoPanel = juegoPanel;
        this.ventana = ventana;
        this.juegoPanel.setTeclasActionListener(this::controlarTeclas);
        LogicaJuego.getInstance().setControlJuego(this);
    }

    public void mostrarResultado(String titulo, String mensaje, int id, String categoria, int dificultad, boolean gano) {
        JDialog dialog = new JDialog(
                SwingUtilities.getWindowAncestor(juegoPanel),
                titulo,
                java.awt.Dialog.ModalityType.APPLICATION_MODAL
        );
        dialog.setLayout(new java.awt.BorderLayout());
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        String icono;
        if (gano) {
            icono = "tito_libre.png";
        } else icono = "tito_muerto.png";

        JLabel imagen = new JLabel();
        imagen.setIcon(Recursos.cargarImagen(icono));
        dialog.add(imagen, BorderLayout.EAST);

        JLabel lblMensaje = new JLabel("<html><div style='text-align:left; padding:20px;'>" + mensaje.replace("\n", "<br>") + "</div></html>");
        lblMensaje.setFont(Recursos.Fuentes.fuenteComic(Font.PLAIN, 16));
        lblMensaje.setHorizontalAlignment(SwingConstants.LEFT);
        dialog.add(lblMensaje, java.awt.BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));
        String[] opciones = {"Volver a jugar", "Cambiar opciones", "Salir al menú"};
        JButton[] botones = new JButton[opciones.length];

        for (int i = 0; i < opciones.length; i++) {
            final int idx = i;
            botones[i] = new JButton(opciones[i]);
            botones[i].setEnabled(false);
            botones[i].setFont(Recursos.Fuentes.fuenteComic(Font.PLAIN, 18));
            botones[i].addActionListener(e -> {
                dialog.setVisible(false);
                dialog.dispose();
                switch (idx) {
                    case 0:
                        LogicaJuego.getInstance().newGame(id, categoria, dificultad);
                        break;
                    case 1:
                        ventana.cambiarVista(Constantes.PREGAME);
                        break;
                    default:
                        ventana.cambiarVista(Constantes.INICIO);
                        break;
                }
            });
            panelBotones.add(botones[i]);
        }

        Timer timerHabilitar = new Timer(1500, e -> {
            for (JButton b : botones) {
                b.setEnabled(true);
            }
        });
        timerHabilitar.setRepeats(false);
        timerHabilitar.start();

        dialog.add(panelBotones, java.awt.BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(juegoPanel);
        dialog.setVisible(true);
    }

    public void mostrarCategoriaCompletada(String nombreCategoria) {
        JOptionPane.showMessageDialog(
                juegoPanel,
                "¡Felicidades! Has descubierto todas las palabras de la categoría: " + nombreCategoria + ".\n\n" +
                "Esta categoría queda bloqueada para tu jugador.",
                "Categoría Completada",
                JOptionPane.INFORMATION_MESSAGE
        );
        ventana.cambiarVista(Constantes.PREGAME);
    }

    public void refrescarDatosJugador() {
        ventana.getPnlHeader().actualizarDatosJugador();
        ventana.getInicio().actualizarDatos();
    }

    public void mostrarLogrosEnPartida(List<LogroId> logros) {
        if (logros == null || logros.isEmpty()) return;
        LogrosPanel.mostrarPopupRT(logros, juegoPanel);
    }

    private void controlarTeclas(ActionEvent e) {

        JButton boton = (JButton) e.getSource();

        char tecla = boton.getText().charAt(0);
        LogicaJuego.getInstance().probarLetra(tecla);
    }
}
