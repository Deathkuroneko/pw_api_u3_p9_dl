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
import jakarta.ws.rs.QueryParam;
import uce.edu.web.api.matricula.aplication.EstudianteService;
import uce.edu.web.api.matricula.domain.Estudiante;

@Path("/estudiantes")
public class EstudianteResource {

    @Inject
    private EstudianteService estudianteService;

    @GET
    @Path("/todos")
    public List<Estudiante> ListarTodos() {
        return this.estudianteService.ListarTodos();
    }

    /* LUEGO SE USARA EL MODELO DE MADURES DE RICHARSON */
    @GET
    @Path("/consultarPorId/{id}")
    public Estudiante consultarPorId(@PathParam("id") Integer ids) {
        return this.estudianteService.consultarPorId(ids);
    }

    @POST
    @Path("/crear")
    public void guardar(Estudiante estudiante) {
        this.estudianteService.crearEstudiante(estudiante);
    }
    
    @PUT
    @Path("/actualizar/{id}")
    public void actualizar(@PathParam("id") Integer id, Estudiante estudiante) {
        this.estudianteService.actualizarEstudiante(id, estudiante);
    }

    @PATCH
    @Path("/actualizarParcial/{id}")
    public void actualizarParcial(@PathParam("id") Integer id, Estudiante estudiante) {
        this.estudianteService.actualizarParcialEstudiante(id, estudiante);
    }

    @DELETE
    @Path("/eliminar/{id}")
    public void eliminar(@PathParam("id") Long id) {
        this.estudianteService.deleteEstudiante(id);
    }

    // para buscar usando querys
    @GET
    @Path("/buscarPorProvincia")
    public List<Estudiante> buscarPorProvincia(@QueryParam("provincia") String provincia) {
        return this.estudianteService.buscarPorProvincia(provincia);
    }

    @GET
    @Path("/buscarPorNombreYApellido")
    public List<Estudiante> buscarPorNombreYApellido(
            @QueryParam("nombre") String nombre,
            @QueryParam("apellido") String apellido) {
        return estudianteService.buscarPorNombreYApellido(nombre, apellido);
    }

    @GET
    @Path("/buscarPorNombreEId")
    public List<Estudiante> buscarPorNombreEId(
            @QueryParam("nombre") String nombre,
            @QueryParam("id") Integer id) {
        return estudianteService.buscarPorNombreEId(nombre, id);
    }

    @GET
    @Path("/buscarPorNombreGeneroYProvincia")
    public List<Estudiante> buscarPorNombreGeneroYProvincia(
            @QueryParam("nombre") String nombre,
            @QueryParam("genero") String genero,
            @QueryParam("provincia") String provincia) {
        return estudianteService.buscarPorNombreGeneroYProvincia(nombre, genero, provincia);
    }

    @GET
    @Path("/buscarPorNombreIdYProvincia")
    public List<Estudiante> buscarPorNombreIdYProvincia(
            @QueryParam("nombre") String nombre,
            @QueryParam("id") Integer id,
            @QueryParam("provincia") String provincia) {
        return estudianteService.buscarPorNombreIdYProvincia(nombre, id, provincia);
    }


}
