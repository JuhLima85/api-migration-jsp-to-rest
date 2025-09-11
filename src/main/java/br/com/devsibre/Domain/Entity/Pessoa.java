package br.com.devsibre.Domain.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pessoa", uniqueConstraints = {@UniqueConstraint(columnNames = "fone", name = "unique_fone_constraint")})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 200, nullable = false)
    private String nome;
    @Column(length = 20, unique = true)
    private String fone;
    @Column(length = 40)
    private String email;
    private String data;
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private boolean membro;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Parentescos> parentescos = new ArrayList<>();
}
