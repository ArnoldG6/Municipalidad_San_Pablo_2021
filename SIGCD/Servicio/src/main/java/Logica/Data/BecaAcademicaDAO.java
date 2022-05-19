package Logica.Data;

import Logica.Formulario.BecaAcademica;
import Logica.Persona.Direccion;
import Logica.Persona.Estudiante;
import Logica.Persona.Solicitante;
import Logica.Solicitud.SolicitudBecaAcademica;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class BecaAcademicaDAO {

    public boolean insert(BecaAcademica becaAcademica) throws SQLException {
        try {
            String sql = "insert into becaAcademica (idSolicitante,fechaCreacion,claveRecuperacion,idEstudiante,idDireccion,idEstado)"
                    + " values(?,?,?,?,?,?)";
            PreparedStatement stm = Database.instance().prepareStatement(sql);
            stm.setInt(1, becaAcademica.getIdSolicitante());
            stm.setTimestamp(2, becaAcademica.getFechaCreacion());
            stm.setString(3, becaAcademica.getClaveRecuperacion());
            stm.setInt(4, becaAcademica.getIdEstudiante());
            stm.setInt(5, becaAcademica.getIdDireccion());
            stm.setInt(6, becaAcademica.getIdEstado());
            return Database.instance().executeUpdate(stm) > 0;
        } catch (SQLException e) {
            System.out.printf("No se pudo agregar formulario beca academica\n" + e.getMessage() + "\n");
            return false;
        }
    }

    public Optional<BecaAcademica> select(int identificador) throws Exception {
        Optional<BecaAcademica> becaAcademica = Optional.ofNullable(null);
        try {
            String sql = "select * from becaAcademica where idSolicitante = ?";
            PreparedStatement stm = Database.instance().prepareStatement(sql);
            stm.setInt(1, identificador);
            ResultSet rs = Database.instance().executeQuery(stm);
            BecaAcademica becaAcademicaAux = new BecaAcademica();
            if (rs.next()) {
                becaAcademicaAux.setIdFormulario(rs.getInt("id"));
                becaAcademicaAux.setIdSolicitante(rs.getInt("idSolicitante"));
                becaAcademicaAux.setFechaCreacion(rs.getTimestamp("fechaCreacion"));
                becaAcademicaAux.setClaveRecuperacion(rs.getString("claveRecuperacion"));
                becaAcademicaAux.setIdEstudiante(rs.getInt("idEstudiante"));
                becaAcademicaAux.setIdDireccion(rs.getInt("idDireccion"));
                becaAcademicaAux.setIdEstado(rs.getInt("idEstado"));
                becaAcademica = Optional.ofNullable(becaAcademicaAux);
            }
            return becaAcademica;
        } catch (SQLException e) {
            System.out.printf("No se pudo agregar formulario beca academica\n" + e.getMessage() + "\n");
            return becaAcademica;
        }
    }

    public int getEstado(String clave) throws Exception {
        int estado = 0;
        try {
            String sql = "select idEstado from becaAcademica where claveRecuperacion = ?";
            PreparedStatement stm = Database.instance().prepareStatement(sql);
            stm.setString(1, clave);
            ResultSet rs = Database.instance().executeQuery(stm);
            if (rs.next()) {
                estado = rs.getInt("idEstado");
            }
            return estado;
        } catch (SQLException e) {
            System.out.printf("No se pudo obtener el estado del formulario beca academica\n" + e.getMessage() + "\n");
            return 0;
        }
    }

    public BecaAcademica from(ResultSet rs) {
        BecaAcademica becaAcademicaAux = new BecaAcademica();
        try {
            becaAcademicaAux.setIdFormulario(rs.getInt("id"));
            becaAcademicaAux.setIdSolicitante(rs.getInt("idSolicitante"));
            becaAcademicaAux.setFechaCreacion(rs.getTimestamp("fechaCreacion"));
            becaAcademicaAux.setClaveRecuperacion(rs.getString("claveRecuperacion"));
            becaAcademicaAux.setIdEstudiante(rs.getInt("idEstudiante"));
            becaAcademicaAux.setIdDireccion(rs.getInt("idDireccion"));
            becaAcademicaAux.setIdEstado(rs.getInt("idEstado"));
            return becaAcademicaAux;
        } catch (SQLException ex) {
            return null;
        }
    }

    public ArrayList<BecaAcademica> selectAll() throws Exception {
        String sql = "select * from becaacademica;";
        PreparedStatement stm = Database.instance().prepareStatement(sql);
        ResultSet rs = Database.instance().executeQuery(stm);
        ArrayList<BecaAcademica> listaAyudasTemporales = new ArrayList<>();
        while (rs.next()) {
            listaAyudasTemporales.add(from(rs));
        }
        return listaAyudasTemporales;
    }

    public ArrayList<BecaAcademica> selectAllByIdSolicitante(String identificador) throws Exception {
        String sql = "select * from becaacademica where idSolicitante = ?";
        PreparedStatement stm = Database.instance().prepareStatement(sql);
        stm.setString(1, identificador);
        ResultSet rs = Database.instance().executeQuery(stm);
        ArrayList<BecaAcademica> listaBecasAcademicas = new ArrayList<>();
        while (rs.next()) {
            listaBecasAcademicas.add(from(rs));
        }
        return listaBecasAcademicas;
    }

    public ArrayList<SolicitudBecaAcademica> selectAllSolicitudesBecasAcademicas() throws Exception {
        String sql = "select * from becaacademica inner join solicitante on becaacademica.idSolicitante = solicitante.id "
                + "inner join direccion on becaacademica.idDireccion = direccion.id "
                + "inner join estudiante on becaacademica.idestudiante = estudiante.id;";
        PreparedStatement stm = Database.instance().prepareStatement(sql);
        ResultSet rs = Database.instance().executeQuery(stm);
        ArrayList<SolicitudBecaAcademica> listaSolicitudesBecasAcademicas = new ArrayList<>();
        while (rs.next()) {
            listaSolicitudesBecasAcademicas.add(fromAll(rs));
        }
        return listaSolicitudesBecasAcademicas;
    }

    public SolicitudBecaAcademica fromAll(ResultSet rs) {
        try {
            SolicitudBecaAcademica solicitud = new SolicitudBecaAcademica();
            Direccion direccionAux = new Direccion();
            Solicitante solicitanteAux = new Solicitante();
            Estudiante estudianteAux = new Estudiante();
            BecaAcademica becaAcademicaAux = new BecaAcademica();
            direccionAux.setIdDireccion(rs.getInt("idDireccion"));
            direccionAux.setBarrio(rs.getString("barrio"));
            direccionAux.setDireccionExacta(rs.getString("direccionExacta"));
            direccionAux.setDistrito(rs.getString("distrito"));
            solicitanteAux.setIdPersona(rs.getInt("solicitante.id"));
            solicitanteAux.setCedula(rs.getString("solicitante.cedula"));
            solicitanteAux.setNombre(rs.getString("solicitante.nombre"));
            solicitanteAux.setPrimerApellido(rs.getString("solicitante.primerApellido"));
            solicitanteAux.setSegundoApellido(rs.getString("solicitante.primerApellido"));
            solicitanteAux.setFechaNacimiento(rs.getString("solicitante.fechaNacimiento"));
            solicitanteAux.setEdad(rs.getInt("solicitante.edad"));
            solicitanteAux.setTelefonoHabitacion(rs.getString("solicitante.telefonoHabitacion"));
            solicitanteAux.setTelefonoCelular(rs.getString("solicitante.telefonoCelular"));
            solicitanteAux.setCorreoElectronico(rs.getString("solicitante.correoElectronico"));
            estudianteAux.setIdPersona(rs.getInt("estudiante.id"));
            estudianteAux.setCedula(rs.getString("estudiante.cedula"));
            estudianteAux.setNombre(rs.getString("estudiante.nombre"));
            estudianteAux.setPrimerApellido(rs.getString("estudiante.primerApellido"));
            estudianteAux.setSegundoApellido(rs.getString("estudiante.SegundoApellido"));
            estudianteAux.setFechaNacimiento(rs.getString("estudiante.fechaNacimiento"));
            estudianteAux.setEdad(rs.getInt("estudiante.edad"));
            estudianteAux.setGradoAcademico(rs.getInt("estudiante.gradoAcademico"));
            becaAcademicaAux.setIdFormulario(rs.getInt("id"));
            becaAcademicaAux.setIdSolicitante(rs.getInt("idSolicitante"));
            becaAcademicaAux.setFechaCreacion(rs.getTimestamp("fechaCreacion"));
            becaAcademicaAux.setClaveRecuperacion(rs.getString("claveRecuperacion"));
            becaAcademicaAux.setIdEstudiante(rs.getInt("idEstudiante"));
            becaAcademicaAux.setIdDireccion(rs.getInt("idDireccion"));
            becaAcademicaAux.setIdEstado(rs.getInt("idEstado"));
            solicitud.setDireccion(direccionAux);
            solicitud.setSolicitante(solicitanteAux);
            solicitud.setEstudiante(estudianteAux);
            solicitud.setBecaAcademica(becaAcademicaAux);
            return solicitud;
        } catch (SQLException ex) {
            return null;
        }
    }

}
