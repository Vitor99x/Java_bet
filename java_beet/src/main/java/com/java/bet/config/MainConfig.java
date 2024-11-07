package com.java.bet.config;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.java.bet.entidades.Apostador;
import com.java.bet.entidades.Apostas;
import com.java.bet.entidades.enums.ApostasStatus;
import com.java.bet.repositorios.ApostadorRepositorio;
import com.java.bet.repositorios.ApostasRepositorio;

@Configuration
@Profile("vitorHugo")
public class MainConfig implements CommandLineRunner {
	Instant now = Instant.now();
	LocalDate localDate = now.atZone(ZoneId.systemDefault()).toLocalDate();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String formattedDate = localDate.format(formatter);
	
	@Autowired
	private ApostasRepositorio apostasRepositorio;
	

	private ApostasStatus apostasStatus;
	
	@Autowired
	private ApostadorRepositorio apostadorRepositorio;

	@Override
	public void run(String... args) throws Exception {
		//Apostadores da tb_apostadores
		Apostador apostador01 = new Apostador(null, "Carlos", "carlos@gmail.com", "java.bet@404");
		Apostador apostador02 = new Apostador(null, "Joao", "joao@gmail.com", "java.bet@303");
		Apostador apostador03 = new Apostador(null, "Thomaz", "thomaz@gmail.com", "java.bet@808");
		Apostador apostador04 = new Apostador(null, "Vitor", "vitor@gmail.com", "java.bet@505");
		Apostador apostador05 = new Apostador(null, "Walalce", "wallace@gmail.com", "java.bet@707");
		Apostador apostador06 = new Apostador(null, "Vitor", "vitor@gmail.com", "java.bet@505");
		Apostador apostador07 = new Apostador(null, "Walalce", "Walalce@gmail.com", "java.bet@505");
		
		apostadorRepositorio.saveAll(Arrays.asList(apostador01, apostador02 ,apostador03 ,apostador04, apostador05,apostador06,apostador07));
		
		//Apostas dos usuarios vinculados com a tb_apostadores
		//ATENÇAO: Está tabela está fazendo um join column, 
		Apostas aposta01 = new Apostas(null,"Futebol", 2, 100.00, apostador01, ApostasStatus.APOSTA_CANCELADA);
		Apostas aposta022 = new Apostas(null,"Futebol", 2, 100.00, apostador01, ApostasStatus.APOSTA_PENDENTE);
		Apostas aposta02 = new Apostas(null,"Corrida de Cavalo", 3, 800.00, apostador02, ApostasStatus.APOSTA_REALIZADA);
		Apostas aposta03 = new Apostas(null,"Fórmula Um", 10, 840.00, apostador03, ApostasStatus.APOSTA_CANCELADA);
		Apostas aposta04 = new Apostas(null,"Tigrinho", 25, 0.500, apostador04, ApostasStatus.APOSTA_PENDENTE);
		
		apostasRepositorio.saveAll(Arrays.asList(aposta01,aposta022, aposta02, aposta03, aposta04));
	}
}
