package uce.edu.web.api.matricula.interfaces;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import uce.edu.web.api.matricula.aplication.MateriaService;
import uce.edu.web.api.matricula.domain.Materia;

@Path("/materias")
public class MateriaResource {

    @Inject
    private MateriaService materiaService;

    @GET
    @Path("/todos")
    public List<Materia> listarTodas() {
        return materiaService.listarTodas();
    }

    @GET
    @Path("/consultarPorId/{id}")
    public Materia consultarPorId(@PathParam("id") Integer id) {
        return materiaService.consultarPorId(id);
    }

    @POST
    @Path("/crear")
    public void crear(Materia materia) {
        materiaService.crearMateria(materia);
    }

    @PUT
    @Path("/actualizar/{id}")
    public void actualizar(
            @PathParam("id") Integer id,
            Materia materia) {
        materiaService.actualizarMateria(id, materia);
    }

    @PATCH
    @Path("/actualizarParcial/{id}")
    public void actualizarParcial(
            @PathParam("id") Integer id,
            Materia materia) {
        materiaService.actualizarParcialMateria(id, materia);
    }

    @DELETE
    @Path("/eliminar/{id}")
    public void eliminar(@PathParam("id") Long id) {
        materiaService.deleteMateria(id);
    }

    @GET
    @Path("/buscarPorNombre/{nombre}")
    public List<Materia> buscarPorNombre(@PathParam("nombre") String nombre) {
        return materiaService.buscarPorNombre(nombre);
    }

    @GET
    @Path("/porCreditos/{creditos}")
    public List<Materia> listarPorCreditos(
            @PathParam("creditos") Integer creditos) {
        return materiaService.listarPorCreditos(creditos);
    }


}
