package com.titomonito.services;

import com.titomonito.config.Constantes;
import com.titomonito.controller.ControlJuego;
import com.titomonito.dao.JuegoDAO;
import com.titomonito.dao.JugadorDAO;
import com.titomonito.models.Jugador;
import com.titomonito.models.Palabra;
import com.titomonito.ui.vistas.JuegoPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    private boolean juegoActivo = false;
    private char[] palabraIncompleta;
    private int letrasIncognitas;
    private int vidas;
    private ArrayList<String> corazones;
    private int letrasDescubiertas;
    private int tiempoBase;
    private int tiempoRestante;
    private int tiempoBonusTurno;
    private int tiempoBonusAcumulado;
    private Timer timer;

    //Variables para el sistema de economía
    private int monedasGanadas;
    private double porcentajeDescubierto;
    private int totalAsegurado;

    // Flags de utiles ya comprados en la partida actual
    private boolean sacapuntasUsado = false;
    private boolean tijerasUsado = false;
    private boolean gomaUsado = false;
    private boolean plumaUsado = false;
    private boolean marcatextosUsado = false;

    // Contexto del jugador logueado
    private Jugador jugadorActual;
    private int bancoInicial;

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
        resetearUtilesUsados();

        // Se obtiene el jugador actual y se guarda su banco inicial
        this.jugadorActual = SesionManager.getInstance().getJugadorActual();
        this.bancoInicial = jugadorActual.getMonedas_actuales();

        this.palabraObtenida = JuegoDAO.obtenerPalabra(id_categoria, jugadorActual.getId_jugador());
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
        tiempoBonusTurno = 0;
        tiempoBonusAcumulado = 0;
        if (vistaJuego != null) {
            vistaJuego.setLblValTiempo(tiempoRestante);
            vistaJuego.reiniciarPista();
            vistaJuego.actualizarEstadoBotonesTienda(jugadorActual.getMonedas_actuales(), dificultad);
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

            int tiempoTotalAlInicio = tiempoBase + tiempoBonusTurno;
            int tiempoConsumido = tiempoTotalAlInicio - tiempoRestante;
            int bonusConsumido = Math.min(tiempoConsumido, tiempoBonusTurno);
            if (bonusConsumido >= tiempoBonusTurno) {
                tiempoBonusAcumulado = 0;
            } else {
                tiempoBonusAcumulado = tiempoBonusTurno - bonusConsumido;
            }

            vistaJuego.setLblPalabra(UtilsJuego.construirPalabra(palabraIncompleta));
            vistaJuego.setLblValAsegurado(String.valueOf(totalAsegurado));

        } else {
            vidas--;
            tiempoBonusAcumulado = 0;
            notificarCambioEstado();
            vistaJuego.setLblValVidas(UtilsJuego.calcularCorazones(vidas));
            vistaJuego.dibujarTito(UtilsJuego.obtenerDibujo(vidas));
            vistaJuego.setLblValPotencial(String.valueOf(UtilsJuego.calcularPremioPotencial(vidas, palabraSecreta.length(), this.dificultad)));
        }

        vistaJuego.setTeclaHabilitada(String.valueOf(letra), false);
        comprobarEstadoPartida();

        if (juegoActivo) {
            iniciarNuevoTurno();
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

    private void liquidarPartida(boolean gano) {
        if (jugadorActual == null) return;

        int idJugador = jugadorActual.getId_jugador();
        int monedasMaximasActuales = jugadorActual.getMonedas_maximas();
        int rachaActualActual = jugadorActual.getRacha_actual();
        int rachaMaximaActual = jugadorActual.getRacha_maxima();

        int monedasFinales;
        int nuevasMonedasMaximas = monedasMaximasActuales;
        int nuevaRachaActual;
        int nuevaRachaMaxima = rachaMaximaActual;

        if (gano) {
            // Victoria: bancoInicial + monedasGanadas (con multiplicador) + bonoVictoria + bonoVidas
            double mult = UtilsJuego.MULTIPLICADORES.getOrDefault(dificultad, 1.0);
            int bonoVictoria = (int) (10 * mult);
            int bonoVidas = vidas;
            monedasFinales = bancoInicial + monedasGanadas + bonoVictoria + bonoVidas;

            // Solo actualizar monedasMaximas si se supera el récord
            if (monedasFinales > monedasMaximasActuales) {
                nuevasMonedasMaximas = monedasFinales;
            }

            // Racha: incrementar actual y posiblemente actualizar máxima
            nuevaRachaActual = rachaActualActual + 1;
            if (nuevaRachaActual > rachaMaximaActual) {
                nuevaRachaMaxima = nuevaRachaActual;
            }

            // Registrar la palabra como descubierta
            JugadorDAO.registrarDescubrimiento(idJugador, palabraObtenida.getId_palabra());
        } else {
            // Derrota: bancoInicial + totalAsegurado (ya penalizado proporcionalmente)
            monedasFinales = bancoInicial + totalAsegurado;

            // Racha: resetear actual
            nuevaRachaActual = 0;
        }

        // Persistir en BD
        JugadorDAO.actualizarMonedas(idJugador, monedasFinales, nuevasMonedasMaximas);
        JugadorDAO.actualizarRachas(idJugador, nuevaRachaActual, nuevaRachaMaxima);

        // Actualizar el objeto en memoria (cache)
        jugadorActual.setMonedas_actuales(monedasFinales);
        jugadorActual.setMonedas_maximas(nuevasMonedasMaximas);
        jugadorActual.setRacha_actual(nuevaRachaActual);
        jugadorActual.setRacha_maxima(nuevaRachaMaxima);

        // Refrescar UI en background (sin forzar navegación)
        if (controlJuego != null) {
            controlJuego.refrescarDatosJugador();
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

        liquidarPartida(juegoGanado);
        controlJuego.mostrarResultado(titulo, mensaje, this.id_categoria, this.categoria, this.dificultad);
    }

    public void setVistaJuego(JuegoPanel vistaJuego) {
        this.vistaJuego = vistaJuego;
    }

    public int getVidas() {
        return vidas;
    }

    public boolean puedeComprar(String util) {
        Jugador j = SesionManager.getInstance().getJugadorActual();
        if (j == null) return false;
        int saldo = j.getMonedas_actuales();

        if (Constantes.UTIL_SACAPUNTAS.equals(util)) {
            return saldo >= Constantes.PRECIO_SACAPUNTAS;
        }
        if (Constantes.UTIL_TIJERAS.equals(util)) {
            return !tijerasUsado && saldo >= Constantes.PRECIO_TIJERAS && vidas < 6;
        }
        if (Constantes.UTIL_GOMA.equals(util)) {
            return !gomaUsado && saldo >= Constantes.PRECIO_GOMA;
        }
        if (Constantes.UTIL_PLUMA.equals(util)) {
            return !plumaUsado && saldo >= Constantes.PRECIO_PLUMA;
        }
        if (Constantes.UTIL_MARCATEXTOS.equals(util)) {
            return !marcatextosUsado
                    && dificultad != Constantes.DIFICULTAD_IMPOSIBLE
                    && saldo >= Constantes.PRECIO_MARCATEXTOS;
        }
        return false;
    }

    private void resetearUtilesUsados() {
        sacapuntasUsado = false;
        tijerasUsado = false;
        gomaUsado = false;
        plumaUsado = false;
        marcatextosUsado = false;
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

    public boolean isJuegoActivo() {
        return juegoActivo;
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

    private void iniciarNuevoTurno() {
        tiempoBonusTurno = tiempoBonusAcumulado;
        tiempoRestante = tiempoBase + tiempoBonusTurno;
        tiempoBonusAcumulado = 0;
        if (vistaJuego != null) {
            vistaJuego.setLblValTiempo(tiempoRestante);
        }
    }

    private void notificarCambioEstado() {
        if (vistaJuego != null && jugadorActual != null) {
            vistaJuego.actualizarEstadoBotonesTienda(jugadorActual.getMonedas_actuales(), dificultad);
        }
    }

    public boolean comprarSacapuntas() {
        Jugador j = SesionManager.getInstance().getJugadorActual();
        if (j == null) return false;
        if (j.getMonedas_actuales() < Constantes.PRECIO_SACAPUNTAS) return false;

        int nuevasMonedas = j.getMonedas_actuales() - Constantes.PRECIO_SACAPUNTAS;
        int nuevasMaximas = Math.max(j.getMonedas_maximas(), nuevasMonedas);
        JugadorDAO.actualizarMonedas(j.getId_jugador(), nuevasMonedas, nuevasMaximas);

        j.setMonedas_actuales(nuevasMonedas);
        j.setMonedas_maximas(nuevasMaximas);
        bancoInicial = nuevasMonedas;

        tiempoRestante += Constantes.BONUS_SACAPUNTAS;
        tiempoBonusTurno += Constantes.BONUS_SACAPUNTAS;
        sacapuntasUsado = true;

        if (controlJuego != null) controlJuego.refrescarDatosJugador();
        if (vistaJuego != null) {
            vistaJuego.setLblValTiempo(tiempoRestante);
        }
        notificarCambioEstado();
        return true;
    }

    public boolean comprarTijeras() {
        Jugador j = SesionManager.getInstance().getJugadorActual();
        if (j == null) return false;
        if (j.getMonedas_actuales() < Constantes.PRECIO_TIJERAS) return false;
        if (vidas >= 6) return false;

        int nuevasMonedas = j.getMonedas_actuales() - Constantes.PRECIO_TIJERAS;
        int nuevasMaximas = Math.max(j.getMonedas_maximas(), nuevasMonedas);
        JugadorDAO.actualizarMonedas(j.getId_jugador(), nuevasMonedas, nuevasMaximas);

        j.setMonedas_actuales(nuevasMonedas);
        j.setMonedas_maximas(nuevasMaximas);
        bancoInicial = nuevasMonedas;

        vidas++;
        tijerasUsado = true;
        if (vistaJuego != null) {
            vistaJuego.setLblValVidas(UtilsJuego.calcularCorazones(vidas));
            vistaJuego.deshabilitarBoton(Constantes.UTIL_TIJERAS);
        }

        if (controlJuego != null) controlJuego.refrescarDatosJugador();
        notificarCambioEstado();
        return true;
    }

    public boolean comprarGoma() {
        Jugador j = SesionManager.getInstance().getJugadorActual();
        if (j == null) return false;
        if (j.getMonedas_actuales() < Constantes.PRECIO_GOMA) return false;

        int nuevasMonedas = j.getMonedas_actuales() - Constantes.PRECIO_GOMA;
        int nuevasMaximas = Math.max(j.getMonedas_maximas(), nuevasMonedas);
        JugadorDAO.actualizarMonedas(j.getId_jugador(), nuevasMonedas, nuevasMaximas);

        j.setMonedas_actuales(nuevasMonedas);
        j.setMonedas_maximas(nuevasMaximas);
        bancoInicial = nuevasMonedas;

        if (vistaJuego != null && palabraSecreta != null) {
            List<String> disponibles = new ArrayList<>();
            for (char c : Constantes.ALFABETO.toCharArray()) {
                String letra = String.valueOf(c);
                if (palabraSecreta.toUpperCase().indexOf(letra) >= 0) continue;
                JButton tecla = vistaJuego.getTecla(letra);
                if (tecla != null && tecla.isEnabled()) {
                    disponibles.add(letra);
                }
            }
            java.util.Collections.shuffle(disponibles);
            int cantidad = Math.min(4, disponibles.size());
            for (int i = 0; i < cantidad; i++) {
                vistaJuego.setTeclaHabilitada(disponibles.get(i), false);
            }
            vistaJuego.deshabilitarBoton(Constantes.UTIL_GOMA);
        }
        gomaUsado = true;

        if (controlJuego != null) controlJuego.refrescarDatosJugador();
        notificarCambioEstado();
        return true;
    }

    public boolean comprarPluma() {
        Jugador j = SesionManager.getInstance().getJugadorActual();
        if (j == null) return false;
        if (j.getMonedas_actuales() < Constantes.PRECIO_PLUMA) return false;
        if (palabraSecreta == null || palabraIncompleta == null) return false;

        int nuevasMonedas = j.getMonedas_actuales() - Constantes.PRECIO_PLUMA;
        int nuevasMaximas = Math.max(j.getMonedas_maximas(), nuevasMonedas);
        JugadorDAO.actualizarMonedas(j.getId_jugador(), nuevasMonedas, nuevasMaximas);

        j.setMonedas_actuales(nuevasMonedas);
        j.setMonedas_maximas(nuevasMaximas);
        bancoInicial = nuevasMonedas;

        java.util.Map<Character, Integer> conteo = new java.util.LinkedHashMap<>();
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (palabraIncompleta[i] == '_') {
                char c = palabraSecreta.charAt(i);
                conteo.merge(c, 1, Integer::sum);
            }
        }

        if (!conteo.isEmpty()) {
            int maxOcurrencias = Collections.max(conteo.values());
            List<Character> candidatas = new ArrayList<>();
            for (java.util.Map.Entry<Character, Integer> e : conteo.entrySet()) {
                if (e.getValue() == maxOcurrencias) candidatas.add(e.getKey());
            }
            char elegida = candidatas.get(new java.util.Random().nextInt(candidatas.size()));

            for (int i = 0; i < palabraSecreta.length(); i++) {
                if (palabraSecreta.charAt(i) == elegida) {
                    palabraIncompleta[i] = elegida;
                    letrasIncognitas--;
                }
            }
            letrasDescubiertas = palabraSecreta.length() - letrasIncognitas;
            monedasGanadas += conteo.get(elegida) * 2;
            porcentajeDescubierto = (double) letrasDescubiertas / palabraSecreta.length();
            totalAsegurado = (int) Math.round(monedasGanadas * porcentajeDescubierto);

            if (vistaJuego != null) {
                vistaJuego.setLblPalabra(UtilsJuego.construirPalabra(palabraIncompleta));
                vistaJuego.setLblValAsegurado(String.valueOf(totalAsegurado));
                vistaJuego.setTeclaHabilitada(String.valueOf(elegida), false);
                vistaJuego.deshabilitarBoton(Constantes.UTIL_PLUMA);
            }
        }
        plumaUsado = true;

        if (controlJuego != null) controlJuego.refrescarDatosJugador();
        notificarCambioEstado();

        comprobarEstadoPartida();
        return true;
    }

    public boolean comprarMarcatextos() {
        Jugador j = SesionManager.getInstance().getJugadorActual();
        if (j == null) return false;
        if (dificultad == Constantes.DIFICULTAD_IMPOSIBLE) return false;
        if (j.getMonedas_actuales() < Constantes.PRECIO_MARCATEXTOS) return false;

        int nuevasMonedas = j.getMonedas_actuales() - Constantes.PRECIO_MARCATEXTOS;
        int nuevasMaximas = Math.max(j.getMonedas_maximas(), nuevasMonedas);
        JugadorDAO.actualizarMonedas(j.getId_jugador(), nuevasMonedas, nuevasMaximas);

        j.setMonedas_actuales(nuevasMonedas);
        j.setMonedas_maximas(nuevasMaximas);
        bancoInicial = nuevasMonedas;

        if (vistaJuego != null && palabraObtenida != null) {
            vistaJuego.setLblValPista(palabraObtenida.getPista());
            vistaJuego.deshabilitarBoton(Constantes.UTIL_MARCATEXTOS);
        }
        marcatextosUsado = true;

        if (controlJuego != null) controlJuego.refrescarDatosJugador();
        notificarCambioEstado();
        return true;
    }

    private void tick() {
        if (!juegoActivo) {
            detenerTiempo();
            return;
        }
        this.tiempoRestante--;
        if (this.tiempoRestante == 0) {
            this.vidas--;
            tiempoBonusAcumulado = 0;
            notificarCambioEstado();
            if (vistaJuego != null) {
                vistaJuego.setLblValVidas(UtilsJuego.calcularCorazones(vidas));
                vistaJuego.setLblValPotencial(String.valueOf(UtilsJuego.calcularPremioPotencial(vidas, palabraSecreta.length(), this.dificultad)));
                vistaJuego.mostrarFeedbackTiempoAgotado();
            }
            if (this.vidas == 0) {
                detenerTiempo();
                comprobarEstadoPartida();
                return;
            }
            iniciarNuevoTurno();
        }
        if (vistaJuego != null) {
            vistaJuego.setLblValTiempo(this.tiempoRestante);
        }
    }
}
