package com.titomonito.enums;

public enum LogroId {

    // === TIEMPO REAL (RT_*) — aparecen primero en la pestaña Hazañas ===

    // Grupo A: Victorias limpias
    RT_PERFECTA("Boceto Perfecto", "Gana una partida sin perder ninguna vida.", "logro_rt_perfecta.png", 140),
    RT_CASI_PERFECTA("Lápiz Firme", "Gana una partida con 5 o más vidas restantes.", "logro_rt_casi_perfecta.png", 70),
    RT_MILAGRO("Punta Floja", "Gana una partida con apenas 1 vida restante.", "logro_rt_milagro.png", 105),

    // Grupo B: Sin ayuda
    RT_SIN_UTILES("Puro Lápiz", "Gana una partida sin comprar ningún power-up.", "logro_rt_sin_utiles.png", 125),
    RT_SACAPUNTAS("Tiempo Prestado", "Usa el Sacapuntas y gana la partida.", "logro_rt_sacapuntas.png", 55),
    RT_MARCATEXTOS("Lectura Atenta", "Usa el Marcatextos y gana la partida.", "logro_rt_marcatextos.png", 55),

    // Grupo C: Rachas en dificultad extrema
    RT_RACHA_EXTREMO("Lápiz de Titanio", "10 victorias consecutivas en dificultad Extremo.", "logro_rt_racha_extremo.png", 350),
    RT_RACHA_IMPOSIBLE("Mente Legendaria", "5 victorias consecutivas en dificultad Imposible.", "logro_rt_racha_imposible.png", 840),

    // Grupo D: Racha en sesión
    RT_RACHA_SESION_10("Ritmo Constante", "10 victorias consecutivas en la sesión actual.", "logro_rt_racha_10.png", 175),
    RT_RACHA_SESION_20("Inercia Implacable", "20 victorias consecutivas en la sesión actual.", "logro_rt_racha_20.png", 490),

    // Grupo E: Categorías exploradas
    RT_CAT_3("Curioso", "Gana al menos 1 partida en 3 categorías diferentes.", "logro_rt_cat_3.png", 140),
    RT_CAT_8("Explorador", "Gana al menos 1 partida en 8 categorías diferentes.", "logro_rt_cat_8.png", 350),

    // Grupo F: Récord económico
    RT_PARTIDA_RICA("Día de Suerte", "Gana una partida obteniendo más de $50.", "logro_rt_partida_rica.png", 140),

    // Grupo G: Hitos dramáticos
    RT_CONTRA_RELOJ("Contra el Reloj", "Gana la partida cuando al reloj le quedaba 1 segundo.", "logro_rt_contra_reloj.png", 35),
    RT_TRES_UTILES("Equipado", "Compra al menos 3 power-ups diferentes y gana la partida.", "logro_rt_tres_utiles.png", 85),
    RT_CINCO_UTILES("Tienda Ambulante", "Compra los 5 power-ups en una sola partida y gana.", "logro_rt_cinco_utiles.png", 175),
    RT_AL_FILO("Al Filo", "Gana la partida con 1 vida y 1 segundo restantes.", "logro_rt_al_filo.png", 200),
    RT_PALABRA_LARGA("Palabra Larga", "Gana una palabra de 10 o más letras sin perder ninguna vida.", "logro_rt_palabra_larga.png", 300),

    // === HISTÓRICOS (DB_*) ===

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
