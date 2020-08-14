create table usuario(

    id int PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL


);

CREATE TABLE permissao (

	id int PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL

);

CREATE TABLE usuario_permissao (

	usuario_id int NOT NULL,
	permissao_id int NOT NULL,
	PRIMARY KEY (usuario_id, permissao_id),
	FOREIGN KEY (usuario_id) REFERENCES usuario(id),
	FOREIGN KEY (permissao_id) REFERENCES permissao(id)
);

