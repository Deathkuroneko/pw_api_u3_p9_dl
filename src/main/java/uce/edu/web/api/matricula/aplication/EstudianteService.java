package uce.edu.web.api.matricula.aplication;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.matricula.aplication.representation.EstudianteRepresentation;
import uce.edu.web.api.matricula.domain.Estudiante;
import uce.edu.web.api.matricula.infraestructura.EstudianteRepository;

@ApplicationScoped
public class EstudianteService {

    @Inject
    private EstudianteRepository estudianteRepository;

    public List<EstudianteRepresentation> ListarTodos() {
        List<EstudianteRepresentation> estu = new ArrayList<>();
        for (Estudiante est : this.estudianteRepository.listAll()) {
            estu.add(this.mapperToEstudianteRepresentation(est));
        }
        return estu;
    }

    public EstudianteRepresentation consultarPorId(Integer id) {
        return this.mapperToEstudianteRepresentation(this.estudianteRepository.findById(id.longValue()));
    }


    @Transactional
    public void crearEstudiante(EstudianteRepresentation estudiante) {
        this.estudianteRepository.persist(this.mapperToEstudiante(estudiante));
    }
    
    @Transactional
    public void actualizarEstudiante(Integer id, EstudianteRepresentation estudianteDto) {
        // 1. Buscamos la entidad MANAGED directamente del repositorio
        Estudiante estuActual = this.estudianteRepository.findById(id.longValue());
        
        if (estuActual != null) {
            // 2. Usamos el nuevo método para actualizar sus campos
            this.actualizarEntidadDesdeDto(estudianteDto, estuActual);
            // El Dirty Checking guardará los cambios automáticamente al final del método.
        }
    }

    @Transactional
    public void actualizarParcialEstudiante(Integer id, EstudianteRepresentation estudianteDto) {
        // 1. Buscamos la entidad MANAGED
        Estudiante estuActual = this.estudianteRepository.findById(id.longValue());
        
        if (estuActual != null) {
            // 2. El método de mapeo ya tiene la lógica de "if != null"
            this.actualizarEntidadDesdeDto(estudianteDto, estuActual);
        }
    }

        // Este método actualiza la entidad que YA existe en la base de datos
    private void actualizarEntidadDesdeDto(EstudianteRepresentation dto, Estudiante entidadExistente) {
        if (dto.nombre != null) {
            entidadExistente.nombre = dto.nombre;
        }
        if (dto.apellido != null) {
            entidadExistente.apellido = dto.apellido;
        }
        if (dto.fechaNacimiento != null) {
            entidadExistente.fechaNacimiento = dto.fechaNacimiento;
        }
        if (dto.provincia != null) {
            entidadExistente.provincia = dto.provincia;
        }
        if (dto.genero != null) {
            entidadExistente.genero = dto.genero;
        }
        // Nota: No mapeamos el ID porque la entidad ya lo tiene y no debe cambiar.
    }


    @Transactional
    public void deleteEstudiante(Long id) {
        this.estudianteRepository.deleteById(id);
    }

    public List<Estudiante> buscarPorProvincia(String provincia) {
        System.out.println("Buscar por provincia en el servicio: " + provincia);
        return this.estudianteRepository.find("provincia = ?1", provincia).list();
    }

    // buscar por genero y provincia
    public List<Estudiante> buscarPorGeneroYProvincia(String genero, String provincia) {
        return this.estudianteRepository.find("genero = ?1 and provincia = ?2", genero, provincia).list();
    }

    // buscar nombre apellido
    public List<Estudiante> buscarPorNombreYApellido(String nombre, String apellido) {
        return this.estudianteRepository.find("nombre = ?1 and apellido = ?2", nombre, apellido).list();
    }

    // buscar nombre id
    public List<Estudiante> buscarPorNombreEId(String nombre, Integer id) {
        return this.estudianteRepository.find("nombre = ?1 and id = ?2", nombre, id.longValue()).list();
    }

    // nombre genero provincia
    public List<Estudiante> buscarPorNombreGeneroYProvincia(String nombre, String genero, String provincia) {
        return this.estudianteRepository
                .find("nombre = ?1 and genero = ?2 and provincia = ?3", nombre, genero, provincia).list();
    }
    // nombre id provincia
    public List<Estudiante> buscarPorNombreIdYProvincia(String nombre, Integer id, String provincia) {
        return this.estudianteRepository
                .find("nombre = ?1 and id = ?2 and provincia = ?3", nombre, id.longValue(), provincia).list();
    }

        private Estudiante mapperToEstudiante(EstudianteRepresentation est){
        Estudiante estu = new Estudiante();
        estu.id = est.id;
        estu.nombre = est.nombre;
        estu.apellido=est.apellido;
        estu.fechaNacimiento=est.fechaNacimiento;
        estu.provincia=est.provincia;
        estu.genero=est.genero;

        return estu;
    }

    private EstudianteRepresentation mapperToEstudianteRepresentation(Estudiante est){
        EstudianteRepresentation estu = new EstudianteRepresentation();
        estu.id = est.id;
        estu.nombre = est.nombre;
        estu.apellido=est.apellido;
        estu.fechaNacimiento=est.fechaNacimiento;
        estu.provincia=est.provincia;
        estu.genero=est.genero;

        return estu;
    }


}
