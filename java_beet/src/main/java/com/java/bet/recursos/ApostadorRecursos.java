package com.java.bet.recursos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.bet.entidades.Apostador;
import com.java.bet.servicos.ApostadorSevicos;
import com.java.bet.servicos.excecoes.DatabaseExecoes;
import com.java.bet.servicos.excecoes.TratamentoDeExcecoes;

@RestController
@RequestMapping(value = "/apostadores")
public class ApostadorRecursos {
	//INJEÇÕES DE DEPENDECIAS
    @Autowired
    private ApostadorSevicos apostadorServicos;

    //================================================ INSERIR APOSTADORES ================================================
    @GetMapping("/inserirApostador")
    public ModelAndView adicionarApostadorView(Apostador obj) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Apostadores/formApostador");
        mv.addObject("apostador", new Apostador());
        return mv;
    }

    @PostMapping("/inserirApostador")  
    public ModelAndView adicionarApostador(Apostador obj, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();
        try {
            apostadorServicos.adicionarApostador(obj);
            redirectAttributes.addFlashAttribute("successMessage", "Apostador inserido com sucesso!");
            mv.setViewName("redirect:/apostadores/Apostadores-adicionados");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao adicionar apostador. Tente novamente.");
            mv.setViewName("redirect:/apostadores/inserirApostador");
        }
        return mv;
    }
    
    //================================================ LISTAR APOSTADORES ================================================
    @GetMapping("/Apostadores-adicionados")
    public ModelAndView listarApostadores() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Apostadores/listarApostadores");
        mv.addObject("apostadoresList", apostadorServicos.buscarApostadores());
        return mv;
    }
    
    //================================================ ALTERAR APOSTADORES ================================================
    @GetMapping("/alterar/{id}")
    public ModelAndView alterar(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Apostadores/alterar");
        Apostador apostador = apostadorServicos.buscarPorId(id);
        mv.addObject("apostador", apostador);
        return mv;
    }
    
    @PostMapping("/alterar")
    public ModelAndView alterar(Apostador apostador, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();
        try {
            apostadorServicos.adicionarApostador(apostador);
            redirectAttributes.addFlashAttribute("successMessage", "Apostador atualizado com sucesso!");
            mv.setViewName("redirect:/apostadores/Apostadores-adicionados");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar apostador. Tente novamente.");
            mv.setViewName("redirect:/apostadores/alterar/" + apostador.getId());
        }
        return mv;
    }

    //================================================ EXCLUIR APOSTADORES ================================================
    @GetMapping("/excluir/{id}")
    public ModelAndView excluirApostador(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/apostadores/Apostadores-adicionados");
        try {
            // Verifica se o apostador tem apostas associadas
            Apostador apostador = apostadorServicos.buscarPorId(id);
            if (apostador != null && !apostador.getApostas().isEmpty()) {
                throw new IllegalStateException("O apostador tem apostas associadas. Não é possível excluir.");
            }
            
            apostadorServicos.deletarApostador(id);
            redirectAttributes.addFlashAttribute("successMessage", "Apostador excluído com sucesso!");
        } catch (TratamentoDeExcecoes e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Apostador não encontrado. Verifique o ID.");
        } catch (DatabaseExecoes e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro de integridade de dados. Verifique se este apostador está vinculado a outras apostas.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ocorreu um erro inesperado. " + e.getMessage());
        }
        return mv;
    }

}
