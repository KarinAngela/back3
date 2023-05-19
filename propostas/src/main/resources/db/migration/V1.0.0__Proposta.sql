CREATE TABLE propostas (
    id BIGINT AUTO_INCREMENT NOT NULL,
    nome VARCHAR(256) NOT NULL,
    email VARCHAR(1024) NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    produtoInteresse VARCHAR(256) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE propostas_SEQ (
    next_val BIGINT
);

INSERT INTO propostas_SEQ values ( 1 );