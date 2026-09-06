package com.titomonito.models;

import java.util.ArrayList;
import java.util.List;

public class Categorias {

    private static final List<Categorias> listaCategorias = new ArrayList<>();

    private final int idCategoria;
    private final String nombreCategoria;
    private final String urlIcono;

    public Categorias(int idCategoria, String nombreCategoria, String urlIcono) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.urlIcono = urlIcono;
    }

    public static List<Categorias> getListaCategorias() {
        return listaCategorias;
    }

    public static void agregarCategoria(Categorias cat) {
        listaCategorias.add(cat);
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public String getUrlIcono() {
        return urlIcono;
    }
}