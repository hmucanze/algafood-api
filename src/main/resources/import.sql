INSERT INTO cozinha(nome) VALUE("Tailandesa");
INSERT INTO cozinha(nome) VALUE("Indiana");
INSERT INTO cozinha(nome) VALUE("Argentina");
INSERT INTO cozinha(nome) VALUE("Brasileira");

INSERT INTO estado(nome) VALUES("Minas Gerais"), ("São Paulo"), ("Ceará");

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