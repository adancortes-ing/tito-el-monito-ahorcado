package com.titomonito.enums;

public enum LogroId {

    DB_PAL_100("Memorión", "Descubre 100 palabras.", "logro_pal_100.png", 200),
    DB_PAL_500("Enciclopedia Andante", "Descubre 500 palabras.", "logro_pal_500.png", 500),
    DB_PAL_1000("Diccionario Humano", "Descubre 1000 palabras.", "logro_pal_1000.png", 1200),

    DB_CAT_5("Polímata del Dibujo", "Completa 5 categorías al 100%.", "logro_cat_5.png", 600),
    DB_CAT_10("Erudito del Lápiz", "Completa 10 categorías al 100%.", "logro_cat_10.png", 1500),

    DB_RAC_10("Mano Caliente", "Alcanza una racha de 10 victorias consecutivas.", "logro_rac_10.png", 400),
    DB_RAC_30("Racha Dorada", "Alcanza una racha de 30 victorias consecutivas.", "logro_rac_30.png", 1000),
    DB_RAC_75("Leyenda del Monito", "Alcanza una racha de 75 victorias consecutivas.", "logro_rac_75.png", 2500),

    DB_MON_500("Ahorrativo", "Acumula 500 monedas (récord histórico).", "logro_mon_500.png", 250),
    DB_MON_2500("Billetera Gruesa", "Acumula 2500 monedas (récord histórico).", "logro_mon_2500.png", 700),
    DB_MON_5000("Millonario del Lápiz", "Acumula 5000 monedas (récord histórico).", "logro_mon_5000.png", 1500),

    DB_TOTAL("Biblioteca Viviente", "Descubre las 2000 palabras del diccionario.", "logro_total.png", 5000);

    private final String nombre;
    private final String descripcion;
    private final String iconoPath;
    private final int premio;

    LogroId(String nombre, String descripcion, String iconoPath, int premio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.iconoPath = iconoPath;
        this.premio = premio;
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

    public int getPremio() {
        return premio;
    }

    public String getCodigo() {
        return this.name();
    }
}
