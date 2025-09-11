package br.com.devsibre.Service;

import br.com.devsibre.Domain.Entity.Parentescos;
import br.com.devsibre.Domain.Entity.Pessoa;
import br.com.devsibre.Domain.Repository.PessoaRepository;
import br.com.devsibre.Service.Inteface.PessoaService;
import br.com.devsibre.error.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    @Override
    public List<Pessoa> listarTodas() {
        return pessoaRepository.findAll();
    }

    @Override
    public Pessoa buscarPorId(Long id) {
        return pessoaRepository.findById(id).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    @Override
    public Pessoa criarVinculo(Long id1, Long id2, String tipo) {
        Pessoa p1 = buscarPorId(id1);
        Pessoa p2 = buscarPorId(id2);

        boolean jaExiste = p1.getParentescos().stream()
                .anyMatch(par -> par.getPessoaRelacionada().getId().equals(p2.getId()));

        if (jaExiste) {
            throw BusinessException.relacionamentoDuplicado();
        }

        Parentescos parentesco = new Parentescos(tipo, p1, p2);
        p1.getParentescos().add(parentesco);

        return pessoaRepository.save(p1);
    }
}
