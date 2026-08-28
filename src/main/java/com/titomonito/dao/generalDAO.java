package com.titomonito.dao;

import com.titomonito.config.ConfigDB;
import com.titomonito.models.Categorias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class generalDAO {

    private static final Logger LOGGER = Logger.getLogger(generalDAO.class.getName());

    public static void obtenerCategorias(){

        Categorias.getListaCategorias().clear();
        String sql = "SELECT * FROM categorias ORDER BY nombre";

        try (
            Connection conn = ConfigDB.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Categorias.agregarCategoria(new Categorias(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre"),
                        rs.getString("url_icono")
                ));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "No se pudo obtener la lista de categorías desde la DB");
        }
    }
}
