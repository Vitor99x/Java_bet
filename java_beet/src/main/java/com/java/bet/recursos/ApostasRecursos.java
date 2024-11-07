package com.java.bet.recursos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.bet.entidades.Apostas;
import com.java.bet.entidades.enums.ApostasStatus;
import com.java.bet.entidades.Apostador;
import com.java.bet.servicos.ApostasSevicos;
import com.java.bet.servicos.ApostadorSevicos;

import java.util.List;

@Controller
@RequestMapping(value = "/apostas")
public class ApostasRecursos {

	
	//INJEÇÕES DE DEPENDECIAS
    @Autowired
    private ApostasSevicos apostasSevicos;

    @Autowired
    private ApostadorSevicos apostadorSevicos;

    //================================================ INSERIR APOSTADORES ================================================
    /*
     * // AQUI ESTOU INDICANDO QUE VOU ENTRAR EM UMA TELA, !!!!@GetMapping http://localhost:8080/apostas/inserirAposta
     *  QUE NO CASO SERIA O MEU formApostas que esta dentro da pasta
     *  src/main/resources/templates/Apostas/formApostas.html
     * */
    @GetMapping("/inserirAposta") 
    public ModelAndView inserirApostaForm() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Apostas/formAposta");

        // Lista todos os apostadores para permitir que o usuário selecione um
        List<Apostador> apostadores = apostadorSevicos.buscarApostadores();
        mv.addObject("apostadores", apostadores);  // Passando a lista de apostadores

        mv.addObject("aposta", new Apostas());  // Cria uma nova aposta

        return mv;
    }

    //================================================ ADICIONAR APOSTADORES ================================================
    
    /*DENTRO DO MEU formApostas eu tenho um formulário, com os seguintes campos: 
      LEMBRE-SE QUE NA CLASSE DE APOSTAS EU TENHO TODOS ESSES ATRIBUTOS!!!!!! esta classe esta dentro de: com.java.bet.entidades
     	private Long id; //ESTE ID ESTA COMO AUTO INCREMENTO NA TABELA!
		private String tipoAposta;
		private double valorAposta;
		private int quantidadeAposta;
		private ApostasStatus apostasStatus;
		private Apostador usuario;
	
		Tenho todos esses campos no meu formApostas
		AO CLICAR NO BOTÃO "Criar Aposta" ele vai entrar no método adicionarAposta e ele faz uma tentativa.
		Se tudo der certo ele executa o meu apostasSevicos.adicionarAposta(aposta);
		lembre-se que esse apostasSevicos vem da minha classe de Servico que lá eu tenho o método de adicionar uma aposta!
		*/
    @PostMapping("/adicionarAposta")
    public ModelAndView adicionarAposta(@ModelAttribute Apostas aposta, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();
        try {
            Apostador apostador = apostadorSevicos.buscarPorId(aposta.getUsuario().getId());
            if (apostador == null) {
                throw new IllegalArgumentException("Apostador não encontrado.");
            }
            
            aposta.setUsuario(apostador);
            apostasSevicos.adicionarAposta(aposta);

            redirectAttributes.addFlashAttribute("successMessage", "Aposta criada com sucesso!");
            mv.setViewName("redirect:/apostas/listarApostas");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao adicionar a aposta: " + e.getMessage());
            redirectAttributes.addFlashAttribute("aposta", aposta);  // Passando o objeto aposta em caso de erro
            mv.setViewName("redirect:/apostas/inserirAposta");
        }
        return mv;
    }




    @GetMapping("/listarApostas")
    public ModelAndView listarApostas(RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Apostas/listarApostas");  // Nome da view

        // Buscar todas as apostas
        List<Apostas> apostas = apostasSevicos.buscarApostas(); 
        mv.addObject("apostas", apostas);  // Passando a lista de apostas para a view

        // Passando as mensagens de sucesso ou erro para a view
        mv.addObject("successMessage", redirectAttributes.getFlashAttributes().get("successMessage"));
        mv.addObject("errorMessage", redirectAttributes.getFlashAttributes().get("errorMessage"));

        return mv;
    }
}
