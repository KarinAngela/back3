package org.acme.controllers;


import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.PropostaDTO;
import org.acme.entities.Proposta;
import org.jboss.logging.Logger;

import java.util.List;

import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/propostas")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("administrador")
public class PropostaController {
    public static final Logger LOGGER = Logger.getLogger(PropostaController.class);

    @GET
    public Uni<List<PropostaDTO>> listAllDeals() {
        return Proposta
                .listAll()
                .map(entityList -> entityList
                        .stream()
                        .map(entity -> new PropostaDTO((Proposta) entity))
                        .toList()
                );
    }

    @GET
    @Path("{id}")
    public Uni<Response> getDealById(Long id) {
        return Panache
                .withTransaction(() -> Proposta.<Proposta>findById(id))
                .onItem().ifNotNull().transform(entity -> Response.ok(new PropostaDTO(entity)).build())
                .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND).build());
    }

    @POST
    public Uni<Response> insertDeal(@Valid PropostaDTO propostaDTO) {
        Proposta proposta = new Proposta(
                propostaDTO.nome,
                propostaDTO.email,
                propostaDTO.telefone,
                propostaDTO.produtoInteresse
        );

        return Panache
                .withTransaction(proposta::persist)
                .replaceWith(Response.ok(new PropostaDTO(proposta)).status(CREATED).build());
    }

    @PUT
    @Path("{id}")
    public Uni<Response> updateDeal(Long id, @Valid PropostaDTO propostaDTO) {
        return Panache
                .withTransaction(() -> Proposta.<Proposta>findById(id)
                        .onItem().ifNotNull().invoke(entity -> {
                            entity.nome = propostaDTO.nome;
                            entity.email = propostaDTO.email;
                            entity.telefone = propostaDTO.telefone;
                            entity.produtoInteresse = propostaDTO.produtoInteresse;
                        }))
                .onItem().ifNotNull().transform(entity -> Response.ok(new PropostaDTO(entity)).build())
                .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND).build());
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> deleteDeal(Long id) {
        return Panache.withTransaction(() -> Proposta.deleteById(id))
                .map(deleted -> deleted
                        ? Response.ok().status(NO_CONTENT).build()
                        : Response.ok().status(NOT_FOUND).build());
    }
}