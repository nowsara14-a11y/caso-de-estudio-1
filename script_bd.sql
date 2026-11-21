CREATE DATABASE IF NOT EXISTS registro_personal_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE registro_personal_db;

CREATE TABLE IF NOT EXISTS personal (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(25) NOT NULL,
    email VARCHAR(120) NOT NULL,
    direccion TEXT NOT NULL
);