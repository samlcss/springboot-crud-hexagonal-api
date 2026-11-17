ALTER TABLE instrutores
ADD COLUMN usuario_id BIGINT;

ALTER TABLE instrutores
ADD CONSTRAINT fk_instrutor_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id);