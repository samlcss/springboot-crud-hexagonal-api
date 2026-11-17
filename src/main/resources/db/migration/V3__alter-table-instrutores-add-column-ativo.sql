ALTER TABLE instrutores
ADD COLUMN ativo TINYINT(1) NOT NULL DEFAULT 1;

UPDATE instrutores
SET ativo = 1;