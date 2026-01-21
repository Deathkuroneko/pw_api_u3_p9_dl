package uce.edu.web.api.matricula.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "materia")
@SequenceGenerator(name = "materia_seq", sequenceName = "materia_secuencia", allocationSize = 1)
public class Materia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materia_seq")
    public Integer id;
    public String nombre;
    public String descripcion;
    public Integer cantidad_creditos;

}
