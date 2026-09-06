package com.titomonito.enums;

public enum NivelMedalla {

    BLOQUEADO(0.0),
    BRONCE(0.25),
    PLATA(0.50),
    ORO(1.00);

    private final double umbral;

    NivelMedalla(double umbral) {
        this.umbral = umbral;
    }

    public double getUmbral() {
        return umbral;
    }

    public static NivelMedalla fromProgreso(double porcentaje) {
        if (porcentaje >= ORO.getUmbral()) {
            return ORO;
        } else if (porcentaje >= PLATA.getUmbral()) {
            return PLATA;
        } else if (porcentaje >= BRONCE.getUmbral()) {
            return BRONCE;
        } else {
            return BLOQUEADO;
        }
    }
}