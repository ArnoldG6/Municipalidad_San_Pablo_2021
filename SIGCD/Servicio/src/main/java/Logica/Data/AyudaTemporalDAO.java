package Logica.Data;

import Logica.Formulario.AyudaTemporal;
import Logica.Persona.Direccion;
import Logica.Persona.Solicitante;
import Logica.Solicitud.SolicitudAyudaTemporal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class AyudaTemporalDAO {

    public boolean insert(AyudaTemporal ayudaTemporal) throws SQLException {
        try {
            String sql = "insert into ayudaTemporal (idSolicitante,fechaCreacion,claveRecuperacion,motivoAyuda,idDireccion,idEstado)"
                    + " values(?,?,?,?,?,?)";
            PreparedStatement stm = Database.instance().prepareStatement(sql);
            stm.setInt(1, ayudaTemporal.getIdSolicitante());
            stm.setTimestamp(2, ayudaTemporal.getFechaCreacion());
            stm.setString(3, ayudaTemporal.getClaveRecuperacion());
            stm.setString(4, ayudaTemporal.getMotivoAyuda());
            stm.setInt(5, ayudaTemporal.getIdDireccion());
            stm.setInt(6, ayudaTemporal.getIdEstado());
            return Database.instance().executeUpdate(stm) > 0;
        } catch (SQLException e) {
            System.out.printf("No se pudo agregar formulario ayuda temporal\n" + e.getMessage() + "\n");
            return false;
        }
    }

    public Optional<AyudaTemporal> select(int identificador) throws Exception {
        Optional<AyudaTemporal> ayudaTemporal = Optional.ofNullable(null);
        try {
            String sql = "select * from ayudaTemporal where idSolicitante = ?";
            PreparedStatement stm = Database.instance().prepareStatement(sql);
            stm.setInt(1, identificador);
            ResultSet rs = Database.instance().executeQuery(stm);
            AyudaTemporal ayudaTemporalAux = new AyudaTemporal();
            if (rs.next()) {
                ayudaTemporalAux.setIdFormulario(rs.getInt("id"));
                ayudaTemporalAux.setIdSolicitante(rs.getInt("idSolicitante"));
                ayudaTemporalAux.setFechaCreacion(rs.getTimestamp("fechaCreacion"));
                ayudaTemporalAux.setClaveRecuperacion(rs.getString("claveRecuperacion"));
                ayudaTemporalAux.setMotivoAyuda(rs.getString("motivoAyuda"));
                ayudaTemporalAux.setIdDireccion(rs.getInt("idDireccion"));
                ayudaTemporalAux.setIdEstado(rs.getInt("idEstado"));
                ayudaTemporal = Optional.ofNullable(ayudaTemporalAux);
            }
            return ayudaTemporal;
        } catch (SQLException e) {
            System.out.printf("No se pudo agregar formulario ayuda temporal\n" + e.getMessage() + "\n");
            return ayudaTemporal;
        }
    }

    public int getEstado(String clave) throws Exception {
        int estado = 0;
        try {
            String sql = "select idEstado from ayudaTemporal where claveRecuperacion = ?";
            PreparedStatement stm = Database.instance().prepareStatement(sql);
            stm.setString(1, clave);
            ResultSet rs = Database.instance().executeQuery(stm);
            if (rs.next()) {
                estado = rs.getInt("idEstado");
            }
            return estado;
        } catch (SQLException e) {
            System.out.printf("No se pudo obtener el estado del formulario ayuda temporal\n" + e.getMessage() + "\n");
            return 0;
        }
    }

    public AyudaTemporal from(ResultSet rs) {
        try {
            AyudaTemporal ayudaTemporalAux = new AyudaTemporal();
            ayudaTemporalAux.setIdFormulario(rs.getInt("id"));
            ayudaTemporalAux.setIdSolicitante(rs.getInt("idSolicitante"));
            ayudaTemporalAux.setFechaCreacion(rs.getTimestamp("fechaCreacion"));
            ayudaTemporalAux.setClaveRecuperacion(rs.getString("claveRecuperacion"));
            ayudaTemporalAux.setMotivoAyuda(rs.getString("motivoAyuda"));
            ayudaTemporalAux.setIdDireccion(rs.getInt("idDireccion"));
            ayudaTemporalAux.setIdEstado(rs.getInt("idEstado"));
            return ayudaTemporalAux;
        } catch (SQLException ex) {
            return null;
        }
    }

    public ArrayList<AyudaTemporal> selectAll() throws Exception {
        String sql = "select * from ayudatemporal;";
        PreparedStatement stm = Database.instance().prepareStatement(sql);
        ResultSet rs = Database.instance().executeQuery(stm);
        ArrayList<AyudaTemporal> listaAyudasTemporales = new ArrayList<>();
        while (rs.next()) {
            listaAyudasTemporales.add(from(rs));
        }
        return listaAyudasTemporales;
    }

    public ArrayList<AyudaTemporal> selectAllByIdSolicitante(String identificador) throws Exception {
        String sql = "select * from ayudaTemporal where idSolicitante = ?";
        PreparedStatement stm = Database.instance().prepareStatement(sql);
        stm.setString(1, identificador);
        ResultSet rs = Database.instance().executeQuery(stm);
        ArrayList<AyudaTemporal> listaAyudasTemporales = new ArrayList<>();
        while (rs.next()) {
            listaAyudasTemporales.add(from(rs));
        }
        return listaAyudasTemporales;
    }

    public ArrayList<SolicitudAyudaTemporal> selectAllSolicitudesAyudasTemporales() throws Exception {
        String sql = "select * from ayudatemporal inner join solicitante on ayudatemporal.idSolicitante = solicitante.id inner join direccion on ayudatemporal.idDireccion = direccion.id;";
        PreparedStatement stm = Database.instance().prepareStatement(sql);
        ResultSet rs = Database.instance().executeQuery(stm);
        ArrayList<SolicitudAyudaTemporal> listaSolicitudesAyudasTemporales = new ArrayList<>();
        while (rs.next()) {
            listaSolicitudesAyudasTemporales.add(fromAll(rs));
        }
        return listaSolicitudesAyudasTemporales;
    }

    public SolicitudAyudaTemporal fromAll(ResultSet rs) {
        try {
            SolicitudAyudaTemporal solicitud = new SolicitudAyudaTemporal();
            Direccion direccionAux = new Direccion();
            Solicitante solicitanteAux = new Solicitante();
            AyudaTemporal ayudaTemporalAux = new AyudaTemporal();
            direccionAux.setIdDireccion(rs.getInt("idDireccion"));
            direccionAux.setBarrio(rs.getString("barrio"));
            direccionAux.setDireccionExacta(rs.getString("direccionExacta"));
            direccionAux.setDistrito(rs.getString("distrito"));
            solicitanteAux.setIdPersona(rs.getInt("id"));
            solicitanteAux.setCedula(rs.getString("cedula"));
            solicitanteAux.setNombre(rs.getString("nombre"));
            solicitanteAux.setPrimerApellido(rs.getString("primerApellido"));
            solicitanteAux.setSegundoApellido(rs.getString("primerApellido"));
            solicitanteAux.setFechaNacimiento(rs.getString("fechaNacimiento"));
            solicitanteAux.setEdad(rs.getInt("edad"));
            solicitanteAux.setTelefonoHabitacion(rs.getString("telefonoHabitacion"));
            solicitanteAux.setTelefonoCelular(rs.getString("telefonoCelular"));
            solicitanteAux.setCorreoElectronico(rs.getString("correoElectronico"));
            ayudaTemporalAux.setIdFormulario(rs.getInt("id"));
            ayudaTemporalAux.setIdSolicitante(rs.getInt("idSolicitante"));
            ayudaTemporalAux.setFechaCreacion(rs.getTimestamp("fechaCreacion"));
            ayudaTemporalAux.setClaveRecuperacion(rs.getString("claveRecuperacion"));
            ayudaTemporalAux.setMotivoAyuda(rs.getString("motivoAyuda"));
            ayudaTemporalAux.setIdDireccion(rs.getInt("idDireccion"));
            ayudaTemporalAux.setIdEstado(rs.getInt("idEstado"));
            solicitud.setDireccion(direccionAux);
            solicitud.setSolicitante(solicitanteAux);
            solicitud.setAyudaTemporal(ayudaTemporalAux);
            return solicitud;
        } catch (SQLException ex) {
            return null;
        }
    }
}
