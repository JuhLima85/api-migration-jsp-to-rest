package br.com.devsibre.Service.Inteface;

import java.util.List;

import br.com.devsibre.Domain.Entity.DTO.RelacionamentoDTO;
import br.com.devsibre.Domain.Entity.Formulario;
import br.com.devsibre.Domain.Entity.GrauDeParentesco;
import br.com.devsibre.Domain.Entity.Relacionamento;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

public interface RelacionamentoService {

	List<Relacionamento> adicionarRelacionamento(Formulario pessoa1, Formulario pessoa2, GrauDeParentesco grauDeParentesco);
	
	void deletarRelacionamento(Long id);

	List<RelacionamentoDTO> listarFamiliarEGrauParentesco(Long idPessoa);

	void deletarRelacionamentosPorPessoa1(Long pessoaId);

	void atualizarGrauDeParentesco(Long relacionamentoId, int novoGrauParentesco) throws EntityNotFoundException;

	List<RelacionamentoDTO> listarHistorico(Long idPessoa, HttpSession session);
}
