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


