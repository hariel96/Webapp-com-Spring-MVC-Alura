package br.com.caelum.contas.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.caelum.contas.dao.ContaDAO;
import br.com.caelum.contas.modelo.Conta;

@Controller
public class ContaController {

	private ContaDAO dao;

	@Autowired
	public ContaController(ContaDAO dao) {
		this.dao = dao;
	}

	@RequestMapping("/form")
	public String formulario() {

		return "conta/Formulario";
	}

	@RequestMapping("/adicionaConta")
	public String adiciona(@Valid Conta conta, BindingResult result) {

		if (result.hasErrors()) {

			return "conta/Formulario";
		}

		dao.adiciona(conta);

		return "redirect:listaContas";

	}

	@RequestMapping("/pagaConta")
	public void paga(Conta conta, HttpServletResponse response) {
		dao.paga(conta.getId());

		response.setStatus(200);
	}

	@RequestMapping("/removeConta")
	public String remove(Conta conta) {
		dao.remove(conta);

		return "redirect:listaContas";
	}

	@RequestMapping("/listaContas")
	public String lista(Model mv) {

		List<Conta> contas = dao.lista();

		mv.addAttribute("contas", contas);

		return "conta/lista";
	}

	@RequestMapping("/mostraConta")
	public String mostra(Long id, Model model) {
		model.addAttribute("conta", dao.buscaPorId(id));
		return "conta/mostra";

	}

	@RequestMapping("/alteraConta")
	public String altera(Conta conta) {
		dao.altera(conta);
		return "redirect:listaContas";
	}

}
