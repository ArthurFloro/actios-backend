# 🎓 Actios Backend

[![Java](https://img.shields.io/badge/Java-17+-orange)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

> Sistema backend para gestão de eventos acadêmicos, desenvolvido com arquitetura em camadas, segurança com JWT e boas práticas de desenvolvimento com Spring Boot.

---

## 📌 Índice
- [📐 Arquitetura](#-arquitetura)
- [🛠 Tecnologias](#-tecnologias)
- [⚙️ Pré-requisitos](#-pré-requisitos)
- [🚀 Execução](#-execução)
- [📡 Endpoints](#-endpoints)
- [📁 Estrutura de Pastas](#-estrutura-de-pastas)
- [🎨 Diagramas](#-diagramas)
- [📝 Licença](#-licença)

---

## 📐 Arquitetura

O projeto segue a arquitetura tradicional de sistemas em camadas:

| Camada            | Função                                                                  | Exemplo                |
|-------------------|-------------------------------------------------------------------------|------------------------|
| **Controllers**   | Recebem requisições HTTP e delegam as ações para o serviço               | `UsuarioController`    |
| **Services**      | Implementam regras de negócio e lógica principal do sistema              | `CursoService`         |
| **Repositories**  | Camada de acesso ao banco de dados utilizando Spring Data JPA            | `InscricaoRepository`  |
| **Models**        | Entidades de domínio com mapeamento JPA                                 | `Usuario.java`         |
| **DTOs**          | Transferência segura de dados entre camadas                              | `UsuarioDTO`           |
| **Exceptions**    | Tratamento centralizado de erros                                        | `CustomException`      |

---

## 🛠 Tecnologias

| Categoria       | Tecnologias/Frameworks           |
|-----------------|----------------------------------|
| **Linguagem**   | Java 17+                         |
| **Framework**   | Spring Boot 3.x                  |
| **Persistência**| Spring Data JPA, Hibernate, MySQL|
| **Segurança**   | Spring Security, JWT             |
| **Ferramentas** | Maven, Lombok, MapStruct         |

---

## ⚙️ Pré-requisitos

- Java 17 (OpenJDK ou OracleJDK)
- MySQL 8.0+ (ou Docker)
- Maven 3.9+
- IDE recomendada: IntelliJ, Eclipse ou VS Code com extensão Java

---

## 🚀 Execução

### 1️⃣ Clonar o Projeto
```bash
git clone https://github.com/ArthurFloro/actios-backend


### 2️⃣ Configuração do Banco de Dados

CREATE DATABASE actios_db;
CREATE USER 'actios_user'@'localhost' IDENTIFIED BY 'senha_segura';
GRANT ALL PRIVILEGES ON actios_db.* TO 'actios_user'@'localhost';

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

CREATE TABLE IF NOT EXISTS cursos (
    id_curso INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    area_academica VARCHAR(100)
);

-- Relacionamento entre cursos e usuários (via IDs)
CREATE TABLE IF NOT EXISTS vinculo_curso_usuario (
    id_usuario INT,
    id_curso INT,
    PRIMARY KEY (id_usuario, id_curso),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_curso) REFERENCES cursos(id_curso)
);

-- Eventos do tipo estendido (valores, formato, datas adicionais)

CREATE TABLE IF NOT EXISTS eventos_detalhes (
    id_evento INT PRIMARY KEY,
    data_fim DATE,
    formato ENUM('online', 'presencial', 'hibrido'),
    certificado BOOLEAN,
    valor DECIMAL(10,2),
    FOREIGN KEY (id_evento) REFERENCES eventos(id_evento)
);

-- Participação extra (check-in e feedback específico)
CREATE TABLE IF NOT EXISTS participacoes (
    id_participacao INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_evento INT,
    checkin BOOLEAN,
    feedback TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_evento) REFERENCES eventos(id_evento)
);

-- Organizadores (administradores vinculados à faculdade)
CREATE TABLE IF NOT EXISTS organizadores (
    id_organizador INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    id_faculdade INT,
    FOREIGN KEY (id_faculdade) REFERENCES faculdades(id_faculdade)
);

-- Notificações aos usuários sobre eventos
CREATE TABLE IF NOT EXISTS notificacoes (
    id_notificacao INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    id_evento INT,
    data_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    mensagem TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_evento) REFERENCES eventos(id_evento)
);

-- Certificados emitidos para usuários
CREATE TABLE IF NOT EXISTS registro_certificados (
    id_certificado INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    id_curso INT,
    data_emissao DATE,
    codigo_validacao VARCHAR(100) UNIQUE,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_curso) REFERENCES cursos(id_curso)
);

-- Feedback detalhado sobre eventos
CREATE TABLE IF NOT EXISTS feedback_eventos (
    id_feedback INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    id_evento INT,
    nota INT CHECK (nota BETWEEN 1 AND 5),
    comentario TEXT,
    data_feedback DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_evento) REFERENCES eventos(id_evento)
);

ALTER TABLE eventos ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT TRUE;

### 3️⃣ Configuração do Aplicativo (application.yml)

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/actios_db
    username: actios_user
    password: senha_segura

### 4️⃣ Build e Execução

mvn clean install
mvn spring-boot:run


📁 Estrutura de Pastas
graphql
Copiar
Editar
📦 actios-backend
 ┣ 📂 config         # Configurações globais e segurança
 ┣ 📂 controller     # Camada de API REST (Controllers)
 ┣ 📂 service        # Regras de negócio (Services)
 ┣ 📂 repository     # Persistência (Repositories)
 ┣ 📂 model          # Entidades JPA (Models)
 ┣ 📂 dto            # Data Transfer Objects (DTOs)
 ┣ 📂 exception      # Tratamento de exceções
 ┣ 📂 security       # Autenticação e filtros de segurança
 ┣ 📂 docs           # Documentação e diagramas
 ┗ 📂 resources
     ┣ 📜 application.yml  # Configurações do Spring
     ┗ 📜 schema.sql        # Scripts SQL opcionais


📡 Endpoints
🔐 Autenticação
Método	Endpoint	Descrição
POST	/api/auth/login	Autenticação e JWT

👤 Usuários
Método	Endpoint	Permissão	Descrição
GET	/api/usuarios	ADMIN	Lista todos os usuários
POST	/api/usuarios	ADMIN	Cadastra novo usuário
PUT	/api/usuarios/{id}	ADMIN	Atualiza um usuário
DELETE	/api/usuarios/{id}	ADMIN	Remove um usuário

🎓 Cursos
Método	Endpoint	Permissão	Descrição
GET	/api/cursos	PÚBLICO	Lista todos os cursos
POST	/api/cursos	ADMIN	Cria novo curso
PUT	/api/cursos/{id}	ADMIN	Atualiza curso
DELETE	/api/cursos/{id}	ADMIN	Remove curso

📝 Inscrições
Método	Endpoint	Permissão	Descrição
GET	/api/inscricoes	USER	Lista inscrições do usuário
POST	/api/inscricoes	USER	Realiza inscrição em curso
DELETE	/api/inscricoes/{id}	USER	Cancela inscrição

## 🎨 Diagramas

📘 Diagrama de Arquitetura do Backend:

![Diagrama Back-End Actios](docs/diagrama-backend-actios.png)

📬 Contato
Desenvolvido pela equipe Actios:

Dev back-end: 
Airon Valentim - https://github.com/aironvalentim
Davi Francisco - https://github.com/davifrancisco

Dev Front-end: 
Arthur Floro - https://github.com/ArthurFloro
Vinnicius Mariano - https://github.com/vinniciusmariano

Desenvolvimento e Analise da estrutura de dados:
Allyson George - https://github.com/allysongeorge
Gustavo Carvalho - https://github.com/gustvcarvalho
Willami Durand - https://github.com/willamidurand
GitHub
