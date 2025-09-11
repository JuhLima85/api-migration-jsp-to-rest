package br.com.devsibre.Service;

import br.com.devsibre.Domain.Entity.DTO.RelacionamentoDTO;
import br.com.devsibre.Domain.Repository.ParentescosRepository;
import br.com.devsibre.Service.Inteface.ParentescosService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentescosServiceImpl implements ParentescosService {

    private final ParentescosRepository parentescosRepository;

    public ParentescosServiceImpl(ParentescosRepository parentescosRepository) {
        this.parentescosRepository = parentescosRepository;
    }

    public List<RelacionamentoDTO> buscarRelacionamentos(Long id) {
        return parentescosRepository.buscarRelacionamentos(id);
    }
}
