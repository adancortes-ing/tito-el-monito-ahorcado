# Tito el Monito Ahorcado - Agent Instructions

## Tech Stack & Tooling
- **Language/Runtime:** Java 21 (`pom.xml`)
- **Build System:** Maven (`pom.xml`)
- **GUI Framework:** Java Swing with FlatLaf (`com.formdev.flatlaf`, theme: `FlatMTGitHubIJTheme`)
- **Database:** SQLite (xerial sqlite-jdbc 3.53.4.0)
- **Logging:** java.util.logging + FileHandler (rotates in user home)

## Build & Run Commands
- Compile: `mvn clean compile`
- Package: `mvn package`
- Run: `mvn exec:java -Dexec.mainClass="com.titomonito.Main"`

## Architecture & Project Structure
- **Pattern:** Model-View-Controller (MVC) in Swing.
- **Entrypoint:** `com.titomonito.Main` -> FlatLaf theme, `ConfigDB.initDB()`, load categories, show `VentanaLogin`.
- **Navigation:** `CardLayout` in `VentanaBase`. View IDs in `Constantes`: INICIO, ESTADISTICAS, LOGROS, AYUDA, OPCIONES, ACERCA_DE, PREGAME, JUEGO.
- **Window flow:** `VentanaLogin` -> `VentanaBase` (with `PanelMenu`) -> views in `com.titomonito.ui.vistas`.
- **Resources:** Fonts (`IndieFlower-Regular.ttf`, `tahoma.ttf`) in `src/main/resources/fonts/`; UI assets in `src/main/resources/ui/`; audio in `src/main/resources/audio/`.

### Services (`com.titomonito.services`)
| Service | Role |
|---|---|
| `LogicaJuego` | Game engine singleton: vidas, tiempo, letras, power-ups, liquidacion. |
| `LogrosService` | Achievement evaluation (historical + in-game) and prize payment. |
| `SesionJuegoTracker` | In-memory session streaks (global, extremo, imposible) and categorias ganadas. |
| `SesionManager` | Current player + load from DB. Resets `SesionJuegoTracker` on login/logout. |
| `UtilsJuego` | Difficulty constants (multipliers 0.8-3.0, base times 20s-4s), prize formulas. |

### DAOs (`com.titomonito.dao`)
| DAO | Responsibility |
|---|---|
| `GeneralDAO` | Loads singleton list of categories. |
| `JuegoDAO` | Selects a non-discovered word for current player. |
| `JugadorDAO` | CRUD jugadores, descubrimientos, rankings, progreso por categoria. |
| `LogrosDAO` | Logros historicos: registrar, verificar, queries agregadas. |

### Models (`com.titomonito.models`)
- `Categorias`, `Jugador`, `Palabra`, `SnapshotPartida` (inmutable, Builder for game-end state).

## Game Systems

### Difficulty (`UtilsJuego`)
| Nivel | Multiplier | Base time |
|---|---|---|
| 1 (Facil) | 0.8x | 20s |
| 2 (Normal) | 1.0x | 15s |
| 3 (Dificil) | 1.6x | 10s |
| 4 (Extremo) | 2.2x | 7s |
| 5 (Imposible) | 3.0x | 4s |

### Economy
- Coins per letter: $2. Base prize: $10 x multiplier. Victory bonus: $10 x mult. Lives bonus: equals remaining lives.
- Banco inicial: coins at game start. Premio potencial = base + letters + bonus. Premio asegurado = letters x %progress.
- 50 coins welcome bonus on creating a player.

### Power-ups (`Constantes.UTIL_*`)
| Util | Precio | Effect |
|---|---|---|
| Sacapuntas | $20 | +10s al reloj |
| Tijeras | $35 | +1 vida (one time per game) |
| Goma | $30 | Disables 4 incorrect letters |
| Pluma | $50 | Reveals most frequent remaining letter |
| Marcatextos | $65 | Shows hint (disabled in Imposible) |

### Logros - Phase 8
- **30 logros totales** grouped in 2 tabs:
  - **Medallas (16 categorias x 4 niveles)**: bloqueado, bronce (25%), plata (50%), oro (100%).
  - **Hazanas (30 logros)**: 12 historicos `DB_*` + 18 en tiempo real `RT_*`.
- **Premios**: $20 (Contra el Reloj) to $3,000 (Biblioteca Viviente).
- **Evaluation**:
  - Historicos: when opening Logros tab (`LogrosPanel.refrescar()` -> `LogrosService.evaluarLogrosHistoricos`).
  - En tiempo real: on game liquidation (`LogicaJuego.liquidarPartida()` -> `SnapshotPartida` -> `LogrosService.evaluarLogrosEnPartida`).
- `SesionJuegoTracker` resets on `SesionManager.cerrarSesion()` and `iniciarSesion()`.

## Persistence
- **DB:** SQLite at `~/Documents/Tito el Monito Ahorcado/tito_db.db`.
- **Migrations:** `ConfigDB.initDB()` via `PRAGMA user_version`. Current version: 3.
- **Tables:** `categorias`, `palabras`, `jugadores`, `descubrimientos`, `logros (id INTEGER PK AUTOINCREMENT, id_jugador, id_logro)` — PK simple que permite repetición de logros.
- **Logs:** `~/Documents/Tito el Monito Ahorcado/logs/error.log` (FileHandler append).

## Product Vision & Roadmap
- **Concept:** "Tito el Monito v2.0" - Hangman reimagined as interactive GUI with paper-sketch visual identity and "Lapiz" (pencil) economy.
- **Version:** v0.9.0-BETA.
- **Completed phases:**
  1. GUI + navigation + main menu (v0.1.0)
  2. Game engine core (categories, words, letters, lives)
  3. Difficulty + economy + time
  4. SQLite persistence + migrations
  5. Login + player creation
  6. Statistics + rankings
  7. Power-ups + time management
  8. Achievements + medals
  9. Review & final adjustments (current)
- **Pending (placeholders):** `OpcionesPanel`.
- **Future:** time-attack modes, remote leaderboards, MVP v1.0.
