package com.java.bet.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.bet.entidades.Apostas;
import com.java.bet.entidades.Apostador;
import com.java.bet.repositorios.ApostasRepositorio;
import com.java.bet.repositorios.ApostadorRepositorio;

@Service
public class ApostasSevicos {

    @Autowired
    private ApostasRepositorio apostasRepositorio;

    @Autowired
    private ApostadorRepositorio apostadorRepositorio;  // Repositório para acessar os apostadores

    // Buscar todas as apostas
    public List<Apostas> buscarApostas() {
        return apostasRepositorio.findAll(); 
    }

    // Buscar uma aposta por ID
    public Apostas buscarPorId(Long id) {
        Optional<Apostas> obj = apostasRepositorio.findById(id);
        return obj.orElseThrow(() -> new RuntimeException("Aposta não encontrada"));
    }

    // Método para adicionar uma nova aposta associada a um apostador
    public Apostas adicionarAposta(Apostas aposta) {
        // Verifica se o apostador existe no banco de dados
        if (aposta.getUsuario() == null || aposta.getUsuario().getId() == null) {
            throw new RuntimeException("Apostador não encontrado para associar à aposta");
        }

        // Pode ser interessante verificar se o apostador realmente existe no banco de dados
        Apostador apostador = apostadorRepositorio.findById(aposta.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Apostador não encontrado"));

        // Associa o apostador à aposta
        aposta.setUsuario(apostador);

        // Salva a aposta associada ao apostador
        return apostasRepositorio.save(aposta);
    }

}
