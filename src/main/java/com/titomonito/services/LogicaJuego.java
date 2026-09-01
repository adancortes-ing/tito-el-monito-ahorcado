package com.titomonito.services;

import com.titomonito.controller.ControlJuego;
import com.titomonito.dao.JuegoDAO;
import com.titomonito.dao.JugadorDAO;
import com.titomonito.models.Jugador;
import com.titomonito.models.Palabra;
import com.titomonito.ui.vistas.JuegoPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LogicaJuego {
    // Variables de comunicación
    private static LogicaJuego instance;
    private ControlJuego controlJuego;
    private JuegoPanel vistaJuego;

    //Variables de configuración de partida
    private static final int VIDAS_MAX = 6;
    private int id_categoria;
    private String categoria;
    private Palabra palabraObtenida;
    private String palabraSecreta;
    private int dificultad;

    //Variables que cambian durante la partida
    private boolean juegoActivo;
    private char[] palabraIncompleta;
    private int letrasIncognitas;
    private int vidas;
    private ArrayList<String> corazones;
    private int letrasDescubiertas;
    private int tiempoBase;
    private int tiempoRestante;
    private Timer timer;

    //Variables para el sistema de economía
    private int monedasGanadas;
    private double porcentajeDescubierto;
    private int totalAsegurado;

    // Crea una instancia de esta clase cuando no existe
    public static LogicaJuego getInstance() {
        if (instance == null) {
            instance = new LogicaJuego();
        }
        return instance;
    }

    // Configuración inicial para una nueva partida
    public void newGame(int id_categoria, String nombreCategoria, int dificultad) {
        this.id_categoria = id_categoria;
        this.categoria = nombreCategoria;
        this.dificultad = dificultad;

        //Parámetros de la nueva partida
        this.tiempoBase = UtilsJuego.getTiempoBase(this.dificultad);
        vidas = VIDAS_MAX;
        corazones = new ArrayList<>();
        monedasGanadas = 0;
        totalAsegurado = 0;
        juegoActivo = true;

        // Se usa jugador fijo 1 por ahora
        this.palabraObtenida = JuegoDAO.obtenerPalabra(id_categoria,
                SesionManager.getInstance().getJugadorActual().getId_jugador());
        assert palabraObtenida != null;
        this.palabraSecreta = palabraObtenida.getPalabra();
        this.letrasIncognitas = palabraSecreta.length();

        palabraIncompleta = new char[palabraSecreta.length()];
        Arrays.fill(palabraIncompleta, '_');

        vistaJuego.setLblValPotencial(String.valueOf(UtilsJuego.calcularPremioPotencial(vidas, palabraSecreta.length(), this.dificultad)));
        vistaJuego.setLblValAsegurado(String.valueOf(totalAsegurado));
        vistaJuego.setLblPalabra(UtilsJuego.construirPalabra(palabraIncompleta));
        vistaJuego.setLblValVidas(UtilsJuego.calcularCorazones(vidas));
        vistaJuego.setLblValCategoria(categoria);
        vistaJuego.setTeclado(true);
        vistaJuego.dibujarTito("game_horca.png");
        vistaJuego.reiniciarPista();

        tiempoRestante = tiempoBase;
        if (vistaJuego != null) {
            vistaJuego.setLblValTiempo(tiempoRestante);
        }
        iniciarTiempo();
    }

    public void probarLetra(char letra) {

        boolean letraEncontrada = false;

        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (letra == palabraSecreta.charAt(i)) {
                palabraIncompleta[i] = letra;
                letrasIncognitas--;
                letraEncontrada = true;
                letrasDescubiertas = palabraSecreta.length() - letrasIncognitas;
                monedasGanadas += 2;
            }
        }

        if (letraEncontrada) {

            porcentajeDescubierto = (double) letrasDescubiertas / palabraSecreta.length();
            totalAsegurado = (int) Math.round(monedasGanadas * porcentajeDescubierto);

            vistaJuego.setLblPalabra(UtilsJuego.construirPalabra(palabraIncompleta));
            vistaJuego.setLblValAsegurado(String.valueOf(totalAsegurado));

        } else {
            vidas--;
            vistaJuego.setLblValVidas(UtilsJuego.calcularCorazones(vidas));
            vistaJuego.dibujarTito(UtilsJuego.obtenerDibujo(vidas));
            vistaJuego.setLblValPotencial(String.valueOf(UtilsJuego.calcularPremioPotencial(vidas, palabraSecreta.length(), this.dificultad)));
        }

        vistaJuego.setTeclaHabilitada(String.valueOf(letra), false);
        comprobarEstadoPartida();

        if (juegoActivo) {
            this.tiempoRestante = this.tiempoBase;
            if (vistaJuego != null) {
                vistaJuego.setLblValTiempo(this.tiempoRestante);
            }
        }
    }

    private void comprobarEstadoPartida() {

        // Comprobar si la partida ha sido ganada
        if (String.valueOf(palabraIncompleta).equals(palabraSecreta)) {
            juegoActivo = false;
            vistaJuego.setTeclado(false);
            calcularResultado(true);
            return;
        }

        // Comprobar si la partida se ha perdido
        if (vidas == 0 && letrasIncognitas > 0) {
            juegoActivo = false;
            vistaJuego.setTeclado(false);
            calcularResultado(false);
        }
    }

    private void calcularResultado(boolean juegoGanado) {

        String titulo = juegoGanado ? "Salvaste a Tito" : "Tito a Muerto";
        String mensaje;

        if (juegoGanado) {
            mensaje = "<html>¡Ganaste! has descubierto la palabra: " + palabraSecreta +
                    "<br><b>Resultados de la partida:</b>" +
                    "<br>Premio base: ----------------- $10" +
                    "<br>Monedas por cada letra: ----- $" + totalAsegurado +
                    "<br>Bono por dificultad (x" + UtilsJuego.MULTIPLICADORES.get(dificultad) + "): -- $"  +
                    UtilsJuego.calcularBonoDificultad(this.dificultad) +
                    "<br>Bono por vidas restantes: ---- $" + vidas +
                    "<br><b>Total del premio:</b> ----------- $" +
                    (UtilsJuego.calcularPremioPotencial(vidas, palabraSecreta.length(), this.dificultad)) + "</html>";
        } else { // Lo que ocurre después de perder la partida
            int letrasDescubiertas = palabraSecreta.length() - letrasIncognitas;
            double porcentajeDescubierto = (double) letrasDescubiertas / palabraSecreta.length();

            String monedaS;
            if (totalAsegurado == 1) {
                monedaS = " moneda ";
            } else monedaS = " monedas ";

            String letraS;
            if (letrasDescubiertas == 1) {
                letraS = " triste letra ";
            } else letraS = " letras ";

            if (porcentajeDescubierto >= 0.70) {
                mensaje = "<html>Perdiste, pero te quedaste muy cerca.<br>La palabra era <b>" + palabraSecreta +
                "<br><br></b>Te llevas " + totalAsegurado + monedaS + "por descubrir el " +
                        String.format("%.1f", porcentajeDescubierto * 100) + "% de la palabra</html>";
            } else if (porcentajeDescubierto <= 0.40) {
                mensaje = "<html>Perdiste sin esforzarte, nunca sabras la palabra." +
                        "<br><br>Solo conseguiste <b>" + totalAsegurado + "</b>" + monedaS + "por encontrar " +
                        letrasDescubiertas + letraS + "</html>";
            } else {
                mensaje = "<html>Perdiste y no descubriste lo suficiente.<br>Pequeña pista: " +
                        palabraObtenida.getPista() + "<br><br>Por tu esfuerzo te quedas con <b>" + totalAsegurado +
                        "</b>" + monedaS + "por descubrir " + letrasDescubiertas + letraS + "</html>";
            }
        }

        controlJuego.mostrarResultado(titulo, mensaje, this.id_categoria, this.categoria, this.dificultad);
    }

    public void setVistaJuego(JuegoPanel vistaJuego) {
        this.vistaJuego = vistaJuego;
    }

    public void reiniciarCorazones() {
        corazones.clear();
    }

    public ArrayList<String> getCorazones() {
        return corazones;
    }

    public void setCorazones(String simbolo) {
        corazones.add(simbolo);
    }

    public void setControlJuego(ControlJuego controlJuego) {
        this.controlJuego = controlJuego;
    }

    private void iniciarTiempo() {
        detenerTiempo();
        timer = new Timer(1000, e -> tick());
        timer.start();
    }

    private void detenerTiempo() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    private void tick() {
        if (!juegoActivo) {
            detenerTiempo();
            return;
        }
        this.tiempoRestante--;
        if (this.tiempoRestante == 0) {
            this.vidas--;
            if (vistaJuego != null) {
                vistaJuego.setLblValVidas(UtilsJuego.calcularCorazones(vidas));
                vistaJuego.setLblValPotencial(String.valueOf(UtilsJuego.calcularPremioPotencial(vidas, palabraSecreta.length(), this.dificultad)));
            }
            if (this.vidas == 0) {
                detenerTiempo();
                comprobarEstadoPartida();
                return;
            }
            this.tiempoRestante = this.tiempoBase;
        }
        if (vistaJuego != null) {
            vistaJuego.setLblValTiempo(this.tiempoRestante);
        }
    }
}
