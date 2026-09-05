package com.titomonito.services;

import com.titomonito.dao.JugadorDAO;
import com.titomonito.dao.LogrosDAO;
import com.titomonito.enums.LogroId;
import com.titomonito.models.Jugador;

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
