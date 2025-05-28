-- Criação do banco de dados e seleção
CREATE DATABASE IF NOT EXISTS actiosdb;
USE actiosdb;

-- Tabelas do 1º código (NÃO MODIFICADAS)
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

-- Tabelas adicionais do 2º código (ajustadas para evitar conflitos)

-- Tabela Curso (não existe no primeiro código)
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
-- Evitamos conflito criando uma extensão da tabela original
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

-- Palestrantes vinculados diretamente a um evento (versão alternativa)
-- Esta tabela já está representada em evento_palestrante, então será omitida para evitar redundância

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

-- Preenchemento das tabelas
-- Faculdades
INSERT INTO faculdades (nome, localizacao, site) VALUES
('Universidade Federal de Pernambuco', 'Recife, PE', NULL),
('Universidade de Pernambuco - Campus Petrolina', 'Petrolina, PE', NULL),
('Instituto Federal de Educação, Ciência e Tecnologia de Pernambuco - Campus Recife', 'Recife, PE', NULL),
('Universidade Católica de Pernambuco', 'Recife, PE', NULL),
('Universidade Federal Rural de Pernambuco', 'Recife, PE', NULL),
('Universidade Católica de Olinda', 'Olinda, PE', NULL),
('Faculdade Boa Viagem', 'Recife, PE', NULL),
('Centro Universitário Senac', 'Recife, PE', NULL),
('Faculdade Metropolitana de Campina Grande', 'Campina Grande, PB', NULL),
('Instituto Brasileiro de Gestão e Tecnologia da Informação - IBGTI', 'Recife, PE', NULL),
('Escola Superior de Marketing - ESPM Recife', 'Recife, PE', NULL),
('Faculdade de Ciências Humanas de Olinda - FACHO', 'Olinda, PE', NULL),
('Universidade Salgado de Oliveira - Universo Recife', 'Recife, PE', NULL),
('Faculdade Maurício de Nassau - Recife', 'Recife, PE', NULL),
('Centro Universitário Estácio do Recife', 'Recife, PE', NULL);

-- Cursos
INSERT INTO cursos (nome, area_academica) VALUES
('Engenharia de Software', 'Tecnologia'),
('Medicina', 'Saúde'),
('Direito', 'Ciências Sociais Aplicadas'),
('Arquitetura e Urbanismo', 'Artes e Humanidades'),
('Administração de Empresas', 'Ciências Sociais Aplicadas'),
('Sistemas de Informação', 'Tecnologia'),
('Enfermagem', 'Saúde'),
('Psicologia', 'Ciências Humanas'),
('Design Gráfico', 'Artes e Humanidades'),
('Ciências Contábeis', 'Ciências Sociais Aplicadas'),
('Engenharia Civil', 'Engenharia'),
('Nutrição', 'Saúde'),
('Jornalismo', 'Comunicação'),
('Ciência Política', 'Ciências Humanas'),
('Marketing', 'Ciências Sociais Aplicadas');

-- Categorias
INSERT INTO categorias (nome) VALUES
('Tecnologia da Informação'),
('Saúde e Bem-estar'),
('Desenvolvimento Pessoal'),
('Empreendedorismo'),
('Artes e Cultura'),
('Ciências Exatas'),
('Ciências Humanas'),
('Línguas Estrangeiras'),
('Gastronomia'),
('Meio Ambiente'),
('Finanças Pessoais'),
('Desenvolvimento Web'),
('Fotografia'),
('Música'),
('Inteligência Artificial');

