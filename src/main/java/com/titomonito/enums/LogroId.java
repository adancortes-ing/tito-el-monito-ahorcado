package com.titomonito.enums;

public enum LogroId {

    DB_PAL_100("Memorión", "Descubre 100 palabras.", "logro_pal_100.png"),
    DB_PAL_500("Enciclopedia Andante", "Descubre 500 palabras.", "logro_pal_500.png"),
    DB_PAL_1000("Diccionario Humano", "Descubre 1000 palabras.", "logro_pal_1000.png"),

    DB_CAT_5("Polímata del Dibujo", "Completa 5 categorías al 100%.", "logro_cat_5.png"),
    DB_CAT_10("Erudito del Lápiz", "Completa 10 categorías al 100%.", "logro_cat_10.png"),

    DB_RAC_10("Mano Caliente", "Alcanza una racha de 10 victorias consecutivas.", "logro_rac_10.png"),
    DB_RAC_30("Racha Dorada", "Alcanza una racha de 30 victorias consecutivas.", "logro_rac_30.png"),
    DB_RAC_75("Leyenda del Monito", "Alcanza una racha de 75 victorias consecutivas.", "logro_rac_75.png"),

    DB_MON_500("Ahorrativo", "Acumula 500 monedas (récord histórico).", "logro_mon_500.png"),
    DB_MON_2500("Billetera Gruesa", "Acumula 2500 monedas (récord histórico).", "logro_mon_2500.png"),
    DB_MON_5000("Millonario del Lápiz", "Acumula 5000 monedas (récord histórico).", "logro_mon_5000.png"),

    DB_TOTAL("Biblioteca Viviente", "Descubre las 2000 palabras del diccionario.", "logro_total.png");

    private final String nombre;
    private final String descripcion;
    private final String iconoPath;

    LogroId(String nombre, String descripcion, String iconoPath) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.iconoPath = iconoPath;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getIconoPath() {
        return iconoPath;
    }

    public String getCodigo() {
        return this.name();
    }
}
