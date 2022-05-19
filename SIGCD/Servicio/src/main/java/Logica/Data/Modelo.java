package Logica.Data;

import Logica.Formulario.AyudaTemporal;
import Logica.Formulario.BecaAcademica;
import Logica.Persona.Direccion;
import Logica.Persona.Estudiante;
import Logica.Persona.Solicitante;
import Logica.Solicitud.SolicitudAyudaTemporal;
import Logica.Solicitud.SolicitudBecaAcademica;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class Modelo {

    private static Modelo instancia;

    public static Modelo getInstancia() {
        if (instancia == null) {
            instancia = new Modelo();
        }
        return instancia;
    }

    private final AyudaTemporalDAO ayudaTemporalDAO;
    private final BecaAcademicaDAO becaAcademicaDAO;
    private final DireccionDAO direccionDAO;
    private final EstudianteDAO estudianteDAO;
    private final SolicitanteDAO solicitanteDAO;

    public Modelo() {
        ayudaTemporalDAO = new AyudaTemporalDAO();
        becaAcademicaDAO = new BecaAcademicaDAO();
        direccionDAO = new DireccionDAO();
        estudianteDAO = new EstudianteDAO();
        solicitanteDAO = new SolicitanteDAO();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------
    //Ayuda temporal
    public boolean createAyudaTemporal(AyudaTemporal ayudaTemporal) {
        try {
            return ayudaTemporalDAO.insert(ayudaTemporal);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean updateAyudaTemporal(AyudaTemporal ayudaTemporal) {
        try {
            return ayudaTemporalDAO.update(ayudaTemporal);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Optional<Optional<AyudaTemporal>> getAyudaTemporalById(int identificador) {
        try {
            return Optional.ofNullable(ayudaTemporalDAO.select(identificador));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Optional.ofNullable(null);
        }
    }

    public ArrayList<AyudaTemporal> getAllAyudasTemporales() throws Exception {
        return ayudaTemporalDAO.selectAll();
    }

    public ArrayList<AyudaTemporal> getAllAyudasTemporalesByIdSolicitante(String identificador) throws Exception {
        return ayudaTemporalDAO.selectAllByIdSolicitante(identificador);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------
    //Beca academica
    public boolean createBecaAcademica(BecaAcademica becaAcademica) {
        try {
            return becaAcademicaDAO.insert(becaAcademica);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Optional<Optional<BecaAcademica>> getBecaAcademicaById(int identificador) {
        try {
            return Optional.ofNullable(becaAcademicaDAO.select(identificador));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return Optional.ofNullable(null);
        }
    }

    public ArrayList<BecaAcademica> getAllBecasAcademicas() throws Exception {
        return becaAcademicaDAO.selectAll();
    }

    public ArrayList<BecaAcademica> getAllBecasAcademicasBySolicitanteId(String identificador) throws Exception {
        return becaAcademicaDAO.selectAllByIdSolicitante(identificador);
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------
    //Direccion
    public boolean createDireccion(Direccion direccion) {
        try {
            return direccionDAO.insert(direccion);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean updateDireccion(Direccion direccion) {
        try {
            return direccionDAO.update(direccion);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Optional<Optional<Direccion>> getDireccionById(int identificador) {
        try {
            return Optional.ofNullable(direccionDAO.select(identificador));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return Optional.ofNullable(null);
        }
    }

    public int getLastDireccionId() {
        return direccionDAO.getLastId();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------
    //Estudiante
    public boolean createEstudiante(Estudiante estudiante) {
        try {
            return estudianteDAO.insert(estudiante);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    
        public boolean updateEstudiante(Estudiante estudiante) {
        try {
            return estudianteDAO.update(estudiante);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Optional<Optional<Estudiante>> getEstudianteById(String identificador) {
        try {
            return Optional.ofNullable(estudianteDAO.select(identificador));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Optional.ofNullable(null);
        }
    }

    public int getLastEstudianteId() {
        return estudianteDAO.getLastId();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------
    //Solicitante
    public boolean createSolicitante(Solicitante solicitante) {
        try {
            return solicitanteDAO.insert(solicitante);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean updateSolicitante(Solicitante solicitante) {
        try {
            return solicitanteDAO.update(solicitante);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Optional<Optional<Solicitante>> getSolicitanteById(String identificador) {
        try {
            return Optional.ofNullable(solicitanteDAO.select(identificador));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Optional.ofNullable(null);
        }
    }

    public int getLastSolicitanteId() {
        return solicitanteDAO.getLastId();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------
    //Consultar estado solicitud
    public int getEstadoSolicitudByClaveRecuperacion(String clave) throws Exception {
        try {
            if ("".equals(clave)) {
                return 0;
            } else {
                String substring = clave.substring(0, 2);
                if (null == substring) {
                    return 0;
                } else {
                    switch (substring) {
                        case "AT":
                            return ayudaTemporalDAO.getEstado(clave);
                        case "BA":
                            return becaAcademicaDAO.getEstado(clave);
                        default:
                            return 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------
    //Solicitudes ayudas temporales
    public ArrayList<SolicitudAyudaTemporal> selectAllSolicitudesAyudasTemporales() throws Exception {
        return ayudaTemporalDAO.selectAllSolicitudesAyudasTemporales();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------
    //Solicitudes becas academicas
    public ArrayList<SolicitudBecaAcademica> selectAllSolicitudesBecasAcademicas() throws Exception {
        return becaAcademicaDAO.selectAllSolicitudesBecasAcademicas();
    }
}
