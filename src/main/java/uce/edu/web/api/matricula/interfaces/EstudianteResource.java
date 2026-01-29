package uce.edu.web.api.matricula.interfaces;

import java.util.ArrayList;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.web.api.matricula.aplication.EstudianteService;
import uce.edu.web.api.matricula.aplication.HijoService;
import uce.edu.web.api.matricula.aplication.representation.EstudianteRepresentation;
import uce.edu.web.api.matricula.aplication.representation.HijoRepresentation;
import uce.edu.web.api.matricula.aplication.representation.LinkDto;
import uce.edu.web.api.matricula.domain.Estudiante;

@Path("/estudiantes")
public class EstudianteResource {

    @Inject
    private EstudianteService estudianteService;

    @Inject
    private HijoService hijoService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EstudianteRepresentation> ListarTodos() {
        List<EstudianteRepresentation> estu = new ArrayList<>();
        for (EstudianteRepresentation est : this.estudianteService.ListarTodos()) {
            estu.add(this.construirLinks(est));
        }
        return estu;
    }

    /* LUEGO SE USARA EL MODELO DE MADURES DE RICHARSON */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public EstudianteRepresentation consultarPorId(@PathParam("id") Integer ids) {
        return this.construirLinks( this.estudianteService.consultarPorId(ids));
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardar(EstudianteRepresentation estudiante) {
        this.estudianteService.crearEstudiante(estudiante);
        return Response.status(Response.Status.CREATED).entity(estudiante).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, EstudianteRepresentation estudiante) {
        this.estudianteService.actualizarEstudiante(id, estudiante);
        return Response.status( 209).entity("Actualizado").build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
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
    @Produces(MediaType.APPLICATION_JSON)
    //en caso de que haya chocque de path debe colocarse el diferenciador
    //y hay otra variable /provincia/genero y así.
    public List<Estudiante> buscarPorProvincia(@QueryParam("provincia") String provincia) {
        return this.estudianteService.buscarPorProvincia(provincia);
    }

    @GET
    @Path("/buscarPorNombreYApellido")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Estudiante> buscarPorNombreYApellido(
            @QueryParam("nombre") String nombre,
            @QueryParam("apellido") String apellido) {
        return estudianteService.buscarPorNombreYApellido(nombre, apellido);
    }

    @GET
    @Path("/provincia/id")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Estudiante> buscarPorNombreEId(
            @QueryParam("nombre") String nombre,
            @QueryParam("id") Integer id) {
        return estudianteService.buscarPorNombreEId(nombre, id);
    }

    @GET
    @Path("/buscarPorNombreGeneroYProvincia")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Estudiante> buscarPorNombreGeneroYProvincia(
            @QueryParam("nombre") String nombre,
            @QueryParam("genero") String genero,
            @QueryParam("provincia") String provincia) {
        return estudianteService.buscarPorNombreGeneroYProvincia(nombre, genero, provincia);
    }

    @GET
    @Path("/buscarPorNombreIdYProvincia")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Estudiante> buscarPorNombreIdYProvincia(
            @QueryParam("nombre") String nombre,
            @QueryParam("id") Integer id,
            @QueryParam("provincia") String provincia) {
        return estudianteService.buscarPorNombreIdYProvincia(nombre, id, provincia);
    }

    @GET
    @Path("/{id}/hijos")
    public List<HijoRepresentation> buscarPorIdEstudiante(@PathParam("id") Integer id){
        return this.hijoService.buscarPorIdEstudiante(id);
    }

    private EstudianteRepresentation construirLinks(EstudianteRepresentation estr){
        String self = this.uriInfo.getBaseUriBuilder()
        .path(EstudianteResource.class)
        .path(String.valueOf(estr.id))
        .build()
        .toString();

        String hijos = this.uriInfo.getBaseUriBuilder()
        .path(EstudianteResource.class)
        .path(String.valueOf(estr.id))
        .path("hijos")
        .build()
        .toString();

        estr.links = List.of(new LinkDto(self, "self"),new LinkDto(hijos, "hijosSelf"));
        return estr;
    }
}

/* Supongamos que tenga estudiantes/provincia?genero=M&pichincha=Cuenca 
- si es un query param si se envia pero no se mapea - de una manera filtrada
- si es un path param no se envia y da error 404, porque no se encuentra la ruta
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

Nivel 2:- uso correcto de metodos o verbos HTTP
            GET: para obtener recursos
            POST: para crear recursos
            PUT: para actualizar recursos (actualizacion completa)
            PATCH: para actualizar recursos (actualizacion parcial)
            DELETE: para eliminar recursos
        - Uso correcto de codigos de estado HTTP
            Grupos de codigos de estado HTTP:
                1xx: para respuestas informativas -> no necesariamente hubo un error pero tampoco se completo la solicitud
                    100 - Esta destinado a entregar uan respuesta indicando que todo esta correcto pero un se esta procesando la solicitud
                    102 - Indica que el servidor a recibido su solicitud y aun esta procesandola pero hay una respuesta disponible
                2xx: para repuestas satisfactorias -> donde hubo y se consumio correctamente el recurso o la  API
                    200 - El request a tenido exito y todo ha salido bien en su peticion
                    201 - Se ha creado un recurso de manera correcta
                    204 - La petición fue procesada correctamente pero no hay respuesta
                3xx: para respuestas de redireccionamiento -> cuando hay cambios en las rutas o URIs
                4xx: para respuestas de error en el cliente -> cuando el cliente hace una solicitud incorrecta
                    400 - Bad Request o peticion mala, un error de sintaxis o algo que este mal estructurado
                    401 - Autorized, cuando la autenticación no fue correcta
                    403 - Usted no esta autorizado para consumir este recurso
                    404 - Indica que se hizo una peticion a algo que no existe
                    405 - Metodo no permitido
                    408 - Request time out, significa que mi peticion se desconecto por tiempo excedido
                    415 - unsuportted media type
                5xx: para respuestas de error en el servidor -> cuando el servidor no puede procesar la solicitud del cliente
                    500 - Error interno del servidor,error en el servidor al procesar la solicitud
                    503 - Servicio no disponible, que el servidor no esta disponible para recibir la petición
        URL DE COD DE ESTADO: https://developer.mozilla.org/es/docs/Web/HTTP/Reference/Status 
        - Uso de media Types correcto (el tipo de contenido que viaja en el body del request o response) haciendo un eso explicito del formato
        para prevenir ambiguedades en caso de que el cliente y el servidor puedan manejar multiples formatos
            Consume: notacion para consumir
            Produce: notacion para producir
            application/json: para datos en formato JSON
            application/xml: para datos en formato XML
            text/plain: para datos en formato texto plano

Nivel 3: Tambien conocido como HATEOS, es ineficiente traer siempre todos los hijos del padre, no deberiamos traer todos los datos
        de una sola peticion, sino atravez de hipervinculos, y en lugar de tener una lista de hijos hay un vinculo a otro endpoint
        que traera a los hijos de este.
        Nosotros no debemos exponer directamente las entidades (estudiantes) en el resource

        La consulta se va hacer mediante un hipervinculo o hipermedia,
*/