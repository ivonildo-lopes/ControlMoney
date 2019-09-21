CREATE TABLE categorias (
id bigint(20) primary key AUTO_INCREMENT,
nome varchar(100) not null 

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into categorias (nome) values('Lazer');
insert into categorias (nome) values('Alimentação');
insert into categorias (nome) values('Supermercado');
insert into categorias (nome) values('Farmacia');
insert into categorias (nome) values('outros');
