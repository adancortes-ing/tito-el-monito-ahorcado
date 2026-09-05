package com.titomonito.dao;

import com.titomonito.config.ConfigDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class LogrosDAO {

    private static final Logger LOGGER = Logger.getLogger(LogrosDAO.class.getName());

    public static void registrarLogro(int idJugador, String codigoLogro) {
        String sql = "INSERT OR IGNORE INTO logros (id_jugador, id_logro) VALUES (?, ?)";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            ps.setString(2, codigoLogro);
            ps.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.severe("Error al registrar logro: " + ex.getMessage());
        }
    }

    public static boolean yaDesbloqueado(int idJugador, String codigoLogro) {
        String sql = "SELECT 1 FROM logros WHERE id_jugador = ? AND id_logro = ?";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            ps.setString(2, codigoLogro);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al verificar logro: " + ex.getMessage());
        }
        return false;
    }

    public static Set<String> obtenerCodigosDesbloqueados(int idJugador) {
        Set<String> codigos = new HashSet<>();
        String sql = "SELECT id_logro FROM logros WHERE id_jugador = ?";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    codigos.add(rs.getString("id_logro"));
                }
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al obtener logros desbloqueados: " + ex.getMessage());
        }
        return codigos;
    }

    public static int contarPalabrasDescubiertas(int idJugador) {
        String sql = "SELECT COUNT(*) AS total FROM descubrimientos WHERE id_jugador = ?";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("total");
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al contar palabras descubiertas: " + ex.getMessage());
        }
        return 0;
    }

    public static int contarCategoriasCompletas(int idJugador) {
        String sql = "SELECT COUNT(*) AS total FROM (" +
                "  SELECT c.id_categoria " +
                "  FROM categorias c " +
                "  WHERE NOT EXISTS (" +
                "    SELECT 1 FROM palabras p " +
                "    WHERE p.id_categoria = c.id_categoria " +
                "    AND p.id_palabra NOT IN (" +
                "      SELECT id_palabra FROM descubrimientos WHERE id_jugador = ?" +
                "    )" +
                "  )" +
                ")";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("total");
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al contar categorías completas: " + ex.getMessage());
        }
        return 0;
    }

    public static int contarPalabrasTotales() {
        String sql = "SELECT COUNT(*) AS total FROM palabras";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException ex) {
            LOGGER.severe("Error al contar palabras totales: " + ex.getMessage());
        }
        return 0;
    }

    public static int obtenerRachaMaxima(int idJugador) {
        String sql = "SELECT racha_maxima FROM jugadores WHERE id_jugador = ?";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("racha_maxima");
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al obtener racha máxima: " + ex.getMessage());
        }
        return 0;
    }

    public static int obtenerMonedasMaximas(int idJugador) {
        String sql = "SELECT monedas_maximas FROM jugadores WHERE id_jugador = ?";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("monedas_maximas");
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al obtener monedas máximas: " + ex.getMessage());
        }
        return 0;
    }
}
