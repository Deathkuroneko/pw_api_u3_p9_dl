package uce.edu.web.api.matricula.aplication.representation;

import java.time.LocalDateTime;
import java.util.List;

public class EstudianteRepresentation {

    public Integer id;
    public String nombre;
    public String apellido;
    public LocalDateTime FechaNacimiento;
    public String provincia;
    public String genero;
    // http://localhost:8080/matricula/api/v1.0/estudiantes/1/hijos
    public List<LinkDto> links;

}