-- Usuários
INSERT INTO usuarios (nome, email, senha, tipo) VALUES
('Ana Paula Silva', 'ana.paula.silva@estudante.ufpe.br', 'senha123', 'ALUNO'),
('Pedro Henrique Oliveira', 'pedro.henrique@estudante.upe.br', 'senha456', 'ALUNO'),
('Mariana Costa', 'mariana.costa@organizacao.com.br', 'organiza789', 'FACULDADE'),
('Ricardo Gomes', 'ricardo.gomes@admin.ifpe.edu.br', 'admin1011', 'FACULDADE'),
('Juliana Fernandes', 'juliana.fernandes@estudante.unicap.br', 'senha1213', 'ALUNO'),
('Fábio Nunes', 'fabio.nunes@organiza.com.br', 'evento4321', 'FACULDADE'),
('Carla Rodrigues', 'carla.rodrigues@estudante.ufrpe.br', 'seguranca987', 'ALUNO'),
('Lucas Martins', 'lucas.martins@admin.unicap.br', 'master5678', 'FACULDADE'),
('Sofia Pereira', 'sofia.pereira@estudante.ifpe.edu.br', 'aprendizado10', 'ALUNO'),
('Mateus Souza', 'mateus.souza@organiza.com.br', 'inovacao2025', 'FACULDADE'),
('Laura Almeida', 'laura.almeida@estudante.fbv.edu.br', 'novasenha1', 'ALUNO'),
('Gustavo Pereira', 'gustavo.pereira@organizaeventos.com', 'gestao2024', 'FACULDADE'),
('Isabela Santos', 'isabela.santos@estudante.senacpe.br', 'seguranca22', 'ALUNO'),
('Renato Costa', 'renato.costa@admin.espmrecife.br', 'admin12345', 'FACULDADE'),
('Beatriz Oliveira', 'beatriz.oliveira@estudante.facho.com.br', 'leitura33', 'ALUNO');

-- Vínculo de curso com usuários
INSERT INTO vinculo_curso_usuario (id_usuario, id_curso) VALUES
(1, 1), (2, 2), (5, 3), (7, 4), (9, 5),
(1, 6), (2, 7), (5, 8), (7, 9), (9, 10),
(11, 11), (13, 12), (1, 13), (5, 14), (7, 15);

-- Eventos
INSERT INTO eventos (
    titulo, descricao, data, horario, local, categoria_id, faculdade_id
) VALUES
('Imersão em Python para Ciência de Dados', 'Workshop intensivo sobre Python e suas aplicações em ciência de dados.', '2025-07-15', NULL, NULL, 1, 1),
('Congresso Pernambucano de Cardiologia', 'Evento com palestras de especialistas e apresentação de pesquisas na área de cardiologia.', '2025-08-20', NULL, NULL, 2, 2),
('Mesa Redonda: Os Desafios do Novo Código Civil', 'Debate sobre as principais mudanças e impactos do novo código civil.', '2025-09-10', NULL, NULL, 3, 4),
('Oficina de Desenho Urbano', 'Atividade prática para explorar técnicas de desenho e representação do espaço urbano.', '2025-10-05', NULL, NULL, 5, 3),
('Seminário de Gestão Ágil de Projetos', 'Apresentação de metodologias ágeis e ferramentas para gerenciamento de projetos.', '2025-11-12', NULL, NULL, 4, 5),
('Bootcamp de JavaScript Moderno', 'Imersão completa em JavaScript ES6+ e frameworks.', '2025-12-01', NULL, NULL, 1, 2),
('Jornada de Atualização em Enfermagem', 'Palestras e mesas redondas sobre as últimas práticas em enfermagem.', '2026-01-15', NULL, NULL, 2, 4),
('Workshop de Criação de Conteúdo para Redes Sociais', 'Aprenda a criar conteúdo engajador para diversas plataformas.', '2026-02-05', NULL, NULL, 3, 1),
('Encontro de Economia Criativa', 'Discussões e apresentações sobre o mercado da economia criativa em Pernambuco.', '2026-03-10', NULL, NULL, 5, 5),
('Minicurso de Introdução à Inteligência Artificial', 'Conceitos básicos e aplicações práticas de IA.', '2026-04-01', NULL, NULL, 15, 3),
('Semana de Design de Interiores', 'Palestras, workshops e exposições sobre tendências em design de interiores.', '2026-05-05', NULL, NULL, 5, 6),
('Oficina de Fotografia de Rua', 'Prática fotográfica explorando a dinâmica da vida urbana.', '2026-06-15', NULL, NULL, 13, 7),
('Roda de Conversa sobre Saúde Mental no Trabalho', 'Espaço para diálogo sobre bem-estar e saúde mental no ambiente profissional.', '2026-07-20', NULL, NULL, 2, 8),
('Curso de Marketing Digital para Pequenos Negócios', 'Estratégias e ferramentas para impulsionar negócios online.', '2026-08-05', NULL, NULL, 4, 9),
('Sarau Literário e Musical', 'Noite de apresentações de poesia, música e outras formas de expressão artística.', '2026-09-12', NULL, NULL, 5, 10);

