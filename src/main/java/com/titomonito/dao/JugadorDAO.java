package com.titomonito.dao;

import com.titomonito.config.ConfigDB;
import com.titomonito.models.Jugador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class JugadorDAO {

    private static final Logger LOGGER = Logger.getLogger(JugadorDAO.class.getName());

    private static final int MONEDAS_BIENVENIDA = 50;

    public static List<Jugador> listarTodos() {
        List<Jugador> jugadores = new ArrayList<>();
        String sql = "SELECT id_jugador, nombre, monedas_actuales, monedas_maximas, racha_actual, racha_maxima " +
                     "FROM jugadores ORDER BY nombre ASC";

        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                jugadores.add(new Jugador(
                        rs.getInt("id_jugador"),
                        rs.getString("nombre"),
                        rs.getInt("monedas_actuales"),
                        rs.getInt("monedas_maximas"),
                        rs.getInt("racha_actual"),
                        rs.getInt("racha_maxima")
                ));
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al listar jugadores: " + ex.getMessage());
        }
        return jugadores;
    }

    public static Jugador obtenerPorId(int idJugador) {
        String sql = "SELECT id_jugador, nombre, monedas_actuales, monedas_maximas, racha_actual, racha_maxima " +
                     "FROM jugadores WHERE id_jugador = ?";

        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idJugador);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Jugador(
                        rs.getInt("id_jugador"),
                        rs.getString("nombre"),
                        rs.getInt("monedas_actuales"),
                        rs.getInt("monedas_maximas"),
                        rs.getInt("racha_actual"),
                        rs.getInt("racha_maxima")
                );
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al obtener jugador por id: " + ex.getMessage());
        }
        return null;
    }

    public static Jugador crear(String nombre) {
        String sql = "INSERT INTO jugadores (nombre, monedas_actuales, monedas_maximas, racha_actual, racha_maxima) " +
                     "VALUES (?, ?, 0, 0, 0)";

        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nombre);
            ps.setInt(2, MONEDAS_BIENVENIDA);
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idGenerado = generatedKeys.getInt(1);
                    return new Jugador(idGenerado, nombre, MONEDAS_BIENVENIDA, 0, 0, 0);
                }
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al crear jugador: " + ex.getMessage());
        }
        return null;
    }

    public static boolean actualizarMonedas(int idJugador, int monedasActuales, int monedasMaximas) {
        String sql = "UPDATE jugadores SET monedas_actuales = ?, monedas_maximas = ? WHERE id_jugador = ?";

        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, monedasActuales);
            ps.setInt(2, monedasMaximas);
            ps.setInt(3, idJugador);

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.severe("Error al actualizar monedas del jugador: " + ex.getMessage());
        }
        return false;
    }

    public static boolean actualizarRachas(int idJugador, int rachaActual, int rachaMaxima) {
        String sql = "UPDATE jugadores SET racha_actual = ?, racha_maxima = ? WHERE id_jugador = ?";

        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rachaActual);
            ps.setInt(2, rachaMaxima);
            ps.setInt(3, idJugador);

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            LOGGER.severe("Error al actualizar rachas del jugador: " + ex.getMessage());
        }
        return false;
    }

    public static boolean registrarDescubrimiento(int idJugador, int idPalabra) {
        String sql = "INSERT INTO descubrimientos (id_jugador, id_palabra) VALUES (?, ?)";

        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idJugador);
            ps.setInt(2, idPalabra);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            LOGGER.severe("Error al registrar descubrimiento: " + ex.getMessage());
        }
        return false;
    }
}
