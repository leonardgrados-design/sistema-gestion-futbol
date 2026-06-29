
package conexion;
import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {
    Connection con;
    PreparedStatement stmt;
    ResultSet tabla;
    String sql, cadena;
    int sw;

    public Conexion() {
       cadena = "jdbc:mysql://localhost/gestion_deportiva?" + "user=root&password=12345678";
       sql = "com.mysql.cj.jdbc.Driver";
    }
    
    public Connection conecta(){
        try{Class.forName(sql);
        con = DriverManager.getConnection(cadena);
            JOptionPane.showMessageDialog(null, "Conexion exitosa");
        } catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return con;
    }
    public static void main(String[] args) {
        Conexion mycon = new Conexion();
        mycon.conecta();
    }
    
}
