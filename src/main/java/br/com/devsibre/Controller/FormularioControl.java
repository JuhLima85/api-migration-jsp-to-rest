package br.com.devsibre.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.devsibre.Domain.Entity.Formulario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.devsibre.Domain.Entity.DTO.CadastroDTO;
import br.com.devsibre.Domain.Entity.DTO.RelacionamentoDTO;

import br.com.devsibre.Domain.Entity.GrauDeParentesco;
import br.com.devsibre.Domain.Entity.Relacionamento;
import br.com.devsibre.Service.FormularioServiceImpl;
import br.com.devsibre.Service.RelacionamentoServiceImpl;
import br.com.devsibre.UtilsReports.Formulario_Report;
import br.com.devsibre.Domain.Entity.DTO.SaveOrUpdateResponse;

import static br.com.devsibre.UtilsReports.ModelAuthentication_Report.addAuthenticationStatusToModel;

@Controller
public class FormularioControl {

	@Autowired
	private FormularioServiceImpl service;

	@Autowired
	private Formulario_Report cadreport;

	@Autowired
	private ServletContext context;

	@Autowired
	private RelacionamentoServiceImpl relacionamentoServiceImpl;

	Formulario valoresTemporarios = new Formulario();

	// Método para abrir novo formulário
	@RequestMapping(method = RequestMethod.GET, value = "/formulario")
	public ModelAndView novoCadastro(@RequestParam(value = "acao", required = false) String acao, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		ModelAndView v = new ModelAndView();
		List<Formulario> retorno = service.listAll();
		v.addObject("selectPessoa", retorno);
		v.addObject(new Formulario());

		if (acao != null && acao.equals("adicionarParentesco")) {			
			v.addObject("NomePessoa1", valoresTemporarios.getNome());
			v.setViewName("adicionarParentesco");
		} else {
			v.setViewName("formulario_Cadastro");
		}
		return v;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/salvar")
	public String salvar(Formulario cadastro, RedirectAttributes attributes, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		SaveOrUpdateResponse response = service.saveOrUpdate(cadastro);

	    // Salvo o id e nome da pessoa1 temporariamente - usado em novoCadastro e vincularParente
	    valoresTemporarios.setId_c(response.getFormulario().getId_c());
	    valoresTemporarios.setNome(response.getFormulario().getNome());

	    if (!response.isNewCadastro()) {
	        // Se isNewCadastro for falso, significa que o cadastro já existia
	        attributes.addFlashAttribute("mensagem_error", "Erro ao atualizar. Já existe um cadastro com este número de telefone.");
	        return "redirect:/formulario";
	    } else {
	        // isNewCadastro é verdadeiro, é um cadastro novo
	        attributes.addFlashAttribute("mensagem", "Novo cadastro salvo com sucesso!");
	    }

	    return "redirect:/formulario";
	}

	// Adicionar parentesco
	@RequestMapping(method = RequestMethod.POST, value = "/{idPessoa1}/relacionar/{idPessoa2}")
	public ResponseEntity<String> vincularParente(@RequestParam("selectPessoa") String idpessoa2,
	        @RequestParam("parentesco") int grauparentesco, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
	    Formulario pessoa1 = service.getId(valoresTemporarios.getId_c());
	    Formulario pessoa2 = service.getId(Long.parseLong(idpessoa2));
	    GrauDeParentesco grau = service.buscarGrauDeParentescoPorGrau(grauparentesco, null);	  
	    List<Relacionamento> relacionamentos = null;

	    if (pessoa1.getId_c() != null && pessoa2 != null && grau != null) {
	        relacionamentos = relacionamentoServiceImpl.adicionarRelacionamento(pessoa1, pessoa2, grau);   
	       
	        model.addAttribute("relacionamentos", relacionamentos);
	       
	        String htmlTabela = montarTabelaHtml(relacionamentos);
	        return new ResponseEntity<>(htmlTabela, HttpStatus.OK);
	    }

	    throw new IllegalArgumentException("Erro ao vincular pessoa.");
	}

	// Atualizar Relacionamento
	@RequestMapping(value = "/atualizarRelacionamento", method = RequestMethod.POST)
	public String atualizarRelacionamento(@ModelAttribute("relacionamento") Relacionamento relacionamento,
			@RequestParam("valorParentesco") int valorParentesco, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);

		try {
			relacionamentoServiceImpl.atualizarGrauDeParentesco(relacionamento.getId().longValue(), valorParentesco);
			model.addAttribute("mensagem", "Atualização realizada com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("mensagem_error", "Erro ao atualizar!");
		}
		return "/editarRelacionamento";
	}


	@GetMapping("/deletarRelacionamento/{id}")
	public ModelAndView deletarRelacionamento(@PathVariable long id, @RequestParam(required = false) Long idPessoa,
			HttpSession session, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		relacionamentoServiceImpl.deletarRelacionamento(id);
		List<RelacionamentoDTO> relacionamentos = relacionamentoServiceImpl.listarHistorico(idPessoa, session);

		CadastroDTO cadastro = (CadastroDTO) session.getAttribute("historicoDTO");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if (cadastro != null && cadastro.getPessoaData() != null && !cadastro.getPessoaData().isEmpty()) {
			LocalDate data = LocalDate.parse(cadastro.getPessoaData(), DateTimeFormatter.ISO_DATE);
			cadastro.setPessoaData(data.format(formatter));
		}

		ModelAndView mV = new ModelAndView("historico.html");
		mV.addObject("relacionamentos", relacionamentos);
		mV.addObject("cadastro", cadastro);
		return mV;
	}

	// Metodo para listar todos e buscar os cadastros
	@GetMapping("/listarcadastro")
	public ModelAndView lista(@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "membroFilter", defaultValue = "todos") String membroFilter, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		List<Formulario> retorno = new ArrayList<>();
		ModelAndView mV = new ModelAndView("listaCadastro.html");

		if ("membros".equals(membroFilter)) {
			retorno = service.listarApenasMembros();
		} else if ("naoMembros".equals(membroFilter)) {
			retorno = service.listarApenasNaoMembros();
		} else if ("todos".equals(membroFilter) && nome == null) {
			retorno = service.listAll();
		} else {
			retorno = service.findByNomeContainingIgnoreCase(nome);
		}

		int count = retorno.size();

		mV.addObject("cadastro", retorno);
		mV.addObject("nome", nome);
		mV.addObject("count", count);
		return mV;
	}

