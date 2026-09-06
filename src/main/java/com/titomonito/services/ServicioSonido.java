package com.titomonito.services;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioSonido {
    private static final Logger LOGGER = Logger.getLogger(ServicioSonido.class.getName());
    private static ServicioSonido instancia;

    private final Map<String, Clip> clips = new HashMap<>();
    private boolean habilitado = true;

    private ServicioSonido() {
        cargar("tecla_acierto");
        cargar("tecla_error");
        cargar("tic-tac");
        cargar("victoria");
        cargar("derrota");
        cargar("powerup");
        cargar("lesion");
    }

    public static ServicioSonido getInstance() {
        if (instancia == null) {
            instancia = new ServicioSonido();
        }
        return instancia;
    }

    private void cargar(String nombre) {
        try {
            URL url = ServicioSonido.class.getResource("/audio/" + nombre + ".wav");
            if (url == null) {
                LOGGER.log(Level.WARNING, "Audio no encontrado: /audio/{0}.wav", nombre);
                return;
            }
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clips.put(nombre, clip);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "No se pudo cargar el audio: {0}", nombre);
        }
    }

    public void reproducir(String nombre) {
        if (!habilitado) return;
        Clip clip = clips.get(nombre);
        if (clip == null) return;
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }
}