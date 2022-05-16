package Logica.Solicitud;

import Logica.Formulario.Formulario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;

public class Solicitud implements Serializable {

    private Formulario formulario;

    public Solicitud() {
    }

    public Solicitud(Formulario formulario) {
        this.formulario = formulario;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public String toGson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
