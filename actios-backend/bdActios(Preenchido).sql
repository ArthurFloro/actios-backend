-- Cria o banco de dados, caso não exista
CREATE DATABASE IF NOT EXISTS sistema_atendimentos;

-- Seleciona o banco de dados
USE sistema_atendimentos;

-- Cria a tabela de clientes
-- Tabela: Usuario
CREATE TABLE IF NOT EXISTS Usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    senha VARCHAR(255),
    tipo_usuario ENUM('estudante', 'organizador', 'admin')
);

-- Cria a tabela de Faculdade
CREATE TABLE IF NOT EXISTS Faculdade (
    id_faculdade INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(100)
);

-- Cria a tabela de Curso
CREATE TABLE IF NOT EXISTS Curso (
    id_curso INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) not null,
    area_academica VARCHAR(100)
);

-- Cria a tabela de VinculoCursoUsuario
CREATE TABLE IF NOT EXISTS VinculoCursoUsuario (
id_usuario int,
id_curso int,
primary key(id_usuario, id_curso),
foreign key(id_usuario) references usuario(id_usuario),
foreign key(id_curso) references curso(id_curso)
);

-- Cria a tabela de Categoria
CREATE TABLE IF NOT EXISTS Categoria (
    id_categoria INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100)
);

-- Cria a tabela de Evento
CREATE TABLE IF NOT EXISTS Evento (
    id_evento INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    descricao TEXT,
    data_inicio DATE,
    data_fim DATE,
    formato ENUM('online', 'presencial', 'hibrido'),
    certificado BOOLEAN,
    valor DECIMAL(10,2),
    id_faculdade INT,
    id_categoria INT,
    FOREIGN KEY (id_faculdade) REFERENCES Faculdade(id_faculdade),
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria)
);

-- Cria a tabela de Participacao
CREATE TABLE IF NOT EXISTS Participacao (
    id_participacao INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    id_evento INT,
    checkin BOOLEAN,
    feedback TEXT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_evento) REFERENCES Evento(id_evento)
);

-- Cria a tabela de Palestrante
CREATE TABLE IF NOT EXISTS Palestrante (
    id_palestrante INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    mini_bio TEXT,
    id_evento INT,
    FOREIGN KEY (id_evento) REFERENCES Evento(id_evento)
);

-- Cria a tabela de Organizador
CREATE TABLE IF NOT EXISTS Organizador (
    id_organizador INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    id_faculdade INT,
    FOREIGN KEY (id_faculdade) REFERENCES Faculdade(id_faculdade)
);

-- Cria a tabela de Notificacao
CREATE TABLE IF NOT EXISTS Notificacao (
    id_notificacao INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    id_evento INT,
    data_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    mensagem TEXT,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_evento) REFERENCES Evento(id_evento)
);

-- Cria a tabela de RegistroCertificado
CREATE TABLE IF NOT EXISTS RegistroCertificado (
id_certificado int primary key auto_increment,
id_usuario int,
id_curso int,
data_emissao DATE,
codigo_validacao VARCHAR(100) UNIQUE,
FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
FOREIGN KEY (id_curso) REFERENCES Curso(id_curso)
);

-- Cria a tabela de FeedbackEvento
CREATE TABLE IF NOT EXISTS FeedbackEvento (
    id_feedback INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT,
    id_evento INT,
    nota INT CHECK (nota BETWEEN 1 AND 5),
    comentario TEXT,
    data_feedback DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (id_evento) REFERENCES Evento(id_evento)
);

-- Inserção de dados nas tabelas
-- Faculdades (15)
INSERT INTO Faculdade (nome, cidade, estado) VALUES
('Universidade Federal de Pernambuco', 'Recife', 'PE'),
('Universidade de Pernambuco - Campus Petrolina', 'Petrolina', 'PE'),
('Instituto Federal de Educação, Ciência e Tecnologia de Pernambuco - Campus Recife', 'Recife', 'PE'),
('Universidade Católica de Pernambuco', 'Recife', 'PE'),
('Universidade Federal Rural de Pernambuco', 'Recife', 'PE'),
('Universidade Católica de Olinda', 'Olinda', 'PE'),
('Faculdade Boa Viagem', 'Recife', 'PE'),
('Centro Universitário Senac', 'Recife', 'PE'),
('Faculdade Metropolitana de Campina Grande', 'Campina Grande', 'PB'),
('Instituto Brasileiro de Gestão e Tecnologia da Informação - IBGTI', 'Recife', 'PE'),
('Escola Superior de Marketing - ESPM Recife', 'Recife', 'PE'),
('Faculdade de Ciências Humanas de Olinda - FACHO', 'Olinda', 'PE'),
('Universidade Salgado de Oliveira - Universo Recife', 'Recife', 'PE'),
('Faculdade Maurício de Nassau - Recife', 'Recife', 'PE'),
('Centro Universitário Estácio do Recife', 'Recife', 'PE');

