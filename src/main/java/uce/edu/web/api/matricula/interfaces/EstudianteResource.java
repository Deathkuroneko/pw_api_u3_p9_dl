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
    @Path("")
    public List<Estudiante> ListarTodos() {
        return this.estudianteService.ListarTodos();
    }

    /* LUEGO SE USARA EL MODELO DE MADURES DE RICHARSON */
    @GET
    @Path("/{id}")
    public Estudiante consultarPorId(@PathParam("id") Integer ids) {
        return this.estudianteService.consultarPorId(ids);
    }

    @POST
    @Path("")
    public void guardar(Estudiante estudiante) {
        this.estudianteService.crearEstudiante(estudiante);
    }
    
    @PUT
    @Path("/{id}")
    public void actualizar(@PathParam("id") Integer id, Estudiante estudiante) {
        this.estudianteService.actualizarEstudiante(id, estudiante);
    }

    @PATCH
    @Path("/{id}")
    public void actualizarParcial(@PathParam("id") Integer id, Estudiante estudiante) {
        this.estudianteService.actualizarParcialEstudiante(id, estudiante);
    }

    @DELETE
    @Path("/{id}")
    public void eliminar(@PathParam("id") Long id) {
        this.estudianteService.deleteEstudiante(id);
    }

    // para buscar usando querys
    @GET
    @Path("/provincia")
    //en caso de que haya chocque de path debe colocarse el diferenciador
    //y hay otra variable /provincia/genero y así.
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
    @Path("/provincia/id")
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

/* Supongamos que tenga estudiantes/provincia?genero=M&pichincha=Cuenca 
- si es un query param si se emvia pero no se mapea
- si es un path param no se envia y da error 404
*/

/* Existe un modelo de madurez- el modelo de madurez de Richardson
busca mejorar la calidad de la transicion de las Apis, permite clasificar 
la madurez de una api Rest en  4 niveles:

Nivel O: solo tiene que cumplir que trabaje con los protocolos HTTP O HTTPS
Nivel 1: se empiza a usar todo como un recurso y construir la URIs deben ser
        autodescriptivas, la nomenclatura adecuada debe ser como minimo:

        - https://Localhost:8080/(nombre reacionado a lo que estoy haciendo)/api/(version y maximo hasta 2 digitos)v1/(el nombre del recurso o entidad en plural)
        -> https://Localhost:8080/matricula/api/v1/estudiantes

        -forma didactica de ver los recursos:
        https://Localhost:8080/matricula/api/v1/estudiantes/consultarPorId/1 GET  https://Localhost:8080/matricula/api/v1/estudiantes/{id}
        https://Localhost:8080/matricula/api/v1/estudiantes/eliminarPorId/1 DELETE https://Localhost:8080/matricula/api/v1/estudiantes/{id}
        https://Localhost:8080/matricula/api/v1/estudiantes/crear POST (ya tiene un body, no recibe path param) https://Localhost:8080/matricula/api/v1/estudiantes/{id}
        https://Localhost:8080/matricula/api/v1/estudiantes/actualizar/1 PUT (ya tiene un body) https://Localhost:8080/matricula/api/v1/estudiantes/{id}
        https://Localhost:8080/matricula/api/v1/estudiantes/actualizarParcial/1 PATCH (ya tiene un body) https://Localhost:8080/matricula/api/v1/estudiantes/{id}

        - se puede poner un diferenciador cuando las URIs se chocan tanto en verbo como en path

Nivel 2:
*/