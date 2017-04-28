insert into LANCAMENTO (ID_LANCAMENTO, DATE, DEBITO, CREDITO, EXERCICIO, HISTORICO, LANCAMENTO_PRINCIPAL, lancamentoTipo, VALOR) values (default, TO_DATE ('31/12/2006', 'DD/MM/YYYY'),   95,    87, 1, 'Servico prestado a vista cfe nf nr 0002 - Matheus Portela Souza', null, 'SIMPLES',    5);
insert into LANCAMENTO (ID_LANCAMENTO, DATE, DEBITO, CREDITO, EXERCICIO, HISTORICO, LANCAMENTO_PRINCIPAL, lancamentoTipo, VALOR) values (default, TO_DATE ('05/01/2017', 'DD/MM/YYYY'),    4,    87, 1, 'Servico prestado a vista cfe nf nr 0001 - Maria da Silva Mendonca', null, 'SIMPLES', 5000);
insert into LANCAMENTO (ID_LANCAMENTO, DATE, DEBITO, CREDITO, EXERCICIO, HISTORICO, LANCAMENTO_PRINCIPAL, lancamentoTipo, VALOR) values (default, TO_DATE ('05/01/2017', 'DD/MM/YYYY'),    6,     4, 1, 'Deposito no banco Alfa', null, 'SIMPLES', 1000);
insert into LANCAMENTO (ID_LANCAMENTO, DATE, DEBITO, CREDITO, EXERCICIO, HISTORICO, LANCAMENTO_PRINCIPAL, lancamentoTipo, VALOR) values (default, TO_DATE ('05/01/2017', 'DD/MM/YYYY'),   95,    87, 1, 'Servico prestado a vista cfe nf nr 0002 - Matheus Portela Souza', null, 'SIMPLES',    5);
insert into LANCAMENTO (ID_LANCAMENTO, DATE, DEBITO, CREDITO, EXERCICIO, HISTORICO, LANCAMENTO_PRINCIPAL, lancamentoTipo, VALOR) values (default, TO_DATE ('05/01/2017', 'DD/MM/YYYY'),    8,    87, 1, 'Servico prestado a prazo cfe nf nr 0003 - Rafael Dias Santos', null, 'SIMPLES',  200);
insert into LANCAMENTO (ID_LANCAMENTO, DATE, DEBITO, CREDITO, EXERCICIO, HISTORICO, LANCAMENTO_PRINCIPAL, lancamentoTipo, VALOR) values (default, TO_DATE ('15/01/2017', 'DD/MM/YYYY'),    8,    87, 1, 'Servico prestado a prazo cfe nf nr 0004 - Lucas di Marco Ataides', null, 'SIMPLES',  550);
insert into LANCAMENTO (ID_LANCAMENTO, DATE, DEBITO, CREDITO, EXERCICIO, HISTORICO, LANCAMENTO_PRINCIPAL, lancamentoTipo, VALOR) values (default, TO_DATE ('15/01/2017', 'DD/MM/YYYY'),    4,     8, 1, 'Recebimento cfe nf nr 0003 1/4 - Rafael Dias Santos', null, 'SIMPLES',  50);
insert into LANCAMENTO (ID_LANCAMENTO, DATE, DEBITO, CREDITO, EXERCICIO, HISTORICO, LANCAMENTO_PRINCIPAL, lancamentoTipo, VALOR) values (default, TO_DATE ('15/01/2017', 'DD/MM/YYYY'),   95,     4, 1, 'Deposito no bradesco', null, 'SIMPLES',  50);
-- Exemplo de lancamento composto
insert into LANCAMENTO (ID_LANCAMENTO, DATE, DEBITO, CREDITO, EXERCICIO, HISTORICO, LANCAMENTO_PRINCIPAL, lancamentoTipo, VALOR) values (default, TO_DATE ('16/01/2017', 'DD/MM/YYYY'), null,  null, 1, 'Mercadotias vendidas a prazo nf 001 a joaozinho da silva vai pagar parcelado', null, 'COMPOSTO', 7580);
insert into LANCAMENTO (ID_LANCAMENTO, DATE, DEBITO, CREDITO, EXERCICIO, HISTORICO, LANCAMENTO_PRINCIPAL, lancamentoTipo, VALOR) values (default, TO_DATE ('16/01/2017', 'DD/MM/YYYY'),   18,  null, 1, 'Mercadotias vendidas a prazo nf 001 a joaozinho da silva vai pagar parcelado', 5, 'COMPOSTO', 5030);
insert into LANCAMENTO (ID_LANCAMENTO, DATE, DEBITO, CREDITO, EXERCICIO, HISTORICO, LANCAMENTO_PRINCIPAL, lancamentoTipo, VALOR) values (default, TO_DATE ('16/01/2017', 'DD/MM/YYYY'),   19,  null, 1, 'Mercadotias vendidas a prazo nf 001 a joaozinho da silva vai pagar com favores', 5, 'COMPOSTO', 2550);
insert into LANCAMENTO (ID_LANCAMENTO, DATE, DEBITO, CREDITO, EXERCICIO, HISTORICO, LANCAMENTO_PRINCIPAL, lancamentoTipo, VALOR) values (default, TO_DATE ('16/01/2017', 'DD/MM/YYYY'), null,    86, 1, 'Mercadotias vendidas a prazo nf 001 a joaozinho da silva va se arrebentar pra pagar', 5, 'COMPOSTO',7580);