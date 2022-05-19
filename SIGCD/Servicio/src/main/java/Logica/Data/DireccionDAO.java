package Logica.Data;

import Logica.Persona.Direccion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DireccionDAO {

    public boolean insert(Direccion direccion) throws SQLException {
        try {
            String sql = "insert into direccion (distrito,barrio,direccionExacta)"
                    + " values(?,?,?)";
            PreparedStatement stm = Database.instance().prepareStatement(sql);
            stm.setString(1, direccion.getDistrito());
            stm.setString(2, direccion.getBarrio());
            stm.setString(3, direccion.getDireccionExacta());
            return Database.instance().executeUpdate(stm) > 0;
        } catch (SQLException e) {
            System.out.printf("No se pudo agregar direccion\n" + e.getMessage() + "\n");
            return false;
        }
    }

    public boolean update(Direccion direccion) throws SQLException {
        try {
            String sql = "UPDATE direccion SET distrito = ?, barrio = ?, direccionexacta = ? WHERE id = ?";
            PreparedStatement stm = Database.instance().prepareStatement(sql);
            stm.setString(1, direccion.getDistrito());
            stm.setString(2, direccion.getBarrio());
            stm.setString(3, direccion.getDireccionExacta());
            stm.setInt(4, direccion.getIdDireccion());
            System.out.println(sql);
            return Database.instance().executeUpdate(stm) > 0;
        } catch (SQLException e) {
            System.out.printf("No se pudo actualizar la direccion\n" + e.getMessage() + "\n");
            return false;
        }
    }

    public Optional<Direccion> select(int identificador) throws SQLException {
        Optional<Direccion> direccion = Optional.ofNullable(null);
        try {
            String sql = "select * from direccion where id = ?";
            PreparedStatement stm = Database.instance().prepareStatement(sql);
            stm.setInt(1, identificador);
            ResultSet rs = Database.instance().executeQuery(stm);
            Direccion direccionAux = new Direccion();
            if (rs.next()) {
                direccionAux.setIdDireccion(rs.getInt("idDireccion"));
                direccionAux.setBarrio(rs.getString("barrio"));
                direccionAux.setDireccionExacta(rs.getString("direccionExacta"));
                direccionAux.setDistrito(rs.getString("distrito"));
                direccion = Optional.ofNullable(direccionAux);
            }
            return direccion;
        } catch (SQLException e) {
            System.out.printf("No se pudo agregar direccion\n" + e.getMessage() + "\n");
            return direccion;
        }
    }

    public int getLastId() {
        try {

            String sql = "select count(*) from Direccion;";
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
