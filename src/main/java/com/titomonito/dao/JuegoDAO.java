package com.titomonito.dao;

import com.titomonito.config.ConfigDB;
import com.titomonito.models.Palabra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class JuegoDAO {

    private static final Logger LOGGER = Logger.getLogger(JuegoDAO.class.getName());

    public static Palabra obtenerPalabra(int idCategoria, int idJugador) {
        String sql = "SELECT p.id_palabra, p.palabra, p.pista " +
                     "FROM palabras p " +
                     "WHERE p.id_categoria = ? " +
                     "AND p.id_palabra NOT IN (" +
                     "    SELECT d.id_palabra FROM descubrimientos d WHERE d.id_jugador = ?" +
                     ") " +
                     "ORDER BY RANDOM() LIMIT 1";

        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCategoria);
            ps.setInt(2, idJugador);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Palabra(
                        rs.getInt("id_palabra"),
                        idCategoria,
                        rs.getString("palabra"),
                        rs.getString("pista")
                );
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al obtener palabra aleatoria: " + ex.getMessage());
        }
        return null;
    }

    public static int contarPalabrasDisponibles(int idCategoria, int idJugador) {
        String sql = "SELECT COUNT(*) FROM palabras p " +
                     "WHERE p.id_categoria = ? " +
                     "AND p.id_palabra NOT IN (" +
                     "    SELECT d.id_palabra FROM descubrimientos d WHERE d.id_jugador = ?" +
                     ")";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            ps.setInt(2, idJugador);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException ex) {
            LOGGER.severe("Error al contar palabras disponibles: " + ex.getMessage());
        }
        return 0;
    }
}
