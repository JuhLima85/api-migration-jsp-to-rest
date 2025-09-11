package br.com.devsibre.Domain.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parentescos", uniqueConstraints = {@UniqueConstraint(columnNames = "fone", name = "unique_fone_constraint")})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parentescos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    @JsonBackReference
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "pessoa_relacionada_id")
    private Pessoa pessoaRelacionada;

    public Parentescos(String tipo, Pessoa pessoa, Pessoa pessoaRelacionada) {
        this.tipo = tipo;
        this.pessoa = pessoa;
        this.pessoaRelacionada = pessoaRelacionada;
    }
}
