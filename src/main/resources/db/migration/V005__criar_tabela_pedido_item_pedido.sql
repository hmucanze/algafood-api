
CREATE TABLE pedido(
	id BIGINT NOT NULL AUTO_INCREMENT,
    subtotal DECIMAL(10,2) NOT NULL,
    taxa_frete DECIMAL(10, 2) NOT NULL,
	valor_total DECIMAL(10, 2) NOT NULL,
    data_criacao DATETIME NOT NULL,
    data_confirmacao DATETIME,
    data_cancelamento DATETIME,
    data_entrega DATETIME,
    status VARCHAR(10) NOT NULL,
    
    endereco_cep VARCHAR(9) NOT NULL,
    endereco_logradouro VARCHAR(100) NOT NULL,
    endereco_numero VARCHAR(20) NOT NULL,
    endereco_complemento VARCHAR(60),
    endereco_bairro VARCHAR(60) NOT NULL,
    endereco_cidade_id BIGINT NOT NULL,
    
    forma_pagamento_id BIGINT NOT NULL,
    restaurante_id BIGINT NOT NULL,
    usuario_cliente_id BIGINT NOT NULL,
    
    PRIMARY KEY(id),
    
    CONSTRAINT fk_pedido_endereco_cidade FOREIGN KEY(endereco_cidade_id) REFERENCES cidade(id),
    CONSTRAINT fk_pedido_forma_pagamento FOREIGN KEY(forma_pagamento_id) REFERENCES forma_pagamento(id),
    CONSTRAINT fk_pedido_restaurante FOREIGN KEY(restaurante_id) REFERENCES restaurante(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE item_pedido(
	id BIGINT NOT NULL AUTO_INCREMENT,
    quantidade SMALLINT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    preco_total DECIMAL(10,2) NOT NULL,
    observacao VARCHAR(255),
    
    produto_id BIGINT NOT NULL,
    pedido_id BIGINT NOT NULL,
    
    PRIMARY KEY(id),
    UNIQUE KEY uk_item_pedido_produto(pedido_id, produto_id),
    
    CONSTRAINT fk_item_pedido_produto FOREIGN KEY (produto_id) REFERENCES produto(id),
    CONSTRAINT fk_item_pedido_pedido FOREIGN KEY (pedido_id) REFERENCES pedido(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;