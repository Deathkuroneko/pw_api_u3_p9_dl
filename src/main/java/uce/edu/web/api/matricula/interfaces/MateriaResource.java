package uce.edu.web.api.matricula.interfaces;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uce.edu.web.api.matricula.aplication.MateriaService;
import uce.edu.web.api.matricula.domain.Materia;

@Path("/materias")
public class MateriaResource {

    @Inject
    private MateriaService materiaService;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Materia> listarTodas() {
        return materiaService.listarTodas();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Materia consultarPorId(@PathParam("id") Integer id) {
        return materiaService.consultarPorId(id);
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crear(Materia materia) {
        materiaService.crearMateria(materia);
        return Response.status(Response.Status.CREATED).entity(materia).build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(
            @PathParam("id") Integer id,
            Materia materia) {
        materiaService.actualizarMateria(id, materia);

        return Response.status(209).entity("Actualizar Materia").build();
    }

    @PATCH
    @Path("/{id}")
    public void actualizarParcial(
            @PathParam("id") Integer id,
            Materia materia) {
        materiaService.actualizarParcialMateria(id, materia);
    }

    @DELETE
    @Path("/{id}")
    public void eliminar(@PathParam("id") Long id) {
        materiaService.deleteMateria(id);
    }

    @GET
    @Path("/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Materia> buscarPorNombre(@PathParam("nombre") String nombre) {
        return materiaService.buscarPorNombre(nombre);
    }

    @GET
    @Path("/{creditos}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Materia> listarPorCreditos(
            @PathParam("creditos") Integer creditos) {
        return materiaService.listarPorCreditos(creditos);
    }


}
