CREATE TABLE IF NOT EXISTS categorias (
id_categoria INTEGER PRIMARY KEY AUTOINCREMENT,
nombre TEXT NOT NULL UNIQUE
);

-- Tabla de palabras
CREATE TABLE IF NOT EXISTS palabras (
id_palabra INTEGER PRIMARY KEY AUTOINCREMENT,
id_categoria INTEGER NOT NULL,
palabra TEXT NOT NULL,
pista TEXT NOT NULL,
FOREIGN KEY(id_categoria) REFERENCES categorias(id_categoria)  ON DELETE CASCADE,
UNIQUE(id_categoria, palabra)
);

-- Tabla de los jugadores
CREATE TABLE IF NOT EXISTS jugadores (
id_jugador INTEGER PRIMARY KEY AUTOINCREMENT,
nombre TEXT NOT NULL,
monedas_actuales INTEGER NOT NULL DEFAULT 0,
monedas_maximas INTEGER NOT NULL DEFAULT 0,
racha_actual INTEGER NOT NULL DEFAULT 0,
racha_maxima INTEGER NOT NULL DEFAULT 0
);

-- Tabla de progreso con las palabras descubiertas por jugador
CREATE TABLE IF NOT EXISTS descubrimientos (
id INTEGER PRIMARY KEY AUTOINCREMENT,
id_jugador INTEGER NOT NULL,
id_palabra INTEGER NOT NULL,
FOREIGN KEY(id_jugador) REFERENCES jugadores(id_jugador) ON DELETE CASCADE,
FOREIGN KEY(id_palabra) REFERENCES palabras(id_palabra) ON DELETE CASCADE
);

-- Tabla ligera para eventos de una sola vez
CREATE TABLE IF NOT EXISTS logros (
id_jugador INTEGER ,
id_logro TEXT,
PRIMARY KEY(id_jugador, id_logro),
FOREIGN KEY(id_jugador) REFERENCES jugadores(id_jugador)
);