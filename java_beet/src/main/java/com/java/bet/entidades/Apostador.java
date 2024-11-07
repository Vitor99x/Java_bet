package com.java.bet.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_apostadores")
public class Apostador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String senha;
	
	
	//defin o relacionamento um para muitos,
	//um Apostador pondendo fazer n apostas
	//JsonIgnore faz com que minha aplicaçao nao retorne um looping quando eu chamar tal id na minha url
	@JsonIgnore 
	
	@OneToMany(mappedBy = "usuario")
	private List<Apostas> apostas = new ArrayList<>();
	
	public Apostador() {}
	
	public Apostador(Long id, String nome, String email, String senha) {
		this.id=id;
		this.nome=nome;
		this.email=email;
		this.senha=senha;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Apostas> getApostas() {
		return apostas;
	}
	
			
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	

	//Equals para comparar se um objeto é diferente do outro 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Apostador other = (Apostador) obj;
		return Objects.equals(id, other.id);
	}

	
	
}
