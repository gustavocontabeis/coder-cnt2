--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.6
-- Dumped by pg_dump version 9.5.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE contabilidade;
--
-- Name: contabilidade; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE contabilidade WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'pt_BR.UTF-8' LC_CTYPE = 'pt_BR.UTF-8';


\connect contabilidade

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- Name: configuracao; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE configuracao (
    id_configuracao bigint NOT NULL,
    chave character varying(30) NOT NULL,
    valor character varying(1000)
);


--
-- Name: contas; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE contas (
    id_conta bigint NOT NULL,
    conta_origem character varying(255),
    conta_tipo character varying(255),
    descricao character varying(150),
    estrutura character varying(20) NOT NULL,
    nivel integer NOT NULL,
    nome character varying(150) NOT NULL,
    ordem integer,
    id_empresa bigint,
    id_conta_pai bigint,
    id_plano_contas bigint
);


--
-- Name: empresa; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE empresa (
    id_empresa bigint NOT NULL,
    cnpj character varying(18) NOT NULL,
    razao_social character varying(80) NOT NULL,
    id_empresa_matriz bigint
);


--
-- Name: exercicio; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE exercicio (
    id_exercicio bigint NOT NULL,
    ano integer NOT NULL,
    fechado boolean NOT NULL,
    id_empresa bigint NOT NULL,
    id_plano_contas bigint NOT NULL
);


--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: historico_padrao; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE historico_padrao (
    id_historico_padrao bigint NOT NULL,
    historico character varying(600) NOT NULL
);


--
-- Name: lancamento; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE lancamento (
    id_lancamento bigint NOT NULL,
    data date NOT NULL,
    historico character varying(600) NOT NULL,
    lancamentotipo character varying(255),
    valor real NOT NULL,
    credito bigint,
    debito bigint,
    exercicio bigint NOT NULL,
    lancamento_principal bigint
);


--
-- Name: lancamento_padrao; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE lancamento_padrao (
    id_lancamento_padrao bigint NOT NULL,
    nome character varying(60) NOT NULL,
    credito bigint,
    debito bigint,
    id_historico_padrao bigint
);


--
-- Name: perfil_acesso; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE perfil_acesso (
    id_perfil bigint NOT NULL,
    descricao character varying(255) NOT NULL,
    nome character varying(15) NOT NULL
);


--
-- Name: plano_contas; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE plano_contas (
    id_plano_contas bigint NOT NULL,
    nome character varying(150) NOT NULL
);


--
-- Name: seq_configuracao; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE seq_configuracao
    START WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_conta; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE seq_conta
    START WITH 1000
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_empresa; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE seq_empresa
    START WITH 100
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_exercicio; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE seq_exercicio
    START WITH 100
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_historico_padrao; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE seq_historico_padrao
    START WITH 100
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_lancamento; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE seq_lancamento
    START WITH 100
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_lancamento_padrao; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE seq_lancamento_padrao
    START WITH 100
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_plano_contas; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE seq_plano_contas
    START WITH 100
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_usuario; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE seq_usuario
    START WITH 100
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: seq_usuario_perfil; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE seq_usuario_perfil
    START WITH 100
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: usuario; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE usuario (
    id_usuario bigint NOT NULL,
    inativo date,
    dt_senha date NOT NULL,
    dt_ultimo_acesso date,
    email character varying(80) NOT NULL,
    login character varying(50) NOT NULL,
    perfis character varying(50) NOT NULL,
    senha character varying(50) NOT NULL
);


--
-- Name: usuario_perfil; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE usuario_perfil (
    id_usuario_perfil bigint NOT NULL,
    nome_perfil character varying(50) NOT NULL,
    nome_usuario character varying(50) NOT NULL,
    id_perfil_acesso bigint NOT NULL,
    id_usuario bigint NOT NULL
);


--
-- Data for Name: configuracao; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO configuracao (id_configuracao, chave, valor) VALUES (1000, 'CONFIG_USUARIO-2', '{"empresa":1,"exercicio":1,"periodo":"a","template":"layout-1"}');
INSERT INTO configuracao (id_configuracao, chave, valor) VALUES (1, 'CONFIG_USUARIO-100', '{"empresa":151,"exercicio":151,"periodo":"a","template":"layout-2"}');


