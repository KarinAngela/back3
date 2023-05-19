package org.acme.dto;

import jakarta.validation.constraints.NotBlank;
import org.acme.entities.Produto;

public class ProdutoDTO {
    @NotBlank
    public String nome;

    @NotBlank
    public String descricao;

    @NotBlank
    public String cultura;

    @NotBlank
    public String areaSuportada;

    public ProdutoDTO() {}

    public ProdutoDTO(Produto produto) {
        this.nome = produto.nome;
        this.descricao = produto.descricao;
        this.cultura = produto.cultura;
        this.areaSuportada = produto.areaSuportada;
    }

    public ProdutoDTO(String nome, String descricao, String cultura, String areaSuportada) {
        this.nome = nome;
        this.descricao = descricao;
        this.cultura = cultura;
        this.areaSuportada = areaSuportada;
    }
}
