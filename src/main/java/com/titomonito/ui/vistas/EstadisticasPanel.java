package com.titomonito.ui.vistas;

import com.titomonito.dao.JugadorDAO;
import com.titomonito.models.Jugador;
import com.titomonito.services.SesionManager;
import com.titomonito.utils.Recursos;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class EstadisticasPanel extends JPanel {

    private static final String[] TIPOS_RANKING = {"Palabras", "Monedas", "Racha"};

    private JProgressBar barraProgresoGlobal;
    private JLabel lblPorcentajeGlobal;
    private JLabel lblPalabrasDescubiertas;
    private JLabel lblRachaActual;
    private JLabel lblRachaMaxima;
    private JLabel lblMonedasActuales;
    private JLabel lblMonedasMaximas;

    private JComboBox<String> cmbFiltroRanking;
    private JTable tablaRanking;
    private RankingTableModel modeloRanking;
    private JLabel lblPosicionJugador;

    private JTable tablaCategorias;
    private CategoriasTableModel modeloCategorias;

    public EstadisticasPanel() {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(5, 65, 5, 30));

        initUI();
    }

    private void initUI() {

        add(crearPanelResumen(), BorderLayout.WEST);
        add(crearPanelRanking(), BorderLayout.EAST);
        add(crearPanelCategorias(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelResumen() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        javax.swing.border.TitledBorder borderRes = BorderFactory.createTitledBorder("Resumen Personal");
        borderRes.setTitleFont(borderRes.getTitleFont().deriveFont(16.0f));
        panel.setBorder(borderRes);
        panel.setPreferredSize(new Dimension(360, 0));

        barraProgresoGlobal = new JProgressBar(0, 100);
        barraProgresoGlobal.setValue(0);
        barraProgresoGlobal.setStringPainted(false);
        barraProgresoGlobal.setPreferredSize(new Dimension(350, 30));
        barraProgresoGlobal.setMaximumSize(new Dimension(350, 30));
        barraProgresoGlobal.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblPorcentajeGlobal = new JLabel("0.0%");
        lblPorcentajeGlobal.setFont(lblPorcentajeGlobal.getFont().deriveFont(Font.BOLD, 16.0f));
        lblPorcentajeGlobal.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblPalabrasDescubiertas = new JLabel("Palabras descubiertas: 0 / 0");
        lblPalabrasDescubiertas.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPalabrasDescubiertas.setFont(lblPalabrasDescubiertas.getFont().deriveFont( 20.0f));

        panel.add(Box.createVerticalStrut(10));
        panel.add(barraProgresoGlobal);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblPorcentajeGlobal);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblPalabrasDescubiertas);
        panel.add(Box.createVerticalStrut(15));

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(230, 5));
        panel.add(sep);
        panel.add(Box.createVerticalStrut(10));

        lblRachaActual = crearLabelDetalle("Racha Actual: 0");
        lblRachaMaxima = crearLabelDetalle("Racha Máxima: 0");
        lblMonedasActuales = crearLabelDetalle("Monedas Actuales: $0");
        lblMonedasMaximas = crearLabelDetalle("Monedas Máximas: $0");

        panel.add(lblRachaActual);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblRachaMaxima);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblMonedasActuales);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblMonedasMaximas);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JLabel crearLabelDetalle(String texto) {

        JLabel lbl = new JLabel(texto);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setFont(lbl.getFont().deriveFont(20.0f));
        return lbl;
    }

    private JPanel crearPanelRanking() {

        JPanel panel = new JPanel(new BorderLayout(5, 0));
        javax.swing.border.TitledBorder borderRank = BorderFactory.createTitledBorder("Salón de la Fama");
        borderRank.setTitleFont(borderRank.getTitleFont().deriveFont(16.0f));
        panel.setBorder(borderRank);
        panel.setPreferredSize(new Dimension(390, 0));

        JPanel pnlFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblFiltro = new JLabel("Filtrar por:");
        lblFiltro.setFont(lblFiltro.getFont().deriveFont(20.0f));
        pnlFiltro.add(lblFiltro);
        cmbFiltroRanking = new JComboBox<>(TIPOS_RANKING);
        cmbFiltroRanking.addActionListener(e -> cargarRanking());
        cmbFiltroRanking.setFont(lblFiltro.getFont().deriveFont(20.0f));
        pnlFiltro.add(cmbFiltroRanking);
        panel.add(pnlFiltro, BorderLayout.NORTH);

        modeloRanking = new RankingTableModel();
        tablaRanking = new JTable(modeloRanking);
        tablaRanking.setRowHeight(28);
        tablaRanking.getTableHeader().setReorderingAllowed(false);
        tablaRanking.setEnabled(false);
        tablaRanking.setFont(lblFiltro.getFont().deriveFont(20.0f));

        javax.swing.table.JTableHeader headerRanking = tablaRanking.getTableHeader();
        headerRanking.setFont(headerRanking.getFont().deriveFont(Font.BOLD, 20.0f));
        headerRanking.setPreferredSize(new Dimension(0, 35));

        tablaRanking.getColumnModel().getColumn(0).setPreferredWidth(60);
        tablaRanking.getColumnModel().getColumn(0).setMaxWidth(60);
        tablaRanking.getColumnModel().getColumn(1).setPreferredWidth(135);
        tablaRanking.getColumnModel().getColumn(2).setPreferredWidth(70);

        panel.add(new JScrollPane(tablaRanking), BorderLayout.CENTER);

        lblPosicionJugador = new JLabel(" ");
        lblPosicionJugador.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPosicionJugador.setFont(lblPosicionJugador.getFont().deriveFont(Font.ITALIC, 12.0f));
        panel.add(lblPosicionJugador, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelCategorias() {

        JPanel panel = new JPanel(new BorderLayout());
        javax.swing.border.TitledBorder borderCat = BorderFactory.createTitledBorder("Progreso por Categoría");
        borderCat.setTitleFont(borderCat.getTitleFont().deriveFont(16.0f));
        panel.setBorder(borderCat);
        panel.setPreferredSize(new Dimension(0, 280));

        modeloCategorias = new CategoriasTableModel();
        tablaCategorias = new JTable(modeloCategorias) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 3) return new ProgressBarRenderer();
                return super.getCellRenderer(row, column);
            }
        };
        tablaCategorias.setRowHeight(24);
        tablaCategorias.getTableHeader().setReorderingAllowed(false);
        tablaCategorias.setEnabled(false);
        tablaCategorias.getColumnModel().getColumn(3).setPreferredWidth(180);
        tablaCategorias.getColumnModel().getColumn(0).setPreferredWidth(40);
        tablaCategorias.getColumnModel().getColumn(0).setMaxWidth(50);
        tablaCategorias.getColumnModel().getColumn(2).setPreferredWidth(50);
        tablaCategorias.setFont(tablaCategorias.getFont().deriveFont(20.0f));

        javax.swing.table.JTableHeader headerCat = tablaCategorias.getTableHeader();
        headerCat.setFont(headerCat.getFont().deriveFont(Font.BOLD, 20.0f));
        headerCat.setPreferredSize(new Dimension(0, 35));

        panel.add(new JScrollPane(tablaCategorias), BorderLayout.CENTER);

        return panel;
    }

    public void refrescar() {

        Jugador j = SesionManager.getInstance().getJugadorActual();
        if (j == null) return;

        int totalPalabras = JugadorDAO.contarPalabrasTotales();
        int descubiertas = JugadorDAO.contarDescubrimientosJugador(j.getId_jugador());

        double porcentaje = totalPalabras == 0 ? 0.0 : (descubiertas * 100.0 / totalPalabras);
        barraProgresoGlobal.setValue((int) Math.round(porcentaje));
        lblPorcentajeGlobal.setText(String.format("%.1f%%", porcentaje));
        lblPalabrasDescubiertas.setText("Palabras descubiertas: " + descubiertas + " / " + totalPalabras);

        lblRachaActual.setText("Racha Actual: " + j.getRacha_actual());
        lblRachaMaxima.setText("Racha Máxima: " + j.getRacha_maxima());
        lblMonedasActuales.setText("Monedas Actuales: $" + j.getMonedas_actuales());
        lblMonedasMaximas.setText("Monedas Máximas: $" + j.getMonedas_maximas());

        modeloCategorias.setDatos(JugadorDAO.obtenerProgresoPorCategorias(j.getId_jugador()));
        cargarRanking();
    }

    private void cargarRanking() {

        Jugador j = SesionManager.getInstance().getJugadorActual();
        if (j == null) return;

        int idx = cmbFiltroRanking.getSelectedIndex();
        List<Object[]> datos;
        int posicionJugador;
        String valorLabel;

        switch (idx) {
            case 0:
                datos = JugadorDAO.obtenerRankingPorPalabras();
                posicionJugador = JugadorDAO.obtenerPosicionEnRankingPorPalabras(j.getId_jugador());
                valorLabel = "Palabras";
                break;
            case 1:
                datos = JugadorDAO.obtenerRankingPorMonedasMaximas();
                posicionJugador = JugadorDAO.obtenerPosicionEnRankingPorMonedas(j.getId_jugador());
                valorLabel = "Monedas";
                break;
            default:
                datos = JugadorDAO.obtenerRankingPorRachaMaxima();
                posicionJugador = JugadorDAO.obtenerPosicionEnRankingPorRacha(j.getId_jugador());
                valorLabel = "Racha";
                break;
        }

        modeloRanking.setDatos(datos, valorLabel);

        boolean jugadorEnTop = datos.stream()
                .anyMatch(fila -> fila[1].equals(j.getNombre()));
        if (!jugadorEnTop && posicionJugador > 0) {
            lblPosicionJugador.setText("Tu posición actual: #" + posicionJugador);
        } else {
            lblPosicionJugador.setText(" ");
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Image fondo = Objects.requireNonNull(Recursos.cargarImagen("bg_contenedor.png")).getImage();

        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }

    private static class RankingTableModel extends AbstractTableModel {

        private String[] columnas = {"Pos", "Jugador", "Estad."};
        private Object[][] datos = new Object[0][3];

        public void setDatos(List<Object[]> filas, String nombreColumnaValor) {
            columnas[2] = nombreColumnaValor;
            datos = new Object[filas.size()][3];
            for (int i = 0; i < filas.size(); i++) {
                datos[i] = filas.get(i);
            }
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() { return datos.length; }

        @Override
        public int getColumnCount() { return columnas.length; }

        @Override
        public String getColumnName(int column) { return columnas[column]; }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return datos[rowIndex][columnIndex];
        }
    }

    private static class CategoriasTableModel extends AbstractTableModel {

        private final String[] columnas = {"#", "Categoría", "Progreso", "Barra"};
        private Object[][] datos = new Object[0][4];

        public void setDatos(List<Object[]> filas) {
            datos = new Object[filas.size()][4];
            for (int i = 0; i < filas.size(); i++) {
                Object[] fila = filas.get(i);
                int descubiertas = (Integer) fila[2];
                int total = (Integer) fila[3];
                datos[i] = new Object[]{
                        i + 1,
                        fila[1],
                        descubiertas + " / " + total,
                        total == 0 ? 0.0 : (descubiertas * 100.0 / total)
                };
            }
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() { return datos.length; }

        @Override
        public int getColumnCount() { return columnas.length; }

        @Override
        public String getColumnName(int column) { return columnas[column]; }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return datos[rowIndex][columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 3) return Double.class;
            return super.getColumnClass(columnIndex);
        }
    }

    private static class ProgressBarRenderer extends JProgressBar implements TableCellRenderer {

        public ProgressBarRenderer() {
            super(0, 100);
            setStringPainted(true);
            setBorderPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            double porcentaje = value == null ? 0.0 : (Double) value;
            setValue((int) Math.round(porcentaje));
            setString(String.format("%.0f%%", porcentaje));
            if (porcentaje >= 100.0) {
                setForeground(new Color(255, 215, 0));
            } else if (porcentaje >= 50.0) {
                setForeground(new Color(192, 192, 192));
            } else if (porcentaje > 0.0) {
                setForeground(new Color(205, 127, 50));
            } else {
                setForeground(Color.LIGHT_GRAY);
            }
            return this;
        }
    }
}
