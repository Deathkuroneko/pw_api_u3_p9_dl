package uce.edu.web.api.matricula.aplication;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.matricula.domain.Materia;
import uce.edu.web.api.matricula.infraestructura.MateriaRepository;

@ApplicationScoped
public class MateriaService {

    @Inject
    MateriaRepository materiaRepository;

    public List<Materia> listarTodas() {
        return materiaRepository.listAll();
    }

    public Materia consultarPorId(Integer id) {
        return materiaRepository.findById(id.longValue());
    }

    @Transactional
    public void crearMateria(Materia materia) {
        materiaRepository.persist(materia);
    }

    @Transactional
    public void actualizarMateria(Integer id, Materia materia) {
        Materia matActual = consultarPorId(id);
        matActual.nombre = materia.nombre;
        matActual.descripcion = materia.descripcion;
        matActual.cantidad_creditos = materia.cantidad_creditos;
    }

    // Dirty checking
    @Transactional
    public void actualizarParcialMateria(Integer id, Materia materia) {
        Materia matActual = consultarPorId(id);

        if (materia.nombre != null) {
            matActual.nombre = materia.nombre;
        }
        if (materia.descripcion != null) {
            matActual.descripcion = materia.descripcion;
        }
        if (materia.cantidad_creditos != null) {
            matActual.cantidad_creditos = materia.cantidad_creditos;
        }
    }

    @Transactional
    public void deleteMateria(Long id) {
        materiaRepository.deleteById(id);
    }

    public List<Materia> buscarPorNombre(String nombre) {
    return materiaRepository.list("lower(nombre) like ?1",
            "%" + nombre.toLowerCase() + "%");
    }

    public List<Materia> listarPorCreditos(Integer creditos) {
    return materiaRepository.list("cantidad_creditos", creditos);
    }




}
