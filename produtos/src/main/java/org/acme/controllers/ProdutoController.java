package org.acme.controllers;


import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.ProdutoDTO;
import org.acme.entities.Produto;
import org.jboss.logging.Logger;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.*;


@Path("/produtos")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoController {

    // O quarkus automaticamente faz logs das requisições,
    // não sendo necessário invocar uma chamada pra esse logger
    public static final Logger LOGGER = Logger.getLogger(ProdutoController.class);

    @GET
    @RolesAllowed("cliente")
    public Uni<List<ProdutoDTO>> listAllProducts() {
        return Produto
                .listAll()
                .map(entityList -> entityList
                        .stream()
                        .map(entity -> new ProdutoDTO((Produto) entity))
                        .toList()
                );
    }

    @GET
    @Path("{id}")
    @RolesAllowed("cliente")
    public Uni<Response> getProductById(Long id) {
        return Panache
                .withTransaction(() -> Produto.<Produto>findById(id))
                .onItem().ifNotNull().transform(entity -> Response.ok(new ProdutoDTO(entity)).build())
                .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND).build());
    }

    @POST
    @RolesAllowed("administrador")
    public Uni<Response> insertProduct(@Valid ProdutoDTO produtoDTO) {
        Produto produto = new Produto(
                produtoDTO.nome,
                produtoDTO.descricao,
                produtoDTO.cultura,
                produtoDTO.areaSuportada
        );

        return Panache
                .withTransaction(produto::persist)
                .replaceWith(Response.ok(new ProdutoDTO(produto)).status(CREATED).build());
    }

    @PUT
    @Path("{id}")
    @RolesAllowed("administrador")
    public Uni<Response> updateProduct(Long id, @Valid ProdutoDTO produtoDTO) {
        return Panache
                .withTransaction(() -> Produto.<Produto>findById(id)
                .onItem().ifNotNull().invoke(entity -> {
                    entity.nome = produtoDTO.nome;
                    entity.descricao = produtoDTO.descricao;
                    entity.cultura = produtoDTO.cultura;
                    entity.areaSuportada = produtoDTO.areaSuportada;
                }))
                .onItem().ifNotNull().transform(entity -> Response.ok(new ProdutoDTO(entity)).build())
                .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND).build());
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("administrador")
    public Uni<Response> deleteProduct(Long id) {
        return Panache.withTransaction(() -> Produto.deleteById(id))
                .map(deleted -> deleted
                        ? Response.ok().status(NO_CONTENT).build()
                        : Response.ok().status(NOT_FOUND).build());
    }
}
