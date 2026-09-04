package com.titomonito.models;

import com.titomonito.enums.LogroId;
import com.titomonito.enums.NivelMedalla;

import java.time.LocalDateTime;

public class Logro {

    private final LogroId id;
    private boolean desbloqueado;
    private boolean esNuevoEnSesion;
    private LocalDateTime fechaDesbloqueo;

    public Logro(LogroId id) {
        this.id = id;
        this.desbloqueado = false;
        this.esNuevoEnSesion = false;
        this.fechaDesbloqueo = null;
    }

    public LogroId getId() {
        return id;
    }

    public String getNombre() {
        return id.getNombre();
    }

    public String getDescripcion() {
        return id.getDescripcion();
    }

    public String getIconoPath() {
        return id.getIconoPath();
    }

    public String getCodigo() {
        return id.getCodigo();
    }

    public boolean isDesbloqueado() {
        return desbloqueado;
    }

    public void setDesbloqueado(boolean desbloqueado) {
        this.desbloqueado = desbloqueado;
    }

    public boolean isEsNuevoEnSesion() {
        return esNuevoEnSesion;
    }

    public void setEsNuevoEnSesion(boolean esNuevoEnSesion) {
        this.esNuevoEnSesion = esNuevoEnSesion;
    }

    public LocalDateTime getFechaDesbloqueo() {
        return fechaDesbloqueo;
    }

    public void setFechaDesbloqueo(LocalDateTime fechaDesbloqueo) {
        this.fechaDesbloqueo = fechaDesbloqueo;
    }
}
