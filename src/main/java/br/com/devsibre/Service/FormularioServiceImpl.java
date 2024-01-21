package br.com.devsibre.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devsibre.Domain.Entity.DTO.SaveOrUpdateResponse;
import br.com.devsibre.Domain.Entity.Formulario;
import br.com.devsibre.Domain.Entity.GrauDeParentesco;
import br.com.devsibre.Domain.Entity.Relacionamento;
import br.com.devsibre.Domain.Repository.FormularioRepository;
import br.com.devsibre.Domain.Repository.GrauDeParentescoRepository;
import br.com.devsibre.Domain.Repository.RelacionamentoRepository;
import br.com.devsibre.Service.Inteface.FormularioService;

@Service
public class FormularioServiceImpl implements FormularioService {

	private FormularioRepository formularioRepo;
	private final RelacionamentoRepository relacionamentoRepository;
	private final GrauDeParentescoRepository grauDeParentescoRepository;

	@Autowired
	public FormularioServiceImpl(FormularioRepository formularioRepo, RelacionamentoRepository relacionamentoRepository,
			GrauDeParentescoRepository grauDeParentescoRepository) {
		this.formularioRepo = formularioRepo;
		this.relacionamentoRepository = relacionamentoRepository;
		this.grauDeParentescoRepository = grauDeParentescoRepository;
	}

//lista parentesco - busca todos os cadastros
	@Override
	public List<Formulario> listAll() {
		List<Formulario> cm = new ArrayList<>();
		formularioRepo.findAll().forEach(cm::add);
		return cm;
	}

	@Override
	public List<Formulario> findByNomeContainingIgnoreCase(String nome) {
		return formularioRepo.findByNomeContainingIgnoreCase(nome);
	}

	public List<Formulario> listarApenasMembros() {
		return formularioRepo.findByMembroTrue();
	}

	public List<Formulario> listarApenasNaoMembros() {
		return formularioRepo.findByMembroFalse();
	}

	@Override
	public boolean alterar(Formulario dto) {
		try {
			formularioRepo.save(dto);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Formulario getId(Long id) {
		return formularioRepo.findById(id).get();
	}

	@Override
	public SaveOrUpdateResponse saveOrUpdate(Formulario cm) {
		// Verifique se já existe um cadastro com o mesmo número de telefone
		Formulario existingCadastro = formularioRepo.findByFone(cm.getFone());

		if (existingCadastro == null) {
			// Não há cadastro existente com o mesmo número de telefone, então podemos
			// salvar
			formularioRepo.save(cm);
			return new SaveOrUpdateResponse(cm, true); // Retorna o DTO com isNewCadastro = true
		}

		return new SaveOrUpdateResponse(existingCadastro, false); // Retorna o DTO com isNewCadastro = false
	}

	@Override
	public void delete(Long id) {
		formularioRepo.deleteById(id);
	}

	public GrauDeParentesco buscarGrauDeParentescoPorGrau(int parentesco, Relacionamento relacionamento) {
		GrauDeParentesco descricao = new GrauDeParentesco();
		descricao.setGrau(parentesco);

		switch (parentesco) {
		case 1:
			descricao.setDescricao("Pai");
			break;
		case 2:
			descricao.setDescricao("Mãe");
			break;
		case 3:
			descricao.setDescricao("Cônjuge");
			break;
		case 4:
			descricao.setDescricao("Filho(a)");
			break;
		case 5:
			descricao.setDescricao("Avô(a)");
			break;
		case 6:
			descricao.setDescricao("Neto(a)");
			break;
		case 7:
			descricao.setDescricao("Bisavô(a)");
			break;
		case 8:
			descricao.setDescricao("Bisneto(a)");
			break;
		case 9:
			descricao.setDescricao("Irmão(a)");
			break;
		case 10:
			descricao.setDescricao("Tio(a)");
			break;
		case 11:
			descricao.setDescricao("Sobrinho(a)");
			break;
		case 12:
			descricao.setDescricao("Primo(a)");
			break;
		case 13:
			descricao.setDescricao("Cunhado(a)");
			break;
		case 14:
			descricao.setDescricao("Genro/Nora");
			break;
		case 15:
			descricao.setDescricao("Padrasto(a)");
			break;
		case 16:
			descricao.setDescricao("Enteado(a)");
			break;
		case 17:
			descricao.setDescricao("Sogro(a)");
			break;
		case 18:
			descricao.setDescricao("Companheiro(a)");
			break;
		case 19:
			descricao.setDescricao("Noivo(a)");
			break;
		default:
			descricao.setDescricao("");
			break;
		}

		if (relacionamento != null) {
			GrauDeParentesco grauDeParentesco = relacionamento.getGrauDeParentesco();

			grauDeParentesco.setDescricao(descricao.getDescricao());

			relacionamentoRepository.save(relacionamento);
		} else {
			grauDeParentescoRepository.save(descricao);
		}

		return descricao;
	}

}
