insert into perfil_acesso (id_perfil, nome, descricao) values (1, 'ADM', 'Administrador');
insert into usuario (id_usuario, inativo, login, senha, dt_senha, dt_ultimo_acesso) values (1, false, 'login', 'senha', TO_DATE ('31/12/2009', 'DD/MM/YYYY'), TO_DATE ('31/12/2009', 'DD/MM/YYYY'));
insert into usuario_perfil (id_usuario_perfil, id_usuario, id_perfil_acesso, nome_usuario, nome_perfil) values (1, 1, 1, 'login', 'ADM');
INSERT INTO usuario(id_usuario, inativo, dt_senha, dt_ultimo_acesso, email, login, perfis, senha) VALUES (1), false, TO_DATE ('31/12/2009', 'DD/MM/YYYY'), TO_DATE ('31/12/2009', 'DD/MM/YYYY'), 'gustavos.cadastros@gmail.com', 'gustavo', 'USU-ADM,WEB,USU', '202cb962ac59075b964b07152d234b70');
