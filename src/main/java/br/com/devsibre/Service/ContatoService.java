package br.com.devsibre.Service;

import br.com.devsibre.Domain.Entity.Contatos.Contato;
import br.com.devsibre.Domain.Repository.ContatoRepository;
import org.springframework.dao.DataAccessException;
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

    public Contato save(Contato contato) {
        try {
            return contatoRepository.save(contato);
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao salvar o contato: " + e.getMessage(), e);
        }
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
