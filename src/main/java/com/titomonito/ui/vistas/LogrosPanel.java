package com.titomonito.ui.vistas;

import com.titomonito.enums.LogroId;
import com.titomonito.services.LogrosService;
import com.titomonito.services.SesionManager;
import com.titomonito.utils.Recursos;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class LogrosPanel extends JPanel {

    private static final Object[][] CATEGORIAS_CON_MEDALLA = {
            {1, "Alimentos", "cat_alimentos.png"},
            {20, "Anatomía", "cat_cuerpo.png"},
            {2, "Animales", "cat_animals.png"},
            {23, "Astronomía", "cat_astronomia.png"},
            {7, "Botánica", "cat_flowers.png"},
            {19, "Ciudades", "cat_ciudades.png"},
            {5, "Deportes", "cat_deportes.png"},
            {6, "Elementos", "cat_elementos.png"},
            {27, "Juegos/Juguetes", "cat_juegos.png"},
            {21, "Mitología", "cat_mitologia.png"},
            {24, "Naturaleza", "cat_naturaleza.png"},
            {18, "Nombres", "cat_nombres.png"},
            {12, "Países", "cat_paises.png"},
            {15, "Profesiones", "cat_profesiones.png"},
            {22, "Tecnología", "cat_tecnologia.png"},
            {25, "Personajes", "cat_personajes.png"}
    };

    private final ArrayList<TarjetaMedalla> tarjetasMedalla = new ArrayList<>();
    private final ArrayList<TarjetaHazana> tarjetasHazana = new ArrayList<>();
    private final Set<LogroId> mostradosEnSesion = new HashSet<>();

    private JTabbedPane pestanas;

    public LogrosPanel() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(0, 20, 5, 20));
        initUI();
    }

    private void initUI() {
        pestanas = new JTabbedPane();
        pestanas.addTab("Medallas", crearPanelMedallas());
        pestanas.addTab("Hazañas", crearPanelHazanas());
        add(pestanas, BorderLayout.CENTER);
    }

    private JPanel crearPanelMedallas() {
        JPanel panel = new JPanel(new GridLayout(4, 4, 12, 8));
        panel.setOpaque(false);

        for (Object[] cat : CATEGORIAS_CON_MEDALLA) {
            int idCat = (Integer) cat[0];
            String nombre = (String) cat[1];
            String icono = (String) cat[2];
            TarjetaMedalla tarjeta = new TarjetaMedalla(idCat, nombre, icono);
            tarjetasMedalla.add(tarjeta);
            panel.add(tarjeta);
        }

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(panel, BorderLayout.NORTH);
        return wrapper;
    }

    private JPanel crearPanelHazanas() {
        JPanel panel = new JPanel(new GridLayout(0, 5, 10, 10));
        panel.setOpaque(false);

        for (LogroId logro : LogroId.values()) {
            TarjetaHazana tarjeta = new TarjetaHazana(logro);
            tarjetasHazana.add(tarjeta);
            panel.add(tarjeta);
        }

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(panel, BorderLayout.NORTH);
        return wrapper;
    }

    public void refrescar() {
        var jugador = SesionManager.getInstance().getJugadorActual();
        if (jugador == null) return;

        int idJugador = jugador.getId_jugador();

        List<LogroId> nuevos = LogrosService.getInstance().evaluarLogrosHistoricos(idJugador);

        Map<Integer, double[]> progreso = LogrosService.getInstance().obtenerProgresoPorCategoria(idJugador);
        for (TarjetaMedalla tm : tarjetasMedalla) {
            double[] datos = progreso.get(tm.getIdCategoria());
            tm.actualizar(datos);
        }

        Set<String> desbloqueados = com.titomonito.dao.LogrosDAO.obtenerCodigosDesbloqueados(idJugador);
        for (TarjetaHazana th : tarjetasHazana) {
            boolean estabaDesbloqueado = th.isDesbloqueado();
            boolean ahoraDesbloqueado = desbloqueados.contains(th.getCodigo());
            boolean esNuevo = ahoraDesbloqueado && !estabaDesbloqueado && nuevos.contains(th.getLogroId());
            th.actualizar(ahoraDesbloqueado, esNuevo);
        }

        if (!nuevos.isEmpty()) {
            List<LogroId> sinMostrar = new ArrayList<>();
            for (LogroId logro : nuevos) {
                if (!mostradosEnSesion.contains(logro)) {
                    sinMostrar.add(logro);
                    mostradosEnSesion.add(logro);
                }
            }
            if (!sinMostrar.isEmpty()) {
                mostrarPopupNuevosLogros(sinMostrar);
            }
        }
    }

    private void mostrarPopupNuevosLogros(List<LogroId> logros) {
        int totalPremio = logros.stream().mapToInt(LogroId::getPremio).sum();

        StringBuilder sb = new StringBuilder("<html><div style='width:320px'>");
        sb.append("<b>¡Nuevo").append(logros.size() > 1 ? "s" : "").append(" logro").append(logros.size() > 1 ? "s" : "").append(" desbloqueado").append(logros.size() > 1 ? "s" : "").append("!</b><br><br>");
        for (LogroId logro : logros) {
            sb.append("&#9733; <b>").append(logro.getNombre()).append("</b>");
            sb.append(" <span style='color:#FFD700'>+$").append(logro.getPremio()).append("</span><br>");
            sb.append("&nbsp;&nbsp;&nbsp;").append(logro.getDescripcion()).append("<br><br>");
        }
        sb.append("<span style='color:#FFD700'>+$").append(totalPremio).append("</span> monedas agregadas a tu cuenta.");
        sb.append("</div></html>");

        JOptionPane.showMessageDialog(
                this,
                sb.toString(),
                "Logro Desbloqueado",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void reiniciarEstadoSesion() {
        mostradosEnSesion.clear();
        for (TarjetaHazana th : tarjetasHazana) {
            th.actualizar(th.isDesbloqueado(), false);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image fondo = Objects.requireNonNull(Recursos.cargarImagen("bg_center.png")).getImage();
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}
