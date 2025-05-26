-- EVER SANTORO
-- TESTE L2
--//EXERCÍCIO 2 - HORÁRIOS DE AULA

-- Criação do banco de dados
CREATE DATABASE L2;

-- Setando a utilização do banco de dados
USE L2;

-- Criação das tabelas

CREATE TABLE DEPARTMENT (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE TITLE (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE PROFESSOR (
    id INT PRIMARY KEY,
    department_id INT NOT NULL,
    title_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    FOREIGN KEY (department_id) REFERENCES DEPARTMENT(id),
    FOREIGN KEY (title_id) REFERENCES TITLE(id)
);

CREATE TABLE BUILDING (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE ROOM (
    id INT PRIMARY KEY,
    building_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    FOREIGN KEY (building_id) REFERENCES BUILDING(id)
);

CREATE TABLE SUBJECT (
    id INT PRIMARY KEY,
    code VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE SUBJECT_PREREQUISITE (
    id INT PRIMARY KEY,
    subject_id INT NOT NULL,
    prerequisiteid INT NOT NULL,
    FOREIGN KEY (subject_id) REFERENCES SUBJECT(id),
    FOREIGN KEY (prerequisiteid) REFERENCES SUBJECT(id)
);

CREATE TABLE CLASS (
    id INT PRIMARY KEY,
    subject_id INT NOT NULL,
    yenr INT NOT NULL,
    semester INT NOT NULL,
    code VARCHAR(20) NOT NULL,
    professor_id INT NOT NULL,
    FOREIGN KEY (subject_id) REFERENCES SUBJECT(id),
    FOREIGN KEY (professor_id) REFERENCES PROFESSOR(id)
);

CREATE TABLE CLASS_SCHEDULE (
    id INT PRIMARY KEY,
    class_id INT NOT NULL,
    room_id INT NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    FOREIGN KEY (class_id) REFERENCES CLASS(id),
    FOREIGN KEY (room_id) REFERENCES ROOM(id)
);

-- Inserts de exemplo

-- DEPARTAMENTOS
INSERT INTO DEPARTMENT (id, name) VALUES (1, 'Matemática'), (2, 'Português'), (3, 'História'), (4, 'Ciências');

-- TÍTULOS
INSERT INTO TITLE (id, name) VALUES (1, 'Professor Doutor'), (2, 'Professor Mestre'), (3, 'Professor Especialista');

-- PROFESSORES
INSERT INTO PROFESSOR (id, department_id, title_id, name) VALUES
(1, 1, 1, 'Ever Santoro'),
(2, 2, 2, 'Girafales'),
(3, 3, 3, 'Helena'),
(4, 4, 1, 'Cláudia'),
(5, 1, 2, 'Carlos'),
(6, 2, 3, 'Ana');

-- PRÉDIOS
INSERT INTO BUILDING (id, name) VALUES (1, 'Bloco A'), (2, 'Bloco B');

-- SALAS
INSERT INTO ROOM (id, building_id, name) VALUES
(1, 1, 'Sala 101'),
(2, 1, 'Sala 102'),
(3, 2, 'Sala 201'),
(4, 2, 'Sala 202');

-- DISCIPLINAS
INSERT INTO SUBJECT (id, code, name) VALUES
(1, 'MAT101', 'Matemática Básica'),
(2, 'MAT201', 'Álgebra Linear'),
(3, 'POR101', 'Gramática'),
(4, 'HIS101', 'História Geral'),
(5, 'CIE101', 'Ciências Naturais'),
(6, 'MAT301', 'Cálculo I');

-- PRÉ-REQUISITOS
INSERT INTO SUBJECT_PREREQUISITE (id, subject_id, prerequisiteid) VALUES
(1, 2, 1),
(2, 6, 2);

-- TURMAS
INSERT INTO CLASS (id, subject_id, yenr, semester, code, professor_id) VALUES
(1, 1, 2024, 1, 'A', 1),
(2, 2, 2024, 1, 'A', 5),
(3, 3, 2024, 1, 'A', 2),
(4, 4, 2024, 1, 'A', 3),
(5, 5, 2024, 1, 'A', 4),
(6, 6, 2024, 1, 'A', 1),
(7, 1, 2024, 2, 'B', 6),
(8, 3, 2024, 2, 'B', 2);

-- HORÁRIOS DAS TURMAS
INSERT INTO CLASS_SCHEDULE (id, class_id, room_id, day_of_week, start_time, end_time) VALUES
(1, 1, 1, 'Monday', '08:00', '10:00'),
(2, 2, 2, 'Monday', '10:00', '12:00'),
(3, 3, 3, 'Tuesday', '08:00', '10:00'),
(4, 4, 4, 'Tuesday', '10:00', '12:00'),
(5, 5, 1, 'Wednesday', '08:00', '10:00'),
(6, 6, 2, 'Wednesday', '10:00', '12:00'),
(7, 7, 3, 'Thursday', '08:00', '10:00'),
(8, 8, 4, 'Thursday', '10:00', '12:00'),
(9, 1, 1, 'Friday', '14:00', '16:00'),
(10, 2, 2, 'Friday', '16:00', '18:00'),
(11, 3, 3, 'Monday', '14:00', '16:00'),
(12, 4, 4, 'Monday', '16:00', '18:00');



-- 1. Consulta: Quantidade de horas que cada professor tem comprometido em aulas
-- Esta consulta soma o total de horas de aula de cada professor, considerando o horário de início e fim de cada aula na tabela CLASS_SCHEDULE:

SELECT 
    p.id AS professor_id,
    p.name AS professor_name,
    SUM(DATEDIFF(MINUTE, cs.start_time, cs.end_time)) / 60.0 AS total_hours
FROM 
    PROFESSOR p
JOIN 
    CLASS c ON c.professor_id = p.id
JOIN 
    CLASS_SCHEDULE cs ON cs.class_id = c.id
GROUP BY 
    p.id, p.name
ORDER BY 
    total_hours DESC;

-- 1. Consulta SQL – Horários Ocupados
-- Primeiro, para listar os horários ocupados de cada sala, basta consultar a tabela CLASS_SCHEDULE:
SELECT 
    r.id AS room_id,
    r.name AS room_name,
    cs.day_of_week,
    cs.start_time,
    cs.end_time,
    'Ocupado' AS status
FROM 
    ROOM r
JOIN 
    CLASS_SCHEDULE cs ON cs.room_id = r.id
ORDER BY 
    r.id, cs.day_of_week, cs.start_time;

-- 2. Consulta de Horários Livres (SQL + Grade Horária)
-- Para listar horários livres, é necessário definir uma grade horária padrão (por exemplo, das 08:00 às 18:00, em blocos de 2 horas).
-- Como SQL puro não gera linhas para horários livres automaticamente, o ideal é criar uma tabela auxiliar chamada TIME_SLOT com todos os horários possíveis.

-- Criação da tabela de horários:
CREATE TABLE TIME_SLOT (
    id INT PRIMARY KEY,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);

INSERT INTO TIME_SLOT (id, start_time, end_time) VALUES
(1, '08:00', '10:00'),
(2, '10:00', '12:00'),
(3, '14:00', '16:00'),
(4, '16:00', '18:00');

-- Consulta para listar horários livres e ocupados:
SELECT
    r.id AS room_id,
    r.name AS room_name,
    ts.start_time,
    ts.end_time,
    cs.day_of_week,
    CASE 
        WHEN cs.id IS NOT NULL THEN 'Ocupado'
        ELSE 'Livre'
    END AS status
FROM
    ROOM r
CROSS JOIN
    TIME_SLOT ts
CROSS JOIN
    (SELECT DISTINCT day_of_week FROM CLASS_SCHEDULE) d
LEFT JOIN
    CLASS_SCHEDULE cs
    ON cs.room_id = r.id
    AND cs.start_time = ts.start_time
    AND cs.end_time = ts.end_time
    AND cs.day_of_week = d.day_of_week
ORDER BY
    r.id, d.day_of_week, ts.start_time;