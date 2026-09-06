-- Migración a versión 3: permitir repetición de logros
CREATE TABLE IF NOT EXISTS logros_nueva (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_jugador INTEGER NOT NULL,
    id_logro TEXT NOT NULL,
    FOREIGN KEY(id_jugador) REFERENCES jugadores(id_jugador) ON DELETE CASCADE
);

INSERT OR IGNORE INTO logros_nueva (id_jugador, id_logro)
SELECT id_jugador, id_logro FROM logros;

DROP TABLE logros;
ALTER TABLE logros_nueva RENAME TO logros;
CREATE INDEX IF NOT EXISTS idx_logros_jugador_logro ON logros(id_jugador, id_logro);