	/*
	 * Listar cadastro por id e seus relaciomentos
	 */
	@GetMapping("/historico/{id}")
	public ModelAndView listaCadastroERelacionamentos(@PathVariable Long id, HttpSession session, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		List<RelacionamentoDTO> relacionamentos = relacionamentoServiceImpl.listarHistorico(id, session);

		CadastroDTO cadastro = (CadastroDTO) session.getAttribute("historicoDTO");

		valoresTemporarios.setId_c(cadastro.getIdPessoa());
		valoresTemporarios.setNome(cadastro.getPessoaNome());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if (cadastro != null && cadastro.getPessoaData() != null && !cadastro.getPessoaData().isEmpty()) {
			LocalDate data = LocalDate.parse(cadastro.getPessoaData(), DateTimeFormatter.ISO_DATE);
			cadastro.setPessoaData(data.format(formatter));
		}

		ModelAndView mV = new ModelAndView("historico.html");
		mV.addObject("relacionamentos", relacionamentos);
		mV.addObject("cadastro", cadastro);
		return mV;
	}

	// Metodo para excluir dados do cadastro
	@GetMapping("/deletar/{idPessoa}")
	public String remover(@PathVariable long idPessoa, @RequestParam(required = false) Long idRelacionamento, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		if (idRelacionamento != null) {
			relacionamentoServiceImpl.deletarRelacionamentosPorPessoa1(idPessoa);
			System.out.println("ID do relacionamento: " + idRelacionamento);
		}
		service.delete(idPessoa);
		return "redirect:/listarcadastro";
	}

	// Metodo para abrir formulario de edição de cadastro
	@GetMapping("/edite/{idPessoa}")
	public String editar(@PathVariable long idPessoa, Model m, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		Formulario cad = service.getId(idPessoa);
		m.addAttribute("cad", cad);
		return "edita_Cadastro";
	}

	// Metodo para abrir formulario de edição de relacionamento
	@GetMapping("/editeRelacionamento/{id}")
	public String editarRelacionamento(@PathVariable long id,  @RequestParam(name = "idPessoa", required = false) Long idPessoa, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		Relacionamento rela = relacionamentoServiceImpl.buscarRelacionamentoPorId(id);
		if(valoresTemporarios.getNome().equals( rela.getPessoa2().getNome())) {
	        rela.getPessoa2().setNome(rela.getPessoa1().getNome());
		}

		List<Formulario> retorno = service.listAll();
		model.addAttribute("selectPessoa", retorno);
		model.addAttribute("relacionamento", rela);
		model.addAttribute("NomePessoa1", valoresTemporarios.getNome());
		return "editarRelacionamento";
	}

	@RequestMapping(value = "/editsave", method = RequestMethod.POST)
	public String editsave(@ModelAttribute("cad") Formulario emp, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		try {
			boolean idd = service.alterar(emp);
			model.addAttribute("mensagem", "Atualização realizada com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("mensagem_error", "Erro ao atualizar!");
			model.addAttribute("cad", emp);
		}
		model.addAttribute("cad", emp);
		return "/edita_Cadastro";
	}

	@GetMapping(value = "/pdf")
	public void createPdf(HttpServletRequest request, HttpServletResponse response, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		List<Formulario> cad = service.listAll();
		boolean isFlag = cadreport.creatPdf(cad, context, request, response);
		if (isFlag) {
			String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "cad" + ".pdf");
			filedownload(fullPath, response, "cad.pdf");
		}
	}

	@GetMapping(value = "/Exls")
	public void createExcel(HttpServletRequest request, HttpServletResponse response, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		List<Formulario> cad = service.listAll();
		boolean isFlag = cadreport.createExcel(cad, context, request, response);
//        if (isFlag) {
//            String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "cad" + ".pdf");
//            filedownload(fullPath, response, "cad.pdf");
//        }
	}

	private String montarTabelaHtml(List<Relacionamento> relacionamentos) {
		StringBuilder html = new StringBuilder();
		for (Relacionamento relacionamento : relacionamentos) {
			html.append("<tr>");
			html.append("<td>").append(relacionamento.getPessoa2().getNome()).append("</td>");
			html.append("<td>").append(relacionamento.getGrauDeParentesco().getDescricao()).append("</td>");
			html.append("</tr>");
		}
		if (relacionamentos.isEmpty()) {
			html.append("<tr><td colspan=\"2\">Nenhum registro encontrado</td></tr>");
		}
		return html.toString();
	}

	private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
		File file = new File(fullPath);

		final int BUFFER_SIZE = 4096;
		if (file.exists()) {
			try {
				FileInputStream inputStream = new FileInputStream(file);
				String mimeType = context.getMimeType(fullPath);
				response.setContentType(mimeType);
				response.setHeader("content-disposition", "attachment; filename=" + fileName);
				OutputStream outputStream = response.getOutputStream();

				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				inputStream.close();
				outputStream.close();
				file.delete();
			} catch (Exception e) {
			}
		}
	}
}
