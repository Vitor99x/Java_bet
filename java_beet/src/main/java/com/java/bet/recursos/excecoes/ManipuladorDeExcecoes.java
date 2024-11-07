package com.java.bet.recursos.excecoes;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.java.bet.servicos.excecoes.DatabaseExecoes;
import com.java.bet.servicos.excecoes.TratamentoDeExcecoes;

import jakarta.servlet.http.HttpServletRequest;
/*Controller Advice -> ele intercepta as excecoes ocorridas na aplicaçao 
para que a classe (ManipuladorDeExcecoes) possa executar o tratamento de excecoes vindo da aplicacao */
@ControllerAdvice
public class ManipuladorDeExcecoes {
	/* 
	 Anotaçao @ExceptionHandler para que ele seja capaz de 
	 interceptar a requisição que deu exceçao vindo da classe TratamentoDeExcecoes
	 para que caia na minha classe errosPadroes e possa tratar
	  */
	
	
	@ExceptionHandler(TratamentoDeExcecoes.class)
	public ResponseEntity<ErrosPadroes> errosPadroes (TratamentoDeExcecoes e, HttpServletRequest reequisiao){
		String erro = "Vejá se existe este Id no Banco de dados";
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrosPadroes erros = new ErrosPadroes(Instant.now(), status.value(), erro, e.getMessage(), reequisiao.getRequestURI());
		return ResponseEntity.status(status).body(erros);
	}
	
	@ExceptionHandler(DatabaseExecoes.class)
	public ResponseEntity<ErrosPadroes> database (DatabaseExecoes e, HttpServletRequest reequisiao){
		String erro = "Vejá se o apostador está atrelado a alguma aposta!";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ErrosPadroes erros = new ErrosPadroes(Instant.now(), status.value(), erro, e.getMessage(), reequisiao.getRequestURI());
		return ResponseEntity.status(status).body(erros);
	}

}
