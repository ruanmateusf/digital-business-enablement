package br.com.fiap.resource;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import br.com.fiap.jpa.dao.JogadorDAO;
import br.com.fiap.jpa.dao.impl.JogadorDAOImpl;
import br.com.fiap.jpa.entity.Jogador;
import br.com.fiap.jpa.entity.Selecao;
import br.com.fiap.jpa.exception.CommitException;
import br.com.fiap.jpa.singleton.EntityManagerFactorySingleton;

@Path("/jogador")
public class JogadorResource {

	private JogadorDAO dao;

	public JogadorResource() {
		EntityManager em  = EntityManagerFactorySingleton
								.getInstance().createEntityManager();
		dao = new JogadorDAOImpl(em);
	}
	
	@GET
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Jogador> listar(){
		return dao.listar();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Jogador pesquisar(@PathParam("id") int codigo) {
		return dao.pesquisar(codigo);
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response atualizar(Jogador jogador, @PathParam("id") int codigo) {
		
		try {
			jogador.setCodigo(codigo);
			dao.atualizar(jogador);
			dao.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok().build();
		
	}
	
	@DELETE
	@Path("{id}")
	public void apagar(@PathParam("id") int codigo) {
		try {
			dao.remover(codigo);
			dao.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new
				WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cadsatrar(Jogador jogador, @Context UriInfo uri) {
	
		try {
			dao.inserir(jogador);
			dao.commit();
		} catch (CommitException e) {
			e.printStackTrace();
			// Retorna o Status Code 500 do HTTP
			return Response.serverError().build();
		}
		
		//Recupera a URL atual
		UriBuilder b = uri.getAbsolutePathBuilder();
		//Adiciona o c�digo da sele��o na URL
		b.path(String.valueOf(jogador.getCodigo()));
		//Retorna Status 201
		return Response.created(b.build()).build();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