-- Cursos (15)
INSERT INTO Curso (nome, area_academica) VALUES
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

-- Categorias (15)
INSERT INTO Categoria (nome) VALUES
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

-- Usuários (15 Personas)
INSERT INTO Usuario (nome, email, senha, tipo_usuario) VALUES
('Ana Paula Silva', 'ana.paula.silva@estudante.ufpe.br', 'senha123', 'estudante'),
('Pedro Henrique Oliveira', 'pedro.henrique@estudante.upe.br', 'senha456', 'estudante'),
('Mariana Costa', 'mariana.costa@organizacao.com.br', 'organiza789', 'organizador'),
('Ricardo Gomes', 'ricardo.gomes@admin.ifpe.edu.br', 'admin1011', 'admin'),
('Juliana Fernandes', 'juliana.fernandes@estudante.unicap.br', 'senha1213', 'estudante'),
('Fábio Nunes', 'fabio.nunes@organiza.com.br', 'evento4321', 'organizador'),
('Carla Rodrigues', 'carla.rodrigues@estudante.ufrpe.br', 'seguranca987', 'estudante'),
('Lucas Martins', 'lucas.martins@admin.unicap.br', 'master5678', 'admin'),
('Sofia Pereira', 'sofia.pereira@estudante.ifpe.edu.br', 'aprendizado10', 'estudante'),
('Mateus Souza', 'mateus.souza@organiza.com.br', 'inovacao2025', 'organizador'),
('Laura Almeida', 'laura.almeida@estudante.fbv.edu.br', 'novasenha1', 'estudante'),
('Gustavo Pereira', 'gustavo.pereira@organizaeventos.com', 'gestao2024', 'organizador'),
('Isabela Santos', 'isabela.santos@estudante.senacpe.br', 'seguranca22', 'estudante'),
('Renato Costa', 'renato.costa@admin.espmrecife.br', 'admin12345', 'admin'),
('Beatriz Oliveira', 'beatriz.oliveira@estudante.facho.com.br', 'leitura33', 'estudante');

-- Vínculo de curso com usuários (15)
INSERT INTO VinculoCursoUsuario (id_usuario, id_curso) VALUES
(1, 1), (2, 2), (5, 3), (7, 4), (9, 5),
(1, 6), (2, 7), (5, 8), (7, 9), (9, 10),
(11, 11), (13, 12), (1, 13), (5, 14), (7, 15);

-- Evento (15)
INSERT INTO Evento (
    nome, descricao, data_inicio, data_fim, formato, certificado, valor, id_faculdade, id_categoria
) VALUES
('Imersão em Python para Ciência de Dados', 'Workshop intensivo sobre Python e suas aplicações em ciência de dados.', '2025-07-15', '2025-07-17', 'online', true, 49.90, 1, 1),
('Congresso Pernambucano de Cardiologia', 'Evento com palestras de especialistas e apresentação de pesquisas na área de cardiologia.', '2025-08-20', '2025-08-22', 'presencial', true, 120.00, 2, 2),
('Mesa Redonda: Os Desafios do Novo Código Civil', 'Debate sobre as principais mudanças e impactos do novo código civil.', '2025-09-10', '2025-09-10', 'online', false, 0.00, 4, 3),
('Oficina de Desenho Urbano', 'Atividade prática para explorar técnicas de desenho e representação do espaço urbano.', '2025-10-05', '2025-10-06', 'presencial', true, 35.00, 3, 5),
('Seminário de Gestão Ágil de Projetos', 'Apresentação de metodologias ágeis e ferramentas para gerenciamento de projetos.', '2025-11-12', '2025-11-13', 'hibrido', true, 75.00, 5, 4),
('Bootcamp de JavaScript Moderno', 'Imersão completa em JavaScript ES6+ e frameworks.', '2025-12-01', '2025-12-05', 'online', true, 79.90, 2, 1),
('Jornada de Atualização em Enfermagem', 'Palestras e mesas redondas sobre as últimas práticas em enfermagem.', '2026-01-15', '2026-01-17', 'presencial', true, 95.00, 4, 2),
('Workshop de Criação de Conteúdo para Redes Sociais', 'Aprenda a criar conteúdo engajador para diversas plataformas.', '2026-02-05', '2026-02-06', 'hibrido', true, 55.00, 1, 3),
('Encontro de Economia Criativa', 'Discussões e apresentações sobre o mercado da economia criativa em Pernambuco.', '2026-03-10', '2026-03-12', 'presencial', false, 25.00, 5, 5),
('Minicurso de Introdução à Inteligência Artificial', 'Conceitos básicos e aplicações práticas de IA.', '2026-04-01', '2026-04-03', 'online', true, 39.90, 3, 15),
('Semana de Design de Interiores', 'Palestras, workshops e exposições sobre tendências em design de interiores.', '2026-05-05', '2026-05-07', 'presencial', true, 65.00, 6, 5),
('Oficina de Fotografia de Rua', 'Prática fotográfica explorando a dinâmica da vida urbana.', '2026-06-15', '2026-06-16', 'presencial', false, 45.00, 7, 13),
('Roda de Conversa sobre Saúde Mental no Trabalho', 'Espaço para diálogo sobre bem-estar e saúde mental no ambiente profissional.', '2026-07-20', '2026-07-20', 'online', false, 0.00, 8, 2),
('Curso de Marketing Digital para Pequenos Negócios', 'Estratégias e ferramentas para impulsionar negócios online.', '2026-08-05', '2026-08-07', 'hibrido', true, 89.00, 9, 4),
('Sarau Literário e Musical', 'Noite de apresentações de poesia, música e outras formas de expressão artística.', '2026-09-12', '2026-09-12', 'presencial', false, 15.00, 10, 5);

