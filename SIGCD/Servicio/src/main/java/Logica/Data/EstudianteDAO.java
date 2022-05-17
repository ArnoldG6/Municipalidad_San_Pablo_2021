package Logica.Data;

import Logica.Persona.Estudiante;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class EstudianteDAO {

    public boolean insert(Estudiante estudiante) throws SQLException {
        try {
            if (!select(estudiante.getCedula()).isPresent()) {
                String sql = "insert into estudiante(cedula,nombre,primerApellido,segundoApellido,fechaNacimiento,edad,gradoAcademico)"
                        + " values(?,?,?,?,?,?,?)";
                PreparedStatement stm = Database.instance().prepareStatement(sql);
                stm.setString(1, estudiante.getCedula());
                stm.setString(2, estudiante.getNombre());
                stm.setString(3, estudiante.getPrimerApellido());
                stm.setString(4, estudiante.getSegundoApellido());
                stm.setString(5, estudiante.getFechaNacimiento());
                stm.setInt(6, estudiante.getEdad());
                stm.setInt(7, estudiante.getGradoAcademico());
                return Database.instance().executeUpdate(stm) > 0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.printf("No se pudo agregar Estudiante\n" + e.getMessage() + "\n");
            return false;
        }
    }

    public Optional<Estudiante> select(String identificador) throws SQLException {
        Optional<Estudiante> estudiante = Optional.ofNullable(null);
        try {
            String sql = "select * from estudiante where cedula = ?";
            PreparedStatement stm = Database.instance().prepareStatement(sql);
            stm.setString(1, identificador);
            ResultSet rs = Database.instance().executeQuery(stm);
            Estudiante estudianteAux = new Estudiante();
            if (rs.next()) {
                estudianteAux.setIdPersona(rs.getInt("id"));
                estudianteAux.setCedula(rs.getString("cedula"));
                estudianteAux.setNombre(rs.getString("nombre"));
                estudianteAux.setPrimerApellido(rs.getString("primerApellido"));
                estudianteAux.setSegundoApellido(rs.getString("SegundoApellido"));
                estudianteAux.setFechaNacimiento(rs.getString("fechaNacimiento"));
                estudianteAux.setEdad(rs.getInt("edad"));
                estudianteAux.setGradoAcademico(rs.getInt("gradoAcademico"));
                estudiante = Optional.ofNullable(estudianteAux);
            }
            return estudiante;
        } catch (SQLException e) {
            System.out.printf("No se pudo obtener estudiante\n" + e.getMessage() + "\n");
            return estudiante;
        }
    }

    public int getLastId() {
        try {
            String sql = "select count(*) from Estudiante;";
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
