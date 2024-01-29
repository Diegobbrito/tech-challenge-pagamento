CREATE DATABASE IF NOT EXISTS lanchonete;
USE lanchonete;

CREATE TABLE produtos (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(200) NOT NULL,
    descricao VARCHAR(200) NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    imagem_url VARCHAR(200),
    categoria_id INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE categorias (
    id INT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(200) NOT NULL,
    descricao VARCHAR(200),
    PRIMARY KEY (id)
);