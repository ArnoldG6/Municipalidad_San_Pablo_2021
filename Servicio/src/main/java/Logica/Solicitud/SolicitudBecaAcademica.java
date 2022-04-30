package Logica.Solicitud;

import Logica.Formulario.BecaAcademica;
import Logica.Persona.Direccion;
import Logica.Persona.Estudiante;
import Logica.Persona.Solicitante;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SolicitudBecaAcademica {

    private Direccion direccion;
    private Solicitante solicitante;
    private Estudiante estudiante;
    private BecaAcademica becaAcademica;

    public SolicitudBecaAcademica() {
    }

    public SolicitudBecaAcademica(Direccion direccion, Solicitante solicitante, Estudiante estudiante, BecaAcademica becaAcademica) {
        this.direccion = direccion;
        this.solicitante = solicitante;
        this.estudiante = estudiante;
        this.becaAcademica = becaAcademica;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Solicitante getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Solicitante solicitante) {
        this.solicitante = solicitante;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public BecaAcademica getBecaAcademica() {
        return becaAcademica;
    }

    public void setBecaAcademica(BecaAcademica becaAcademica) {
        this.becaAcademica = becaAcademica;
    }

    public String toGson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