-- Participação (15)
INSERT INTO Participacao (id_usuario, id_evento, checkin, feedback) VALUES
(1, 1, true, 'Excelente conteúdo e palestrantes!'),
(2, 2, true, 'Muito relevante para minha área de estudo.'),
(5, 3, true, 'Ótima discussão sobre o tema.'),
(7, 4, true, 'Aprendi muito com a oficina prática.'),
(9, 5, false, 'Gostaria de ter participado presencialmente.'),
(3, 1, true, ''),
(6, 2, true, 'Organização impecável.'),
(8, 3, false, ''),
(10, 4, true, 'Recomendo a todos os interessados.'),
(1, 5, true, 'As ferramentas apresentadas foram muito úteis.'),
(11, 6, true, 'Muito prático e aplicável.'),
(12, 7, true, 'As discussões foram enriquecedoras.'),
(13, 8, true, 'Adorei a dinâmica do workshop.'),
(14, 9, false, 'Infelizmente não pude comparecer.'),
(15, 10, true, 'Uma noite cultural muito agradável.');

-- Palestrante (14)
INSERT INTO Palestrante (nome, mini_bio, id_evento) VALUES
('Dra. Maria Clara Albuquerque', 'Especialista em Ciência de Dados e Machine Learning.', 1),
('Dr. João Silva', 'Cardiologista renomado com vasta experiência clínica e pesquisa.', 2),
('Dr. Ana Beatriz Melo', 'Advogada e professora de Direito Civil.', 3),
('Prof. Carlos Eduardo Vieira', 'Arquiteto e urbanista com foco em planejamento urbano sustentável.', 4),
('Eng. Patrícia Oliveira', 'Consultora em gestão de projetos com certificação PMP.', 5),
('Dr. Lucas Pereira', 'Desenvolvedor Full-Stack com foco em React e Node.js.', 6),
('Enfa. Camila Souza', 'Enfermeira especialista em cuidados intensivos.', 7),
('Mariana Flores', 'Especialista em marketing de conteúdo e storytelling.', 8),
('Prof. Ricardo Almeida', 'Economista e analista do mercado criativo.', 9),
('MSc. Juliana Castro', 'Pesquisadora na área de inteligência artificial e aprendizado de máquina.', 10),
('Arq. André Lima', 'Designer de interiores com experiência em projetos residenciais e comerciais.', 11),
('João Pedro Silva', 'Fotógrafo de rua com trabalhos publicados em diversas revistas.', 12);

-- Palestrante (Continuando e completando para 15)
INSERT INTO Palestrante (nome, mini_bio, id_evento) VALUES
('Ana Clara Gomes', 'Poetisa e cantora com diversas apresentações em eventos culturais.', 15),
('Dr. Paulo Roberto Santos', 'Engenheiro Civil com especialização em estruturas.', 11),
('Nut. Lívia Maria Oliveira', 'Nutricionista clínica com foco em bem-estar e alimentação saudável.', 12),
('Prof. Carolina Peixoto', 'Jornalista e pesquisadora em comunicação digital.', 13),
('Dr. Fernando Albuquerque', 'Cientista Político com experiência em análise de políticas públicas.', 14),
('Mestre em Artes Visuais, Renata Flores', 'Explora a relação entre arte e tecnologia em suas obras.', 1),
('Engenheiro de Software Sênior, Carlos Menezes', 'Atua na área de desenvolvimento de sistemas há mais de 10 anos.', 2),
('Advogada especialista em direito digital, Patrícia Lemos', 'Assessora empresas em questões de privacidade e proteção de dados.', 3),
('Arquiteto paisagista, Thiago Oliveira', 'Desenvolve projetos de espaços urbanos e áreas verdes.', 4),
('Especialista em Marketing Digital, Luiza Costa', 'Focada em estratégias de engajamento e conversão online.', 5);

