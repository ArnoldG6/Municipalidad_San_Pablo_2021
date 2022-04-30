package Logica.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Database {

    private static Database instancia;

    public static Database instance() {
        if (instancia == null) {
            instancia = new Database();
        }
        return instancia;
    }

    Connection conexion;
    private final String driver = "com.mysql.cj.jdbc.Driver";
    private final String server = "localhost";
    private final String port = "3306";
    private final String user = "root";
    private final String password = "root";
    private final String name = "sigcd";

    public Database() {
        conexion = this.getConnection();
    }

    public Connection getConnection() {
        try {
            String driver = this.driver;
            String server = this.server;
            String port = this.port;
            String user = this.user;
            String password = this.password;
            String database = this.name;
            String URL_conexion = "jdbc:mysql://" + server + ":" + port + "/"
                    + database + "?user=" + user + "&password=" + password + "&serverTimezone=UTC&autoReconnect=true&useSSL=false";
            Class.forName(driver).newInstance();
            return DriverManager.getConnection(URL_conexion);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    public PreparedStatement prepareStatement(String statement) throws SQLException {
        return conexion.prepareStatement(statement);
    }

    public int executeUpdate(PreparedStatement statement) {
        try {
            statement.executeUpdate();
            return statement.getUpdateCount();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    public ResultSet executeQuery(PreparedStatement statement) {
        try {
            return statement.executeQuery();
        } catch (SQLException ex) {
        }
        return null;
    }
}
