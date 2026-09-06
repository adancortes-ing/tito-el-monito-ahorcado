package com.titomonito.ui.vistas;

import com.titomonito.config.GlobalConfig;
import com.titomonito.ui.VentanaBase;
import com.titomonito.utils.Recursos;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class AcercaPanel extends JDialog {

    private static final String URL_REPOSITORIO = "https://github.com/adancortes-ing/tito-el-monito-ahorcado";

    private static final Font F_TITULO   = Recursos.Fuentes.fuenteComic(Font.BOLD, 24);
    private static final Font F_SUBTITULO = Recursos.Fuentes.fuenteComic(Font.PLAIN, 14);
    private static final Font F_VERSION  = Recursos.Fuentes.fuenteComic(Font.BOLD, 14);
    private static final Font F_SECCION  = Recursos.Fuentes.fuenteComic(Font.BOLD, 16);
    private static final Font F_TEXTO    = Recursos.Fuentes.fuenteComic(Font.PLAIN, 14);
    private static final Font F_ENLACE   = Recursos.Fuentes.fuenteComic(Font.PLAIN, 14);
    private static final Font F_PIE      = Recursos.Fuentes.fuenteComic(Font.PLAIN, 12);

    public AcercaPanel(VentanaBase owner) {

        super(owner, true);

        setTitle("Acerca de Tito el Monito Ahorcado");
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(crearContenido());
        setSize(450, 800);
        setPreferredSize(getSize());
        setMaximumSize(getPreferredSize());
        setLocationRelativeTo(owner);
    }

    private JPanel crearContenido() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 24, 20, 24));

        JLabel logoJuego = new JLabel();
        logoJuego.setIcon(Recursos.cargarImagen("logo_tito.png"));
        logoJuego.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoJuego);

        panel.add(Box.createVerticalStrut(12));

        JLabel titulo = new JLabel("Tito el Monito Ahorcado");
        titulo.setFont(F_TITULO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);

        panel.add(Box.createVerticalStrut(4));

        JLabel subtitulo = new JLabel("El clásico ahorcado reinventado con lápices y estrategia");
        subtitulo.setFont(F_SUBTITULO);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(subtitulo);

        panel.add(Box.createVerticalStrut(8));

        JLabel version = new JLabel("Versión " + GlobalConfig.VERSION_JUEGO.replace("version ", ""));
        version.setFont(F_VERSION);
        version.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(version);

        panel.add(Box.createVerticalStrut(10));
        panel.add(crearSeparador());
        panel.add(Box.createVerticalStrut(10));

        panel.add(crearEtiqueta("🐵  Sobre el juego", F_SECCION));
        panel.add(crearParrafo(
            "Ayuda a Tito a adivinar la palabra, gestiona monedas, compra útiles escolares y conquista más " +
                    "de 30 logros y medallas por cada categoría."));

        panel.add(Box.createVerticalStrut(10));
        panel.add(crearSeparador());
        panel.add(Box.createVerticalStrut(10));

        panel.add(crearEtiqueta("👨‍💻  Desarrollador", F_SECCION));
        panel.add(crearParrafo("Adán Cortés Rodríguez"));

        panel.add(Box.createVerticalStrut(8));

        JLabel logoMarca = new JLabel();
        logoMarca.setIcon(Recursos.cargarImagen("corlogic_logo.png"));
        logoMarca.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoMarca);

        panel.add(Box.createVerticalStrut(8));

        panel.add(crearEtiqueta("🛠️  Tecnologías", F_SECCION));
        panel.add(crearParrafo("Java 21  •  Swing + FlatLaf  •  SQLite"));

        panel.add(Box.createVerticalStrut(10));
        panel.add(crearSeparador());
        panel.add(Box.createVerticalStrut(10));

        panel.add(crearEtiqueta("🌐  Repositorio", F_SECCION));
        panel.add(crearEnlace(URL_REPOSITORIO));

        panel.add(Box.createVerticalStrut(10));
        panel.add(crearSeparador());
        panel.add(Box.createVerticalStrut(10));

        JLabel licencia = crearParrafo("Licenciado bajo GNU GPL v3.0");
        licencia.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(licencia);

        JLabel copyright = new JLabel("Copyright © 2026 CorLogic. Todos los derechos reservados.");
        copyright.setFont(F_PIE);
        copyright.setAlignmentX(Component.CENTER_ALIGNMENT);
        copyright.setForeground(Color.GRAY);
        panel.add(copyright);

        panel.add(Box.createVerticalGlue());

        JButton btnCerrar = crearBtnCerrar();
        btnCerrar.addActionListener(e -> dispose());
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(btnCerrar);

        return panel;
    }

    private JLabel crearEtiqueta(String texto, Font fuente) {

        JLabel lbl = new JLabel(texto);
        lbl.setFont(fuente);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        return lbl;
    }

    private JLabel crearParrafo(String texto) {

        JLabel lbl = new JLabel("<html><div style='text-align:center;'>" + texto + "</div></html>");
        lbl.setFont(F_TEXTO);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setMaximumSize(new Dimension(330, Integer.MAX_VALUE));
        return lbl;
    }

    private JEditorPane crearEnlace(String url) {

        JEditorPane enlace = new JEditorPane();
        enlace.setContentType("text/html");
        enlace.setEditable(false);
        enlace.setOpaque(false);
        enlace.setFocusable(false);
        enlace.setBorder(null);
        enlace.setFont(F_ENLACE);
        enlace.setText("<html><div style='text-align:center;'>"
                     + "<a href='" + url + "'>" + url.replace("https://", "") + "</a>"
                     + "</div></html>");
        enlace.setAlignmentX(Component.CENTER_ALIGNMENT);
        enlace.setMaximumSize(new Dimension(330, 40));
        enlace.setPreferredSize(new Dimension(330, 32));

        enlace.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                abrirUrl(e.getURL().toString());
            }
        });

        return enlace;
    }

    private JSeparator crearSeparador() {
        return new JSeparator(SwingConstants.HORIZONTAL);
    }

    private JButton crearBtnCerrar() {

        JButton btn = new JButton("Cerrar");
        btn.setFont(Recursos.Fuentes.fuenteComic(Font.BOLD, 16));
        btn.setFocusable(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBackground(com.titomonito.config.Constantes.COLOR_VERDE);
        return btn;
    }

    private void abrirUrl(String url) {

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(URI.create(url));
            }
        } catch (IOException | SecurityException ex) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo abrir el enlace en el navegador.",
                    "Acerca de",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}