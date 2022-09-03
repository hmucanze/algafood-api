
SET FOREIGN_KEY_CHECKS=0;

DELETE FROM cidade;
DELETE FROM cozinha;
DELETE FROM estado;
DELETE FROM forma_pagamento;
DELETE FROM grupo;
DELETE FROM grupo_permissao;
DELETE FROM permissao;
DELETE FROM produto;
DELETE FROM restaurante;
DELETE FROM restaurante_forma_pagamento;
DELETE FROM usuario;
DELETE FROM usuario_grupo;
DELETE FROM restaurante_usuario_responsavel;
DELETE FROM pedido;
DELETE FROM item_pedido;

SET FOREIGN_KEY_CHECKS=1;

ALTER TABLE cidade AUTO_INCREMENT = 1;
ALTER TABLE cozinha AUTO_INCREMENT = 1;
ALTER TABLE estado AUTO_INCREMENT = 1;
ALTER TABLE forma_pagamento AUTO_INCREMENT = 1;
ALTER TABLE grupo AUTO_INCREMENT = 1;
ALTER TABLE permissao AUTO_INCREMENT = 1;
ALTER TABLE produto AUTO_INCREMENT = 1;
ALTER TABLE restaurante AUTO_INCREMENT = 1;
ALTER TABLE usuario AUTO_INCREMENT = 1;
ALTER TABLE pedido AUTO_INCREMENT = 1;
ALTER TABLE item_pedido AUTO_INCREMENT = 1;

INSERT INTO cozinha(nome) VALUE("Tailandesa");
INSERT INTO cozinha(nome) VALUE("Indiana");
INSERT INTO cozinha(nome) VALUE("Argentina");
INSERT INTO cozinha(nome) VALUE("Brasileira");

INSERT INTO estado(nome) VALUES("Minas Gerais"), ("São Paulo"), ("Pernambuco");

INSERT INTO cidade(nome, estado_id) VALUES("Uberlândia", 1);
INSERT INTO cidade(nome, estado_id) VALUES("Belo Horizonte", 1);
INSERT INTO cidade(nome, estado_id) VALUES("São Paulo", 2);
INSERT INTO cidade(nome, estado_id) VALUES("Campinas", 2);
INSERT INTO cidade(nome, estado_id) VALUES("Fortaleza", 3);

INSERT INTO restaurante(nome, taxa_frete, activo, aberto, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro, data_cadastro, data_actualizacao) VALUES("Thai Gourmet", 10, true, true, 1, 1, "38400-999", "Rua João Pinheiro", "1000", "Centro", utc_timestamp, utc_timestamp);
INSERT INTO restaurante(nome, taxa_frete, activo, aberto, cozinha_id, data_cadastro, data_actualizacao) VALUES("Thai Delivery", 9.50, true, false, 1, utc_timestamp, utc_timestamp);
INSERT INTO restaurante(nome, taxa_frete, activo, aberto, cozinha_id, data_cadastro, data_actualizacao) VALUES("Tuk Tuk Comida Indiana", 15, true, false, 2, utc_timestamp, utc_timestamp);
INSERT INTO restaurante(nome, taxa_frete, activo, aberto, cozinha_id, data_cadastro, data_actualizacao) VALUES("Java Steakhouse", 12, false, false, 3, utc_timestamp, utc_timestamp);
INSERT INTO restaurante(nome, taxa_frete, activo, aberto, cozinha_id, data_cadastro, data_actualizacao) VALUES("Lanchonete do Tio Sam", 11, false, true, 4, utc_timestamp, utc_timestamp);
INSERT INTO restaurante(nome, taxa_frete, activo, aberto, cozinha_id, data_cadastro, data_actualizacao) VALUES("Bar da Maria", 6, true, true, 4, utc_timestamp, utc_timestamp);

INSERT INTO permissao(nome, descricao) VALUES("CONSULTAR_COZINHAS", "Permite consultar cozinhas");
INSERT INTO permissao(nome, descricao) VALUES("EDITAR COZINHAS", "Permite editar cozinhas");

INSERT INTO forma_pagamento(descricao) VALUES("Cartão de crédito");
INSERT INTO forma_pagamento(descricao) VALUES("Cartão de débito");
INSERT INTO forma_pagamento(descricao) VALUES("Dinheiro");

INSERT INTO restaurante_forma_pagamento(restaurante_id, forma_pagamento_id) VALUES(1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

INSERT INTO produto(nome, descricao, preco, activo, restaurante_id) VALUES("Porco com molho agridoce", "Deliciosa carne suína ao molho especial", 78.90, 1, 1);
INSERT INTO produto(nome, descricao, preco, activo, restaurante_id) VALUES("Camarão tailandês", "16 camarões grandes ao molho picante", 110, 1, 1);
INSERT INTO produto(nome, descricao, preco, activo, restaurante_id) VALUES("Salada picante com carne grelhada", "Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha", 87.20, 1, 2);
INSERT INTO produto(nome, descricao, preco, activo, restaurante_id) VALUES("Garlic Naan", "Pão tradicional indiano com cobertura de alho", 21, 1, 3);
INSERT INTO produto(nome, descricao, preco, activo, restaurante_id) VALUES("Murg Curry", "Cubos de frango preparados com molho curry e especiarias", 43, 1, 3);
INSERT INTO produto(nome, descricao, preco, activo, restaurante_id) VALUES("Bife Ancho", "Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé", 79, 1, 4);
INSERT INTO produto(nome, descricao, preco, activo, restaurante_id) VALUES("T-Bone", "Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon", 89, 1, 4);
INSERT INTO produto(nome, descricao, preco, activo, restaurante_id) VALUES("Sanduíche X-Tudo", "Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese", 19, 1, 5);
INSERT INTO produto(nome, descricao, preco, activo, restaurante_id) VALUES("Espetinho de Cupim", "Acompanha farinha, mandioca e vinagrete", 8, 1, 6);

INSERT INTO grupo (nome) VALUES ('Gerente'), ('Vendedor'), ('Secretária'), ('Cadastrador');

INSERT INTO usuario (id, nome, email, senha, data_cadastro) VALUES
(1, 'João da Silva', 'joao.ger@algafood.com', '123', utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@algafood.com', '123', utc_timestamp),
(3, 'José Souza', 'jose.sec@algafood.com', '123', utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@algafood.com', '123', utc_timestamp),
(5, 'Manoel Lima', 'manoel.loja@algafood.com', '123', utc_timestamp);

INSERT INTO grupo_permissao(grupo_id, permissao_id) VALUES(1, 1), (1, 2), (2, 1), (3, 1), (4, 1);

INSERT INTO usuario_grupo(usuario_id, grupo_id) VALUES(1, 1), (2, 2), (3, 3), (4, 4);

INSERT INTO restaurante_usuario_responsavel(restaurante_id, usuario_id) VALUES(1, 5), (3, 5);

INSERT INTO pedido (id, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
    status, data_criacao, subtotal, taxa_frete, valor_total)
VALUES (1, 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
'CRIADO', utc_timestamp, 298.90, 10, 308.90);

INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
VALUES (1, 1, 1, 1, 78.9, 78.9, null);

INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
VALUES (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');


INSERT INTO pedido (id, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, 
        endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
        status, data_criacao, subtotal, taxa_frete, valor_total)
VALUES (2, 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
'CRIADO', utc_timestamp, 79, 0, 79);

INSERT INTO item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
VALUES (3, 2, 6, 1, 79, 79, 'Ao ponto');