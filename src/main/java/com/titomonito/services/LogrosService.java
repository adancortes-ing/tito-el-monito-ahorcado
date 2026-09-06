package com.titomonito.services;

import com.titomonito.dao.JugadorDAO;
import com.titomonito.dao.LogrosDAO;
import com.titomonito.enums.LogroId;
import com.titomonito.models.Jugador;
import com.titomonito.models.SnapshotPartida;

import javax.swing.*;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogrosService {

    private static LogrosService instance;

    private LogrosService() {
    }

    public static LogrosService getInstance() {
        if (instance == null) {
            instance = new LogrosService();
        }
        return instance;
    }

    public List<LogroId> evaluarLogrosHistoricos(int idJugador) {
        List<LogroId> nuevos = new ArrayList<>();

        int palabrasDescubiertas = LogrosDAO.contarPalabrasDescubiertas(idJugador);
        int categoriasCompletas = LogrosDAO.contarCategoriasCompletas(idJugador);
        int rachaMaxima = LogrosDAO.obtenerRachaMaxima(idJugador);
        int monedasMaximas = LogrosDAO.obtenerMonedasMaximas(idJugador);
        int totalPalabras = LogrosDAO.contarPalabrasTotales();
        int dificultadMinimaLogros = 2; // Normal
        int dificultadMinimaExtremoLogros = 3; // Difícil

        if (palabrasDescubiertas >= 200 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_PAL_200.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_PAL_200.getCodigo());
            nuevos.add(LogroId.DB_PAL_200);
        }
        if (palabrasDescubiertas >= 800 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_PAL_800.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_PAL_800.getCodigo());
            nuevos.add(LogroId.DB_PAL_800);
        }
        if (palabrasDescubiertas >= 1600 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_PAL_1600.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_PAL_1600.getCodigo());
            nuevos.add(LogroId.DB_PAL_1600);
        }

        if (categoriasCompletas >= 10 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_CAT_10.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_CAT_10.getCodigo());
            nuevos.add(LogroId.DB_CAT_10);
        }
        if (categoriasCompletas >= 20 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_CAT_20.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_CAT_20.getCodigo());
            nuevos.add(LogroId.DB_CAT_20);
        }

        if (rachaMaxima >= 10 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_RAC_10.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_RAC_10.getCodigo());
            nuevos.add(LogroId.DB_RAC_10);
        }
        if (rachaMaxima >= 30 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_RAC_30.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_RAC_30.getCodigo());
            nuevos.add(LogroId.DB_RAC_30);
        }
        if (rachaMaxima >= 75 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_RAC_75.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_RAC_75.getCodigo());
            nuevos.add(LogroId.DB_RAC_75);
        }

        if (monedasMaximas >= 500 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_MON_500.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_MON_500.getCodigo());
            nuevos.add(LogroId.DB_MON_500);
        }
        if (monedasMaximas >= 2500 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_MON_2500.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_MON_2500.getCodigo());
            nuevos.add(LogroId.DB_MON_2500);
        }
        if (monedasMaximas >= 5000 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_MON_5000.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_MON_5000.getCodigo());
            nuevos.add(LogroId.DB_MON_5000);
        }

        if (totalPalabras > 0 && palabrasDescubiertas >= totalPalabras
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_TOTAL.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_TOTAL.getCodigo());
            nuevos.add(LogroId.DB_TOTAL);
        }

        if (!nuevos.isEmpty()) {
            int totalPremio = nuevos.stream().mapToInt(LogroId::getPremio).sum();
            pagarPremio(idJugador, totalPremio);
        }

        return nuevos;
    }

    public List<LogroId> evaluarLogrosEnPartida(SnapshotPartida snap) {
        List<LogroId> nuevos = new ArrayList<>();
        if (!snap.isGano()) return nuevos;

        int idJugador = snap.getIdJugador();
        SesionJuegoTracker tracker = SesionJuegoTracker.getInstance();

        int dificultadPartida = snap.getDificultad();

        if (snap.getVidasRestantes() == 6 && dificultadPartida >= 2) {
            int countPerfecta = LogrosDAO.contarLogrosDesbloqueados(idJugador, LogroId.RT_PERFECTA.getCodigo());
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_PERFECTA.getCodigo());
            if (countPerfecta + 1 >= 3) {
                nuevos.add(LogroId.RT_PERFECTA);
            }
        }
        if (snap.getVidasRestantes() == 5 && dificultadPartida >= 2) {
            int countCasi = LogrosDAO.contarLogrosDesbloqueados(idJugador, LogroId.RT_CASI_PERFECTA.getCodigo());
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_CASI_PERFECTA.getCodigo());
            if (countCasi + 1 >= 3) {
                nuevos.add(LogroId.RT_CASI_PERFECTA);
            }
        }
        if (snap.getVidasRestantes() == 1 && dificultadPartida >= 2) {
            int countMilagro = LogrosDAO.contarLogrosDesbloqueados(idJugador, LogroId.RT_MILAGRO.getCodigo());
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_MILAGRO.getCodigo());
            if (countMilagro + 1 >= 3) {
                nuevos.add(LogroId.RT_MILAGRO);
            }
        }

        if (!snap.isUsoAlgunUtil() && dificultadPartida >= 2) {
            int countSinUtiles = LogrosDAO.contarLogrosDesbloqueados(idJugador, LogroId.RT_SIN_UTILES.getCodigo());
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_SIN_UTILES.getCodigo());
            if (countSinUtiles + 1 >= 5) {
                nuevos.add(LogroId.RT_SIN_UTILES);
            }
        }
        if (snap.isUsoSacapuntas() && dificultadPartida >= 2) {
            int countSacapuntas = LogrosDAO.contarLogrosDesbloqueados(idJugador, LogroId.RT_SACAPUNTAS.getCodigo());
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_SACAPUNTAS.getCodigo());
            if (countSacapuntas + 1 >= 3) {
                nuevos.add(LogroId.RT_SACAPUNTAS);
            }
        }
        if (snap.isUsoMarcatextos() && dificultadPartida >= 3) {
            int countMarcatextos = LogrosDAO.contarLogrosDesbloqueados(idJugador, LogroId.RT_MARCATEXTOS.getCodigo());
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_MARCATEXTOS.getCodigo());
            if (countMarcatextos + 1 >= 3) {
                nuevos.add(LogroId.RT_MARCATEXTOS);
            }
        }

        if (tracker.getRachaExtremoSesion() >= 10
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_RACHA_EXTREMO.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_RACHA_EXTREMO.getCodigo());
            nuevos.add(LogroId.RT_RACHA_EXTREMO);
        }
        if (tracker.getRachaImposibleSesion() >= 10
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_RACHA_IMPOSIBLE.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_RACHA_IMPOSIBLE.getCodigo());
            nuevos.add(LogroId.RT_RACHA_IMPOSIBLE);
        }

        if (tracker.getRachaGlobalSesion() >= 10 && dificultadPartida >= 2
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_RACHA_SESION_10.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_RACHA_SESION_10.getCodigo());
            nuevos.add(LogroId.RT_RACHA_SESION_10);
        }
        if (tracker.getRachaGlobalSesion() >= 20 && dificultadPartida >= 2
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_RACHA_SESION_20.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_RACHA_SESION_20.getCodigo());
            nuevos.add(LogroId.RT_RACHA_SESION_20);
        }

        if (tracker.getCategoriasDistintasGanadas() >= 6
                && LogrosDAO.contarLogrosDesbloqueados(idJugador, LogroId.RT_CAT_6.getCodigo()) < 1) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_CAT_6.getCodigo());
            nuevos.add(LogroId.RT_CAT_6);
        }
        if (tracker.getCategoriasDistintasGanadas() >= 15
                && LogrosDAO.contarLogrosDesbloqueados(idJugador, LogroId.RT_CAT_15.getCodigo()) < 1) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_CAT_15.getCodigo());
            nuevos.add(LogroId.RT_CAT_15);
        }

        if (snap.getMonedasObtenidas() > 50 && dificultadPartida >= 2
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_PARTIDA_RICA.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_PARTIDA_RICA.getCodigo());
            nuevos.add(LogroId.RT_PARTIDA_RICA);
        }

        if (snap.getTiempoRestanteAlFinal() == 1
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_CONTRA_RELOJ.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_CONTRA_RELOJ.getCodigo());
            nuevos.add(LogroId.RT_CONTRA_RELOJ);
        }

        if (snap.getUtilesCount() >= 3
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_TRES_UTILES.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_TRES_UTILES.getCodigo());
            nuevos.add(LogroId.RT_TRES_UTILES);
        }
        if (snap.getUtilesCount() >= 5
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_CINCO_UTILES.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_CINCO_UTILES.getCodigo());
            nuevos.add(LogroId.RT_CINCO_UTILES);
        }

        if (snap.isGano() && snap.getVidasRestantes() == 1 && snap.getTiempoRestanteAlFinal() == 1
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_AL_FILO.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_AL_FILO.getCodigo());
            nuevos.add(LogroId.RT_AL_FILO);
        }

        if (snap.isGano() && snap.getVidasRestantes() == 6 && snap.getLongitudPalabra() >= 10
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_PALABRA_LARGA.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_PALABRA_LARGA.getCodigo());
            nuevos.add(LogroId.RT_PALABRA_LARGA);
        }

        if (!nuevos.isEmpty()) {
            int totalPremio = nuevos.stream().mapToInt(LogroId::getPremio).sum();
            pagarPremio(idJugador, totalPremio);
        }

        return nuevos;
    }

    private void pagarPremio(int idJugador, int montoPremio) {
        Jugador j = JugadorDAO.obtenerPorId(idJugador);
        if (j == null) return;

        int nuevasActuales = j.getMonedas_actuales() + montoPremio;
        int nuevasMaximas = Math.max(j.getMonedas_maximas(), nuevasActuales);

        JugadorDAO.actualizarMonedas(idJugador, nuevasActuales, nuevasMaximas);

        Jugador jugadorSesion = SesionManager.getInstance().getJugadorActual();
        if (jugadorSesion != null && jugadorSesion.getId_jugador() == idJugador) {
            jugadorSesion.setMonedas_actuales(nuevasActuales);
            jugadorSesion.setMonedas_maximas(nuevasMaximas);
        }

        SwingUtilities.invokeLater(() -> {
            Frame frame = JOptionPane.getRootFrame();
            if (frame != null && frame instanceof com.titomonito.ui.VentanaBase) {
                ((com.titomonito.ui.VentanaBase) frame).getPnlHeader().actualizarDatosJugador();
            }
        });
    }

    public Map<Integer, double[]> obtenerProgresoPorCategoria(int idJugador) {
        Map<Integer, double[]> resultado = new HashMap<>();
        List<Object[]> progreso = JugadorDAO.obtenerProgresoPorCategorias(idJugador);
        for (Object[] fila : progreso) {
            int idCategoria = (Integer) fila[0];
            int descubiertas = (Integer) fila[2];
            int total = (Integer) fila[3];
            double porcentaje = total == 0 ? 0.0 : (double) descubiertas / total;
            resultado.put(idCategoria, new double[]{descubiertas, total, porcentaje});
        }
        return resultado;
    }
}
