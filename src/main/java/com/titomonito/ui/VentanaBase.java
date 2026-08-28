package com.titomonito.ui;

import com.titomonito.config.Constantes;
import com.titomonito.config.GlobalConfig;
import com.titomonito.services.LogicaJuego;
import com.titomonito.ui.vistas.*;
import com.titomonito.utils.Recursos;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class VentanaBase extends JFrame {

    public PanelHeader pnlHeader;
    private CardLayout vistas;
    private JPanel contenedor;

    private PanelMenu panelMenu;
    private InicioPanel inicio;
    private PreGamePanel preGamePanel;
    private JuegoPanel juego;

    public VentanaBase() {

        //Propiedades de la ventana principal
        //==============================================================================================================
        setTitle("Tito el Monito Ahorcado");
        setIconImage(Objects.requireNonNull(Recursos.cargarImagen("icono.png")).getImage());
        setSize(GlobalConfig.MEDIDA_VENTANA);
        setLayout(new BorderLayout());
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        //Iniciar listeners y services
        new com.titomonito.controller.Navegacion(panelMenu, inicio, this);
        new com.titomonito.controller.ControlVentana(this);
        new com.titomonito.controller.ControlPreGame(preGamePanel, this);
        LogicaJuego.getInstance().setVistaJuego(juego);
    }

    public void cambiarVista(String vista) {

        vistas.show(contenedor, vista);
    }

    private void initUI() {

        pnlHeader = new PanelHeader();
        add(pnlHeader, BorderLayout.NORTH);

        panelMenu = new PanelMenu();
        add(panelMenu, BorderLayout.WEST);

        vistas = new CardLayout();
        contenedor = new JPanel(vistas);
        add(contenedor, BorderLayout.CENTER);

        inicio = new InicioPanel();
        EstadisticasPanel estadisticas = new EstadisticasPanel();
        LogrosPanel logros = new LogrosPanel();
        AyudaPanel ayuda = new AyudaPanel();
        OpcionesPanel opciones = new OpcionesPanel();
        preGamePanel = new PreGamePanel();
        juego = new JuegoPanel();

        contenedor.add(inicio, Constantes.INICIO);
        contenedor.add(estadisticas, Constantes.ESTADISTICAS);
        contenedor.add(logros, Constantes.LOGROS);
        contenedor.add(ayuda, Constantes.AYUDA);
        contenedor.add(opciones, Constantes.OPCIONES);
        contenedor.add(preGamePanel, Constantes.PREGAME);
        contenedor.add(juego, Constantes.JUEGO);

        setPanelInferior();
    }

    private void setPanelInferior() {

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.X_AXIS));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        panelInferior.setBackground(Constantes.COLOR_VERDE);

        Font footerFont = Recursos.cargarFuente("tahoma.ttf", 12.0f);

        JLabel lblVersion = new JLabel();
        lblVersion.setFont(footerFont);
        lblVersion.setForeground(Constantes.COLOR_GRIS_OSCURO);
        lblVersion.setText("<html>Tito el Monito Ahorcado - <b>" + GlobalConfig.VERSION_JUEGO + "</b></html>");
        panelInferior.add(lblVersion);
        panelInferior.add(Box.createHorizontalGlue());

        JLabel lblDesarrollador = new JLabel();
        lblDesarrollador.setFont(footerFont);
        lblDesarrollador.setForeground(Constantes.COLOR_GRIS_OSCURO);
        lblDesarrollador.setText("Developed by: CorLogic - Copyright © 2026");
        panelInferior.add(lblDesarrollador);
        add(panelInferior, BorderLayout.SOUTH);
    }

    public final PanelHeader getPnlHeader() {

        return pnlHeader;
    }

    public static class PanelHeader extends JPanel {

        public PanelHeader() {

            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setBorder(BorderFactory.createEmptyBorder(33, 270, 5, 15));
            setPreferredSize(new Dimension(1080, 120));

            initComponentes();
        }

        private void initComponentes() {

            JLabel lblIconoJugador = new JLabel("Jugador: ");
            lblIconoJugador.setFont(lblIconoJugador.getFont().deriveFont(Font.BOLD));
            lblIconoJugador.setAlignmentY(Component.TOP_ALIGNMENT);
            lblIconoJugador.setIcon(Recursos.cargarImagen("header_player.png"));
            lblIconoJugador.setVerticalTextPosition(SwingConstants.TOP);
            add(lblIconoJugador);

            JLabel lblValJugador = new JLabel("[Nombre del Jugador]");
            lblValJugador.setAlignmentY(Component.TOP_ALIGNMENT);
            lblValJugador.setMaximumSize(new Dimension(250, 35));
            lblValJugador.setPreferredSize(new Dimension(250, 35));
            add(lblValJugador);

            add(Box.createHorizontalStrut(50));

            JLabel lblIconoMonedas = new JLabel("Monedas: ");
            lblIconoMonedas.setFont(lblIconoMonedas.getFont().deriveFont(Font.BOLD));
            lblIconoMonedas.setAlignmentY(Component.TOP_ALIGNMENT);
            lblIconoMonedas.setIcon(Recursos.cargarImagen("header_monedas.png"));
            lblIconoMonedas.setVerticalTextPosition(SwingConstants.TOP);
            add(lblIconoMonedas);

            JLabel lblValMonedas = new JLabel("[$ 0000]");
            lblValMonedas.setAlignmentY(Component.TOP_ALIGNMENT);
            add(lblValMonedas);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Image fondo = Objects.requireNonNull(Recursos.cargarImagen("bg_header.png")).getImage();

            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);

        }
    }
}
