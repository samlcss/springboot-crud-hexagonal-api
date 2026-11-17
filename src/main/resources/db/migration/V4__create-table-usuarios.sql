CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    login VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,

    CONSTRAINT pk_usuarios PRIMARY KEY (id),
    CONSTRAINT uk_usuarios_login UNIQUE (login)
);

INSERT INTO usuarios (id, login, senha)
VALUES (
    1,
    'admin',
    '$2a$10$hVdhaTBxqNOxWOLzF.QF2Oz1lERHaPL5xVwAMU00OEY.OJDg.NKAu'
);