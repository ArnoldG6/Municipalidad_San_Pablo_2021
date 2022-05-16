package Logica.Formulario;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.sql.Timestamp;

public class AyudaTemporal extends Formulario implements Serializable {

    private String motivoAyuda;

    public AyudaTemporal() {
    }

    public AyudaTemporal(int idFormulario, int idSolicitante, Timestamp fechaCreacion, String claveRecuperacion, String motivoAyuda, int idDireccion, int idEstado) {
        super(idFormulario, idSolicitante, fechaCreacion, claveRecuperacion, idDireccion, idEstado);
        this.motivoAyuda = motivoAyuda;
    }

    public AyudaTemporal(int idSolicitante, Timestamp fechaCreacion, String claveRecuperacion, String motivoAyuda, int idDireccion, int idEstado) {
        super(idSolicitante, fechaCreacion, claveRecuperacion, idDireccion, idEstado);
        this.motivoAyuda = motivoAyuda;
    }

    public String getMotivoAyuda() {
        return motivoAyuda;
    }

    public void setMotivoAyuda(String motivoAyuda) {
        this.motivoAyuda = motivoAyuda;
    }

    @Override
    public String toString() {
        return "AyudaTemporal{"+ super.toString() + "motivoAyuda=" + motivoAyuda +'}';
    }

    @Override
    public String toGson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
