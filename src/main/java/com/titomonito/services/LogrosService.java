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

        if (palabrasDescubiertas >= 100 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_PAL_100.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_PAL_100.getCodigo());
            nuevos.add(LogroId.DB_PAL_100);
        }
        if (palabrasDescubiertas >= 500 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_PAL_500.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_PAL_500.getCodigo());
            nuevos.add(LogroId.DB_PAL_500);
        }
        if (palabrasDescubiertas >= 1000 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_PAL_1000.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_PAL_1000.getCodigo());
            nuevos.add(LogroId.DB_PAL_1000);
        }

        if (categoriasCompletas >= 5 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_CAT_5.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_CAT_5.getCodigo());
            nuevos.add(LogroId.DB_CAT_5);
        }
        if (categoriasCompletas >= 10 && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.DB_CAT_10.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.DB_CAT_10.getCodigo());
            nuevos.add(LogroId.DB_CAT_10);
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

        if (snap.getVidasRestantes() == 6
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_PERFECTA.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_PERFECTA.getCodigo());
            nuevos.add(LogroId.RT_PERFECTA);
        }
        if (snap.getVidasRestantes() == 5
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_CASI_PERFECTA.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_CASI_PERFECTA.getCodigo());
            nuevos.add(LogroId.RT_CASI_PERFECTA);
        }
        if (snap.getVidasRestantes() == 1
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_MILAGRO.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_MILAGRO.getCodigo());
            nuevos.add(LogroId.RT_MILAGRO);
        }

        if (!snap.isUsoAlgunUtil()
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_SIN_UTILES.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_SIN_UTILES.getCodigo());
            nuevos.add(LogroId.RT_SIN_UTILES);
        }
        if (snap.isUsoSacapuntas()
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_SACAPUNTAS.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_SACAPUNTAS.getCodigo());
            nuevos.add(LogroId.RT_SACAPUNTAS);
        }
        if (snap.isUsoMarcatextos()
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_MARCATEXTOS.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_MARCATEXTOS.getCodigo());
            nuevos.add(LogroId.RT_MARCATEXTOS);
        }

        if (tracker.getRachaExtremoSesion() >= 10
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_RACHA_EXTREMO.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_RACHA_EXTREMO.getCodigo());
            nuevos.add(LogroId.RT_RACHA_EXTREMO);
        }
        if (tracker.getRachaImposibleSesion() >= 5
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_RACHA_IMPOSIBLE.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_RACHA_IMPOSIBLE.getCodigo());
            nuevos.add(LogroId.RT_RACHA_IMPOSIBLE);
        }

        if (tracker.getRachaGlobalSesion() >= 10
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_RACHA_SESION_10.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_RACHA_SESION_10.getCodigo());
            nuevos.add(LogroId.RT_RACHA_SESION_10);
        }
        if (tracker.getRachaGlobalSesion() >= 20
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_RACHA_SESION_20.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_RACHA_SESION_20.getCodigo());
            nuevos.add(LogroId.RT_RACHA_SESION_20);
        }

        if (tracker.getCategoriasDistintasGanadas() >= 3
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_CAT_3.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_CAT_3.getCodigo());
            nuevos.add(LogroId.RT_CAT_3);
        }
        if (tracker.getCategoriasDistintasGanadas() >= 8
                && !LogrosDAO.yaDesbloqueado(idJugador, LogroId.RT_CAT_8.getCodigo())) {
            LogrosDAO.registrarLogro(idJugador, LogroId.RT_CAT_8.getCodigo());
            nuevos.add(LogroId.RT_CAT_8);
        }

        if (snap.getMonedasObtenidas() > 50
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
