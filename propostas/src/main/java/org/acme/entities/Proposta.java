package org.acme.entities;


import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Cacheable
@Table(name = "propostas")
public class Proposta extends PanacheEntity {
    public String nome;
    public String email;
    public String telefone;
    public String produtoInteresse;

    public Proposta() {}

    public Proposta(String nome, String email, String telefone, String produtoInteresse) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.produtoInteresse = produtoInteresse;
    }
}
