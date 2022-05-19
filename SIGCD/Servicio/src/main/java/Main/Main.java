package Main;

import Logica.Data.AyudaTemporalDAO;
import Logica.Data.DireccionDAO;
import Logica.Data.EstudianteDAO;
import Logica.Data.Modelo;
import Logica.Data.SolicitanteDAO;
import Logica.Formulario.AyudaTemporal;
import Logica.Persona.Direccion;
import Logica.Persona.Estudiante;
import Logica.Persona.Solicitante;
import Logica.Solicitud.SolicitudBecaAcademica;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author sebas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Modelo model = Modelo.getInstancia();
//        ArrayList<SolicitudBecaAcademica> list = model.selectAllSolicitudesBecasAcademicas();
//        System.out.println(list.size());
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i).getDireccion().toString() + " " + list.get(i).getSolicitante().getCedula() + " " + list.get(i).getEstudiante().getCedula() + " " + list.get(i).getBecaAcademica());
//        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Solicitante solicitante = new Solicitante(1, "111", "oscar", "h", "c", "00/00/2222", 455, "dfadsf", "adfa", "ab.dfff");
        AyudaTemporal ayudaTemporal = new AyudaTemporal(1, 5, timestamp, "AThoUsgo", "Necesito weed", 4, 1);
        Estudiante estudiante = new Estudiante(8,"5", "David", "h", "z", "00/00/1995", 77, 4);
        SolicitanteDAO d = new SolicitanteDAO();
        DireccionDAO a = new DireccionDAO();
        AyudaTemporalDAO t = new AyudaTemporalDAO();
        Direccion direccion = new Direccion(28, "vvv", "2", "2");
        EstudianteDAO e = new EstudianteDAO();
        //e.update(estudiante);
        
        
        //t.update(ayudaTemporal);
        //d.update(solicitante);
        //a.update(direccion);


//
//        AyudaTemporal ayudaTemporal = new AyudaTemporal("1", timestamp, "ATdfgsfwe1", "Soy pobretas", 1, 1);
//        AyudaTemporal ayudaTemporal1 = new AyudaTemporal("1", timestamp, "ATdfgsfwe2", "Soy pobretas", 1, 2);
//        AyudaTemporal ayudaTemporal2 = new AyudaTemporal("1", timestamp, "ATdfgsfwe3", "Soy pobretas", 1, 3);
//
//        BecaAcademica becaAcademica = new BecaAcademica("1", timestamp, "BAdfgsfwe1", "1", 1, 1);
//        BecaAcademica becaAcademica1 = new BecaAcademica("1", timestamp, "BAdfgsfwe2", "1", 1, 2);
//        BecaAcademica becaAcademica2 = new BecaAcademica("1", timestamp, "BAdfgsfwe3", "1", 1, 3);
//        model.createSolicitante(solicitante);
//        model.createDireccion(direccion);
//        model.createEstudiante(estudiante);
//
//        model.createAyudaTemporal(ayudaTemporal);
//        model.createAyudaTemporal(ayudaTemporal1);
//        model.createAyudaTemporal(ayudaTemporal2);
//
//        model.createBecaAcademica(becaAcademica);
//        model.createBecaAcademica(becaAcademica1);
//        model.createBecaAcademica(becaAcademica2);
//
//        Optional<Solicitante> solicitanteRec = Optional.ofNullable(null);
//        Optional<Direccion> direccionRec = Optional.ofNullable(null);
//        Optional<Estudiante> estudianteRec = Optional.ofNullable(null);
//        Optional<BecaAcademica> becaAcademicaRec = Optional.ofNullable(null);
//        Optional<AyudaTemporal> ayudaTemporalRec = Optional.ofNullable(null);
//
//        solicitanteRec = model.getSolicitanteById("1").get();
//        direccionRec = model.getDireccionById(1).get();
//        estudianteRec = model.getEstudianteById("111111111").get();
//        becaAcademicaRec = model.getBecaAcademicaById(1).get();
//        ayudaTemporalRec = model.getAyudaTemporalById(1).get();
//
//        System.out.println("1" + solicitanteRec.toString());
//        System.out.println("2" + direccionRec.toString());
//        System.out.println("3" + estudianteRec.toString());
//        System.out.println("4" + becaAcademicaRec.toString());
//        System.out.println("5" + ayudaTemporalRec.toString());
        ArrayList<SolicitudBecaAcademica> at = model.selectAllSolicitudesBecasAcademicas();
        for (int i = 0; i < at.size(); i++) {
            System.out.println(at.get(i).toString());
        }
    }
}
