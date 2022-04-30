package Logica.Solicitud;

import Logica.Formulario.AyudaTemporal;
import Logica.Persona.Direccion;
import Logica.Persona.Solicitante;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;

public class SolicitudAyudaTemporal extends Solicitud implements Serializable {

    private Direccion direccion;
    private Solicitante solicitante;
    private AyudaTemporal ayudaTemporal;

    public SolicitudAyudaTemporal() {
    }

    public SolicitudAyudaTemporal(Direccion direccion, Solicitante solicitante, AyudaTemporal ayudaTemporal) {
        this.direccion = direccion;
        this.solicitante = solicitante;
        this.ayudaTemporal = ayudaTemporal;
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

    public AyudaTemporal getAyudaTemporal() {
        return ayudaTemporal;
    }

    public void setAyudaTemporal(AyudaTemporal ayudaTemporal) {
        this.ayudaTemporal = ayudaTemporal;
    }

    public String toGson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
