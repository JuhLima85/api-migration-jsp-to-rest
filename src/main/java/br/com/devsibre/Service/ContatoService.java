package br.com.devsibre.Service;

import br.com.devsibre.Domain.Entity.Contatos.Contato;
import br.com.devsibre.Domain.Repository.ContatoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContatoService {

    private ContatoRepository contatoRepository;

    public ContatoService (ContatoRepository contatoRepository){
        this.contatoRepository = contatoRepository;
    }

    public List<Contato> ListAll(){
        List<Contato> ct = new ArrayList<>();
        contatoRepository.findAll().forEach(ct::add);
        return ct;
    }

    public Contato sava(Contato contato) {
        Contato ct = this.contatoRepository.save(contato);
        if (ct == null){
            throw new RuntimeException("nulo");
        }
        return ct;
    }

    public Contato getById(Long id){
      return contatoRepository.findById(id).orElse(null);
    }

    public void delete(Long id){
        Optional<Contato> ct = this.contatoRepository.findById(id);
        if (!ct.isPresent()) {
            throw new RuntimeException("O id não foi encontrado");
        }
      contatoRepository.deleteById(id);

    }
}
