package br.com.devsibre.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import br.com.devsibre.Domain.Entity.DTO.RelacionamentoDTO;
import br.com.devsibre.Domain.Entity.Formulario;
import br.com.devsibre.Domain.Entity.GrauDeParentesco;
import br.com.devsibre.Domain.Entity.Relacionamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.devsibre.Domain.Entity.DTO.CadastroDTO;

import br.com.devsibre.Domain.Repository.FormularioRepository;
import br.com.devsibre.Domain.Repository.GrauDeParentescoRepository;
import br.com.devsibre.Domain.Repository.RelacionamentoRepository;
import br.com.devsibre.Service.Inteface.RelacionamentoService;

@Service
public class RelacionamentoServiceImpl implements RelacionamentoService {

	private RelacionamentoRepository relacionamentoRepository;
	private FormularioRepository formularioRepo;
	private FormularioServiceImpl formularioServiceImpl;
	private GrauDeParentescoRepository grauDeParentescoRepository;

	@Autowired
	public RelacionamentoServiceImpl(RelacionamentoRepository relacionamentoRepository,
			FormularioRepository formularioRepo, FormularioServiceImpl formularioServiceImpl, GrauDeParentescoRepository grauDeParentescoRepository) {
		super();
		this.relacionamentoRepository = relacionamentoRepository;
		this.formularioRepo = formularioRepo;
		this.formularioServiceImpl = formularioServiceImpl;
		this.grauDeParentescoRepository = grauDeParentescoRepository;
	}

	public List<Relacionamento> adicionarRelacionamento(Formulario pessoa1, Formulario pessoa2,
			GrauDeParentesco grauDeParentesco) {
		
		List<Relacionamento> relacionamentoExistente = buscarRelacionamentosPorIdsPessoa(pessoa1, pessoa2);

		if(!relacionamentoExistente.isEmpty()){
			 return relacionamentoExistente;
		}
		Relacionamento relacionamento = new Relacionamento();
		relacionamento.setPessoa1(pessoa1);
		relacionamento.setPessoa2(pessoa2);
		relacionamento.setGrauDeParentesco(grauDeParentesco);

		relacionamentoRepository.save(relacionamento);
		 
	    return Collections.singletonList(relacionamento);
	}
	
	/**
	 * Atualiza o campo 'grau' na entidade GrauDeParentesco associada a um Relacionamento.
	 *
	 * @param relacionamentoId   O ID do Relacionamento que terá o GrauDeParentesco atualizado.
	 * @param novoGrauParentesco O novo valor para o campo 'grau'.
	 * @throws EntityNotFoundException Se o Relacionamento com o ID especificado não for encontrado.
	 */
	@Transactional
	public void atualizarGrauDeParentesco(Long relacionamentoId, int novoGrauParentesco) throws EntityNotFoundException {	   
	    Optional<Relacionamento> relacionamentoOptional = relacionamentoRepository.findById(relacionamentoId);
	    
	    if (relacionamentoOptional.isPresent()) {
	        Relacionamento relacionamento = relacionamentoOptional.get();
	       
	        GrauDeParentesco grauDeParentesco = relacionamento.getGrauDeParentesco();
	       
	        grauDeParentesco.setGrau(novoGrauParentesco);	        
	        
	        formularioServiceImpl.buscarGrauDeParentescoPorGrau(novoGrauParentesco, relacionamento);
	      
	        relacionamentoRepository.save(relacionamento);
	    } else {
	        throw new EntityNotFoundException("Relacionamento não encontrado com o ID: " + relacionamentoId);
	    }
	}

	public void deletarRelacionamento(Long id) {
		relacionamentoRepository.deleteById(id);
		grauDeParentescoRepository.deleteById(id);
	}
	
	/*
	 * método personalizado para deletar todos os relacionamentos de uma pessoa específica (pessoa1)
	 * */
	@Transactional
	 public void deletarRelacionamentosPorPessoa1(Long pessoaId) {
	        relacionamentoRepository.deleteByPessoa1Id(pessoaId);
	    }
		
	public Relacionamento buscarRelacionamentoPorId(Long relacionamentoId) {
		Relacionamento relacionamento = relacionamentoRepository.findRelacionamentoById(relacionamentoId);
		return relacionamento;
    }

	private List<Relacionamento> buscarRelacionamentosPorIdsPessoa(Formulario pessoa1, Formulario pessoa2) {
	    return relacionamentoRepository.findRelacionamentos(pessoa1, pessoa2);
	}

	/*
	 * Lista todos os relacionamentos onde uma pessoa específica (pessoa1) está envolvida
	 */
	public List<RelacionamentoDTO> listarFamiliarEGrauParentesco(Long idPessoa) {
		List<Relacionamento> rela = relacionamentoRepository.findAllByPessoa1Id(idPessoa);		
		List<RelacionamentoDTO> relacionamentos = new ArrayList<>();		
		
		for (Relacionamento relacionamento : rela) {
			String nomeFamiliar = relacionamento.getPessoa2().getNome();
			String grauParentesco = relacionamento.getGrauDeParentesco().getDescricao();
			Long idRelacionamento = relacionamento.getId();
			
			RelacionamentoDTO dto = new RelacionamentoDTO();
			dto.setId(idRelacionamento);
			dto.setNomeFamiliar(nomeFamiliar);
			dto.setGrauParentesco(grauParentesco);

			relacionamentos.add(dto);
		}
		return relacionamentos;
	}
	
	/*
	 * Listar cadastro por id e seus relaciomentos
	 */
	public List<RelacionamentoDTO> listarHistorico(Long idPessoa, HttpSession session) {		
		CadastroDTO cadastroPessoa = formularioRepo.findCadastroDTOById(idPessoa);	
		session.setAttribute("historicoDTO", cadastroPessoa);
		
		List<RelacionamentoDTO> familiarDTO = new ArrayList<>();
		List<Relacionamento> relacionamentosPessoa1 = relacionamentoRepository.findAllByPessoa1Id(idPessoa);
		List<Relacionamento> relacionamentosPessoa2 = relacionamentoRepository.findAllByPessoa2Id(idPessoa);
		
	    List<Relacionamento> relacionamentos = new ArrayList<>();
	    relacionamentos.addAll(relacionamentosPessoa1);
	    relacionamentos.addAll(relacionamentosPessoa2);    
	  
	    for (Relacionamento relacionamento : relacionamentos) {
	        RelacionamentoDTO relaTDO = new RelacionamentoDTO();
	        relaTDO.setId(relacionamento.getId());
	      
	        if (idPessoa.equals(relacionamento.getPessoa1().getId_c())) {
	            relaTDO.setNomeFamiliar(relacionamento.getPessoa2().getNome());
	        } else {
	            relaTDO.setNomeFamiliar(relacionamento.getPessoa1().getNome());
	        }

	        relaTDO.setGrauParentesco(relacionamento.getGrauDeParentesco().getDescricao());

	        familiarDTO.add(relaTDO);
	    }

		return familiarDTO;
	}
}
