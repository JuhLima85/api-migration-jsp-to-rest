//package br.com.devsibre.ServiceImpl;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import br.com.devsibre.Model.GrauDeParentesco;
//import br.com.devsibre.Model.PessoaModel;
//import br.com.devsibre.Model.Relacionamento;
//import br.com.devsibre.Repository.GrauDeParentescoRepository;
//import br.com.devsibre.Repository.PessoaRepository;
//import br.com.devsibre.Repository.RelacionamentoRepository;
//import br.com.devsibre.Service.PessoaService;
//
//@Service
//public class PessoaServiceImpl implements PessoaService {
//
//	private final PessoaRepository pessoaRepository;
//	private final RelacionamentoRepository relacionamentoRepository;
//	private final GrauDeParentescoRepository grauDeParentescoRepository;
//
//	@Autowired
//	public PessoaServiceImpl(PessoaRepository pessoaRepository, RelacionamentoRepository relacionamentoRepository,
//			GrauDeParentescoRepository grauDeParentescoRepository) {
//		this.pessoaRepository = pessoaRepository;
//		this.relacionamentoRepository = relacionamentoRepository;
//		this.grauDeParentescoRepository = grauDeParentescoRepository;
//	}
//
//	public PessoaModel adicionarPessoa(PessoaModel pessoa) {
//		return pessoaRepository.save(pessoa);
//	}
//
//	public Relacionamento adicionarRelacionamento(PessoaModel pessoa1, PessoaModel pessoa2,
//			GrauDeParentesco grauDeParentesco) {
//		Relacionamento relacionamento = new Relacionamento();
////		relacionamento.setPessoa1(pessoa1);
////		relacionamento.setPessoa2(pessoa2);
//		relacionamento.setGrauDeParentesco(grauDeParentesco);
//
//		return relacionamentoRepository.save(relacionamento);
//	}
//
//	public PessoaModel buscarPessoaPorId(Long id) {
//		Optional<PessoaModel> optionalPessoa = pessoaRepository.findById(id);
//		return optionalPessoa.orElse(null);
//	}
//
//	public GrauDeParentesco buscarGrauDeParentescoPorGrau(int grau) {
//		return grauDeParentescoRepository.findByGrau(grau);
//	}
//
//}
