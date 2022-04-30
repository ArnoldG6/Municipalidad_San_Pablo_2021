package Logica.Formulario;

import java.sql.Timestamp;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import java.io.Serializable;

public class BecaAcademica extends Formulario implements Serializable {

    private int idEstudiante;

    public BecaAcademica() {
    }

    public BecaAcademica(int idFormulario, int idSolicitante, Timestamp fechaCreacion, String claveRecuperacion, int idEstudiante, int idDireccion, int idEstado) {
        super(idFormulario, idSolicitante, fechaCreacion, claveRecuperacion, idDireccion, idEstado);
        this.idEstudiante = idEstudiante;
    }

    public BecaAcademica(int idSolicitante, Timestamp fechaCreacion, String claveRecuperacion, int idEstudiante, int idDireccion, int idEstado) {
        super(idSolicitante, fechaCreacion, claveRecuperacion, idDireccion, idEstado);
        this.idEstudiante = idEstudiante;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    @Override
    public String toString() {
        return "BecaAcademica{" + super.toString() + "idEstudiante=" + idEstudiante + '}';
    }

    @Override
    public String toGson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
