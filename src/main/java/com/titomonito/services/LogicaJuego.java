package com.titomonito.services;

import com.titomonito.config.Constantes;
import com.titomonito.controller.ControlJuego;
import com.titomonito.dao.JuegoDAO;
import com.titomonito.dao.JugadorDAO;
import com.titomonito.enums.LogroId;
import com.titomonito.models.Jugador;
import com.titomonito.models.Palabra;
import com.titomonito.models.SnapshotPartida;
import com.titomonito.ui.vistas.JuegoPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogicaJuego {
    private static LogicaJuego instance;
    private ControlJuego controlJuego;
    private JuegoPanel vistaJuego;

    private static final int VIDAS_MAX = 6;
    private int id_categoria;
    private String categoria;
    private Palabra palabraObtenida;
    private String palabraSecreta;
    private int dificultad;

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

    private int monedasGanadas;
    private double porcentajeDescubierto;
    private int totalAsegurado;

    private boolean sacapuntasUsado = false;
    private boolean tijerasUsado = false;
    private boolean gomaUsado = false;
    private boolean plumaUsado = false;
    private boolean marcatextosUsado = false;

    private Jugador jugadorActual;
    private int bancoInicial;

    private int erroresEnPartida = 0;
    private int letrasCorrectasEnPartida = 0;
    private int montoGastadoEnUtiles = 0;
    private final Set<String> utilesUsadosSet = new HashSet<>();

    private int utilesTierBasicoComprados = 0;
    private int utilesTierMedioComprados = 0;
    private int utilesTierCaroComprados = 0;

    public static LogicaJuego getInstance() {
        if (instance == null) {
            instance = new LogicaJuego();
        }
        return instance;
    }

    public void newGame(int id_categoria, String nombreCategoria, int dificultad) {
        this.id_categoria = id_categoria;
        this.categoria = nombreCategoria;
        this.dificultad = dificultad;

        this.tiempoBase = UtilsJuego.getTiempoBase(this.dificultad);
        vidas = VIDAS_MAX;
        corazones = new ArrayList<>();
        monedasGanadas = 0;
        totalAsegurado = 0;
        juegoActivo = true;
        resetearUtilesUsados();

        this.erroresEnPartida = 0;
        this.letrasCorrectasEnPartida = 0;
        this.montoGastadoEnUtiles = 0;
        this.utilesUsadosSet.clear();
        this.utilesTierBasicoComprados = 0;
        this.utilesTierMedioComprados = 0;
        this.utilesTierCaroComprados = 0;

        this.jugadorActual = SesionManager.getInstance().getJugadorActual();
        this.bancoInicial = jugadorActual.getMonedas_actuales();

        this.palabraObtenida = JuegoDAO.obtenerPalabra(id_categoria, jugadorActual.getId_jugador());
        if (palabraObtenida == null) {
            if (controlJuego != null) {
                controlJuego.mostrarCategoriaCompletada(nombreCategoria);
            }
            juegoActivo = false;
            return;
        }
        this.palabraSecreta = palabraObtenida.getPalabra();
        this.letrasIncognitas = palabraSecreta.length();
        System.out.println(palabraSecreta);

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
            letrasCorrectasEnPartida++;

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
            erroresEnPartida++;
            vidas--;
            tiempoBonusAcumulado = 0;
            notificarCambioEstado();
            vistaJuego.setLblValVidas(UtilsJuego.calcularCorazones(vidas));
            vistaJuego.dibujarTito(UtilsJuego.obtenerDibujo(vidas));
            vistaJuego.setLblValPotencial(String.valueOf(UtilsJuego.calcularPremioPotencial(vidas, palabraSecreta.length(), this.dificultad)));
            if (vistaJuego != null) {
                vistaJuego.sacudir();
            }
        }

        vistaJuego.feedbackTecla(String.valueOf(letra), letraEncontrada);
        ServicioSonido.getInstance().reproducir(letraEncontrada ? "tecla_acierto" : "tecla_error");
        List<LogroId> nuevos = comprobarEstadoPartida();

        if (juegoActivo) {
            iniciarNuevoTurno();
        }
    }

    private List<LogroId> comprobarEstadoPartida() {
        if (String.valueOf(palabraIncompleta).equals(palabraSecreta)) {
            juegoActivo = false;
            vistaJuego.setTeclado(false);
            return calcularResultado(true);
        }

        if (vidas == 0 && letrasIncognitas > 0) {
            juegoActivo = false;
            vistaJuego.setTeclado(false);
            return calcularResultado(false);
        }

        return new ArrayList<>();
    }

    private List<LogroId> calcularResultado(boolean juegoGanado) {
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
        } else {
            int letrasDescubiertasFinal = palabraSecreta.length() - letrasIncognitas;
            double porcentajeDescubiertoFinal = (double) letrasDescubiertasFinal / palabraSecreta.length();

            String monedaS;
            if (totalAsegurado == 1) {
                monedaS = " moneda ";
            } else monedaS = " monedas ";

            String letraS;
            if (letrasDescubiertasFinal == 1) {
                letraS = " triste letra ";
            } else letraS = " letras ";

            if (porcentajeDescubiertoFinal >= 0.70) {
                mensaje = "<html>Perdiste, pero te quedaste muy cerca.<br>La palabra era <b>" + palabraSecreta +
                "<br><br></b>Te llevas " + totalAsegurado + monedaS + "por descubrir el " +
                        String.format("%.1f", porcentajeDescubiertoFinal * 100) + "% de la palabra</html>";
            } else if (porcentajeDescubiertoFinal <= 0.40) {
                mensaje = "<html>Perdiste sin esforzarte, nunca sabras la palabra." +
                        "<br><br>Solo conseguiste <b>" + totalAsegurado + "</b>" + monedaS + "por encontrar " +
                        letrasDescubiertasFinal + letraS + "</html>";
            } else {
                mensaje = "<html>Perdiste y no descubriste lo suficiente.<br>Pequeña pista: " +
                        palabraObtenida.getPista() + "<br><br>Por tu esfuerzo te quedas con <b>" + totalAsegurado +
                        "</b>" + monedaS + "por descubrir " + letrasDescubiertasFinal + letraS + "</html>";
            }
        }

        List<LogroId> logros = liquidarPartida(juegoGanado);
        controlJuego.mostrarLogrosEnPartida(logros);
        ServicioSonido.getInstance().reproducir(juegoGanado ? "victoria" : "derrota");
        controlJuego.mostrarResultado(titulo, mensaje, this.id_categoria, this.categoria, this.dificultad, juegoGanado);
        return logros;
    }

    private List<LogroId> liquidarPartida(boolean gano) {
        if (jugadorActual == null) return new ArrayList<>();

        int idJugador = jugadorActual.getId_jugador();
        int monedasMaximasActuales = jugadorActual.getMonedas_maximas();
        int rachaActualActual = jugadorActual.getRacha_actual();
        int rachaMaximaActual = jugadorActual.getRacha_maxima();

        int monedasFinales;
        int nuevasMonedasMaximas = monedasMaximasActuales;
        int nuevaRachaActual;
        int nuevaRachaMaxima = rachaMaximaActual;

        if (gano) {
            double mult = UtilsJuego.MULTIPLICADORES.getOrDefault(dificultad, 1.0);
            int bonoVictoria = (int) (10 * mult);
            int bonoVidas = vidas;
            monedasFinales = bancoInicial + monedasGanadas + bonoVictoria + bonoVidas;

            if (monedasFinales > monedasMaximasActuales) {
                nuevasMonedasMaximas = monedasFinales;
            }

            nuevaRachaActual = rachaActualActual + 1;
            if (nuevaRachaActual > rachaMaximaActual) {
                nuevaRachaMaxima = nuevaRachaActual;
            }

            JugadorDAO.registrarDescubrimiento(idJugador, palabraObtenida.getId_palabra());
        } else {
            monedasFinales = bancoInicial + totalAsegurado;
            nuevaRachaActual = 0;
        }

        JugadorDAO.actualizarMonedas(idJugador, monedasFinales, nuevasMonedasMaximas);
        JugadorDAO.actualizarRachas(idJugador, nuevaRachaActual, nuevaRachaMaxima);

        jugadorActual.setMonedas_actuales(monedasFinales);
        jugadorActual.setMonedas_maximas(nuevasMonedasMaximas);
        jugadorActual.setRacha_actual(nuevaRachaActual);
        jugadorActual.setRacha_maxima(nuevaRachaMaxima);

        List<LogroId> logrosRT = new ArrayList<>();

        if (gano) {
            SesionJuegoTracker.getInstance().registrarVictoria(dificultad, id_categoria);

            int letrasCorrectas = palabraSecreta.length() - letrasIncognitas;
            int errores = VIDAS_MAX - vidas;

            SnapshotPartida snap = new SnapshotPartida.Builder()
                    .idJugador(idJugador)
                    .gano(true)
                    .vidasRestantes(vidas)
                    .errores(errores)
                    .dificultad(dificultad)
                    .idCategoria(id_categoria)
                    .usoSacapuntas(sacapuntasUsado)
                    .usoTijeras(tijerasUsado)
                    .usoGoma(gomaUsado)
                    .usoPluma(plumaUsado)
                    .usoMarcatextos(marcatextosUsado)
                    .utilesCount(utilesUsadosSet.size())
                    .monedasObtenidas(monedasFinales - bancoInicial)
                    .tiempoRestanteAlFinal(tiempoRestante)
                    .letrasCorrectas(letrasCorrectas)
                    .longitudPalabra(palabraSecreta != null ? palabraSecreta.length() : 0)
                    .build();

            logrosRT = LogrosService.getInstance().evaluarLogrosEnPartida(snap);
        } else {
            SesionJuegoTracker.getInstance().registrarDerrotaOAbandono();
        }

        if (controlJuego != null) {
            controlJuego.refrescarDatosJugador();
        }

        return logrosRT;
    }

    public void setVistaJuego(JuegoPanel vistaJuego) {
        this.vistaJuego = vistaJuego;
    }

    public int getVidas() {
        return vidas;
    }

    public int getUtilesCount() {
        return utilesUsadosSet.size();
    }

    public boolean puedeComprar(String util) {
        Jugador j = SesionManager.getInstance().getJugadorActual();
        if (j == null) return false;
        int saldo = j.getMonedas_actuales();

        if (Constantes.UTIL_TIJERAS.equals(util) && (tijerasUsado || vidas >= 6)) return false;
        if (Constantes.UTIL_GOMA.equals(util) && gomaUsado) return false;
        if (Constantes.UTIL_PLUMA.equals(util) && plumaUsado) return false;
        if (Constantes.UTIL_MARCATEXTOS.equals(util)
                && (marcatextosUsado || dificultad == Constantes.DIFICULTAD_IMPOSIBLE)) return false;

        int precio = Constantes.obtenerPrecio(util);
        if (saldo < precio) return false;

        if (montoGastadoEnUtiles + precio > Constantes.obtenerCapGastoPorDificultad(dificultad)) return false;

        String tier = Constantes.obtenerTier(util);
        if (tier == null) return false;
        int usadosTier = switch (tier) {
            case Constantes.TIER_BASICO -> utilesTierBasicoComprados;
            case Constantes.TIER_MEDIO -> utilesTierMedioComprados;
            case Constantes.TIER_CARO -> utilesTierCaroComprados;
            default -> Integer.MAX_VALUE;
        };
        int maxPermitido = switch (tier) {
            case Constantes.TIER_BASICO -> Constantes.obtenerCapTierPorDificultad(dificultad)[0];
            case Constantes.TIER_MEDIO -> Constantes.obtenerCapTierPorDificultad(dificultad)[1];
            case Constantes.TIER_CARO -> Constantes.obtenerCapTierPorDificultad(dificultad)[2];
            default -> 0;
        };
        if (usadosTier >= maxPermitido) return false;

        return true;
    }

    private void incrementarTier(String util) {
        String tier = Constantes.obtenerTier(util);
        if (tier == null) return;
        switch (tier) {
            case Constantes.TIER_BASICO -> utilesTierBasicoComprados++;
            case Constantes.TIER_MEDIO -> utilesTierMedioComprados++;
            case Constantes.TIER_CARO -> utilesTierCaroComprados++;
        }
    }

    private void resetearUtilesUsados() {
        sacapuntasUsado = false;
        tijerasUsado = false;
        gomaUsado = false;
        plumaUsado = false;
        marcatextosUsado = false;
        utilesUsadosSet.clear();
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

    public void abandonarPartida() {
        if (!juegoActivo) return;
        detenerTiempo();
        juegoActivo = false;
        if (vistaJuego != null) {
            vistaJuego.setTeclado(false);
        }

        if (jugadorActual == null) return;

        int idJugador = jugadorActual.getId_jugador();
        int monedasActuales = jugadorActual.getMonedas_actuales();
        int monedasMaximas = jugadorActual.getMonedas_maximas();
        int rachaMaxima = jugadorActual.getRacha_maxima();

        JugadorDAO.actualizarMonedas(idJugador, monedasActuales, monedasMaximas);
        JugadorDAO.actualizarRachas(idJugador, 0, rachaMaxima);

        jugadorActual.setRacha_actual(0);

        SesionJuegoTracker.getInstance().registrarDerrotaOAbandono();

        monedasGanadas = 0;
        totalAsegurado = 0;
        letrasDescubiertas = 0;

        if (controlJuego != null) {
            controlJuego.refrescarDatosJugador();
        }
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
        utilesUsadosSet.add(Constantes.UTIL_SACAPUNTAS);
        montoGastadoEnUtiles += Constantes.PRECIO_SACAPUNTAS;
        incrementarTier(Constantes.UTIL_SACAPUNTAS);

        if (controlJuego != null) controlJuego.refrescarDatosJugador();
        if (vistaJuego != null) {
            vistaJuego.setLblValTiempo(tiempoRestante);
        }
        notificarCambioEstado();
        ServicioSonido.getInstance().reproducir("powerup");
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
        utilesUsadosSet.add(Constantes.UTIL_TIJERAS);
        montoGastadoEnUtiles += Constantes.PRECIO_TIJERAS;
        incrementarTier(Constantes.UTIL_TIJERAS);
        if (vistaJuego != null) {
            vistaJuego.setLblValVidas(UtilsJuego.calcularCorazones(vidas));
            vistaJuego.dibujarTito(UtilsJuego.obtenerDibujo(vidas));
            vistaJuego.deshabilitarBoton(Constantes.UTIL_TIJERAS);
        }

        if (controlJuego != null) controlJuego.refrescarDatosJugador();
        notificarCambioEstado();
        ServicioSonido.getInstance().reproducir("powerup");
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
        utilesUsadosSet.add(Constantes.UTIL_GOMA);
        montoGastadoEnUtiles += Constantes.PRECIO_GOMA;
        incrementarTier(Constantes.UTIL_GOMA);

        if (controlJuego != null) controlJuego.refrescarDatosJugador();
        notificarCambioEstado();
        ServicioSonido.getInstance().reproducir("powerup");
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
        utilesUsadosSet.add(Constantes.UTIL_PLUMA);
        montoGastadoEnUtiles += Constantes.PRECIO_PLUMA;
        incrementarTier(Constantes.UTIL_PLUMA);

        if (controlJuego != null) controlJuego.refrescarDatosJugador();
        notificarCambioEstado();
        ServicioSonido.getInstance().reproducir("powerup");

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
        utilesUsadosSet.add(Constantes.UTIL_MARCATEXTOS);
        montoGastadoEnUtiles += Constantes.PRECIO_MARCATEXTOS;
        incrementarTier(Constantes.UTIL_MARCATEXTOS);

        if (controlJuego != null) controlJuego.refrescarDatosJugador();
        notificarCambioEstado();
        ServicioSonido.getInstance().reproducir("powerup");
        return true;
    }

    private void tick() {
        if (!juegoActivo) {
            detenerTiempo();
            return;
        }
        this.tiempoRestante--;
        boolean vidaPerdidaPorTiempo = false;
        if (this.tiempoRestante == 0) {
            this.vidas--;
            vidaPerdidaPorTiempo = true;
            tiempoBonusAcumulado = 0;
            notificarCambioEstado();
            if (vistaJuego != null) {
                vistaJuego.setLblValVidas(UtilsJuego.calcularCorazones(vidas));
                vistaJuego.setLblValPotencial(String.valueOf(UtilsJuego.calcularPremioPotencial(vidas, palabraSecreta.length(), this.dificultad)));
                vistaJuego.dibujarTito(UtilsJuego.obtenerDibujo(vidas));
                vistaJuego.mostrarFeedbackTiempoAgotado();
            }
            if (this.vidas == 0) {
                detenerTiempo();
                comprobarEstadoPartida();
                return;
            }
            ServicioSonido.getInstance().reproducir("lesion");
            iniciarNuevoTurno();
        }
        if (!vidaPerdidaPorTiempo && this.tiempoRestante > 0 && this.tiempoRestante <= 5) {
            ServicioSonido.getInstance().reproducir("tic-tac");
        }
        if (vistaJuego != null) {
            vistaJuego.setLblValTiempo(this.tiempoRestante);
        }
    }
}
