package br.com.devsibre.Domain.Entity.Contatos;

import javax.persistence.*;

@Entity
@Table(name ="tb_contato")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String nome;
    private String email;
    private String assuntoMsg;
    private String conteudoMsg;

    public Contato() {
    }
    public Contato(Long id, String nome, String email, String assuntoMsg, String conteudoMsg) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.assuntoMsg = assuntoMsg;
        this.conteudoMsg = conteudoMsg;
    }

    public Long getId() {
        return id;
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

