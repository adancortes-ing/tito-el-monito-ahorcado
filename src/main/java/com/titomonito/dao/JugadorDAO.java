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

    public static String obtenerUltimaPalabra(int idJugador) {
        String sql = "SELECT p.palabra " +
                     "FROM palabras p " +
                     "JOIN descubrimientos d ON p.id_palabra = d.id_palabra " +
                     "WHERE d.id_jugador = ? " +
                     "ORDER BY d.id DESC LIMIT 1";

        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idJugador);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("palabra");
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al obtener última palabra: " + ex.getMessage());
        }
        return "NINGUNA";
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

    public static int contarDescubrimientosJugador(int idJugador) {
        String sql = "SELECT COUNT(*) AS total FROM descubrimientos WHERE id_jugador = ?";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("total");
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al contar descubrimientos: " + ex.getMessage());
        }
        return 0;
    }

    public static List<Object[]> obtenerRankingPorPalabras() {
        List<Object[]> ranking = new ArrayList<>();
        String sql = "SELECT j.id_jugador, j.nombre, COUNT(d.id) AS total " +
                     "FROM jugadores j " +
                     "LEFT JOIN descubrimientos d ON j.id_jugador = d.id_jugador " +
                     "GROUP BY j.id_jugador " +
                     "ORDER BY total DESC " +
                     "LIMIT 10";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            int posicion = 1;
            while (rs.next()) {
                ranking.add(new Object[]{posicion++, rs.getString("nombre"), rs.getInt("total")});
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al obtener ranking por palabras: " + ex.getMessage());
        }
        return ranking;
    }

    public static List<Object[]> obtenerRankingPorMonedasMaximas() {
        List<Object[]> ranking = new ArrayList<>();
        String sql = "SELECT id_jugador, nombre, monedas_maximas " +
                     "FROM jugadores " +
                     "ORDER BY monedas_maximas DESC " +
                     "LIMIT 10";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            int posicion = 1;
            while (rs.next()) {
                ranking.add(new Object[]{posicion++, rs.getString("nombre"), rs.getInt("monedas_maximas")});
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al obtener ranking por monedas: " + ex.getMessage());
        }
        return ranking;
    }

    public static List<Object[]> obtenerRankingPorRachaMaxima() {
        List<Object[]> ranking = new ArrayList<>();
        String sql = "SELECT id_jugador, nombre, racha_maxima " +
                     "FROM jugadores " +
                     "ORDER BY racha_maxima DESC " +
                     "LIMIT 10";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            int posicion = 1;
            while (rs.next()) {
                ranking.add(new Object[]{posicion++, rs.getString("nombre"), rs.getInt("racha_maxima")});
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al obtener ranking por racha: " + ex.getMessage());
        }
        return ranking;
    }

    public static int obtenerPosicionEnRankingPorPalabras(int idJugador) {
        String sql = "SELECT COUNT(*) + 1 AS posicion FROM (" +
                     "    SELECT j.id_jugador, COUNT(d.id) AS total " +
                     "    FROM jugadores j " +
                     "    LEFT JOIN descubrimientos d ON j.id_jugador = d.id_jugador " +
                     "    GROUP BY j.id_jugador " +
                     "    HAVING total > (" +
                     "        SELECT COUNT(*) FROM descubrimientos WHERE id_jugador = ?" +
                     "    )" +
                     ")";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("posicion");
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al obtener posición: " + ex.getMessage());
        }
        return -1;
    }

    public static int obtenerPosicionEnRankingPorMonedas(int idJugador) {
        String sql = "SELECT COUNT(*) + 1 AS posicion FROM jugadores WHERE monedas_maximas > " +
                     "(SELECT monedas_maximas FROM jugadores WHERE id_jugador = ?)";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("posicion");
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al obtener posición por monedas: " + ex.getMessage());
        }
        return -1;
    }

    public static int obtenerPosicionEnRankingPorRacha(int idJugador) {
        String sql = "SELECT COUNT(*) + 1 AS posicion FROM jugadores WHERE racha_maxima > " +
                     "(SELECT racha_maxima FROM jugadores WHERE id_jugador = ?)";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("posicion");
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al obtener posición por racha: " + ex.getMessage());
        }
        return -1;
    }

    public static List<Object[]> obtenerProgresoPorCategorias(int idJugador) {
        List<Object[]> categorias = new ArrayList<>();
        String sql = "SELECT c.id_categoria, c.nombre, " +
                     "       (SELECT COUNT(*) FROM descubrimientos d " +
                     "        JOIN palabras p2 ON d.id_palabra = p2.id_palabra " +
                     "        WHERE d.id_jugador = ? AND p2.id_categoria = c.id_categoria) AS descubiertas, " +
                     "       (SELECT COUNT(*) FROM palabras p WHERE p.id_categoria = c.id_categoria) AS total " +
                     "FROM categorias c " +
                     "ORDER BY (CAST((SELECT COUNT(*) FROM descubrimientos d " +
                     "               JOIN palabras p2 ON d.id_palabra = p2.id_palabra " +
                     "               WHERE d.id_jugador = ? AND p2.id_categoria = c.id_categoria) AS REAL) / " +
                     "          (SELECT COUNT(*) FROM palabras p WHERE p.id_categoria = c.id_categoria)) DESC";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idJugador);
            ps.setInt(2, idJugador);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    categorias.add(new Object[]{
                            rs.getInt("id_categoria"),
                            rs.getString("nombre"),
                            rs.getInt("descubiertas"),
                            rs.getInt("total")
                    });
                }
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al obtener progreso por categorías: " + ex.getMessage());
        }
        return categorias;
    }

    public static boolean categoriaCompletada(int idCategoria, int idJugador) {
        String sql = "SELECT COUNT(*) FROM palabras p " +
                     "WHERE p.id_categoria = ? " +
                     "AND p.id_palabra NOT IN (" +
                     "    SELECT d.id_palabra FROM descubrimientos d WHERE d.id_jugador = ?" +
                     ")";
        try (Connection conn = ConfigDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            ps.setInt(2, idJugador);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) == 0;
            }
        } catch (SQLException ex) {
            LOGGER.severe("Error al verificar categoría completada: " + ex.getMessage());
        }
        return false;
    }
}
