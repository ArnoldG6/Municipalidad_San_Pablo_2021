package Logica.Data;

import Logica.Persona.Solicitante;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SolicitanteDAO {

    public boolean insert(Solicitante solicitante) throws SQLException {
        try {
            if (!select(solicitante.getCedula()).isPresent()) {
                String sql = "insert into solicitante (cedula,nombre,primerApellido,segundoApellido,fechaNacimiento,"
                        + "edad,telefonoHabitacion,telefonoCelular,correoElectronico)"
                        + " values(?,?,?,?,?,?,?,?,?)";
                PreparedStatement stm = Database.instance().prepareStatement(sql);
                System.out.println(stm);
                stm.setString(1, solicitante.getCedula());
                stm.setString(2, solicitante.getNombre());
                stm.setString(3, solicitante.getPrimerApellido());
                stm.setString(4, solicitante.getSegundoApellido());
                stm.setString(5, solicitante.getFechaNacimiento());
                stm.setInt(6, solicitante.getEdad());
                stm.setString(7, solicitante.getTelefonoHabitacion());
                stm.setString(8, solicitante.getTelefonoCelular());
                stm.setString(9, solicitante.getCorreoElectronico());
                return Database.instance().executeUpdate(stm) > 0;
            }
            else return false;
        } catch (SQLException e) {
            System.out.printf("No se pudo agregar solicitante\n" + e.getMessage() + "\n");
            return false;
        }
    }

    public Optional<Solicitante> select(String identificador) throws SQLException {
        Optional<Solicitante> solicitante = Optional.ofNullable(null);
        try {
            String sql = "select * from solicitante where cedula = ?";
            PreparedStatement stm = Database.instance().prepareStatement(sql);
            stm.setString(1, identificador);
            ResultSet rs = Database.instance().executeQuery(stm);
            Solicitante solicitanteAux = new Solicitante();
            if (rs.next()) {
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
                solicitante = Optional.ofNullable(solicitanteAux);
            }
            return solicitante;
        } catch (SQLException e) {
            System.out.printf("No se pudo agregar solicitante\n" + e.getMessage() + "\n");
            return null;
        }
    }

    public int getLastId() {
        try {
            String sql = "select count(*) from Solicitante;";
            PreparedStatement stm = Database.instance().prepareStatement(sql);
            ResultSet rs = Database.instance().executeQuery(stm);
            int i = 0;
            while (rs.next()) {
                i = rs.getInt(1);
            }
            return i;
        } catch (SQLException e) {
            System.out.printf("No se pudo obtener el dato\n" + e.getMessage() + "\n");
            return -1;
        }
    }
}
