-- Esquema MySQL para Dulce Esmeralda (perfil prod).
-- En desarrollo (perfil dev) se usa H2 y Hibernate genera las tablas automaticamente.

CREATE DATABASE IF NOT EXISTS dulceesmeralda
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE dulceesmeralda;

CREATE TABLE IF NOT EXISTS rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    rol    VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    nombre     VARCHAR(100),
    apellidos  VARCHAR(100),
    correo     VARCHAR(150),
    telefono   VARCHAR(30),
    direccion  VARCHAR(255),
    activo     BIT NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS usuario_rol (
    id_usuario INT NOT NULL,
    id_rol     INT NOT NULL,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario),
    FOREIGN KEY (id_rol) REFERENCES rol (id_rol)
);

CREATE TABLE IF NOT EXISTS categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    descripcion  VARCHAR(50) NOT NULL UNIQUE,
    ruta_imagen  VARCHAR(1024),
    activo       BIT NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS producto (
    id_producto  INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(80) NOT NULL,
    descripcion  VARCHAR(500),
    precio       DECIMAL(12,2) NOT NULL,
    ruta_imagen  VARCHAR(1024),
    disponible   BIT NOT NULL DEFAULT 1,
    destacado    BIT NOT NULL DEFAULT 0,
    id_categoria INT,
    FOREIGN KEY (id_categoria) REFERENCES categoria (id_categoria)
);

CREATE TABLE IF NOT EXISTS pedido (
    id_pedido  INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    fecha      DATETIME NOT NULL,
    estado     VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    total      DECIMAL(12,2) NOT NULL DEFAULT 0,
    nota       VARCHAR(300),
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
);

CREATE TABLE IF NOT EXISTS pedido_detalle (
    id_pedido_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido         INT NOT NULL,
    id_producto       INT NOT NULL,
    cantidad          INT NOT NULL DEFAULT 1,
    precio_unitario   DECIMAL(12,2) NOT NULL DEFAULT 0,
    FOREIGN KEY (id_pedido) REFERENCES pedido (id_pedido),
    FOREIGN KEY (id_producto) REFERENCES producto (id_producto)
);

INSERT IGNORE INTO rol (rol) VALUES ('ADMIN'), ('CLIENTE');
