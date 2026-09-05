package com.titomonito.enums;

public enum NivelMedalla {

    BLOQUEADO(0.0, "medalla_marco_bloqueado.png"),
    BRONCE(0.25, "medalla_marco_bronce.png"),
    PLATA(0.50, "medalla_marco_plata.png"),
    ORO(1.00, "medalla_marco_oro.png");

    private final double umbral;
    private final String marcoPath;

    NivelMedalla(double umbral, String marcoPath) {
        this.umbral = umbral;
        this.marcoPath = marcoPath;
    }

    public double getUmbral() {
        return umbral;
    }

    public String getMarcoPath() {
        return marcoPath;
    }

    public static NivelMedalla fromProgreso(double porcentaje) {
        if (porcentaje >= 1.0) {
            return ORO;
        } else if (porcentaje >= 0.50) {
            return PLATA;
        } else if (porcentaje >= 0.25) {
            return BRONCE;
        } else {
            return BLOQUEADO;
        }
    }
}
