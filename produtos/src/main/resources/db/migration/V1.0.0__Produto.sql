CREATE TABLE produtos (
  id BIGINT AUTO_INCREMENT NOT NULL,
  nome VARCHAR(256) NOT NULL,
  descricao VARCHAR(4096) NOT NULL,
  cultura VARCHAR(256) NOT NULL,
  areaSuportada VARCHAR(16) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE produtos_SEQ (
    next_val BIGINT
);

INSERT INTO produtos_SEQ values ( 1 );