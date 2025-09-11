package br.com.devsibre.Domain.Entity;

//import javax.persistence.*;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_oracao")
public class Oracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String msg;

    public Oracao() {
    }

    public Oracao(Long id, String nome, String email, String msg) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.msg = msg;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
