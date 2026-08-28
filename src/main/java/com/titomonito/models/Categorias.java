package com.titomonito.models;

import java.util.ArrayList;
import java.util.List;

public class Categorias {

    private static final List<Categorias> listaCategorias = new ArrayList<>();;

    private final int id_categoria;
    private final String nombre_categoria;
    private final String url_icono;

    public Categorias(int id_categoria, String nombre_categoria, String url_icono) {
        this.id_categoria = id_categoria;
        this.nombre_categoria = nombre_categoria;
        this.url_icono = url_icono;
    }

    public static List<Categorias> getListaCategorias() {
        return listaCategorias;
    }

    public static void agregarCategoria(Categorias cat) {
        listaCategorias.add(cat);
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public String getUrl_icono() {
        return url_icono;
    }
}
