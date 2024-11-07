package com.java.bet.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.java.bet.entidades.Apostador;
import com.java.bet.repositorios.ApostadorRepositorio;
import com.java.bet.servicos.excecoes.DatabaseExecoes;
import com.java.bet.servicos.excecoes.TratamentoDeExcecoes;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ApostadorSevicos {
	
	
	//Ineção de dependecia, ApostadorRepositorio é uma interface que extende de JpaRepository onde no meu JPA tenho diversosos metodos
	@Autowired 
	private ApostadorRepositorio apostadorRepositorio;
	
	public List<Apostador> buscarApostadores(){
		
		return apostadorRepositorio.findAll();
		
	}
	
	public Apostador buscarPorId(Long id) {
		Optional <Apostador> obj = apostadorRepositorio.findById(id);
		return obj.orElseThrow(() ->  new TratamentoDeExcecoes(id));		
	}
	
	public Apostador adicionarApostador(Apostador obj) {
		return apostadorRepositorio.save(obj);
		
	}
	
	
	public void deletarApostador(Long id) {
		try {
			apostadorRepositorio.deleteById(id);
		}catch(EmptyResultDataAccessException e){
			throw new TratamentoDeExcecoes(id); 
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseExecoes(e.getMessage());
		}
	}
	
	public Apostador atualizarApostador(Long id, Apostador novoApostador) {
		try {
			//pegando a referencia por id do apostador
			Apostador entidade = apostadorRepositorio.getReferenceById(id);
			// chamando minha funçao, passando meu objeto para que atualize os dados do apostador
			atualizarDados(entidade, novoApostador);
			//return para salvar 
			return apostadorRepositorio.save(entidade);
		}catch(EntityNotFoundException e) {
			throw new TratamentoDeExcecoes(id); 
		}
		
	}

	private void atualizarDados(Apostador entidade, Apostador obj) {
		entidade.setNome(obj.getNome());
		entidade.setEmail(obj.getEmail());
		entidade.setSenha(obj.getSenha());
		
	}
}