-- Inscrições
INSERT INTO inscricoes (usuario_id, evento_id, numero_inscricao) VALUES
(1, 1, 'INSCR-0001'),
(2, 2, 'INSCR-0002'),
(5, 3, 'INSCR-0003'),
(7, 4, 'INSCR-0004'),
(9, 5, 'INSCR-0005'),
(3, 1, 'INSCR-0006'),
(6, 2, 'INSCR-0007'),
(8, 3, 'INSCR-0008'),
(10, 4, 'INSCR-0009'),
(1, 5, 'INSCR-0010'),
(11, 6, 'INSCR-0011'),
(12, 7, 'INSCR-0012'),
(13, 8, 'INSCR-0013'),
(14, 9, 'INSCR-0014'),
(15, 10, 'INSCR-0015');

-- Palestrantes
INSERT INTO palestrantes (nome, email, telefone, biografia) VALUES
('Dra. Maria Clara Albuquerque', 'maria.clara@evento1.com', NULL, 'Especialista em Ciência de Dados e Machine Learning.'),
('Dr. João Silva', 'joao.silva@evento2.com', NULL, 'Cardiologista renomado com vasta experiência clínica e pesquisa.'),
('Dr. Ana Beatriz Melo', 'ana.melo@evento3.com', NULL, 'Advogada e professora de Direito Civil.'),
('Prof. Carlos Eduardo Vieira', 'carlos.vieira@evento4.com', NULL, 'Arquiteto e urbanista com foco em planejamento urbano sustentável.'),
('Eng. Patrícia Oliveira', 'patricia.oliveira@evento5.com', NULL, 'Consultora em gestão de projetos com certificação PMP.'),
('Dr. Lucas Pereira', 'lucas.pereira@evento6.com', NULL, 'Desenvolvedor Full-Stack com foco em React e Node.js.'),
('Enfa. Camila Souza', 'camila.souza@evento7.com', NULL, 'Enfermeira especialista em cuidados intensivos.'),
('Mariana Flores', 'mariana.flores@evento8.com', NULL, 'Especialista em marketing de conteúdo e storytelling.'),
('Prof. Ricardo Almeida', 'ricardo.almeida@evento9.com', NULL, 'Economista e analista do mercado criativo.'),
('MSc. Juliana Castro', 'juliana.castro@evento10.com', NULL, 'Pesquisadora na área de inteligência artificial e aprendizado de máquina.'),
('Arq. André Lima', 'andre.lima@evento11.com', NULL, 'Designer de interiores com experiência em projetos residenciais e comerciais.'),
('João Pedro Silva', 'joao.pedro@evento12.com', NULL, 'Fotógrafo de rua com trabalhos publicados em diversas revistas.'),
('Ana Clara Gomes', 'ana.gomes@evento15.com', NULL, 'Poetisa e cantora com diversas apresentações em eventos culturais.'),
('Dr. Paulo Roberto Santos', 'paulo.roberto@evento11.com', NULL, 'Engenheiro Civil com especialização em estruturas.'),
('Nut. Lívia Maria Oliveira', 'livia.oliveira@evento12.com', NULL, 'Nutricionista clínica com foco em bem-estar e alimentação saudável.'),
('Prof. Carolina Peixoto', 'carolina.peixoto@evento13.com', NULL, 'Jornalista e pesquisadora em comunicação digital.'),
('Dr. Fernando Albuquerque', 'fernando.albuquerque@evento14.com', NULL, 'Cientista Político com experiência em análise de políticas públicas.'),
('Renata Flores', 'renata.flores@evento1.com', NULL, 'Explora a relação entre arte e tecnologia em suas obras.'),
('Carlos Menezes', 'carlos.menezes@evento2.com', NULL, 'Engenheiro de Software com mais de 10 anos de experiência.'),
('Patrícia Lemos', 'patricia.lemos@evento3.com', NULL, 'Advogada especialista em direito digital.'),
('Thiago Oliveira', 'thiago.oliveira@evento4.com', NULL, 'Arquiteto paisagista com projetos urbanos e verdes.'),
('Luiza Costa', 'luiza.costa@evento5.com', NULL, 'Especialista em Marketing Digital e engajamento online.');

