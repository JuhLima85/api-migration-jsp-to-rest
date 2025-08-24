package br.com.devsibre.Controller;

import br.com.devsibre.Domain.Entity.DTO.CadastroDTO;
import br.com.devsibre.Domain.Entity.DTO.HistoricoDTO;
import br.com.devsibre.Domain.Entity.DTO.RelacionamentoDTO;
import br.com.devsibre.Domain.Entity.DTO.VincularRelacionamentoDTO;
import br.com.devsibre.Domain.Entity.Formulario;
import br.com.devsibre.Domain.Entity.Relacionamento;
import br.com.devsibre.Service.Inteface.FormularioService;
import br.com.devsibre.Service.Inteface.RelacionamentoService;

import br.com.devsibre.error.NotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/relacionamento")
public class RelacionamentosController {

    @Autowired
    private FormularioService service;
    @Autowired
    private RelacionamentoService relacionamentoService;

    @GetMapping("/historico/{id}")
    public ResponseEntity<HistoricoDTO> listarCadastroERelacionamentos(
            @PathVariable Long id,
            HttpSession session) {

        // carrega lista
        List<RelacionamentoDTO> relacionamentos =
                relacionamentoService.listarHistorico(id, session);

        // obtém o cadastro da sessão (mesma origem do seu código atual)
        CadastroDTO cadastro = (CadastroDTO) session.getAttribute("historicoDTO");

        Formulario valoresTemporarios = (Formulario) session.getAttribute("valoresTemporarios"); // <-- recupera

        if (cadastro != null) {
            // garante que não seja null
            if (valoresTemporarios == null) {
                valoresTemporarios = new Formulario();
            }  // atualiza valores temporários
            valoresTemporarios.setId_c(cadastro.getIdPessoa());
            valoresTemporarios.setNome(cadastro.getPessoaNome());
            session.setAttribute("valoresTemporarios", valoresTemporarios);

            // formata data ISO -> dd/MM/yyyy (se vier como String ISO: 2025-08-20)
            if (cadastro.getPessoaData() != null && !cadastro.getPessoaData().isEmpty()) {
                LocalDate data = LocalDate.parse(cadastro.getPessoaData(), DateTimeFormatter.ISO_DATE);
                DateTimeFormatter br = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                cadastro.setPessoaData(data.format(br));
            }
        }

        // 404 se nada encontrado
        if ((relacionamentos == null || relacionamentos.isEmpty()) && cadastro == null) {
            return ResponseEntity.notFound().build();
        }

        HistoricoDTO body = new HistoricoDTO(cadastro, relacionamentos);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    public List<RelacionamentoDTO> vincular(@Valid @RequestBody VincularRelacionamentoDTO req, HttpSession session) {
        Formulario valoresTemporarios =
                (Formulario) session.getAttribute("valoresTemporarios"); // <-- recupera
        var p1 = service.getId(valoresTemporarios.getId_c());
        var p2 = service.getId(req.getPessoa2Id());
        var grau = service.buscarGrauDeParentescoPorGrau(req.getGrauParentesco(), null);

        if (p1 == null || p2 == null || grau == null) {
            throw new NotFoundException("Dados inválidos para relacionamento");
        }

        var relacionados = relacionamentoService.adicionarRelacionamento(p1, p2, grau);

        return relacionados.stream()
                .map(r -> toDTO(r, p1)) // passamos p1 para decidir quem é o "familiar"
                .toList();
    }

    // Decide "nomeFamiliar" como sendo o nome da outra ponta do relacionamento.
    private RelacionamentoDTO toDTO(Relacionamento r, Formulario referencia) {
        var outra =
                (r.getPessoa1() != null && r.getPessoa1().getId_c().equals(referencia.getId_c()))
                        ? r.getPessoa2()
                        : r.getPessoa1();

        var nomeFamiliar = (outra != null ? outra.getNome() : null);
        var grauDesc = (r.getGrauDeParentesco() != null ? r.getGrauDeParentesco().getDescricao() : null);

        var dto = new RelacionamentoDTO();
        dto.setIdRelacionamento(r.getIdRelacionamento());
        dto.setIdPessoa1(r.getPessoa1() != null ? r.getPessoa1().getId_c() : null);
        dto.setIdPessoa2(r.getPessoa2() != null ? r.getPessoa2().getId_c() : null);
        dto.setNomeFamiliar(nomeFamiliar);
        dto.setGrauParentesco(grauDesc);
        return dto;
    }

    @PutMapping("/{relacionamentoId}")
    public String atualizar(@PathVariable Long relacionamentoId, @Valid @RequestBody int novoGrauParentesco) {
        if (relacionamentoId == null ) {
            throw new NotFoundException("Dados inválidos para relacionamento");
        }
        relacionamentoService.atualizarGrauDeParentesco(relacionamentoId, novoGrauParentesco);
        return "Atualização realizada com sucesso!";
    }

    @DeleteMapping("/{relacionamentoId}")
    public ResponseEntity<HistoricoDTO> deletar(@PathVariable Long relacionamentoId,
                                                @RequestParam(required = false) Long idPessoa,
                                                HttpSession session) {
 System.out.println(" teste");
        // 1) se idPessoa não vier, tenta inferir (sessão ou pelo relacionamento)
        if (idPessoa == null) {
            Formulario tmp = (Formulario) session.getAttribute("valoresTemporarios");
            if (tmp != null) {
                idPessoa = tmp.getId_c();
            }
        }

        // valida
        if (idPessoa == null) {
            return ResponseEntity.badRequest().build();
        }

        // 2) deleta o relacionamento
        relacionamentoService.deletarRelacionamento(relacionamentoId);

        // 3) recarrega o histórico (preenche também historicoDTO na sessão, como hoje)
        List<RelacionamentoDTO> relacionamentos = relacionamentoService.listarHistorico(idPessoa, session);

        // 4) pega cadastro que o listarHistorico já deixou na sessão
        CadastroDTO cadastro = (CadastroDTO) session.getAttribute("historicoDTO");

        // 404 se ficou sem nada
        if ((relacionamentos == null || relacionamentos.isEmpty()) && cadastro == null) {
            return ResponseEntity.notFound().build();
        }

        // 5) retorna o JSON atualizado
        HistoricoDTO body = new HistoricoDTO(cadastro, relacionamentos);
        return ResponseEntity.ok(body);
    }
}
