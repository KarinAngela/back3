package org.acme.dto;

import jakarta.validation.constraints.NotBlank;
import org.acme.entities.Proposta;

public class PropostaDTO {
    @NotBlank
    public String nome;

    @NotBlank
    public String email;

    @NotBlank
    public String telefone;

    @NotBlank
    public String produtoInteresse;

    public PropostaDTO() {}

    public PropostaDTO(Proposta proposta) {
        this.nome = proposta.nome;
        this.email = proposta.email;
        this.telefone = proposta.telefone;
        this.produtoInteresse = proposta.produtoInteresse;
    }

    public PropostaDTO(String nome, String email, String telefone, String produtoInteresse) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.produtoInteresse = produtoInteresse;
    }
}