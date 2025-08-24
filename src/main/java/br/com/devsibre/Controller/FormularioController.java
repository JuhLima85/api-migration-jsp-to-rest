package br.com.devsibre.Controller;

import java.util.List;

import br.com.devsibre.Domain.Entity.DTO.CadastroDTO;
import br.com.devsibre.Domain.Entity.Formulario;
import br.com.devsibre.Domain.mapper.FormularioMapper;
import br.com.devsibre.Enuns.StatusEnum;
import br.com.devsibre.Service.Inteface.RelacionamentoService;
import br.com.devsibre.error.BusinessException;
import br.com.devsibre.error.NotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import br.com.devsibre.Service.FormularioServiceImpl;
import br.com.devsibre.UtilsReports.Formulario_Report;

@RestController
@RequestMapping("/formulario")
//@RequiredArgsConstructor
@Validated
public class FormularioController {
	@Autowired
	private FormularioServiceImpl service;
	@Autowired
	private RelacionamentoService relacionamentoService;

	@PostMapping
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
	public Formulario salvar(@Valid @RequestBody Formulario formulario, HttpSession session) {
		var resp = service.saveOrUpdate(formulario);

		// Salvo temporariamente na sessão o id e nome da pessoa1 temporariamente - usado em novoCadastro e vincularParente
		Formulario tmp = new Formulario();
		tmp.setId_c(resp.getFormulario().getId_c());
		tmp.setNome(resp.getFormulario().getNome());
		session.setAttribute("valoresTemporarios", tmp);

		if (!resp.isNewCadastro()) {
			throw BusinessException.dadoDuplicado();
		}
		return resp.getFormulario();
	}

	@GetMapping
	public List<CadastroDTO> listar(@RequestParam(required = false) String nome,
									@RequestParam(defaultValue = "todos") String membroFilter) {
		var filtro = StatusEnum.from(membroFilter);
		List<Formulario> lista = switch (filtro) {
			case MEMBRO      -> service.listarApenasMembros();
			case NAO_MEMBRO  -> service.listarApenasNaoMembros();
			case TODOS       -> (nome == null || nome.isBlank())
					? service.listAll()
					: service.findByNomeContainingIgnoreCase(nome);
		};
		return lista.stream().map(FormularioMapper::toDTO).toList();
	}

	// Metodo para excluir dados do cadastro
	@DeleteMapping("/{id}")
	@ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id,
						@RequestParam(required = false) Long idRelacionamento) {
		if (idRelacionamento != null) {
			relacionamentoService.deletarRelacionamentosPorPessoa1(id);
		}
		service.delete(id);
	}

	@GetMapping("/{id}")
	public CadastroDTO obter(@PathVariable Long id) {
		var f = service.getId(id);
		if (f == null) throw NotFoundException.dadoNaoEncontrado();
		return FormularioMapper.toDTO(f);
	}

	@PutMapping("/{id}")
	public CadastroDTO atualizar(@PathVariable Long id,
								 @Valid @RequestBody Formulario formulario) {
		var existente = service.getId(id);
		if (existente == null) {
			throw NotFoundException.dadoNaoEncontrado();
		}

		// garante que o objeto recebido vai atualizar o registro certo
		formulario.setId_c(id);

		var atualizado = service.alterar(formulario);
		return FormularioMapper.toDTO(atualizado);
	}
}
