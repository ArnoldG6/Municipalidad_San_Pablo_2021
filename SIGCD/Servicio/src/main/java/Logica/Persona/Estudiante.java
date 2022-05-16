package Logica.Persona;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;

public class Estudiante extends Persona implements Serializable {

    private int gradoAcademico;

    public Estudiante() {
    }

    public Estudiante(int idPersona, String cedula, String nombre, String primerApellido, String segundoApellido, String fechaNacimiento, int edad, int gradoAcademico) {
        super(idPersona, cedula, nombre, primerApellido, segundoApellido, fechaNacimiento, edad);
        this.gradoAcademico = gradoAcademico;
    }

    public Estudiante(String cedula, String nombre, String primerApellido, String segundoApellido, String fechaNacimiento, int edad, int gradoAcademico) {
        super(cedula, nombre, primerApellido, segundoApellido, fechaNacimiento, edad);
        this.gradoAcademico = gradoAcademico;
    }

    public int getGradoAcademico() {
        return gradoAcademico;
    }

    public void setGradoAcademico(int gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }

    @Override
    public String toString() {
        return "Estudiante{"+ super.toString() + "gradoAcademico=" + gradoAcademico + '}';
    }

    @Override
    public String toGson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
