CREATE TABLE instrutores(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    cnh VARCHAR(11) NOT NULL UNIQUE,
    especialidade VARCHAR(100) NOT NULL,
    logradouro VARCHAR(100) NOT NULL,
    numero VARCHAR(20),
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf CHAR(2) NOT NULL,
    cep VARCHAR(9) NOT NULL,

    CONSTRAINT pk_instrutores PRIMARY KEY(id),
    CONSTRAINT uk_instrutores_email UNIQUE(email),
    CONSTRAINT uk_instrutores_cnh UNIQUE(cnh)
);