# Tito el Monito Ahorcado - Agent Instructions

## Tech Stack & Tooling
- **Language/Runtime:** Java 21 (`pom.xml`)
- **Build System:** Maven (`pom.xml`)
- **GUI Framework:** Java Swing with FlatLaf (`com.formdev.flatlaf:flatlaf`, theme: `FlatMTGitHubIJTheme`)

## Build & Run Commands
- Compile: `mvn clean compile`
- Package: `mvn package`
- Run Application: `mvn exec:java -Dexec.mainClass="com.titomonito.Main"`

## Architecture & Project Structure
- **Pattern:** Model-View-Controller (MVC) in Swing.
- **Entrypoint:** `com.titomonito.Main` -> initializes theme (`FlatMTGitHubIJTheme`), loads configuration, sets up `VentanaBase` and `ControlVentana`.
- **Navigation:** CardLayout-based panel switching (`VentanaBase`, `PanelMenu`, and views in `com.titomonito.ui.vistas`).
- **Resources:** Fonts (`IndieFlower-Regular.ttf`, Tahoma) and UI assets (`src/main/resources/ui/`).

## Product Vision & Roadmap Context
- **Concept:** Evolution of classic Hangman into an interactive GUI game ("Tito el Monito v2.0") with a paper sketch visual identity and pencil ("Lápiz") economy.
- **Status:** Phase 1 (Graphical interface, navigation, main menu, card layout) completed (v0.1.0). Future phases include core gameplay logic, time-attack modes, player login, persistence, power-ups store, and leaderboards up to MVP v1.0.
