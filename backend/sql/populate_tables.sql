INSERT INTO docente (id_docente, id_lattes, nome, data_atualizacao)
VALUES(1, 1, 'Felipe Rogerio Teles' , '2023-04-28');

INSERT INTO producao (id_producao, tipo, issn_ou_sigla, nome_local, titulo, ano, qualis , percentile_ou_h5, qtd_grad, qtd_mestrado)
VALUES(1, 'Contrary to popular belief', 'CPB', 'São Luis ', 'Lorem Ipsum', 2023, 'Letraset sheets',7, 3, 1);

-- foreign key
INSERT into orientacao (id_orientacao, id_docente, tipo, titulo, ano, modaliadde, instituicao , curso)
VALUES(1, 1, ' Cicero are also', 'Como fazer um algoritimo do chatgpt usando o chatgpt', 2023, 'Iniciacao Científica', 'Universidade Federal do Maranhão', 'Ciência da Computacação');


INSERT INTO tecnica (id_tecnica, tipo, titulo, ano, financiadora, qtd_gra, qtd_mestrado)
VALUES(1, 'Softwares',' Titulo para uma tecnica muito boa', 2023, 'Universidade Federal do Maranhao', 4  ,2 );

insert into programa (id_programa, nome)
VALUES(1, ' Programa 1');

insert into producao_orientacao(id_orientacao, id_producao)
VALUES(1, 1);

insert into tecnica_orientacao(id_tecnica, id_orientacao)
values(1, 1);

insert into docente_tecnica(id_docente, id_tecnica)
VALUES(1, 1);

INSERT INTO docente_producao (id_docente ,id_producao)
VALUES(1 ,1);

insert into programa_docente(id_programa, id_docente)
VALUES(1, 1);
