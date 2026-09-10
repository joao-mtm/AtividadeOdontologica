CREATE TABLE pacientes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14),
    telefone VARCHAR(20),
    data_nascimento DATE
);

CREATE TABLE dentistas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cro VARCHAR(20)
);

CREATE TABLE consultas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    paciente_id INT NOT NULL,
    dentista_id INT NOT NULL,
    data DATE NOT NULL,
    horario TIME NOT NULL,
    motivo VARCHAR(200),
    status VARCHAR(30),

    FOREIGN KEY (paciente_id) REFERENCES pacientes(id),
    FOREIGN KEY (dentista_id) REFERENCES dentistas(id)
);

CREATE TABLE reagendamentos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    consulta_id INT NOT NULL,
    data_anterior DATE NOT NULL,
    horario_anterior TIME NOT NULL,
    nova_data DATE NOT NULL,
    novo_horario TIME NOT NULL,
    motivo VARCHAR(200),

    FOREIGN KEY (consulta_id) REFERENCES consultas(id)
);

CREATE TABLE tratamentos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    paciente_id INT NOT NULL,
    dentista_id INT NOT NULL,
    descricao VARCHAR(200),
    valor DECIMAL(10,2),
    data_limite DATE,

    FOREIGN KEY (paciente_id) REFERENCES pacientes(id),
    FOREIGN KEY (dentista_id) REFERENCES dentistas(id)
);

INSERT INTO pacientes (nome, cpf, telefone, data_nascimento)
VALUES
('João da Silva', '123.456.789-00', '(47) 99999-1111', '1995-05-10'),
('Maria Oliveira', '987.654.321-00', '(47) 98888-2222', '1998-08-20'),
('Carlos Santos', '456.789.123-00', '(47) 97777-3333', '1987-03-15'),
('Ana Souza', '321.654.987-00', '(47) 96666-4444', '2001-11-25'),
('Pedro Costa', '789.123.456-00', '(47) 95555-5555', '1992-07-30');

INSERT INTO dentistas (nome, cro)
VALUES
('Dr. Ricardo Almeida', 'CRO-SC 12345'),
('Dra. Fernanda Lima', 'CRO-SC 23456'),
('Dr. Marcelo Pereira', 'CRO-SC 34567'),
('Dra. Juliana Martins', 'CRO-SC 45678'),
('Dr. Gabriel Souza', 'CRO-SC 56789');



