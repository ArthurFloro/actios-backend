CREATE DATABASE actiosdb;
USE actiosdb;

CREATE TABLE faculdades (
    id_faculdade INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    localizacao VARCHAR(255),
    site VARCHAR(255)
);

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    curso VARCHAR(100),
    faculdade_id INT,
    tipo ENUM('ALUNO', 'FACULDADE') DEFAULT 'ALUNO',
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (faculdade_id) REFERENCES faculdades(id_faculdade)
);

CREATE TABLE palestrantes (
    id_palestrante INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    biografia TEXT
);

CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE eventos (
    id_evento INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    data DATE NOT NULL,
    horario VARCHAR(50),
    local VARCHAR(255),
    categoria_id INT,
    faculdade_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id_categoria),
    FOREIGN KEY (faculdade_id) REFERENCES faculdades(id_faculdade)
);

CREATE TABLE evento_palestrante (
    evento_id INT,
    palestrante_id INT,
    PRIMARY KEY (evento_id, palestrante_id),
    FOREIGN KEY (evento_id) REFERENCES eventos(id_evento),
    FOREIGN KEY (palestrante_id) REFERENCES palestrantes(id_palestrante)
);

CREATE TABLE inscricoes (
    id_inscricao INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    evento_id INT,
    numero_inscricao VARCHAR(50) UNIQUE NOT NULL,
    data_inscricao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (evento_id) REFERENCES eventos(id_evento)
);


