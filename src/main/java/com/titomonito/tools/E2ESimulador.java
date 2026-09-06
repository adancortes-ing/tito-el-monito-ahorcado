package com.titomonito.tools;

import com.titomonito.config.Constantes;
import com.titomonito.enums.LogroId;
import com.titomonito.services.UtilsJuego;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class E2ESimulador {

    private static final int RONDAS = 50;
    private static final int BANCO_INICIAL = 50;
    private static final int VIDAS_MAX = 6;

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("  E2E SIMULADOR ECONOMIA - TITO EL MONITO AHORCADO (Fase 9)");
        System.out.println("=================================================================");
        System.out.println();

        simular("CONSERVADOR", new EstrategiaConservador());
        System.out.println();
        simular("AGRESIVO", new EstrategiaAgresivo());
        System.out.println();
        simular("PROMEDIO", new EstrategiaPromedio());
    }

    private static void simular(String nombre, Estrategia estrategia) {
        System.out.println("=========================================================");
        System.out.println(" Estrategia: " + nombre);
        System.out.println("=========================================================");
        System.out.printf("%-4s %-3s %-6s %-8s %-8s %-7s %-7s %-10s%n",
                "Rnd", "Dif", "BcoIni", "CapDisp", "Gastado", "Premio", "BcoFin", "LogrosNuevos");
        System.out.println("---------------------------------------------------------");

        EstadoSimulacion estado = new EstadoSimulacion();
        estado.banco = BANCO_INICIAL;
        estado.monedasMaximas = BANCO_INICIAL;
        estado.rachaActual = 0;
        estado.rachaMaxima = 0;
        estado.palabrasDescubiertas = 0;
        estado.categoriasGanadas.clear();
        estado.rachaGlobalSesion = 0;
        estado.rachaExtremoSesion = 0;
        estado.rachaImposibleSesion = 0;
        estado.logrosDesbloqueados.clear();

        int rondasConsecutivasMismaDificultad = 0;
        int ultimaDificultad = -1;

        for (int r = 1; r <= RONDAS; r++) {
            int dificultad = estrategia.elegirDificultad(r, estado);
            if (dificultad != ultimaDificultad) {
                rondasConsecutivasMismaDificultad = 0;
            } else {
                rondasConsecutivasMismaDificultad++;
            }
            ultimaDificultad = dificultad;

            int longitudPalabra = 4 + new Random().nextInt(7);

            int capDisponible = Math.min(
                    Constantes.obtenerCapGastoPorDificultad(dificultad),
                    Integer.MAX_VALUE);

            List<String> utilesComprados = estrategia.decidirUtiles(dificultad, estado);

            int gastado = 0;
            for (String util : utilesComprados) {
                int precio = Constantes.obtenerPrecio(util);
                if (gastado + precio > capDisponible) continue;
                if (estado.banco < precio) continue;
                String tier = Constantes.obtenerTier(util);
                int countTier = contarTierEnComprados(utilesComprados.subList(0, utilesComprados.indexOf(util) + 1), tier);
                int[] capTier = Constantes.obtenerCapTierPorDificultad(dificultad);
                int max = switch (tier) {
                    case Constantes.TIER_BASICO -> capTier[0];
                    case Constantes.TIER_MEDIO -> capTier[1];
                    case Constantes.TIER_CARO -> capTier[2];
                    default -> 0;
                };
                if (countTier > max) continue;
                if (util.equals(Constantes.UTIL_MARCATEXTOS) && dificultad == Constantes.DIFICULTAD_IMPOSIBLE) continue;
                if (util.equals(Constantes.UTIL_TIJERAS) && rondasConsecutivasMismaDificultad > 0) continue;
                gastado += precio;
                estado.banco -= precio;
            }

            int bancoIni = estado.banco + gastado;

            int vidasRestantes = estrategia.simularResultado(dificultad, longitudPalabra, utilesComprados);

            double mult = UtilsJuego.getMultiplicador(dificultad);
            int premioBaseAjustado = (int) (10 * mult);
            int premioLetras = vidasRestantes == longitudPalabra ? longitudPalabra * 2 : vidasRestantes * 2;
            int premio = vidasRestantes == longitudPalabra
                    ? premioBaseAjustado + premioLetras + vidasRestantes
                    : (int) Math.round(premioLetras * 0.5);

            estado.banco += premio;
            int bancoFin = estado.banco;
            if (bancoFin > estado.monedasMaximas) estado.monedasMaximas = bancoFin;

            String logrosNuevos = "";
            if (vidasRestantes == longitudPalabra) {
                estado.rachaActual++;
                if (estado.rachaActual > estado.rachaMaxima) estado.rachaMaxima = estado.rachaActual;
                estado.rachaGlobalSesion++;
                if (dificultad == 4) estado.rachaExtremoSesion++;
                if (dificultad == 5) estado.rachaImposibleSesion++;
                estado.palabrasDescubiertas++;
                estado.categoriasGanadas.add(dificultad * 100 + r % 10);
                logrosNuevos = evaluarLogros(estado, dificultad, vidasRestantes, longitudPalabra,
                        utilesComprados, premio, bancoIni);
            } else {
                estado.rachaActual = 0;
                estado.rachaGlobalSesion = 0;
                estado.rachaExtremoSesion = 0;
                estado.rachaImposibleSesion = 0;
            }

            System.out.printf("%-4d %-3d %-6d %-8d %-8d %-7d %-7d %-10s%n",
                    r, dificultad, bancoIni, capDisponible, gastado, premio, bancoFin,
                    logrosNuevos.isEmpty() ? "-" : logrosNuevos);
        }

        System.out.println("---------------------------------------------------------");
        System.out.printf("Banco final: %d | Monedas maximas: %d | Racha maxima: %d | Palabras: %d | Logros: %d%n",
                estado.banco, estado.monedasMaximas, estado.rachaMaxima, estado.palabrasDescubiertas,
                estado.logrosDesbloqueados.size());
    }

    private static int contarTierEnComprados(List<String> utiles, String tier) {
        int c = 0;
        for (String u : utiles) {
            if (tier.equals(Constantes.obtenerTier(u))) c++;
        }
        return c;
    }

    private static String evaluarLogros(EstadoSimulacion e, int dificultad, int vidasRestantes,
                                        int longitudPalabra, List<String> utiles,
                                        int premio, int bancoIni) {
        List<String> nuevos = new ArrayList<>();

        if (vidasRestantes == 6 && !e.logrosDesbloqueados.contains("RT_PERFECTA")) {
            nuevos.add("RT_PERFECTA");
        }
        if (vidasRestantes == 5 && !e.logrosDesbloqueados.contains("RT_CASI_PERFECTA")) {
            nuevos.add("RT_CASI_PERFECTA");
        }
        if (vidasRestantes == 1 && !e.logrosDesbloqueados.contains("RT_MILAGRO")) {
            nuevos.add("RT_MILAGRO");
        }
        if (utiles.isEmpty() && !e.logrosDesbloqueados.contains("RT_SIN_UTILES")) {
            nuevos.add("RT_SIN_UTILES");
        }
        if (utiles.contains(Constantes.UTIL_SACAPUNTAS) && !e.logrosDesbloqueados.contains("RT_SACAPUNTAS")) {
            nuevos.add("RT_SACAPUNTAS");
        }
        if (utiles.contains(Constantes.UTIL_MARCATEXTOS) && !e.logrosDesbloqueados.contains("RT_MARCATEXTOS")) {
            nuevos.add("RT_MARCATEXTOS");
        }
        if (e.rachaGlobalSesion >= 10 && !e.logrosDesbloqueados.contains("RT_RACHA_SESION_10")) {
            nuevos.add("RT_RACHA_SESION_10");
        }
        if (e.rachaGlobalSesion >= 20 && !e.logrosDesbloqueados.contains("RT_RACHA_SESION_20")) {
            nuevos.add("RT_RACHA_SESION_20");
        }
        if (e.rachaExtremoSesion >= 10 && !e.logrosDesbloqueados.contains("RT_RACHA_EXTREMO")) {
            nuevos.add("RT_RACHA_EXTREMO");
        }
        if (e.rachaImposibleSesion >= 5 && !e.logrosDesbloqueados.contains("RT_RACHA_IMPOSIBLE")) {
            nuevos.add("RT_RACHA_IMPOSIBLE");
        }
        if (e.categoriasGanadas.size() >= 3 && !e.logrosDesbloqueados.contains("RT_CAT_3")) {
            nuevos.add("RT_CAT_3");
        }
        if (e.categoriasGanadas.size() >= 8 && !e.logrosDesbloqueados.contains("RT_CAT_8")) {
            nuevos.add("RT_CAT_8");
        }
        if (premio > 50 && !e.logrosDesbloqueados.contains("RT_PARTIDA_RICA")) {
            nuevos.add("RT_PARTIDA_RICA");
        }
        if (utiles.size() >= 3 && !e.logrosDesbloqueados.contains("RT_TRES_UTILES")) {
            nuevos.add("RT_TRES_UTILES");
        }
        if (utiles.size() >= 5 && !e.logrosDesbloqueados.contains("RT_CINCO_UTILES")) {
            nuevos.add("RT_CINCO_UTILES");
        }
        if (e.palabrasDescubiertas >= 100 && !e.logrosDesbloqueados.contains("DB_PAL_100")) {
            nuevos.add("DB_PAL_100");
        }
        if (e.rachaMaxima >= 10 && !e.logrosDesbloqueados.contains("DB_RAC_10")) {
            nuevos.add("DB_RAC_10");
        }
        if (e.monedasMaximas >= 500 && !e.logrosDesbloqueados.contains("DB_MON_500")) {
            nuevos.add("DB_MON_500");
        }
        if (e.monedasMaximas >= 2500 && !e.logrosDesbloqueados.contains("DB_MON_2500")) {
            nuevos.add("DB_MON_2500");
        }

        for (String l : nuevos) e.logrosDesbloqueados.add(l);
        return String.join(",", nuevos);
    }

    interface Estrategia {
        int elegirDificultad(int ronda, EstadoSimulacion estado);
        List<String> decidirUtiles(int dificultad, EstadoSimulacion estado);
        int simularResultado(int dificultad, int longitudPalabra, List<String> utiles);
    }

    static class EstrategiaConservador implements Estrategia {
        @Override
        public int elegirDificultad(int ronda, EstadoSimulacion estado) {
            if (ronda <= 5) return 1;
            if (ronda <= 15) return 2;
            if (ronda <= 30) return 3;
            if (ronda <= 40) return 4;
            return 5;
        }

        @Override
        public List<String> decidirUtiles(int dificultad, EstadoSimulacion estado) {
            List<String> utiles = new ArrayList<>();
            if (estado.banco < 50) return utiles;
            if (dificultad <= 2) {
                if (estado.banco >= 20) utiles.add(Constantes.UTIL_SACAPUNTAS);
                if (estado.banco >= 30) utiles.add(Constantes.UTIL_GOMA);
            } else if (dificultad == 3) {
                if (estado.banco >= 20) utiles.add(Constantes.UTIL_SACAPUNTAS);
            } else if (dificultad == 4) {
                if (estado.banco >= 20) utiles.add(Constantes.UTIL_SACAPUNTAS);
            } else {
                if (estado.banco >= 30) utiles.add(Constantes.UTIL_GOMA);
            }
            return utiles;
        }

        @Override
        public int simularResultado(int dificultad, int longitudPalabra, List<String> utiles) {
            int probVictoria = switch (dificultad) {
                case 1 -> 90;
                case 2 -> 75;
                case 3 -> 55;
                case 4 -> 35;
                case 5 -> 15;
                default -> 50;
            };
            if (utiles.contains(Constantes.UTIL_PLUMA)) probVictoria += 15;
            if (utiles.contains(Constantes.UTIL_MARCATEXTOS)) probVictoria += 20;
            if (utiles.contains(Constantes.UTIL_GOMA)) probVictoria += 10;
            if (utiles.contains(Constantes.UTIL_SACAPUNTAS)) probVictoria += 5;

            boolean gana = new Random().nextInt(100) < probVictoria;
            if (!gana) {
                int errores = 3 + new Random().nextInt(4);
                return Math.max(0, longitudPalabra - errores);
            }
            int vidasRestantes = 2 + new Random().nextInt(5);
            return longitudPalabra;
        }
    }

    static class EstrategiaAgresivo implements Estrategia {
        @Override
        public int elegirDificultad(int ronda, EstadoSimulacion estado) {
            return Math.min(5, 1 + (ronda / 5));
        }

        @Override
        public List<String> decidirUtiles(int dificultad, EstadoSimulacion estado) {
            List<String> utiles = new ArrayList<>();
            int[] caps = Constantes.obtenerCapTierPorDificultad(dificultad);
            int capGasto = Constantes.obtenerCapGastoPorDificultad(dificultad);

            if (caps[2] > 0 && estado.banco >= Constantes.PRECIO_PLUMA) {
                utiles.add(Constantes.UTIL_PLUMA);
            } else if (caps[2] > 0 && estado.banco >= Constantes.PRECIO_MARCATEXTOS
                    && dificultad != Constantes.DIFICULTAD_IMPOSIBLE) {
                utiles.add(Constantes.UTIL_MARCATEXTOS);
            }
            if (caps[2] > 1 && estado.banco >= Constantes.PRECIO_MARCATEXTOS
                    && dificultad != Constantes.DIFICULTAD_IMPOSIBLE
                    && !utiles.contains(Constantes.UTIL_MARCATEXTOS)) {
                utiles.add(Constantes.UTIL_MARCATEXTOS);
            }
            if (caps[1] > 0 && estado.banco >= Constantes.PRECIO_GOMA) {
                utiles.add(Constantes.UTIL_GOMA);
            } else if (caps[1] > 0 && estado.banco >= Constantes.PRECIO_TIJERAS) {
                utiles.add(Constantes.UTIL_TIJERAS);
            }
            if (caps[0] > 0 && estado.banco >= Constantes.PRECIO_SACAPUNTAS) {
                utiles.add(Constantes.UTIL_SACAPUNTAS);
                if (estado.banco >= Constantes.PRECIO_SACAPUNTAS * 2
                        && (Constantes.obtenerPrecio(utiles.get(0)) + Constantes.PRECIO_SACAPUNTAS * 2) <= capGasto) {
                    utiles.add(Constantes.UTIL_SACAPUNTAS);
                }
            }
            return utiles;
        }

        @Override
        public int simularResultado(int dificultad, int longitudPalabra, List<String> utiles) {
            int probVictoria = switch (dificultad) {
                case 1 -> 85;
                case 2 -> 70;
                case 3 -> 60;
                case 4 -> 40;
                case 5 -> 20;
                default -> 50;
            };
            if (utiles.contains(Constantes.UTIL_PLUMA)) probVictoria += 15;
            if (utiles.contains(Constantes.UTIL_MARCATEXTOS)) probVictoria += 20;
            if (utiles.contains(Constantes.UTIL_GOMA)) probVictoria += 10;
            if (utiles.contains(Constantes.UTIL_SACAPUNTAS)) probVictoria += 5;

            boolean gana = new Random().nextInt(100) < probVictoria;
            if (!gana) {
                int errores = 3 + new Random().nextInt(4);
                return Math.max(0, longitudPalabra - errores);
            }
            int vidasRestantes = 2 + new Random().nextInt(5);
            return longitudPalabra;
        }
    }

    static class EstrategiaPromedio implements Estrategia {
        @Override
        public int elegirDificultad(int ronda, EstadoSimulacion estado) {
            if (ronda <= 8) return 1;
            if (ronda <= 20) return 2;
            if (ronda <= 32) return 3;
            if (ronda <= 42) return 4;
            return 5;
        }

        @Override
        public List<String> decidirUtiles(int dificultad, EstadoSimulacion estado) {
            List<String> utiles = new ArrayList<>();
            int[] caps = Constantes.obtenerCapTierPorDificultad(dificultad);

            if (caps[2] > 0 && estado.banco >= Constantes.PRECIO_PLUMA && dificultad <= 2) {
                utiles.add(Constantes.UTIL_PLUMA);
            } else if (caps[2] > 0 && estado.banco >= Constantes.PRECIO_MARCATEXTOS
                    && dificultad != Constantes.DIFICULTAD_IMPOSIBLE) {
                utiles.add(Constantes.UTIL_MARCATEXTOS);
            } else if (caps[2] > 0 && estado.banco >= Constantes.PRECIO_PLUMA) {
                utiles.add(Constantes.UTIL_PLUMA);
            }
            if (caps[1] > 0 && estado.banco >= Constantes.PRECIO_GOMA) {
                utiles.add(Constantes.UTIL_GOMA);
            }
            if (caps[0] > 0 && estado.banco >= Constantes.PRECIO_SACAPUNTAS) {
                utiles.add(Constantes.UTIL_SACAPUNTAS);
            }
            return utiles;
        }

        @Override
        public int simularResultado(int dificultad, int longitudPalabra, List<String> utiles) {
            int probVictoria = switch (dificultad) {
                case 1 -> 88;
                case 2 -> 73;
                case 3 -> 58;
                case 4 -> 38;
                case 5 -> 18;
                default -> 50;
            };
            if (utiles.contains(Constantes.UTIL_PLUMA)) probVictoria += 12;
            if (utiles.contains(Constantes.UTIL_MARCATEXTOS)) probVictoria += 15;
            if (utiles.contains(Constantes.UTIL_GOMA)) probVictoria += 8;
            if (utiles.contains(Constantes.UTIL_SACAPUNTAS)) probVictoria += 4;

            boolean gana = new Random().nextInt(100) < probVictoria;
            if (!gana) {
                int errores = 3 + new Random().nextInt(4);
                return Math.max(0, longitudPalabra - errores);
            }
            int vidasRestantes = 2 + new Random().nextInt(5);
            return longitudPalabra;
        }
    }

    static class EstadoSimulacion {
        int banco = 50;
        int monedasMaximas = 50;
        int rachaActual = 0;
        int rachaMaxima = 0;
        int palabrasDescubiertas = 0;
        Set<Integer> categoriasGanadas = new HashSet<>();
        int rachaGlobalSesion = 0;
        int rachaExtremoSesion = 0;
        int rachaImposibleSesion = 0;
        Set<String> logrosDesbloqueados = new HashSet<>();
    }
}
