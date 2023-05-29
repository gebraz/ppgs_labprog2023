create table if not exists docente(
	id_docente int primary key,
	id_lattes text,
	nome text,
	data_atualizacao date
);

create table if not exists producao(
	id_producao int primary key,
	tipo text not null,
	issn_ou_sigla text ,
	nome_local text ,
	titulo text,
	ano int,
	qualis text,
	percentile_ou_h5 numeric,
	qtd_grad int,
	qtd_mestrado int,
	qtd_dourado int
);

create table if not exists docente_producao(
	id_docente int not null,
	id_producao int not null,
	primary key(id_docente, id_producao),
	foreign key(id_docente) references docente (id_docente),
	foreign key(id_producao) references producao (id_producao)
);

create table if not exists tecnica(
	id_tecnica int primary key,
	tipo text,
	titulo text,
	ano int,
	financiadora text,
	qtd_grad int,
	qtd_mestrado int,
	qtd_douturado int
);

create table if not exists docente_tecnica(
	id_docente int not null,
	id_tecnica int not null,
	primary key(id_docente, id_tecnica),
	foreign key(id_docente) references docente (id_docente),
	foreign key(id_tecnica) references tecnica (id_tecnica)
);

create table if not exists orientacao(
	id_orientacao int primary key,
	id_docente int,
	foreign key(id_docente) references docente(id_docente),
	discente text,
	tipo text,
	titulo text,
	ano int,
	modalidade text,
	instituicao text,
	curso text,
	status text
);

create table if not exists programa(
	id_programa serial primary key,
	nome text
);


create table if not exists producao_orientacao(
	id_producao int not null,
	id_orientacao int not null,
	primary key(id_producao, id_orientacao),
	foreign key(id_producao) references producao(id_producao),
	foreign key(id_orientacao) references orientacao(id_orientacao)
);

create table if not exists tecnica_orientacao(
	id_tecnica int not null,
	id_orientacao int not null,
	primary key(id_tecnica, id_orientacao),
	foreign key(id_tecnica) references tecnica(id_tecnica),
	foreign key(id_orientacao) references orientacao(id_orientacao)
);

create table if not exists programa_docente(
	id_programa int not null,
	id_docente int not null,
	primary key(id_programa, id_docente),
	foreign key(id_programa) references programa(id_programa),
	foreign key(id_docente) references docente(id_docente)
);


