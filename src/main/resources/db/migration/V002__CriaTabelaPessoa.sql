create table pessoa(

	id SERIAL PRIMARY KEY,
	nome varchar(80) NOT NULL,
	ativo BOOLEAN NOT NULL,
	lagradouro varchar(80),
	numero varchar(20),
	complemento varchar(80),
	bairro varchar(80),
	cep varchar(80),
	cidade varchar(30),
	estado varchar(30)


);