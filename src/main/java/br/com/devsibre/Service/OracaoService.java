package br.com.devsibre.Service;

import br.com.devsibre.Domain.Entity.Contatos.Oracao;
import br.com.devsibre.Domain.Repository.OracaoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OracaoService {

    private OracaoRepository oracaoRepository;

    public OracaoService(OracaoRepository oracaoRepository){
        this.oracaoRepository = oracaoRepository;
    }

    public List<Oracao> ListAll(){
        List<Oracao> oc = new ArrayList<>();
        oracaoRepository.findAll().forEach(oc::add);
        return oc;
    }

    public Oracao save(Oracao oracao){
       Oracao ct = this.oracaoRepository.save(oracao);
        if (ct == null){
            throw new RuntimeException(" nulo");
        }
        return ct;
    }
    public Oracao getById(Long id){
        return oracaoRepository.findById(id).orElse(null);
    }
    public void delete(Long id){
        Optional<Oracao> ct = this.oracaoRepository.findById(id);
        if (!ct.isPresent()) {
            throw new RuntimeException("O id não foi encontrado");
        }
        oracaoRepository.deleteById(id);

    }
}
