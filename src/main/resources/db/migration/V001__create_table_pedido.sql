CREATE TABLE Pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_controle VARCHAR(255) NOT NULL UNIQUE,
    nome_produto VARCHAR(255) NOT NULL,
    valor_unitario DOUBLE NOT NULL,
    quantidade INT NOT NULL,
    valor_total DOUBLE NOT NULL,
    codigo_cliente BIGINT NOT NULL,
    data_cadastro DATE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