--
-- Data for Name: contas; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (200, 'DEVEDORA', 'SINTETICA', NULL, '1.0.0.00.00.00.00', 1, 'ATIVO', NULL, NULL, NULL, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (201, 'DEVEDORA', 'SINTETICA', NULL, '1.1.0.00.00.00.00', 2, 'CIRCULANTE', NULL, NULL, 200, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (202, 'DEVEDORA', 'SINTETICA', NULL, '1.1.1.00.00.00.00', 3, 'Disponível', NULL, NULL, 201, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (203, 'DEVEDORA', 'ANALITICA', NULL, '1.1.1.00.00.00.00', 3, 'Caixa ', 1, NULL, 202, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (204, 'DEVEDORA', 'ANALITICA', NULL, '1.1.1.00.00.00.00', 3, 'Bancos Conta Movimento', 2, NULL, 202, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (215, 'DEVEDORA', 'SINTETICA', NULL, '1.2.0.00.00.00.00', 2, 'ATIVO REALIZÁVEL A LONGO PRAZO', NULL, NULL, 200, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (216, 'DEVEDORA', 'SINTETICA', NULL, '1.2.1.00.00.00.00', 3, 'Realizações', NULL, NULL, 215, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (217, 'DEVEDORA', 'ANALITICA', NULL, '1.2.1.00.00.00.00', 3, 'Títulos a Receber', 1, NULL, 216, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (218, 'DEVEDORA', 'SINTETICA', NULL, '1.3.0.00.00.00.00', 2, 'ATIVO PERMANENTE', NULL, NULL, 200, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (219, 'DEVEDORA', 'SINTETICA', NULL, '1.3.1.00.00.00.00', 3, 'Investimentos', NULL, NULL, 218, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (220, 'DEVEDORA', 'ANALITICA', NULL, '1.3.1.00.00.00.00', 3, 'Participações em Outras Cias', 1, NULL, 219, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (221, 'DEVEDORA', 'ANALITICA', NULL, '1.3.1.00.00.00.00', 3, 'Imóveis para Renda', 2, NULL, 219, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (222, 'DEVEDORA', 'SINTETICA', NULL, '1.3.2.00.00.00.00', 3, 'Imobilizado', NULL, NULL, 218, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (223, 'DEVEDORA', 'ANALITICA', NULL, '1.3.2.00.00.00.00', 3, 'Equipamentos de Informática', 1, NULL, 222, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (224, 'DEVEDORA', 'ANALITICA', NULL, '1.3.2.00.00.00.00', 3, 'Imóveis ', 2, NULL, 222, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (225, 'DEVEDORA', 'ANALITICA', NULL, '1.3.2.00.00.00.00', 3, 'Instalações', 3, NULL, 222, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (226, 'DEVEDORA', 'ANALITICA', NULL, '1.3.2.00.00.00.00', 3, 'Máquinas e Equipamentos', 4, NULL, 222, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (227, 'DEVEDORA', 'ANALITICA', NULL, '1.3.2.00.00.00.00', 3, 'Móveis e Utensílios', 5, NULL, 222, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (228, 'DEVEDORA', 'ANALITICA', NULL, '1.3.2.00.00.00.00', 3, 'Veículos', 6, NULL, 222, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (229, 'CREDORA', 'ANALITICA', NULL, '1.3.2.00.00.00.00', 3, '(-) Depreciação Acumulada', 7, NULL, 222, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (230, 'DEVEDORA', 'SINTETICA', NULL, '1.3.3.00.00.00.00', 3, 'Diferido', NULL, NULL, 218, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (231, 'DEVEDORA', 'ANALITICA', NULL, '1.3.3.00.00.00.00', 3, 'Despesas Pré-Operacionais', 1, NULL, 230, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (232, 'DEVEDORA', 'ANALITICA', NULL, '1.3.3.00.00.00.00', 3, 'Despesas com Desenvolvimento de Sistemas', 2, NULL, 230, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (238, 'CREDORA', 'ANALITICA', NULL, '2.1.1.00.00.00.00', 3, 'Fornecedores', 1, NULL, 237, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (239, 'CREDORA', 'ANALITICA', NULL, '2.1.1.00.00.00.00', 3, 'Aluguéis a Pagar', 2, NULL, 237, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (240, 'CREDORA', 'ANALITICA', NULL, '2.1.1.00.00.00.00', 3, 'Empréstimos a Pagar', 3, NULL, 237, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (241, 'CREDORA', 'ANALITICA', NULL, '2.1.1.00.00.00.00', 3, 'ICMS a Recolher', 4, NULL, 237, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (242, 'CREDORA', 'ANALITICA', NULL, '2.1.1.00.00.00.00', 3, 'Imposto de Renda a Pagar', 5, NULL, 237, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (243, 'CREDORA', 'ANALITICA', NULL, '2.1.1.00.00.00.00', 3, 'IR Fonte a Recolher', 6, NULL, 237, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (244, 'CREDORA', 'ANALITICA', NULL, '2.1.1.00.00.00.00', 3, 'Contribuições Previdenciárias  a Recolher ', 7, NULL, 237, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (245, 'CREDORA', 'ANALITICA', NULL, '2.1.1.00.00.00.00', 3, 'FGTS a Recolher', 8, NULL, 237, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (246, 'CREDORA', 'ANALITICA', NULL, '2.1.1.00.00.00.00', 3, 'Honorários da Diretoria a Pagar', 9, NULL, 237, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (247, 'CREDORA', 'ANALITICA', NULL, '2.1.1.00.00.00.00', 3, 'Salários a Pagar', 10, NULL, 237, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (248, 'CREDORA', 'ANALITICA', NULL, '2.1.1.00.00.00.00', 3, 'Dividendos a pagar', 11, NULL, 237, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (249, 'CREDORA', 'ANALITICA', NULL, '2.1.1.00.00.00.00', 3, 'Outras Obrigações a Pagar', 12, NULL, 237, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (251, 'CREDORA', 'SINTETICA', NULL, '2.2.1.00.00.00.00', 3, 'Obrigações', NULL, NULL, 250, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (252, 'CREDORA', 'ANALITICA', NULL, '2.2.1.00.00.00.00', 3, 'Financiamentos a Pagar', 1, NULL, 251, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (254, 'CREDORA', 'SINTETICA', NULL, '2.3.1.00.00.00.00', 3, 'Resultados Futuros', NULL, NULL, 253, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (255, 'CREDORA', 'ANALITICA', NULL, '2.3.1.00.00.00.00', 3, 'Receitas de Exercícios Futuros ', 1, NULL, 254, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (256, 'DEVEDORA', 'ANALITICA', NULL, '2.3.1.00.00.00.00', 3, '(-) Custos e Despesas', 2, NULL, 254, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (258, 'CREDORA', 'SINTETICA', NULL, '2.4.1.00.00.00.00', 3, 'Capital', NULL, NULL, 257, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (259, 'CREDORA', 'ANALITICA', NULL, '2.4.1.00.00.00.00', 3, 'Capital', 1, NULL, 258, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (260, 'DEVEDORA', 'ANALITICA', NULL, '2.4.1.00.00.00.00', 3, '(-) Capital a Realizar', 2, NULL, 258, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (261, 'CREDORA', 'SINTETICA', NULL, '2.4.2.00.00.00.00', 3, 'Reservas de Capital', NULL, NULL, 257, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (262, 'CREDORA', 'ANALITICA', NULL, '2.4.2.00.00.00.00', 3, 'Ágio na Emissão de Ações', 1, NULL, 261, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (205, 'DEVEDORA', 'SINTETICA', '', '1.1.2.00.00.00.00', 3, 'Realizações', NULL, NULL, 201, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (235, 'CREDORA', 'SINTETICA', '', '2.0.0.00.00.00.00', 1, 'PASSIVO', NULL, NULL, NULL, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (236, 'CREDORA', 'SINTETICA', NULL, '2.1.0.00.00.00.00', 2, 'CIRCULANTE', NULL, NULL, 235, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (237, 'CREDORA', 'SINTETICA', NULL, '2.1.1.00.00.00.00', 3, 'Obrigações', NULL, NULL, 236, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (250, 'CREDORA', 'SINTETICA', NULL, '2.2.0.00.00.00.00', 2, 'EXIGÍVEL A LONGO PRAZO', NULL, NULL, 235, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (253, 'CREDORA', 'SINTETICA', NULL, '2.3.0.00.00.00.00', 2, 'RESULTADO DE EXERCÍCIOS FUTUROS', NULL, NULL, 235, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (257, 'CREDORA', 'SINTETICA', NULL, '2.4.0.00.00.00.00', 2, 'PATRIMÔNIO LÍQUIDO', NULL, NULL, 235, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (233, 'DEVEDORA', 'ANALITICA', '', '1.3.3.00.00.00.00', 3, 'Despesas com Desenvolvimento de Novos Produtos ', 3, NULL, 230, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (234, 'CREDORA', 'ANALITICA', '', '1.3.3.00.00.00.00', 3, '(-) Amortização Acumulada', 4, NULL, 230, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (263, 'CREDORA', 'SINTETICA', NULL, '2.4.3.00.00.00.00', 3, 'Reservas de Reavaliação', NULL, NULL, 257, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (264, 'CREDORA', 'ANALITICA', NULL, '2.4.3.00.00.00.00', 3, 'Reavaliação do Permanente', 1, NULL, 263, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (265, 'CREDORA', 'SINTETICA', NULL, '2.4.4.00.00.00.00', 3, 'Reservas de Lucros', NULL, NULL, 257, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (266, 'CREDORA', 'ANALITICA', NULL, '2.4.4.00.00.00.00', 3, 'Reserva Legal', 1, NULL, 265, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (267, 'CREDORA', 'SINTETICA', NULL, '2.4.5.00.00.00.00', 3, 'Lucros ou Prejuízos Acumulados', NULL, NULL, 257, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (268, 'CREDORA', 'ANALITICA', NULL, '2.4.5.00.00.00.00', 3, 'Lucros Acumulados', 1, NULL, 267, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (269, 'DEVEDORA', 'ANALITICA', NULL, '2.4.5.00.00.00.00', 3, '(-) Prejuízos Acumulados', 2, NULL, 267, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (270, 'DEVEDORA', 'SINTETICA', NULL, '3.0.0.00.00.00.00', 2, 'DESPESAS  ', NULL, NULL, NULL, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (271, 'DEVEDORA', 'SINTETICA', NULL, '3.1.0.00.00.00.00', 2, 'DESPESAS OPERACIONAIS', NULL, NULL, 270, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (272, 'DEVEDORA', 'SINTETICA', NULL, '3.1.1.00.00.00.00', 3, 'Despesas com Vendas', NULL, NULL, 271, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (273, 'DEVEDORA', 'ANALITICA', NULL, '3.1.1.00.00.00.00', 3, 'Comissões sobre Vendas', 1, NULL, 272, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (274, 'DEVEDORA', 'ANALITICA', NULL, '3.1.1.00.00.00.00', 3, 'Fretes e Carretos', 2, NULL, 272, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (275, 'DEVEDORA', 'ANALITICA', NULL, '3.1.1.00.00.00.00', 3, 'Material de Embalagem', 3, NULL, 272, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (276, 'DEVEDORA', 'ANALITICA', NULL, '3.1.1.00.00.00.00', 3, 'Propaganda e Publicidade', 4, NULL, 272, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (277, 'DEVEDORA', 'ANALITICA', NULL, '3.1.1.00.00.00.00', 3, 'Despesas c/ Devedores  Duvidosos', 5, NULL, 272, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (278, 'DEVEDORA', 'SINTETICA', NULL, '3.1.2.00.00.00.00', 3, 'Despesas Administrativas', NULL, NULL, 271, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (279, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Aluguel ', 1, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (280, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Energia Elétrica', 2, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (281, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Água ', 3, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (282, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Correios ', 4, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (283, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Depreciações', 5, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (284, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Amortizações', 6, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (285, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Fretes e Carretos', 7, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (286, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Material de Expediente', 8, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (287, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Prêmios de Seguro', 9, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (288, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Comunicações', 10, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (289, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Impostos e Taxas', 11, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (290, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Serviços de Terceiros', 12, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (291, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Multas Fiscais', 13, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (292, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Salários', 14, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (293, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Honorários da Diretoria', 15, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (294, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Décimo Terceiro Salário', 16, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (295, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Encargos Sociais', 17, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (296, 'DEVEDORA', 'ANALITICA', NULL, '3.1.2.00.00.00.00', 3, 'Férias ', 18, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (297, 'DEVEDORA', 'SINTETICA', NULL, '3.1.3.00.00.00.00', 3, 'Despesas Financeiras', NULL, NULL, 271, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (298, 'DEVEDORA', 'ANALITICA', NULL, '3.1.3.00.00.00.00', 3, 'Despesas Bancárias', 1, NULL, 297, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (299, 'DEVEDORA', 'ANALITICA', NULL, '3.1.3.00.00.00.00', 3, 'Juros Passivos', 2, NULL, 297, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (300, 'DEVEDORA', 'ANALITICA', NULL, '3.1.3.00.00.00.00', 3, 'Descontos Concedidos', 3, NULL, 297, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (301, 'DEVEDORA', 'SINTETICA', NULL, '3.1.4.00.00.00.00', 3, 'Outras Despesas Operacionais', NULL, NULL, 271, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (302, 'DEVEDORA', 'ANALITICA', NULL, '3.1.4.00.00.00.00', 3, 'Prejuízo de Participação em outras Cias ', 1, NULL, 301, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (303, 'DEVEDORA', 'ANALITICA', NULL, '3.1.4.00.00.00.00', 3, 'Despesas Eventuais', 2, NULL, 301, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (304, 'DEVEDORA', 'SINTETICA', NULL, '3.2.0.00.00.00.00', 2, 'DESPESAS NÃO OPERACIONAIS', NULL, NULL, 270, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (305, 'DEVEDORA', 'SINTETICA', NULL, '3.2.1.00.00.00.00', 3, 'Perdas  não Operacionais', NULL, NULL, NULL, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (306, 'DEVEDORA', 'ANALITICA', NULL, '3.2.1.00.00.00.00', 3, 'Perdas na Alienação de Bens', 1, NULL, 305, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (307, 'CREDORA', 'SINTETICA', NULL, '4.0.0.00.00.00.00', 1, 'RECEITAS ', NULL, NULL, NULL, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (308, 'CREDORA', 'SINTETICA', NULL, '4.1.0.00.00.00.00', 2, 'RECEITAS OPERACIONAIS', NULL, NULL, 307, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (309, 'CREDORA', 'SINTETICA', NULL, '4.1.1.00.00.00.00', 3, 'Receitas de Vendas ', NULL, NULL, 308, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (310, 'CREDORA', 'ANALITICA', NULL, '4.1.1.00.00.00.00', 3, 'Venda de Mercadorias', 1, NULL, 309, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (311, 'DEVEDORA', 'ANALITICA', NULL, '4.1.1.00.00.00.00', 3, '(-) Vendas Anuladas', 2, NULL, 309, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (312, 'DEVEDORA', 'ANALITICA', NULL, '4.1.1.00.00.00.00', 3, '(-) ICMS sobre Vendas', 3, NULL, 309, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (313, 'DEVEDORA', 'ANALITICA', NULL, '4.1.1.00.00.00.00', 3, '(-) PIS sobre Faturamento', 4, NULL, 309, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (314, 'CREDORA', 'SINTETICA', NULL, '4.1.2.00.00.00.00', 3, 'Receitas de Serviços prestados', NULL, NULL, 308, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (315, 'CREDORA', 'ANALITICA', NULL, '4.1.2.00.00.00.00', 3, 'Venda de Serviços', 1, NULL, 314, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (316, 'DEVEDORA', 'ANALITICA', NULL, '4.1.2.00.00.00.00', 3, '(-) ISSQN sobre Faturamento', 2, NULL, 314, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (317, 'CREDORA', 'SINTETICA', NULL, '4.1.3.00.00.00.00', 3, 'Receitas Financeiras', NULL, NULL, 308, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (318, 'CREDORA', 'ANALITICA', NULL, '4.1.3.00.00.00.00', 3, 'Rendimentos de Aplicações Financeiras', 1, NULL, 317, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (319, 'CREDORA', 'ANALITICA', NULL, '4.1.3.00.00.00.00', 3, 'Descontos Obtidos', 2, NULL, 317, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (320, 'CREDORA', 'ANALITICA', NULL, '4.1.3.00.00.00.00', 3, 'Juros Ativos', 3, NULL, 317, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (321, 'CREDORA', 'SINTETICA', NULL, '4.1.4.00.00.00.00', 3, 'Outras Receitas Operacionais', NULL, NULL, 308, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (322, 'CREDORA', 'ANALITICA', NULL, '4.1.4.00.00.00.00', 3, 'Lucros de Participações em Outras Cias', 1, NULL, 321, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (323, 'CREDORA', 'ANALITICA', NULL, '4.1.4.00.00.00.00', 3, 'Reversão de Provisão Para Devedores Duvidosos ', 2, NULL, 321, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (324, 'CREDORA', 'ANALITICA', NULL, '4.1.4.00.00.00.00', 3, 'Receitas Eventuais', 3, NULL, 321, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (325, 'CREDORA', 'SINTETICA', NULL, '4.2.0.00.00.00.00', 2, 'RECEITAS NÃO OPERACIONAIS', NULL, NULL, 307, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (326, 'CREDORA', 'SINTETICA', NULL, '4.2.1.00.00.00.00', 3, 'Ganhos não Operacionais', NULL, NULL, 325, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (327, 'CREDORA', 'ANALITICA', NULL, '4.2.1.00.00.00.00', 3, 'Ganho na Alienação de Bens', 1, NULL, 325, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (328, 'CREDORA', 'SINTETICA', NULL, '5.0.0.00.00.00.00', 1, 'CONTAS DE APURAÇÃO DE RESULTADOS', NULL, NULL, NULL, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (329, 'CREDORA', 'SINTETICA', NULL, '5.1.0.00.00.00.00', 2, 'APURAÇÃO DE RESULTADO', NULL, NULL, 328, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (330, 'DEVEDORA', 'SINTETICA', NULL, '5.1.1.00.00.00.00', 3, 'Apuração de Resultado', NULL, NULL, 329, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (331, 'DEVEDORA', 'ANALITICA', NULL, '5.1.1.00.00.00.00', 3, 'Custo das Mercadorias Vendidas (CMV)', 1, NULL, 330, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (332, 'DEVEDORA', 'ANALITICA', NULL, '5.1.1.00.00.00.00', 3, 'Resultado com Vendas de Mercadorias (RVM)', 2, NULL, 330, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (333, 'DEVEDORA', 'ANALITICA', NULL, '5.1.1.00.00.00.00', 3, 'Apuração do Resultado do Exercício (ARE)', 3, NULL, 330, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (207, 'CREDORA', 'ANALITICA', NULL, '1.1.2.00.00.00.00', 3, '(-) Duplicatas Descontadas', 2, NULL, 205, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (208, 'DEVEDORA', 'ANALITICA', NULL, '1.1.2.00.00.00.00', 3, 'Aplicações Financeiras', 3, NULL, 205, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (209, 'DEVEDORA', 'ANALITICA', NULL, '1.1.2.00.00.00.00', 3, 'Impostos a Recuperar ', 4, NULL, 205, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (210, 'DEVEDORA', 'ANALITICA', NULL, '1.1.2.00.00.00.00', 3, 'Despesas do Exercício Seguinte', 5, NULL, 205, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (211, 'CREDORA', 'ANALITICA', NULL, '1.1.2.00.00.00.00', 3, '(-) Provisão para Devedores Duvidosos', 6, NULL, 205, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (214, 'CREDORA', 'ANALITICA', NULL, '1.1.3.00.00.00.00', 3, '(-) Provisão para Ajuste ao Valor de Mercado', 2, NULL, 205, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (212, 'DEVEDORA', 'SINTETICA', '', '1.1.3.00.00.00.00', 3, 'Estoques', NULL, NULL, 201, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (213, 'DEVEDORA', 'ANALITICA', '', '1.1.3.00.00.00.00', 3, 'Estoque de Mercadorias', 1, NULL, 212, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (206, 'DEVEDORA', 'ANALITICA', '', '1.1.2.00.00.00.00', 3, 'Clientes', 1, NULL, 205, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1000, 'DEVEDORA', 'ANALITICA', 'Despesas com alimentação, lanches e refeições', '3.1.2.00.00.00.00', 3, 'Alimentação', 17, NULL, 278, 1);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1051, 'DEVEDORA', 'SINTETICA', '', '1.0.0.00.00.00.00', 1, 'ATIVO', 0, NULL, NULL, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1101, 'CREDORA', 'SINTETICA', '', '3.0.0.00.00.00.00', 1, 'RESULTADO DO EXERCICIO', 0, NULL, NULL, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1054, 'CREDORA', 'SINTETICA', '', '2.0.0.00.00.00.00', 1, 'PASSIVO', NULL, NULL, NULL, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1152, 'CREDORA', 'SINTETICA', '', '2.2.0.00.00.00.00', 2, 'PASSIVO LONGO PRAZO', 0, NULL, 1054, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1053, 'DEVEDORA', 'SINTETICA', '', '1.2.0.00.00.00.00', 2, 'DISPONIVEL LONGO PRAZO', 0, NULL, 1051, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1153, 'CREDORA', 'ANALITICA', '', '2.2.1.00.00.00.00', 3, 'Fornecedores Longo Prazo', 1, NULL, 1152, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1109, 'CREDORA', 'SINTETICA', '', '2.3.0.00.00.00.00', 2, 'PATRIMONIO LIQUIDO', 0, NULL, 1054, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1102, 'CREDORA', 'SINTETICA', '', '3.1.0.00.00.00.00', 2, 'RECEITAS', 0, NULL, 1101, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1107, 'CREDORA', 'ANALITICA', '', '3.1.0.00.00.00.00', 2, 'Venda de Serviços', 0, NULL, 1102, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1103, 'DEVEDORA', 'SINTETICA', '', '3.2.0.00.00.00.00', 2, 'DESPESAS', 0, NULL, 1101, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1108, 'DEVEDORA', 'ANALITICA', '', '3.2.0.00.00.00.00', 2, 'Alimentação', 0, NULL, 1103, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1110, 'CREDORA', 'ANALITICA', '', '2.3.0.00.00.00.00', 2, 'Lucro ou prejuízo do exercicio', NULL, NULL, 1109, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1052, 'DEVEDORA', 'SINTETICA', '', '1.1.0.00.00.00.00', 2, 'ATIVO CIRCULANTE', 0, NULL, 1051, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1104, 'DEVEDORA', 'ANALITICA', '', '1.1.1.00.00.00.00', 3, 'Caixa', 1, NULL, 1052, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1105, 'DEVEDORA', 'ANALITICA', '', '1.2.1.00.00.00.00', 3, 'Clientes', 0, NULL, 1053, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1055, 'CREDORA', 'SINTETICA', '', '2.1.0.00.00.00.00', 2, 'PASSIVO CIRCULANTE', 0, NULL, 1054, 100);
INSERT INTO contas (id_conta, conta_origem, conta_tipo, descricao, estrutura, nivel, nome, ordem, id_empresa, id_conta_pai, id_plano_contas) VALUES (1106, 'CREDORA', 'ANALITICA', '', '2.1.1.00.00.00.00', 3, 'Fornecedores', 1, NULL, 1055, 100);


--
-- Data for Name: empresa; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO empresa (id_empresa, cnpj, razao_social, id_empresa_matriz) VALUES (1, '28.119.152/0001-27', 'Coder Sistemas Ltda', NULL);
INSERT INTO empresa (id_empresa, cnpj, razao_social, id_empresa_matriz) VALUES (2, '12.411.117/0001-47', 'SulAmerica Seguros Saude S.A', NULL);
INSERT INTO empresa (id_empresa, cnpj, razao_social, id_empresa_matriz) VALUES (3, '91.125.159/0001-98', 'Sindicato dos Profissionais em Educacao no Ensino Municipal', NULL);
INSERT INTO empresa (id_empresa, cnpj, razao_social, id_empresa_matriz) VALUES (4, '51.422.211/0001-53', 'Unimed Paulistana Sociedade Cooperativa de Trabalho Medico', NULL);
INSERT INTO empresa (id_empresa, cnpj, razao_social, id_empresa_matriz) VALUES (5, '51.422.211/0002-53', 'Unimed Paulistana Sociedade Cooperativa de Trabalho Medico - Filial 2', 4);
INSERT INTO empresa (id_empresa, cnpj, razao_social, id_empresa_matriz) VALUES (6, '13.663.111/0001-20', 'Conselho Regional dos Corretores de Imoveis do Estado do Rio Gran de do Sul', NULL);
INSERT INTO empresa (id_empresa, cnpj, razao_social, id_empresa_matriz) VALUES (7, '19.116.218/0001-34', 'Intermedica Sistema de Saude AS', NULL);
INSERT INTO empresa (id_empresa, cnpj, razao_social, id_empresa_matriz) VALUES (8, '23.819.125/0001-43', 'Consaide Corretora de Seguros de Vida Ltda.', NULL);
INSERT INTO empresa (id_empresa, cnpj, razao_social, id_empresa_matriz) VALUES (9, '08.012.439/0001-46', 'CENTRO CULTURAL JAMES KULISZ - CEJAK', NULL);
INSERT INTO empresa (id_empresa, cnpj, razao_social, id_empresa_matriz) VALUES (100, '098.098/9080-98', 'Tots Service Ltda', NULL);
INSERT INTO empresa (id_empresa, cnpj, razao_social, id_empresa_matriz) VALUES (151, '798.798/7979-79', 'Teste balanco', NULL);


--
-- Data for Name: exercicio; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO exercicio (id_exercicio, ano, fechado, id_empresa, id_plano_contas) VALUES (1, 2017, false, 1, 1);
INSERT INTO exercicio (id_exercicio, ano, fechado, id_empresa, id_plano_contas) VALUES (2, 2016, false, 1, 1);
INSERT INTO exercicio (id_exercicio, ano, fechado, id_empresa, id_plano_contas) VALUES (3, 2015, false, 1, 1);
INSERT INTO exercicio (id_exercicio, ano, fechado, id_empresa, id_plano_contas) VALUES (4, 2016, false, 2, 1);
INSERT INTO exercicio (id_exercicio, ano, fechado, id_empresa, id_plano_contas) VALUES (5, 2017, false, 2, 1);
INSERT INTO exercicio (id_exercicio, ano, fechado, id_empresa, id_plano_contas) VALUES (100, 2017, false, 100, 100);
INSERT INTO exercicio (id_exercicio, ano, fechado, id_empresa, id_plano_contas) VALUES (151, 2015, false, 151, 100);
INSERT INTO exercicio (id_exercicio, ano, fechado, id_empresa, id_plano_contas) VALUES (152, 2016, false, 151, 100);
INSERT INTO exercicio (id_exercicio, ano, fechado, id_empresa, id_plano_contas) VALUES (153, 2017, false, 151, 100);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- Data for Name: historico_padrao; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO historico_padrao (id_historico_padrao, historico) VALUES (1, 'Venda de serviços a vista cfe NF nº __________ a _____________________________________.');
INSERT INTO historico_padrao (id_historico_padrao, historico) VALUES (2, 'Venda de serviços a prazo cfe NF nº __________ a _____________________________________.');
INSERT INTO historico_padrao (id_historico_padrao, historico) VALUES (3, 'Venda de mercadorias a vista cfe NF nº __________ a _____________________________________.');
INSERT INTO historico_padrao (id_historico_padrao, historico) VALUES (4, 'Venda de mercadorias a prazo cfe NF nº __________ a _____________________________________.');
INSERT INTO historico_padrao (id_historico_padrao, historico) VALUES (5, 'Pagamento de despesa "{debito-nome}" cfe NF nº __________ d _____________________________________.');


--
-- Data for Name: lancamento; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (152, '2017-03-22', '46465456', 'SIMPLES', 10, 315, 204, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (153, '2017-03-22', '654654', 'SIMPLES', 5, 315, 206, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (154, '2017-03-22', 'Compra de mercadorias para revenda cfr nf nº _________ de __________________', 'SIMPLES', 5, 238, 213, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (155, '2017-03-22', 'ICMS a recuperar cfe nf nº _________ de __________________', 'SIMPLES', 1, 213, 209, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (156, '2017-03-22', 'Frete sobre vandas cfe nf nº _________ de __________________', 'SIMPLES', 2, 238, 213, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (157, '2017-03-22', 'Impostos a recuperar cfe nf nº _________ de __________________', 'SIMPLES', 1.5, 213, 209, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (201, '2017-03-22', '4654654654', 'SIMPLES', 1000, 315, 206, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (251, '2017-02-01', 'pagamento nf 54654 de rwerwererewr', 'SIMPLES', 0.899999976, 203, 233, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (301, '2017-03-23', 'venda cfe nf nº 1231 a ioiopiopiopiopip', 'SIMPLES', 200, 315, 217, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (401, '2017-03-24', 'venda de serv. cfe nf 45 a oioiopiopiopiopiopi', 'SIMPLES', 10000, 315, 203, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (402, '2017-03-24', 'venda de serv cfe nf nº 54654 a oiwopriweoirweopri', 'SIMPLES', 5260, 315, 204, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (403, '2017-03-24', 'pagamento energia elétrica ref 02/2017 av. andara 150/201', 'SIMPLES', 350, 203, 280, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (404, '2017-03-24', 'des. alimentanao nf 54654 rest ieieieieieie', 'SIMPLES', 25, 203, 1000, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (405, '2017-03-24', 'desp alimenrtação cfe nf 4654654 rest ororororo', 'SIMPLES', 25, 203, 1000, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (351, '2017-03-24', 'fechamento', 'SIMPLES', 16476, 268, 333, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (406, '2016-12-31', 'Venda de serviços a vista cfe NF nº 1 a rierieorierieprier.


', 'SIMPLES', 6500, 315, 203, 2, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (501, '2016-03-26', 'saasdas', 'SIMPLES', 6500, 268, 333, 2, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (151, '2017-03-22', 'dasdasd', 'SIMPLES', 10, 315, 203, 1, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (701, '2015-12-31', 'Pagamento de despesa "{debito-nome}" cfe NF nº 01/2015 d eweweweqw.


', 'SIMPLES', 1, 203, 279, 3, NULL);
INSERT INTO lancamento (id_lancamento, data, historico, lancamentotipo, valor, credito, debito, exercicio, lancamento_principal) VALUES (1002, '2015-12-31', 'serva de serviços a vista cfe NF nº 464654 a 54654654654654654.


', 'SIMPLES', 1, 1107, 1104, 151, NULL);


--
-- Data for Name: lancamento_padrao; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO lancamento_padrao (id_lancamento_padrao, nome, credito, debito, id_historico_padrao) VALUES (1, 'Venda de serviços a vista', 315, 203, 1);
INSERT INTO lancamento_padrao (id_lancamento_padrao, nome, credito, debito, id_historico_padrao) VALUES (3, 'Despesa com aluguel a vista', 203, 279, 5);
INSERT INTO lancamento_padrao (id_lancamento_padrao, nome, credito, debito, id_historico_padrao) VALUES (2, 'Venda de serviços a prazo 2', 315, 206, 1);


--
-- Data for Name: perfil_acesso; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Data for Name: plano_contas; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO plano_contas (id_plano_contas, nome) VALUES (1, 'Plano de contas Comercio');
INSERT INTO plano_contas (id_plano_contas, nome) VALUES (2, 'Plano de contas Industria');
INSERT INTO plano_contas (id_plano_contas, nome) VALUES (3, 'Plano de contas Servicos');
INSERT INTO plano_contas (id_plano_contas, nome) VALUES (4, 'Plano de contas - CENTRO CULTURAL JAMES KULISZ - CEJAK');
INSERT INTO plano_contas (id_plano_contas, nome) VALUES (100, 'Teste');


--
-- Name: seq_configuracao; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('seq_configuracao', 1050, true);


--
-- Name: seq_conta; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('seq_conta', 1250, true);


--
-- Name: seq_empresa; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('seq_empresa', 200, true);


--
-- Name: seq_exercicio; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('seq_exercicio', 200, true);


--
-- Name: seq_historico_padrao; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('seq_historico_padrao', 100, false);


--
-- Name: seq_lancamento; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('seq_lancamento', 1050, true);


--
-- Name: seq_lancamento_padrao; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('seq_lancamento_padrao', 100, false);


--
-- Name: seq_plano_contas; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('seq_plano_contas', 150, true);


--
-- Name: seq_usuario; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('seq_usuario', 100, false);


--
-- Name: seq_usuario_perfil; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('seq_usuario_perfil', 100, false);


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO usuario (id_usuario, inativo, dt_senha, dt_ultimo_acesso, email, login, perfis, senha) VALUES (100, NULL, '2017-03-22', '2017-03-22', 'gustavos.cadastros@gmail.com', 'gustavo', 'USU-ADM,WEB,USU', '202cb962ac59075b964b07152d234b70');


--
-- Data for Name: usuario_perfil; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- Name: configuracao_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY configuracao
    ADD CONSTRAINT configuracao_pkey PRIMARY KEY (id_configuracao);


--
-- Name: contas_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contas
    ADD CONSTRAINT contas_pkey PRIMARY KEY (id_conta);


--
-- Name: empresa_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY empresa
    ADD CONSTRAINT empresa_pkey PRIMARY KEY (id_empresa);


--
-- Name: exercicio_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY exercicio
    ADD CONSTRAINT exercicio_pkey PRIMARY KEY (id_exercicio);


--
-- Name: historico_padrao_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY historico_padrao
    ADD CONSTRAINT historico_padrao_pkey PRIMARY KEY (id_historico_padrao);


--
-- Name: lancamento_padrao_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lancamento_padrao
    ADD CONSTRAINT lancamento_padrao_pkey PRIMARY KEY (id_lancamento_padrao);


--
-- Name: lancamento_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lancamento
    ADD CONSTRAINT lancamento_pkey PRIMARY KEY (id_lancamento);


--
-- Name: perfil_acesso_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY perfil_acesso
    ADD CONSTRAINT perfil_acesso_pkey PRIMARY KEY (id_perfil);


--
-- Name: plano_contas_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY plano_contas
    ADD CONSTRAINT plano_contas_pkey PRIMARY KEY (id_plano_contas);


--
-- Name: uk_5171l57faosmj8myawaucatdw; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT uk_5171l57faosmj8myawaucatdw UNIQUE (email);


--
-- Name: uk_phscd09njq650crx6yfjmfsrv; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY perfil_acesso
    ADD CONSTRAINT uk_phscd09njq650crx6yfjmfsrv UNIQUE (nome);


--
-- Name: uk_pm3f4m4fqv89oeeeac4tbe2f4; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT uk_pm3f4m4fqv89oeeeac4tbe2f4 UNIQUE (login);


--
-- Name: usuario_perfil_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usuario_perfil
    ADD CONSTRAINT usuario_perfil_pkey PRIMARY KEY (id_usuario_perfil);


--
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario);


--
-- Name: index_configuracao_chave; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX index_configuracao_chave ON configuracao USING btree (chave);


--
-- Name: index_conta_estrutura; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX index_conta_estrutura ON contas USING btree (estrutura);


--
-- Name: index_conta_nome; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX index_conta_nome ON contas USING btree (nome);


--
-- Name: index_empresa_cnpj; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX index_empresa_cnpj ON empresa USING btree (cnpj);


--
-- Name: index_empresa_razao_social; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX index_empresa_razao_social ON empresa USING btree (razao_social);


--
-- Name: index_historico_padrao; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX index_historico_padrao ON historico_padrao USING btree (historico);


--
-- Name: index_lancamento_historico; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX index_lancamento_historico ON lancamento USING btree (historico);


--
-- Name: index_lancamento_padrao_nome; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX index_lancamento_padrao_nome ON lancamento_padrao USING btree (nome);


--
-- Name: empresa_matriz_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY empresa
    ADD CONSTRAINT empresa_matriz_fk FOREIGN KEY (id_empresa_matriz) REFERENCES empresa(id_empresa);


--
-- Name: fk_conta_empresa; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contas
    ADD CONSTRAINT fk_conta_empresa FOREIGN KEY (id_empresa) REFERENCES empresa(id_empresa);


--
-- Name: fk_conta_pai; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contas
    ADD CONSTRAINT fk_conta_pai FOREIGN KEY (id_conta_pai) REFERENCES contas(id_conta);


--
-- Name: fk_conta_plano_contas; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contas
    ADD CONSTRAINT fk_conta_plano_contas FOREIGN KEY (id_plano_contas) REFERENCES plano_contas(id_plano_contas);


--
-- Name: fk_exercicio_empresa; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY exercicio
    ADD CONSTRAINT fk_exercicio_empresa FOREIGN KEY (id_empresa) REFERENCES empresa(id_empresa);


--
-- Name: fk_exercicio_plano_contas; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY exercicio
    ADD CONSTRAINT fk_exercicio_plano_contas FOREIGN KEY (id_plano_contas) REFERENCES plano_contas(id_plano_contas);


--
-- Name: fk_lancamento_credito; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lancamento
    ADD CONSTRAINT fk_lancamento_credito FOREIGN KEY (credito) REFERENCES contas(id_conta);


--
-- Name: fk_lancamento_debito; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lancamento
    ADD CONSTRAINT fk_lancamento_debito FOREIGN KEY (debito) REFERENCES contas(id_conta);


--
-- Name: fk_lancamento_exercicio; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lancamento
    ADD CONSTRAINT fk_lancamento_exercicio FOREIGN KEY (exercicio) REFERENCES exercicio(id_exercicio);


--
-- Name: fk_lancamento_padrao_credito; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lancamento_padrao
    ADD CONSTRAINT fk_lancamento_padrao_credito FOREIGN KEY (credito) REFERENCES contas(id_conta);


--
-- Name: fk_lancamento_padrao_debito; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lancamento_padrao
    ADD CONSTRAINT fk_lancamento_padrao_debito FOREIGN KEY (debito) REFERENCES contas(id_conta);


--
-- Name: fk_lancamento_padrao_historico_padrao; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lancamento_padrao
    ADD CONSTRAINT fk_lancamento_padrao_historico_padrao FOREIGN KEY (id_historico_padrao) REFERENCES historico_padrao(id_historico_padrao);


--
-- Name: fk_lancamento_principal; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lancamento
    ADD CONSTRAINT fk_lancamento_principal FOREIGN KEY (lancamento_principal) REFERENCES lancamento(id_lancamento);


--
-- Name: usuario_perfil_acesso_perfil_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usuario_perfil
    ADD CONSTRAINT usuario_perfil_acesso_perfil_fk FOREIGN KEY (id_perfil_acesso) REFERENCES perfil_acesso(id_perfil);


--
-- Name: usuario_perfil_acesso_usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY usuario_perfil
    ADD CONSTRAINT usuario_perfil_acesso_usuario_fk FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario);


--
-- PostgreSQL database dump complete
--

