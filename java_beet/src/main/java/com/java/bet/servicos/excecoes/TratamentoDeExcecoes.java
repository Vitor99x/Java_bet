package com.java.bet.servicos.excecoes;

public class TratamentoDeExcecoes extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public TratamentoDeExcecoes(Object id) {
		super("Recurso nao encontrado. Id: " + id);
		
	}

}
