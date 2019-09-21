CREATE TABLE pessoas (
id bigint(20) primary key AUTO_INCREMENT,
nome varchar(100) not null,
ativo boolean null,
logradouro varchar(100) null,
numero varchar(5) null,
complemento varchar(100) null,
bairro varchar(100) null,
cep varchar(20) null,
cidade varchar(100) null,
estado varchar(50) null

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert into pessoas (nome, ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) values('ivonildo', true,"tv congo","42","sem info","antonio bezerra","60360450","fortaleza","ce");
insert into pessoas (nome, ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) values('kassia', true,"tv congo","42","sem info","antonio bezerra","60360450","fortaleza","ce");
insert into pessoas (nome, ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) values('henry', true,"tv congo","42","sem info","antonio bezerra","60360450","fortaleza","ce");
insert into pessoas (nome, ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) values('neide', true,"tv congo","42","sem info","antonio bezerra","60360450","fortaleza","ce");