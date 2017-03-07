package br.com.cnt.model.utils;

import java.math.BigDecimal;
import java.util.logging.Logger;

import br.com.cnt.model.entity.balanco.Conta;
import br.com.cnt.model.entity.balanco.ContaOrigem;

public class ContaUtil {
	
	private static final Logger log = Logger.getLogger(ContaUtil.class.getSimpleName());
	
	public static Integer retornarNivel(Conta conta){
		String estrutura = conta.getEstrutura();
		String[] split = estrutura.split("\\.");
		for (int i = 0; i < split.length; i++) {
			if(Integer.parseInt(split[i]) == 0)
				return i;
		}
		return split.length;
	}
	
	public static String estruturaSemZeros(Conta conta) {
		return estruturaSemZeros(conta.getEstrutura());
	}
	
	public static String estruturaSemZeros(String conta) {
		return conta.replace(".00", "").replace(".0", "");
	}
	
	/**
	 * Todo valor a débito será positivo e crédito negativo.
	 * Caso o valor não esteja correto, será a origem será invertida e o valor absoluto será exibido.
	 * @param valor
	 * @param origem
	 * @return
	 */
	public static String getValorContabil(BigDecimal valor, ContaOrigem origem){
		float vlr = valor.floatValue();
		ContaOrigem co = null;
		if(ContaOrigem.DEVEDORA == origem && vlr < 0f ){
			co = ContaOrigem.CREDORA;
		}
		if(ContaOrigem.CREDORA == origem && vlr > 0f ){
			co = ContaOrigem.DEVEDORA;
		}
		if(co == null){
			co = origem;
		}
		String format = NumberUtil.format(Math.abs(valor.doubleValue()));
		return format + " " + co.codigo;
	}
	
	public static boolean isValorContabilPositivo(float valor, ContaOrigem origem){
		if(ContaOrigem.DEVEDORA == origem && valor < 0f ){
			return false;
		}
		if(ContaOrigem.CREDORA == origem && valor > 0f ){
			return false;
		}
		return true;
	}

	public static int compararNivel(Conta conta1, Conta conta2) {
		
		String[] split1 = conta1.getEstrutura().split("\\.");
		String[] split2 = conta2.getEstrutura().split("\\.");
		
		
		boolean direfenca = false;
		int compareToAnterior = 0;
		
		for (int i = 0; i < split2.length; i++) {
			
			Integer integer1 = new Integer(split1[i]);
			Integer integer2 = new Integer(split2[i]);
			
			log.info(integer1+" - "+integer2);
			
			int compareToAtual = integer1.compareTo(integer2);
			
			if(compareToAnterior == 0){
			}else{
				direfenca = true;
			}
			if(direfenca 
					&& (integer1.intValue() == 0 && integer2.intValue() == 0)
					&& conta1.getContaTipo() != conta2.getContaTipo()){
				return compareToAnterior;
			}
		}
		return compareToAnterior;
	}

	public static boolean isFilho(Conta conta1, Conta conta2) {
		int compararNivel = compararNivel(conta1, conta2);
		return compararNivel<0;
	}

	public static boolean isPai(Conta conta1, Conta conta2) {
		int compararNivel = compararNivel(conta1, conta2);
		return compararNivel>0;
	}

}
