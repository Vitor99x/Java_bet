package com.java.bet.recursos.excecoes;

import java.io.Serializable;
import java.time.Instant;

public class ErrosPadroes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Instant horario ;
	private Integer status;
	private String error;
	private String mensagem;
	private String caminho;
	
	public ErrosPadroes() {
		
	}

	public ErrosPadroes(Instant horario, Integer status, String error, String mensagem, String caminho) {
		super();
		this.horario = horario;
		this.status = status;
		this.error = error;
		this.mensagem = mensagem;
		this.caminho = caminho;
	}

	public Instant getHorario() {
		return horario;
	}

	public void setHorario(Instant horario) {
		this.horario = horario;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	

}
