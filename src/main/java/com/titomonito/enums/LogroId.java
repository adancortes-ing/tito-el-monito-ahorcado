package com.titomonito.enums;

public enum LogroId {

    // === TIEMPO REAL (RT_*) — con restricciones y repetición ===
    RT_PERFECTA("Boceto Perfecto (x3)", "Gana 3 partidas sin perder ninguna vida (dif. Normal o superior).", "logro_rt_perfecta.png", 80),
    RT_CASI_PERFECTA("Lápiz Firme (x3)", "Gana 3 partidas con 5 o más vidas restantes (dif. Normal o superior).", "logro_rt_casi_perfecta.png", 40),
    RT_MILAGRO("Punta Floja (x3)", "Gana 3 partidas con apenas 1 vida restante (dif. Normal o superior).", "logro_rt_milagro.png", 60),
    RT_SIN_UTILES("Puro Lápiz (x5)", "Gana 5 partidas sin comprar ningún power-up (dif. Normal o superior).", "logro_rt_sin_utiles.png", 60),
    RT_SACAPUNTAS("Tiempo Prestado (x3)", "Usa el Sacapuntas y gana la partida 3 veces (dif. Normal o superior).", "logro_rt_sacapuntas.png", 30),
    RT_MARCATEXTOS("Lectura Atenta (x3)", "Usa el Marcatextos y gana la partida 3 veces (dif. Difícil o superior).", "logro_rt_marcatextos.png", 40),
    RT_RACHA_EXTREMO("Lápiz de Titanio", "10 victorias consecutivas en dificultad Extremo.", "logro_rt_racha_extremo.png", 350),
    RT_RACHA_IMPOSIBLE("Mente Legendaria", "10 victorias consecutivas en dificultad Imposible.", "logro_rt_racha_imposible.png", 840),
    RT_RACHA_SESION_10("Ritmo Constante", "10 victorias consecutivas en la sesión actual (dif. Normal o superior).", "logro_rt_racha_10.png", 120),
    RT_RACHA_SESION_20("Inercia Implacable", "20 victorias consecutivas en la sesión actual (dif. Normal o superior).", "logro_rt_racha_20.png", 300),
    RT_CAT_6("Curioso", "Gana al menos 1 partida en 6 categorías diferentes.", "logro_rt_cat_3.png", 80),
    RT_CAT_15("Explorador", "Gana al menos 1 partida en 15 categorías diferentes.", "logro_rt_cat_8.png", 200),
    RT_PARTIDA_RICA("Día de Suerte", "Gana una partida obteniendo más de $50 (dif. Normal o superior).", "logro_rt_partida_rica.png", 40),
    RT_CONTRA_RELOJ("Contra el Reloj", "Gana la partida cuando al reloj le quedaba 1 segundo.", "logro_rt_contra_reloj.png", 20),
    RT_TRES_UTILES("Equipado", "Compra al menos 3 power-ups diferentes y gana la partida.", "logro_rt_tres_utiles.png", 85),
    RT_CINCO_UTILES("Tienda Ambulante", "Compra los 5 power-ups en una sola partida y gana.", "logro_rt_cinco_utiles.png", 175),
    RT_AL_FILO("Al Filo", "Gana la partida con 1 vida y 1 segundo restantes.", "logro_rt_al_filo.png", 200),
    RT_PALABRA_LARGA("Palabra Larga", "Gana una palabra de 10 o más letras sin perder ninguna vida.", "logro_rt_palabra_larga.png", 300),

    // HISTÓRICOS — progresión lineal con 27 categorías / 2000 palabras
    DB_PAL_200("Memorión", "Descubre 200 palabras.", "logro_pal_100.png", 150),
    DB_PAL_800("Enciclopedia Andante", "Descubre 800 palabras.", "logro_pal_500.png", 350),
    DB_PAL_1600("Diccionario Viviente", "Descubre 1600 palabras.", "logro_pal_1000.png", 800),
    DB_CAT_10("Polímata del Dibujo", "Completa 10 categorías al 100%.", "logro_cat_5.png", 300),
    DB_CAT_20("Erudito del Lápiz", "Completa 20 categorías al 100%.", "logro_cat_10.png", 800),
    DB_RAC_10("Mano Caliente", "Alcanza una racha de 10 victorias consecutivas.", "logro_rac_10.png", 250),
    DB_RAC_30("Racha Dorada", "Alcanza una racha de 30 victorias consecutivas.", "logro_rac_30.png", 600),
    DB_RAC_75("Leyenda del Monito", "Alcanza una racha de 75 victorias consecutivas.", "logro_rac_75.png", 1200),
    DB_MON_500("Ahorrativo", "Acumula 500 monedas (récord histórico).", "logro_mon_500.png", 150),
    DB_MON_2500("Billetera Gruesa", "Acumula 2500 monedas (récord histórico).", "logro_mon_2500.png", 400),
    DB_MON_5000("Millonario del Lápiz", "Acumula 5000 monedas (récord histórico).", "logro_mon_5000.png", 800),
    DB_TOTAL("Biblioteca Viviente", "Descubre todas las 2000 palabras del diccionario.", "logro_total.png", 3000);

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

    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getIconoPath() { return iconoPath; }
    public int getPremio() { return premio; }
    public String getCodigo() { return this.name(); }
}
