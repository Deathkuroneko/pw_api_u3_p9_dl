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
        System.out.println("ListarTodos en el servicio");
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



}
