package com.titomonito.ui.vistas;

import com.titomonito.utils.Recursos;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Objects;

public class AyudaPanel extends JPanel {

    private static final Color COLOR_PAPEL        = new Color(253, 246, 227);
    private static final Color COLOR_PAPEL_OSCURO  = new Color(238, 226, 196);
    private static final Color COLOR_TINTA         = new Color( 60,  45,  30);
    private static final Color COLOR_LINEA         = new Color(150, 130, 100);
    private static final Color COLOR_DESTACADO_FONDO  = new Color(255, 248, 220);
    private static final Color COLOR_DESTACADO_BORDE = new Color(190, 150,  60);

    private static final Font F_TITULO    = Recursos.cargarFuente("IndieFlower-Regular.ttf", 30f);
    private static final Font F_SUBTITULO = Recursos.cargarFuente("IndieFlower-Regular.ttf", 18f);
    private static final Font F_SECCION   = Recursos.cargarFuente("IndieFlower-Regular.ttf", 24f);
    private static final Font F_TEXTO     = Recursos.cargarFuente("IndieFlower-Regular.ttf", 18f);
    private static final Font F_BLOQUE    = Recursos.cargarFuente("IndieFlower-Regular.ttf", 18f);
    private static final Font F_TABLA     = Recursos.cargarFuente("IndieFlower-Regular.ttf", 16f);
    private static final Font F_TABLA_HDR = Recursos.cargarFuente("IndieFlower-Regular.ttf", 16f);

    private static final String[][] DIFICULTADES = {
        {"🟢 Fácil",     "20 segundos", "1.0x"},
        {"🟡 Normal",    "15 segundos", "1.2x"},
        {"🟠 Difícil",   "10 segundos", "1.5x"},
        {"🔴 Extremo",   "7 segundos",  "1.8x"},
        {"💀 Imposible", "4 segundos",  "2.0x"}
    };

    private static final String[] TIENDA_HEADERS = {
        "", "Útil", "Costo", "Efecto", "Uso por Partida"
    };

    private static final Object[][] TIENDA = {
        { icono("item_sharpener.png"), "✏️ Sacapuntas",  "$15",    "Suma 10 segundos al cronómetro.",   "Ilimitado" },
        { icono("item_cut.png"),       "✂️ Tijeras",      "$20",    "Corta la soga: +1 vida (máx. 6).",  "1 vez" },
        { icono("item_erase.png"),     "🧽 Goma",        "$30",    "Borra 4 letras incorrectas.",       "1 vez" },
        { icono("item_pen.png"),       "✒️ Pluma",        "$35",    "Escribe una letra correcta al azar.","1 vez" },
        { icono("item_marker.png"),    "🖍️ Marcatextos", "$35-$45","Muestra la pista de la palabra.",   "1 vez" }
    };

