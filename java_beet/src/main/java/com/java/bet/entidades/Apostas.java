package com.java.bet.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.java.bet.entidades.enums.ApostasStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "tb_apostas")
public class Apostas implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tipoAposta;
	private double valorAposta;
	private int quantidadeAposta;
	
	
	@Enumerated(EnumType.STRING)
	private ApostasStatus apostasStatus;
	
	
	//definir o relacionamento Muitos para um, Varias apostas para um apostador
	//O Nome id_apostador irá fazer a junçao de coluna com a classe apostador
	@ManyToOne
	@JoinColumn(name = "id_apostador")
	private Apostador usuario;
	
	public Apostas() {
		
	}
	
	
	public Apostas(Long id, String tipoAposta, int quantidadeAposta, double valorAposta, Apostador usuario, ApostasStatus apostasStatus) {
		this.id = id;
		
		this.tipoAposta=tipoAposta;
		this.quantidadeAposta=quantidadeAposta;
		this.valorAposta=valorAposta;
		this.usuario = usuario;
		this.apostasStatus=apostasStatus;
	}

	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoAposta() {
		return tipoAposta;
	}

	public void setTipoAposta(String tipoAposta) {
		this.tipoAposta = tipoAposta;
	}

	public double getValorAposta() {
		return valorAposta;
	}

	public void setValorAposta(double valorAposta) {
		this.valorAposta = valorAposta;
	}

	public int getQuantidadeAposta() {
		return quantidadeAposta;
	}

	public void setQuantidadeAposta(int quantidadeAposta) {
		this.quantidadeAposta = quantidadeAposta;
	}

	public Apostador getUsuario() {
		return usuario;
	}

	public void setUsuario(Apostador usuario) {
		this.usuario = usuario;
	}
	
	public ApostasStatus getApostasStatus() {
		return apostasStatus;
	}

	public void setApostasStatus(ApostasStatus apostasStatus) {
		this.apostasStatus = apostasStatus;
	}
	
	//para aparecer este método no meu Json, eu tenho que deixar especificado "(get)TotalApostas"
	// caso nao estejá especificado (get)NoeDoSeuMetodo meu json nao irá retornar o valor deste método no request!
	public Double getTotalApostas() {
		 return valorAposta * quantidadeAposta;
	}
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Apostas other = (Apostas) obj;
		return Objects.equals(id, other.id);
	}
	
	
	

}
