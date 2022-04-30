package Logica.Persona;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;

public class Solicitante extends Persona implements Serializable {

    private String telefonoHabitacion;
    private String telefonoCelular;
    private String correoElectronico;

    public Solicitante() {
    }

    public Solicitante(int idPersona, String cedula, String nombre, String primerApellido, String segundoApellido, String fechaNacimiento, int edad,
            String telefonoHabitacion, String telefonoCelular, String correoElectronico) {
        super(idPersona, cedula, nombre, primerApellido, segundoApellido, fechaNacimiento, edad);
        this.telefonoHabitacion = telefonoHabitacion;
        this.telefonoCelular = telefonoCelular;
        this.correoElectronico = correoElectronico;
    }

    public Solicitante(String cedula, String nombre, String primerApellido, String segundoApellido, String fechaNacimiento, int edad,
            String telefonoHabitacion, String telefonoCelular, String correoElectronico) {
        super(cedula, nombre, primerApellido, segundoApellido, fechaNacimiento, edad);
        this.telefonoHabitacion = telefonoHabitacion;
        this.telefonoCelular = telefonoCelular;
        this.correoElectronico = correoElectronico;
    }

    public String getTelefonoHabitacion() {
        return telefonoHabitacion;
    }

    public void setTelefonoHabitacion(String telefonoHabitacion) {
        this.telefonoHabitacion = telefonoHabitacion;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @Override
    public String toString() {
        return "Solicitante{"+ super.toString() + "telefonoHabitacion=" + telefonoHabitacion + ", telefonoCelular=" + telefonoCelular + '}';
    }

    @Override
    public String toGson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
