
CREATE TABLE forma_pagamento (
	id bigint not null auto_increment,
    descricao varchar(60),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;
    
CREATE TABLE grupo (
	id bigint not null auto_increment,
    nome varchar(60),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;
    
CREATE TABLE permissao (
	id bigint not null auto_increment,
    descricao varchar(255),
    nome varchar(60),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;
    
CREATE TABLE grupo_permissao (
	grupo_id bigint not null,
    permissao_id bigint not null
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;
    
CREATE TABLE produto (
	id bigint not null auto_increment,
    activo bit,
    descricao varchar(255),
    nome varchar(60),
    preco decimal(19,2),
    restaurante_id bigint,
    primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE restaurante (
	id bigint not null auto_increment,
    aberto bit,
    activo bit,
    data_actualizacao datetime not null,
    data_cadastro datetime not null,
    endereco_bairro varchar(60),
    endereco_cep varchar(60),
    endereco_complemento varchar(60),
    endereco_logradouro varchar(60),
    endereco_numero varchar(60),
    nome varchar(60),
    taxa_frete decimal(19,2),
    cozinha_id bigint not null,
    endereco_cidade_id bigint,
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE restaurante_forma_pagamento (
	restaurante_id bigint not null,
    forma_pagamento_id bigint not null
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

CREATE TABLE usuario (
	id bigint not null auto_increment,
    data_cadastro datetime not null,
    email varchar(100),
    nome varchar(60),
    senha varchar(255),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;
    
CREATE TABLE usuario_grupo (
	usuario_id bigint not null,
    grupo_id bigint not null
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4;

ALTER TABLE grupo_permissao ADD CONSTRAINT fk_grupo_permissao_permissao
FOREIGN KEY (permissao_id) REFERENCES permissao (id);

ALTER TABLE grupo_permissao ADD CONSTRAINT fk_grupo_permissao_grupo
FOREIGN KEY (grupo_id) REFERENCES grupo (id);

ALTER TABLE produto ADD CONSTRAINT fk_produto_restaurante
FOREIGN KEY (restaurante_id) REFERENCES restaurante (id);

ALTER TABLE restaurante ADD CONSTRAINT fk_restaurante_cozinha
FOREIGN KEY (cozinha_id) REFERENCES cozinha (id);

ALTER TABLE restaurante ADD CONSTRAINT fk_restaurante_cidade 
foreign key (endereco_cidade_id) references cidade (id);

ALTER TABLE restaurante_forma_pagamento ADD CONSTRAINT fk_rest_forma_pagto_forma_pagto
FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento (id);

ALTER TABLE restaurante_forma_pagamento ADD CONSTRAINT FKa30vowfejemkw7whjvr8pryvj
FOREIGN KEY (restaurante_id) REFERENCES restaurante (id);

ALTER TABLE usuario_grupo ADD CONSTRAINT fk_usuario_grupo_grupo
FOREIGN KEY (grupo_id) REFERENCES grupo (id);

ALTER TABLE usuario_grupo ADD CONSTRAINT fk_usuario_grupo_usuario
FOREIGN KEY (usuario_id) REFERENCES usuario (id);