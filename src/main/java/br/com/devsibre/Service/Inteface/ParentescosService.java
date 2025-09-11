package br.com.devsibre.Service.Inteface;

import br.com.devsibre.Domain.Entity.DTO.RelacionamentoDTO;
import br.com.devsibre.Domain.Entity.Parentescos;

import java.util.List;
public interface ParentescosService {
    List<RelacionamentoDTO> buscarRelacionamentos(Long id);
}
