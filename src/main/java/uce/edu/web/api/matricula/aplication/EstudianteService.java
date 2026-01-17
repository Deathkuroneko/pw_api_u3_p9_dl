package uce.edu.web.api.matricula.aplication;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.matricula.domain.Estudiante;
import uce.edu.web.api.matricula.infraestructura.EstudianteRepository;

@ApplicationScoped
public class EstudianteService {

    @Inject
    private EstudianteRepository estudianteRepository;

    public List<Estudiante> ListarTodos() {
        return this.estudianteRepository.listAll();
    }

    public Estudiante consultarPorId(Integer id) {
        return this.estudianteRepository.findById(id.longValue());
    }

    @Transactional
    public void crearEstudiante(Estudiante estudiante) {
        this.estudianteRepository.persist(estudiante);
    }

    
    @Transactional
    public void actualizarEstudiante(Integer id, Estudiante estudiante) {
        Estudiante estuActual = this.consultarPorId(id);
        estuActual.apellido = estudiante.apellido;
        estuActual.nombre = estudiante.nombre;
        estuActual.FechaNacimiento = estudiante.FechaNacimiento;
    }
    
    // SE ACTUALIZA AUTOMATICAMENTE POR DIRTY CHECKING
    @Transactional
    public void actualizarParcialEstudiante(Integer id, Estudiante estudiante) {
        Estudiante estuActual = this.consultarPorId(id);
        if (estudiante.apellido != null) {
            estuActual.apellido = estudiante.apellido;
        }
        if (estudiante.nombre != null) {
            estuActual.nombre = estudiante.nombre;
        }
        if (estudiante.FechaNacimiento != null) {
            estuActual.FechaNacimiento = estudiante.FechaNacimiento;
        }
    }

    @Transactional
    public void deleteEstudiante(Long id) {
        this.estudianteRepository.deleteById(id);
    }
}
