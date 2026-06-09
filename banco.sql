-- =====================================================
--  SISTEMA DE ESTOQUE - CONCESSIONÁRIA
--  Script de criação do banco de dados
-- =====================================================

CREATE DATABASE IF NOT EXISTS estoque_carros
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE estoque_carros;

-- -----------------------------------------------------
-- Tabela: carro
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS carro (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    modelo     VARCHAR(60)  NOT NULL,
    marca      VARCHAR(60)  NOT NULL,
    ano        INT          NOT NULL,
    preco      DOUBLE       NOT NULL,
    quantidade INT          NOT NULL DEFAULT 0
);

-- -----------------------------------------------------
-- Tabela: cliente
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS cliente (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    cpf      VARCHAR(14)  NOT NULL UNIQUE,
    telefone VARCHAR(20)
);

-- -----------------------------------------------------
-- Tabela: venda
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS venda (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    carro_id   INT    NOT NULL,
    cliente_id INT    NOT NULL,
    quantidade INT    NOT NULL DEFAULT 1,
    valor_total DOUBLE NOT NULL,
    data_venda DATE   NOT NULL,
    FOREIGN KEY (carro_id)   REFERENCES carro(id),
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

-- -----------------------------------------------------
-- Dados de exemplo (opcional)
-- -----------------------------------------------------
INSERT INTO carro (modelo, marca, ano, preco, quantidade) VALUES
    ('Civic',   'Honda',      2022, 130000.00, 5),
    ('Corolla', 'Toyota',     2023, 145000.00, 3),
    ('Onix',    'Chevrolet',  2023,  85000.00, 8),
    ('HB20',    'Hyundai',    2022,  79000.00, 4);

INSERT INTO cliente (nome, cpf, telefone) VALUES
    ('João Silva',    '111.222.333-44', '(31) 99999-1111'),
    ('Maria Souza',   '555.666.777-88', '(31) 98888-2222');