-- Relacionamentos evento-palestrante (simplificados por ordem)
INSERT INTO evento_palestrante (evento_id, palestrante_id) VALUES
(1, 1), (1, 18),
(2, 2), (2, 19),
(3, 3), (3, 20),
(4, 4), (4, 21),
(5, 5), (5, 22),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 11), (11, 14),
(12, 12), (12, 15),
(13, 16),
(14, 17),
(15, 13);
-- ORGANIZADOR
INSERT INTO organizadores (nome, email, id_faculdade) VALUES
('Sérgio Mendes', 'sergio.mendes@universo.com.br', 13),
('Renata Alves', 'renata.alves@mauriciodenassau.br', 14),
('Thiago Cavalcanti', 'thiago.cavalcanti@estacio.br', 15),
('Amanda Ferreira', 'amanda.ferreira@ucolinda.com.br', 6),
('Bruno Rocha', 'bruno.rocha@fbv.edu.br', 7),
('Isabela Correia', 'isabela.correia@senacpe.com.br', 8),
('Pedro Henrique Silva', 'pedro.hsilva@ibgti.com.br', 9),
('Mariana Rabelo', 'mariana.rabelo@espmrecife.br', 10),
('João Victor Lima', 'joao.vlima@facho.com.br', 11),
('Ana Beatriz Souza', 'ana.bsouza@ufrpe.br', 12);

-- NOTIFICACAO
INSERT INTO notificacoes (id_usuario, id_evento, data_envio, mensagem) VALUES
(11, 11, NOW() - INTERVAL 2 DAY, 'Informações importantes sobre o evento de Design de Interiores.'),
(12, 12, NOW(), 'Compartilhe suas fotos da Oficina de Fotografia!'),
(13, 13, NOW() - INTERVAL 3 DAY, 'Artigos e dicas sobre Saúde Mental no Trabalho.'),
(14, 14, NOW(), 'Últimas vagas para o Curso de Marketing Digital!'),
(15, 15, NOW() - INTERVAL 1 WEEK, 'Reviva os melhores momentos do Sarau Literário e Musical.'),
(1, 6, NOW(), 'Novidades sobre o Bootcamp de JavaScript!'),
(2, 7, NOW() - INTERVAL 4 DAY, 'Agradecemos sua participação na Jornada de Enfermagem.'),
(5, 8, NOW(), 'Dicas extras para turbinar seu conteúdo online.'),
(7, 9, NOW() - INTERVAL 2 DAY, 'Resultados e destaques do Encontro de Economia Criativa.'),
(9, 10, NOW(), 'Aprofunde seus conhecimentos em IA com materiais complementares.'),
(3, 11, NOW() - INTERVAL 1 DAY, 'Lembrete sobre a Semana de Design de Interiores.'),
(4, 12, NOW(), 'Não perca a oportunidade de aprender fotografia de rua!'),
(6, 13, NOW() - INTERVAL 5 DAY, 'Prepare-se para a discussão sobre saúde mental.'),
(8, 14, NOW(), 'Inscreva-se agora no curso de marketing digital!'),
(10, 15, NOW() - INTERVAL 3 DAY, 'Um convite especial para o Sarau Literário.');

