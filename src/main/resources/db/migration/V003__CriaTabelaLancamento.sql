create table lancamento(

	id SERIAL PRIMARY KEY,
	descricao varchar(80) not null,
	data_vencimento Date not null,
	data_pagamento Date not null,
	valor decimal not null,
	observacao varchar(200) not null,
	tipo varchar(20) not null,
	categoria_id int not null,
	pessoa_id int not null,
	foreign key (categoria_id) references categoria (id),
	foreign key (pessoa_id) references pessoa (id)
	
	
);