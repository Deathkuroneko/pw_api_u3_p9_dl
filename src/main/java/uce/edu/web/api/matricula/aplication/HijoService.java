package uce.edu.web.api.matricula.aplication;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import uce.edu.web.api.matricula.aplication.representation.HijoRepresentation;
import uce.edu.web.api.matricula.domain.Hijo;
import uce.edu.web.api.matricula.infraestructura.HijoRepository;

@ApplicationScoped
public class HijoService {

    @Inject
    private HijoRepository hijoRepository;

    public List<HijoRepresentation> buscarPorIdEstudiante(Integer idEst){
        List<HijoRepresentation>  hr = new ArrayList<>();
        for (Hijo h : this.hijoRepository.buscarPorIdEstudainte(idEst)) {
            hr.add(this.mapperTo(h));
        }
        return hr;
    }

    private HijoRepresentation mapperTo(Hijo hijo){
        HijoRepresentation hr = new HijoRepresentation();
        hr.id = hijo.id;
        hr.nombre = hijo.nombre;
        hr.apellido = hijo.apellido;

        return hr;
    }
    
}