-- REGISTROCERTIFICADO
INSERT INTO registro_certificados (id_usuario, id_curso, data_emissao, codigo_validacao) VALUES
(3, 6, CURDATE(), 'CERT-U1V2W3X4'),
(4, 7, CURDATE(), 'CERT-Y5Z6A1B2'),
(6, 8, CURDATE(), 'CERT-C3D4E5F6'),
(8, 9, CURDATE(), 'CERT-G7H8I9J0'),
(10, 10, CURDATE(), 'CERT-K1L2M3N4'),
(11, 11, CURDATE(), 'CERT-O5P6Q7R8'),
(12, 12, CURDATE(), 'CERT-S9T0U1V2'),
(13, 13, CURDATE(), 'CERT-W3X4Y5Z6'),
(14, 14, CURDATE(), 'CERT-A1B2C3D4'),
(15, 15, CURDATE(), 'CERT-E5F6G7H8');

-- FEEEDBACK
INSERT INTO feedback_eventos (id_usuario, id_evento, nota, comentario) VALUES
(1, 1, 5, 'Excelente evento! Aprendi muito sobre Python.'),
(2, 2, 4, 'Muito bom, mas poderia ter mais tempo para perguntas.'),
(5, 3, 5, 'Ótimo debate, esclarecedor.'),
(7, 4, 3, 'A oficina foi boa, mas faltou material impresso.'),
(9, 5, 4, 'Gostei bastante, bem organizado.'),
(11, 6, 5, 'Conteúdo atualizado e relevante.'),
(13, 7, 4, 'Bom evento, mas o som estava ruim.'),
(1, 8, 5, 'Amei! Muito útil para minha área.'),
(5, 9, 4, 'Interessante, embora um pouco técnico demais.'),
(7, 10, 5, 'IA é o futuro! Evento incrível.');










-- Stored Procedure: EmitirCertificado
DELIMITER //

CREATE PROCEDURE EmitirCertificado(
  IN p_id_usuario INT,
  IN p_id_curso INT,
  IN p_codigo_validacao VARCHAR(50)
)
BEGIN
  INSERT INTO registrocertificado (id_usuario, id_curso, data_emissao, codigo_validacao)
  VALUES (p_id_usuario, p_id_curso, NOW(), p_codigo_validacao);
END;
//

-- Stored Procedure: FazerCheckin
ALTER TABLE inscricoes ADD COLUMN checkin BOOLEAN DEFAULT FALSE;
CREATE PROCEDURE FazerCheckin(
  IN p_id_usuario INT,
  IN p_id_evento INT
)
BEGIN
  UPDATE inscricoes
  SET checkin = TRUE
  WHERE usuario_id = p_id_usuario AND evento_id = p_id_evento;
END;
//

-- Stored Procedure: EventosPorCategoria
CREATE PROCEDURE EventosPorCategoria(
  IN p_id_categoria INT
)
BEGIN
  SELECT nome, data_inicio, formato
  FROM evento
  WHERE categoria_id = p_id_categoria;
END;
//

DELIMITER ;

-- Inserir usuário
INSERT INTO usuario (nome, email, senha, tipo_usuario)
VALUES ('João da Silva', 'joao@email.com', 'senha123', 'estudante');

-- Inserir uma categoria (necessária para o evento)
INSERT INTO categoria (nome) VALUES ('Tecnologia');

-- Inserir uma faculdade
INSERT INTO faculdade (nome, cidade, estado)
VALUES ('Faculdade XYZ', 'São Paulo', 'SP');

-- Inserir um evento
INSERT INTO evento (nome, descricao, data_inicio, data_fim, formato, certificado, valor, faculdade_id, categoria_id)
VALUES (
  'Semana Acadêmica', 'Palestras e minicursos', '2025-06-01', '2025-06-05',
  'presencial', true, 0.00, 1, 1
);

-- Verificar os dados inseridos
SELECT * FROM usuario;
SELECT * FROM evento;

-- Emitir um certificado
CALL EmitirCertificado(1, 1, 'CERTIF-ABC123');

-- Verificar se o certificado foi emitido
SELECT * FROM registrocertificado;
