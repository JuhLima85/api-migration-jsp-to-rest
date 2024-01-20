//package br.com.devsibre.Controller;
//
////import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import br.com.devsibre.Model.GrauDeParentesco;
//import br.com.devsibre.Model.PessoaModel;
//import br.com.devsibre.Model.Relacionamento;
//import br.com.devsibre.Service.PessoaService;
//
//@Controller
//@RequestMapping("/pessoas")
//public class PessoaController {
//	private final PessoaService pessoaService;
//
//	@Autowired
//	public PessoaController(PessoaService pessoaService) {
//		this.pessoaService = pessoaService;
//	}
//	
////	@GetMapping("/cadastro")
////	public ModelAndView  novoFormularioCadastro() {
////		ModelAndView v = new ModelAndView("cadastroPessoa.html");
////		 v.addObject(new PessoaModel());
////	        v.setViewName("cadastroPessoa");
////	        return v;		
////	}
//
//	@GetMapping("/cadastro")
//	public String mostrarFormularioCadastro(Model model) {
//		model.addAttribute("pessoa", new PessoaModel());
//		return "cadastroPessoa"; // Nome da página Thymeleaf para o formulário de cadastro
//	}
//	
//	@GetMapping("/cadastroParente")
//	public String novoCadastroParente(Model model) {
//		model.addAttribute("pessoa", new PessoaModel());
//		return "adicionarParentesco"; // Nome da página Thymeleaf para o formulário de cadastro
//	}
//
//	@PostMapping("/adicionar")
//	public String adicionarPessoa(@ModelAttribute("pessoa") PessoaModel pessoa, Model model) {
//		PessoaModel pessoaAdicionada = pessoaService.adicionarPessoa(pessoa);
//		model.addAttribute("pessoaAdicionada", pessoaAdicionada);
//		return "pessoa_adicionada"; // Nome da página Thymeleaf para exibir os detalhes da pessoa adicionada
//	}
//
//	@PostMapping("/{idPessoa1}/relacionar/{idPessoa2}")
//	public String adicionarRelacionamento(@PathVariable Long idPessoa1, @PathVariable Long idPessoa2,
//			@RequestParam int grauDeParentesco, Model model) {
//
//		PessoaModel pessoa1 = pessoaService.buscarPessoaPorId(idPessoa1);
//		PessoaModel pessoa2 = pessoaService.buscarPessoaPorId(idPessoa2);
//		GrauDeParentesco grau = pessoaService.buscarGrauDeParentescoPorGrau(grauDeParentesco);
//
//		if (pessoa1 == null || pessoa2 == null || grau == null) {
//			return "pessoa_nao_encontrada"; // Nome da página Thymeleaf para tratar o erro de pessoa não encontrada
//		}
//
//		Relacionamento relacionamento = pessoaService.adicionarRelacionamento(pessoa1, pessoa2, grau);
//		model.addAttribute("relacionamento", relacionamento);
//		return "relacionamento_adicionado"; // Nome da página Thymeleaf para exibir os detalhes do relacionamento
//											// adicionado
//	}	
//}
//
