insert into perfil_acesso (id_perfil, nome, descricao) values (1, 'ADM', 'Administrador');
insert into usuario (id_usuario, inativo, login, senha, dt_senha, dt_ultimo_acesso) values (1, 0, 'login', 'senha', TO_DATE ('31/12/2009', 'DD/MM/YYYY'), TO_DATE ('31/12/2009', 'DD/MM/YYYY'));
insert into usuario_perfil (id_usuario_perfil, id_usuario, id_perfil_acesso, nome_usuario, nome_perfil) values (1, 1, 1, 'login', 'ADM');