package br.com.devsibre.Domain.mapper;

import br.com.devsibre.Domain.Entity.DTO.CadastroDTO;
import br.com.devsibre.Domain.Entity.Formulario;

public class FormularioMapper {

    public static CadastroDTO toDTO(Formulario f) {
        if (f == null) return null;

        return new CadastroDTO(
                f.getId_c(),
                f.getNome(),
                f.getFone(),
                f.getEmail(),
                f.getData(),
                f.getCep(),
                f.getLogradouro(),
                f.getBairro(),
                f.getLocalidade(),
                f.getUf(),
                f.isMembro()
        );
    }

    public static Formulario toEntity(CadastroDTO dto) {
        if (dto == null) return null;

        Formulario f = new Formulario();
        f.setId_c(dto.getIdPessoa());
        f.setNome(dto.getPessoaNome());
        f.setFone(dto.getPessoaFone());
        f.setEmail(dto.getPessoaEmail());
        f.setData(dto.getPessoaData());
        f.setCep(dto.getPessoaCep());
        f.setLogradouro(dto.getPessoaLogradouro());
        f.setBairro(dto.getPessoaBairro());
        f.setLocalidade(dto.getPessoaLocalidade());
        f.setUf(dto.getPessoaUf());
        f.setMembro(dto.isPessoaMembro());
        return f;
    }
}