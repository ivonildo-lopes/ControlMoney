CREATE TABLE lancamentos (
id bigint(20) primary key AUTO_INCREMENT,
descricao varchar(100) not null,
data_vencimento date not null,
data_pagamento date null,
valor Decimal(10,2) not null,
observacao varchar(100) null,
tipo varchar(50) not null,
id_categoria BIGINT(20) null,
id_pessoa BIGINT(20) null,
FOREIGN key (id_categoria) REFERENCES categorias(id),
FOREIGN key (id_pessoa) REFERENCES pessoas(id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert into lancamentos(descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa) VALUES ('Salario Mensal','2019-08-20',null,6500.00,'Participação de lucros','RECEITA',1,1);
insert into lancamentos(descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa) VALUES ('BAHAMAS','2019-09-02','2019-09-05',177.30,'Participação de lucros','DESPESA',2,2);
insert into lancamentos(descricao,data_vencimento,data_pagamento,valor,observacao,tipo,id_categoria,id_pessoa) VALUES ('TOP CLUBE','2019-08-22',null,120.30,null,'RECEITA',3,1);