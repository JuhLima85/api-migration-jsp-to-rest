package br.com.devsibre.Domain.Entity.Contatos;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name ="tb_contato")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "data_cadastro", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDoContao;
    private String nome;
    private String celular;
    private String email;
    private String assuntoMsg;
    private String conteudoMsg;

    @PrePersist
    public void prePersist() {
        setDataDoContao(LocalDate.now());
    }
    public Contato() {
    }
    public Contato(Long id, LocalDate dataDoContao, String nome, String email, String assuntoMsg, String conteudoMsg) {
        this.id = id;
        this.dataDoContao = dataDoContao;
        this.nome = nome;
        this.email = email;
        this.assuntoMsg = assuntoMsg;
        this.conteudoMsg = conteudoMsg;
    }
    public Contato(Long id, LocalDate dataDoContao, String nome, String celular, String email, String assuntoMsg, String conteudoMsg) {
        this.id = id;
        this.dataDoContao = dataDoContao;
        this.nome = nome;
        this.celular = celular;
        this.email = email;
        this.assuntoMsg = assuntoMsg;
        this.conteudoMsg = conteudoMsg;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDataDoContao() {
        return dataDoContao;
    }

    public void setDataDoContao(LocalDate dataDoContao) {
        this.dataDoContao = dataDoContao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAssuntoMsg() {
        return assuntoMsg;
    }

    public void setAssuntoMsg(String assuntoMsg) {
        this.assuntoMsg = assuntoMsg;
    }

    public String getConteudoMsg() {
        return conteudoMsg;
    }

    public void setConteudoMsg(String conteudoMsg) {
        this.conteudoMsg = conteudoMsg;
    }
}

