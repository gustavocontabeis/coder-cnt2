INSERT INTO LANCAMENTO_PADRAO (ID_LANCAMENTO_PADRAO, NOME, DEBITO, CREDITO, ID_HISTORICO_PADRAO)VALUES(1,'Venda de serviços a vista',203,315,1)
INSERT INTO LANCAMENTO_PADRAO (ID_LANCAMENTO_PADRAO, NOME, DEBITO, CREDITO, ID_HISTORICO_PADRAO)VALUES(2,'Venda de serviços a prazo',206,315,1)
INSERT INTO LANCAMENTO_PADRAO (ID_LANCAMENTO_PADRAO, NOME, DEBITO, CREDITO, ID_HISTORICO_PADRAO)VALUES(3,'Despesa com aluguel a vista',279,203,5)
INSERT INTO public.lancamento_padrao (id_lancamento_padrao,nome,credito,debito,id_historico_padrao) VALUES (
100,'Compra mercadorias para revenda-a vista',203,213,NULL);
INSERT INTO public.lancamento_padrao (id_lancamento_padrao,nome,credito,debito,id_historico_padrao) VALUES (
101,'Compra mercadoreias para revenda-a prazo',238,213,100);
INSERT INTO public.lancamento_padrao (id_lancamento_padrao,nome,credito,debito,id_historico_padrao) VALUES (
102,'Compra mercadoreias para revenda-a prazo',238,213,100);
INSERT INTO public.lancamento_padrao (id_lancamento_padrao,nome,credito,debito,id_historico_padrao) VALUES (
103,'Pagamento de Fornecedor a vista',203,238,101);