    public AyudaPanel() {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 60, 5, 25));
        setOpaque(false);

        add(crearHeader(), BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
    }

    private JPanel crearHeader() {

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 5, 5, 5));

        JLabel titulo = new JLabel("🐵  AYUDA");
        titulo.setFont(F_TITULO);
        titulo.setForeground(COLOR_TINTA);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Manual de Juego: Tito el Monito Ahorcado");
        subtitulo.setFont(F_SUBTITULO);
        subtitulo.setForeground(COLOR_TINTA);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(titulo);
        header.add(Box.createVerticalStrut(4));
        header.add(subtitulo);
        return header;
    }

    private JScrollPane crearContenido() {

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        contenido.setBorder(new EmptyBorder(10, 5, 15, 5));

        agregarParrafo(contenido,
            "<html><div style='text-align:center; font-style:italic;'>"
          + "&laquo;¡No dejes que el monito de palitos se complete!&raquo;"
          + "</div></html>");

        agregarSeparador(contenido);

        agregarSeccion(contenido, "📖", "La Historia de Tito");
        agregarParrafo(contenido,
            "En un pueblo pintado a lápiz vivía <b>Tito</b>, un travieso monito de palitos conocido por ser "
          + "el ladrón más astuto de todos los cuadernos. Cada noche me colaba en el banco del pueblo y "
          + "me llevaba las bolsas de monedas mientras el sheriff dormía. La vida era buena... hasta que "
          + "un día me descubrieron con las manos en la masa.");
        agregarParrafo(contenido,
            "El juez fue implacable: <i>&laquo;¡Condenado a la horca!&raquo;</i>, exclamó. Y así, fui atado "
          + "a una soga esperando el amanecer.");
        agregarParrafo(contenido,
            "Pero aquí me ves, <b>tranquilo y relajado</b>, rodeado de mis bolsas de monedas. ¿Por qué? "
          + "Porque tengo un plan. Antes de que me atraparan, escondí mis ganancias por todo el pueblo. "
          + "Y ahora te propongo un trato:");
        agregarBloque(contenido,
            "<b>&laquo;Ayúdame a adivinar la palabra secreta que me salvará de la horca, y te pagaré con "
          + "mis monedas. Si fallas, el cerco se cerrará y yo desapareceré para siempre&raquo;</b>.");
        agregarParrafo(contenido,
            "¿Aceptas el desafío? Cada letra correcta me acerca a la libertad. Cada error... acerca el "
          + "nudo a mi cuello. 🐵✏️");

        agregarSeparador(contenido);

        agregarParrafo(contenido,
            "Bienvenido a <b>Tito el Monito Ahorcado</b>, una versión moderna y llena de estrategia del "
          + "clásico juego del ahorcado. Aquí no solo pones a prueba tu vocabulario: también gestionas "
          + "monedas, compras útiles escolares y compites con otros jugadores por el primer lugar.");

        agregarSeparador(contenido);

        agregarSeccion(contenido, "🎯", "Objetivo");
        agregarParrafo(contenido,
            "Adivina la palabra oculta letra por letra antes de que se acaben tus vidas. Cada letra "
          + "incorrecta o tiempo agotado acerca a Tito a su destino final... ¡y nadie quiere eso!");

        agregarSeparador(contenido);

        agregarSeccion(contenido, "❤️", "Vidas");
        agregarLista(contenido, new String[] {
            "Empiezas cada partida con <b>6 vidas</b>.",
            "Pierdes una vida por cada <b>letra incorrecta</b>.",
            "Pierdes una vida si el <b>cronómetro llega a cero</b>.",
            "Si pierdes y descubriste al menos el <b>75%</b> de la palabra, se te revela cuál era. Si "
          + "descubriste menos, la palabra queda oculta (así nadie hace trampa perdiendo a propósito)."
        });

        agregarSeparador(contenido);

        agregarSeccion(contenido, "⚙️", "Dificultades");
        agregarParrafo(contenido,
            "El juego ofrece <b>5 niveles de dificultad</b>. Cada uno define cuánto tiempo tienes para "
          + "pensar cada letra y cuánto multiplican tus ganancias:");
        agregarTabla(contenido, new String[] {"Dificultad", "Tiempo por Letra", "Multiplicador"},
                     DIFICULTADES, new int[]{0, 1, 2});
        agregarBloque(contenido,
            "<b>Ejemplo:</b> Si ganas una partida en Normal descubriendo 5 letras, recibes "
          + "10 + 5 × 2 = <b>20 monedas</b> por letras y victoria, más los bonos de dificultad y vidas restantes.");
        agregarParrafo(contenido,
            "A mayor dificultad, más rápido el cronómetro, ¡pero también más monedas! Elige bien según "
          + "tu estado de ánimo.");

        agregarSeparador(contenido);

        agregarSeccion(contenido, "🪙", "Monedas");
        agregarParrafo(contenido, "Tu tesoro personal para comprar útiles durante la partida.");

        agregarSubSeccion(contenido, "¿Cómo se ganan?");
        agregarLista(contenido, new String[] {
            "<b>+2 monedas</b> por cada letra correcta.",
            "<b>+10 monedas</b> al ganar la partida (multiplicado por la dificultad).",
            "<b>+1 moneda</b> extra por cada vida restante al ganar."
        });

        agregarSubSeccion(contenido, "¿Cómo se pierden?");
        agregarLista(contenido, new String[] {
            "Al comprar cualquier útil de la tienda.",
            "Se descuenta <b>inmediatamente</b> de tu saldo, no al final de la partida."
        });

        agregarSubSeccion(contenido, "Récord Histórico");
        agregarParrafo(contenido,
            "El juego recuerda tu mejor marca de monedas (<code>monedas_maximas</code>). Si la superas "
          + "en algún momento, se actualiza. Es tu trofeo personal.");

        agregarSeparador(contenido);

        agregarSeccion(contenido, "🎒", "Tienda de Útiles Escolares");
        agregarParrafo(contenido,
            "Durante la partida tienes <b>5 útiles</b> disponibles en la parte inferior de la pantalla. "
          + "Todos cuestan monedas de tu bolsillo actual (no de lo ganado durante la partida).");
        agregarTablaIconos(contenido, TIENDA_HEADERS, TIENDA, 0);

        agregarSubSeccion(contenido, "Notas Importantes");
        agregarLista(contenido, new String[] {
            "<b>Sacapuntas</b> es la única ayuda que puedes comprar varias veces. Perfecta para emergencias.",
            "<b>Tijeras</b> se deshabilita automáticamente si ya tienes 6 vidas llenas.",
            "<b>Goma</b> elige las letras al azar entre las que <b>no están</b> en la palabra y que aún "
          + "no hayas intentado.",
            "<b>Pluma</b> siempre revela la letra oculta con más apariciones en la palabra. Si la letra "
          + "aparece 3 veces, ¡las 3 se revelan de golpe!",
            "<b>Marcatextos</b> cuesta $35 en Fácil y $45 en Normal/Extremo. <b>No está disponible en "
          + "Imposible</b> (demasiado fácil si no)."
        });

        agregarSeparador(contenido);

        agregarSeccion(contenido, "⏱️", "El Cronómetro (Time-Attack)");
        agregarParrafo(contenido,
            "El tiempo es tu enemigo... y tu aliado. Cada turno individual tiene un límite. El cronómetro "
          + "<b>se reinicia</b> cada vez que:");
        agregarLista(contenido, new String[] {
            "Pulsas una letra correcta.",
            "Pulsas una letra incorrecta.",
            "El tiempo se agota.",
            "Compras un <b>Sacapuntas</b>."
        });

        agregarSubSeccion(contenido, "¿Cómo funciona el Sacapuntas con el tiempo?");
        agregarParrafo(contenido,
            "Imagina que estás en <b>Difícil (10 segundos base)</b> y compras 1 Sacapuntas al inicio de "
          + "tu turno. Tu tiempo ahora es <b>20 segundos</b>. El comportamiento es:");
        agregarBloque(contenido, "<b>El tiempo comprado se consume primero, luego corre el tiempo base.</b>");

        agregarSubSeccion(contenido, "Ejemplo Práctico");
        agregarLista(contenido, new String[] {
            "<b>Compras 1 Sacapuntas</b> (10 base + 10 comprados = 20 segundos).",
            "Piensas 15 segundos y aciertas la letra.",
            "Has consumido: 10s del bonus + 5s del base.",
            "<b>Te sobran 5s del base</b> para el siguiente turno.",
            "<b>Tu siguiente turno:</b> 10s (base) + 0s (bonus, ya se consumió) = <b>10s normales</b>."
        });
        agregarParrafo(contenido,
            "Si compras <b>2 Sacapuntas</b> (10 base + 20 comprados = 30s) y piensas 15s antes de acertar:");
        agregarLista(contenido, new String[] {
            "Consumiste 15s de bonus + 0s del base.",
            "Te sobran 5s del bonus.",
            "<b>Tu siguiente turno:</b> 10s + 5s = 15s (base + bonus restante)."
        });
        agregarBloque(contenido,
            "<b>Regla clave:</b> Si consumes TODO el bonus comprado (aunque quede base restante), el "
          + "siguiente turno se resetea al tiempo puro de la dificultad. ¡Piénsalo bien antes de comprar "
          + "dos Sacapuntas!");

        agregarSeparador(contenido);

        agregarSeccion(contenido, "🏆", "Rachas");
        agregarLista(contenido, new String[] {
            "<b>Racha Actual:</b> Cuántas partidas ganadas llevas <b>sin perder</b>. Se reinicia a cero (0) en "
          + "cuanto pierdes una partida.",
            "<b>Racha Máxima:</b> Tu mejor racha histórica. <b>Nunca baja</b>, solo puede aumentar cuando "
          + "superas tu récord."
        });
        agregarParrafo(contenido,
            "Las rachas aparecen en tu panel de Estadísticas y en el ranking global.");

        agregarSeparador(contenido);

        agregarSeccion(contenido, "📊", "Panel de Estadísticas");
        agregarParrafo(contenido,
            "Al hacer clic en <b>Estadísticas</b> en el menú verás 3 secciones:");
        agregarLista(contenido, new String[] {
            "<b>Resumen Personal:</b> Tu progreso global, monedas, rachas y última palabra descubierta.",
            "<b>Salón de la Fama:</b> Top 10 de jugadores. Puedes filtrar por Palabras, Monedas o Racha.",
            "<b>Progreso por Categoría:</b> Una tabla con tu avance en cada categoría, ordenada de mayor "
          + "a menor progreso."
        });
        agregarParrafo(contenido,
            "Si no estás en el Top 10 del ranking, debajo de la tabla aparecerá tu <b>posición actual</b> "
            + "(#14, #25, etc.).");

        agregarSeparador(contenido);

        agregarSeccion(contenido, "💡", "Consejos de Estrategia");
        agregarLista(contenido, new String[] {
            "<b>Empieza por las vocales.</b> Las palabras casi siempre tienen vocales, así que aciertas rápido.",
            "<b>Cuenta los espacios.</b> Si la palabra tiene 8 letras y la pista es &laquo;Fruta "
          + "tropical&raquo;, probablemente es &laquo;PIÑA&raquo; o &laquo;MANGO&raquo;.",
            "<b>No desperdicies el Marcatextos.</b> En Fácil vale $35, en Normal vale $45. Úsalo cuando la "
          + "pista sea reveladora.",
            "<b>El Sacapuntas salva vidas.</b> Si ves que el reloj está en rojo, cómpralo. $15 por 10 "
          + "segundos extra es un trato justo.",
            "<b>Gana monedas en Fácil para practicar.</b> Las victorias en Fácil se acumulan rápido para "
          + "que tengas saldo en Normal.",
            "<b>Las Tijeras son oro.</b> Si te quedan 2 vidas, cómpralas ($20) y te dan margen para una "
          + "letra incorrecta más."
        });

        agregarSeparador(contenido);

        agregarSeccion(contenido, "🆘", "¿Necesitas más ayuda?");
        agregarParrafo(contenido,
            "Si tienes dudas técnicas o encuentras un error, revisa el panel <b>Acerca De</b> en el menú "
          + "principal. ¡Y que la suerte (y el vocabulario) te acompañe! 🐵✏️");

        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        scroll.getVerticalScrollBar().setBlockIncrement(60);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }

    private void agregarSeccion(JPanel contenido, String emoji, String titulo) {

        JLabel lbl = new JLabel(emoji + "  " + titulo);
        lbl.setFont(F_SECCION);
        lbl.setForeground(COLOR_TINTA);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(8, 0, 6, 0));
        contenido.add(lbl);
        contenido.add(Box.createVerticalStrut(4));
    }

    private void agregarSubSeccion(JPanel contenido, String titulo) {

        JLabel lbl = new JLabel("▸ " + titulo);
        lbl.setFont(F_TEXTO.deriveFont(Font.BOLD, 18f));
        lbl.setForeground(COLOR_TINTA);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(10, 4, 4, 0));
        contenido.add(lbl);
    }

    private void agregarParrafo(JPanel contenido, String texto) {

        JLabel lbl = new JLabel("<html><div style='text-align:justify;'>" + texto + "</div></html>");
        lbl.setFont(F_TEXTO);
        lbl.setForeground(COLOR_TINTA);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(4, 8, 4, 8));

        int ancho = 720;
        lbl.setMaximumSize(new Dimension(ancho, Integer.MAX_VALUE));
        //lbl.setPreferredSize(new Dimension(ancho, lbl.getPreferredSize().height));

        contenido.add(lbl);
        contenido.add(Box.createVerticalStrut(6));
    }

    private void agregarLista(JPanel contenido, String[] items) {

        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setOpaque(false);
        lista.setAlignmentX(Component.LEFT_ALIGNMENT);
        lista.setBorder(new EmptyBorder(2, 28, 2, 8));

        for (String item : items) {
            JLabel lbl = new JLabel("<html><div style='text-align:justify;'>• " + item + "</div></html>");
            lbl.setFont(F_TEXTO);
            lbl.setForeground(COLOR_TINTA);
            lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            lbl.setBorder(new EmptyBorder(2, 0, 2, 0));

            int ancho = 700;
            lbl.setMaximumSize(new Dimension(ancho, Integer.MAX_VALUE));
            //lbl.setPreferredSize(new Dimension(ancho, lbl.getPreferredSize().height));
            lista.add(lbl);
        }

        contenido.add(lista);
        contenido.add(Box.createVerticalStrut(6));
    }

    private void agregarBloque(JPanel contenido, String html) {

        JPanel bloque = new BloqueDestacado();
        bloque.setLayout(new BorderLayout());
        bloque.setAlignmentX(Component.LEFT_ALIGNMENT);
        bloque.setBorder(new EmptyBorder(10, 14, 10, 14));
        bloque.setMaximumSize(new Dimension(720, 100));

        JLabel lbl = new JLabel("<html><div style='text-align:justify;'>" + html + "</div></html>");
        lbl.setFont(F_BLOQUE);
        lbl.setForeground(COLOR_TINTA);
        bloque.add(lbl, BorderLayout.CENTER);

        contenido.add(bloque);
        contenido.add(Box.createVerticalStrut(8));
    }

    private void agregarTabla(JPanel contenido, String[] headers, String[][] datos, int[] columnasCentradas) {

        Object[][] data = new Object[datos.length][];
        for (int i = 0; i < datos.length; i++) {
            data[i] = datos[i];
        }

        DefaultTableModel modelo = new DefaultTableModel(data, headers) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable tabla = new TablaCuaderno(modelo, columnasCentradas);
        tabla.setRowHeight(28);
        tabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        tabla.setBorder(new EmptyBorder(2, 8, 2, 8));
        tabla.setMaximumSize(new Dimension(720, tabla.getPreferredSize().height));

        contenido.add(tabla);
        contenido.add(Box.createVerticalStrut(8));
    }

    private void agregarTablaIconos(JPanel contenido, String[] headers, Object[][] datos, int columnaIcono) {

        DefaultTableModel modelo = new DefaultTableModel(datos, headers) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
            @Override public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == columnaIcono ? ImageIcon.class : String.class;
            }
        };

        JTable tabla = new TablaCuaderno(modelo, new int[]{2, 3});
        tabla.setRowHeight(36);
        tabla.getColumnModel().getColumn(columnaIcono).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(columnaIcono).setMaxWidth(48);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(1).setMaxWidth(130);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(2).setMaxWidth(80);

        tabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        tabla.setBorder(new EmptyBorder(2, 8, 2, 8));
        tabla.setMaximumSize(new Dimension(720, tabla.getPreferredSize().height));

        contenido.add(tabla);
        contenido.add(Box.createVerticalStrut(8));
    }

    private void agregarSeparador(JPanel contenido) {

        SeparadorLapiz sep = new SeparadorLapiz();
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        sep.setMaximumSize(new Dimension(720, 14));
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(sep);
        contenido.add(Box.createVerticalStrut(10));
    }

    private static ImageIcon icono(String archivo) {

        ImageIcon raw = Recursos.cargarImagen(archivo);
        Image img = raw.getImage();
        Image scaled = img.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Image fondo = Objects.requireNonNull(Recursos.cargarImagen("bg_contenedor.png")).getImage();
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }

    private static class TablaCuaderno extends JTable {

        private final int[] columnasCentradas;

        TablaCuaderno(DefaultTableModel modelo, int[] columnasCentradas) {

            super(modelo);
            this.columnasCentradas = columnasCentradas;
            setFont(F_TABLA);
            setBackground(COLOR_PAPEL);
            setForeground(COLOR_TINTA);
            setGridColor(COLOR_LINEA);
            setShowGrid(true);
            setIntercellSpacing(new Dimension(1, 1));
            setFocusable(false);
            setRowSelectionAllowed(false);

            JTableHeader hdr = getTableHeader();
            hdr.setFont(F_TABLA_HDR);
            hdr.setBackground(COLOR_PAPEL_OSCURO);
            hdr.setForeground(COLOR_TINTA);
            hdr.setReorderingAllowed(false);
            hdr.setPreferredSize(new Dimension(hdr.getPreferredSize().width, 30));
            ((DefaultTableCellRenderer) hdr.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                               boolean hasFocus, int row, int column) {

                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    c.setBackground(row % 2 == 0 ? COLOR_PAPEL : COLOR_PAPEL_OSCURO);
                    c.setForeground(COLOR_TINTA);
                    setFont(F_TABLA);
                    setBorder(new EmptyBorder(2, 8, 2, 8));
                    boolean centrar = false;
                    for (int c2 : columnasCentradas) if (c2 == column) { centrar = true; break; }
                    setHorizontalAlignment(centrar ? SwingConstants.CENTER : SwingConstants.LEFT);
                    return c;
                }
            };

            for (int i = 0; i < getColumnCount(); i++) {
                if (i != 0) getColumnModel().getColumn(i).setCellRenderer(renderer);
            }
        }
    }

    private static class BordeCuaderno extends AbstractBorder {

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(COLOR_LINEA);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(x, y, width - 1, height - 1, 6, 6);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) { return new Insets(2, 2, 2, 2); }
    }

    private static class BloqueDestacado extends JPanel {

        BloqueDestacado() { setOpaque(true); }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(COLOR_DESTACADO_FONDO);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

            float[] dash = {6f, 4f};
            g2.setColor(COLOR_DESTACADO_BORDE);
            g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dash, 0f));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 10, 10);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class SeparadorLapiz extends JPanel {

        SeparadorLapiz() {
            setOpaque(false);
            setPreferredSize(new Dimension(0, 12));
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(COLOR_LINEA);
            g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            int y = getHeight() / 2;
            int w = getWidth();
            int x = 4;
            int step = 6;
            while (x < w - 4) {
                int dy = (x / step) % 2 == 0 ? 0 : 2;
                g2.drawLine(x, y - dy, x + step / 2, y + dy);
                x += step;
            }
            g2.dispose();
        }
    }
}
