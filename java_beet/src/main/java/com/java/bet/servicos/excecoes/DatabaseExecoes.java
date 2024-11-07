package com.java.bet.servicos.excecoes;

public class DatabaseExecoes extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DatabaseExecoes(String msg) {
		super(msg);
		
	}

}
