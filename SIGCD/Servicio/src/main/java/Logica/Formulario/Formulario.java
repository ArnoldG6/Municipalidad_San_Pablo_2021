package Logica.Formulario;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.sql.Timestamp;

public class Formulario implements Serializable {

    private int idFormulario;
    private int idSolicitante;
    private Timestamp fechaCreacion;
    private String claveRecuperacion;
    private int idDireccion;
    private int idEstado;

    public Formulario() {
    }

    public Formulario(int idFormulario, int idSolicitante, Timestamp fechaCreacion, String claveRecuperacion, int idDireccion, int idEstado) {
        this.idFormulario = idFormulario;
        this.idSolicitante = idSolicitante;
        this.fechaCreacion = fechaCreacion;
        this.claveRecuperacion = claveRecuperacion;
        this.idDireccion = idDireccion;
        this.idEstado = idEstado;
    }

    public Formulario(int idSolicitante, Timestamp fechaCreacion, String claveRecuperacion, int idDireccion, int idEstado) {
        this.idSolicitante = idSolicitante;
        this.fechaCreacion = fechaCreacion;
        this.claveRecuperacion = claveRecuperacion;
        this.idDireccion = idDireccion;
        this.idEstado = idEstado;
    }

    public int getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(int idFormulario) {
        this.idFormulario = idFormulario;
    }

    public int getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(int idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getClaveRecuperacion() {
        return claveRecuperacion;
    }

    public void setClaveRecuperacion(String claveRecuperacion) {
        this.claveRecuperacion = claveRecuperacion;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String toGson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