-- Organizador (Completando para 15)
INSERT INTO Organizador (nome, email, id_faculdade) VALUES
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

-- Notificação (Completando para 15)
INSERT INTO Notificacao (id_usuario, id_evento, data_envio, mensagem) VALUES
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

-- Certificado (Mais 10)
INSERT INTO RegistroCertificado (id_usuario, id_curso, data_emissao, codigo_validacao) VALUES
(3, 6, CURDATE(), 'CERT-U1V2W3X4'),
(4, 7, CURDATE(), 'CERT-Y5Z6A1B2'),
(6, 8, CURDATE(), 'CERT-C3D4E5F6'),
(8, 9, CURDATE(), 'CERT-G7H8I9J0'),
(10, 10, CURDATE(), 'CERT-K1L2M3N4'),
(11, 11, CURDATE(), 'CERT-O5P6Q7R8'),
(12, 12, CURDATE(), 'CERT-S9T0U1V2'),
(13, 13, CURDATE(), 'CERT-W3X4Y5Z6'),
(14, 14, CURDATE(), 'CERT-A1B2C3D44'), 
(15, 15, CURDATE(), 'CERT-E5F6G7H88'); 

-- Feedback (Mais 10)
INSERT INTO FeedbackEvento (id_usuario, id_evento, nota, comentario, data_feedback) VALUES
(3, 6, 5, 'Conteúdo muito relevante e bem apresentado.', NOW()),
(4, 7, 4, 'Gostei da interação com os palestrantes.', NOW()),
(6, 8, 5, 'O workshop superou minhas expectativas.', NOW()),
(8, 9, 3, 'Achei a organização um pouco confusa.', NOW()),
(10, 10, 5, 'Uma experiência cultural incrível.', NOW()),
(11, 11, 4, 'As palestras foram interessantes.', NOW()),
(12, 12, 5, 'Recomendo a todos os enfermeiros.', NOW()),
(13, 13, 4, 'Boas dicas para criar conteúdo.', NOW()),
(14, 14, 3, 'Esperava mais profundidade no debate.', NOW()),
(15, 15, 5, 'Noite muito agradável e inspiradora.', NOW());


-- Stored Procedure: EmitirCertificado
DELIMITER //

CREATE PROCEDURE EmitirCertificado(
  IN p_id_usuario INT,
  IN p_id_evento INT,
  IN p_codigo_validacao VARCHAR(50)
)
BEGIN
  INSERT INTO RegistroCertificado (id_usuario, id_evento, data_emissao, codigo_validacao)
  VALUES (p_id_usuario, p_id_evento, NOW(), p_codigo_validacao);
END;
//

-- Stored Procedure: FazerCheckin
CREATE PROCEDURE FazerCheckin(
  IN p_id_usuario INT,
  IN p_id_evento INT
)
BEGIN
  UPDATE Participacao
  SET checkin = TRUE
  WHERE id_usuario = p_id_usuario AND id_evento = p_id_evento;
END;
//

-- Stored Procedure: EventosPorCategoria
CREATE PROCEDURE EventosPorCategoria(
  IN p_id_categoria INT
)
BEGIN
  SELECT nome, data_inicio, formato
  FROM Evento
  WHERE id_categoria = p_id_categoria;
END;
//

DELIMITER ;

 -- teste para ver se os dados podem ser inseridos 
INSERT INTO Usuario (nome, email, senha, tipo_usuario)
VALUES ('João da Silva', 'joao@email.com', 'senha123', 'estudante');

-- Inserir uma categoria (necessária para o evento)
INSERT INTO Categoria (nome) VALUES ('Tecnologia');

-- Inserir uma faculdade (necessária para o evento)
INSERT INTO Faculdade (nome, cidade, estado)
VALUES ('Faculdade XYZ', 'São Paulo', 'SP');

-- Inserir um evento
INSERT INTO Evento (nome, descricao, data_inicio, data_fim, formato, certificado, valor, id_faculdade, id_categoria)
VALUES (
  'Semana Acadêmica', 'Palestras e minicursos', '2025-06-01', '2025-06-05',
  'presencial', true, 0.00, 1, 1
);

-- Verificar os ids inseridos 
SELECT * FROM Usuario;
SELECT * FROM Evento;

-- chamando a procedure com call

CALL EmitirCertificado(1, 1, 'CERTIF-ABC123');

-- verificar se o certificado foi emitido

SELECT * FROM RegistroCertificado;
