package org.acme.entities;


import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Cacheable
@Table(name = "produtos")
public class Produto extends PanacheEntity {
    public String nome;
    public String descricao;
    public String cultura;
    public String areaSuportada;

    public Produto() { }

    public Produto(String nome, String descricao, String cultura, String areaSuportada) {
        this.nome = nome;
        this.descricao = descricao;
        this.cultura = cultura;
        this.areaSuportada = areaSuportada;
    }
}