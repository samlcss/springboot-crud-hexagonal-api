ALTER TABLE instrucoes
ADD COLUMN cancelada TINYINT(1) NOT NULL DEFAULT 0,
ADD COLUMN motivo_cancelamento VARCHAR(100),
ADD COLUMN data_cancelamento DATETIME;