
package Ventanas;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import ds.desktop.notify.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.toedter.calendar.JDateChooser;
import conexion.Conexion;
/**
 *
 * @author leona
 */
public class NuevoJugador {
    JFrame jfr1;
    JPanel jpn1, jpn2, jpn3, jpn4, jpn5, jpn6, jpn7, jpn8;
    JLabel jlbl1, jlbl2, jlbl3, jlbl4, jlbl5, jlbl6, jlbl7, jlbl8;
    JTextField jtxt1, jtxt2, jtxt3, jtxt4;
    JComboBox<String> combo;    
    JDateChooser jcal;
    JButton jbtn;
    Conexion mycon;
    Connection con;
    ResultSet tabla;
    PreparedStatement stmt;
    String sql, cadena;
    int sw=0;
    
    public NuevoJugador(){
        mycon = new Conexion();
        jfr1 = new JFrame("Nuevo Jugador");
        jpn1 = new JPanel();
        jpn2 = new JPanel();
        jpn3 = new JPanel();
        jpn4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpn5 = new JPanel();
        jpn6 = new JPanel();
        jpn7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpn8 = new JPanel();
        jlbl7 = new JLabel("Nuevo Jugador");
        jlbl1 = new JLabel("Nombre: ");
        jlbl2 = new JLabel("Apellido: ");
        jlbl3 = new JLabel("Fecha de Nacimiento: ");
        jcal = new JDateChooser();
        jlbl4 = new JLabel("Telefono: ");
        jlbl5 = new JLabel("Correo: ");
        jlbl6 = new JLabel("Posicion: ");
        combo = new JComboBox<>();
        jbtn = new JButton("Guardar");
        jtxt1 = new JTextField(25);
        jtxt2 = new JTextField(25);
        jtxt3 = new JTextField(25);
        jtxt4 = new JTextField(25);
        
    }
            
    public void usar(){
        con  = mycon.conecta();
        combo.addItem("Portero");
        combo.addItem("Defensa");
        combo.addItem("Medio");
        combo.addItem("Delantero");
        jfr1.setLayout(new GridLayout(8,1));
        jpn1.add(jlbl7);jpn2.add(jlbl1);jpn2.add(jtxt1);
        jpn3.add(jlbl2);jpn3.add(jtxt2);
        jpn4.add(jlbl3);jpn4.add(jcal);
        jpn5.add(jlbl4);jpn5.add(jtxt3);
        jpn6.add(jlbl5);jpn6.add(jtxt4);
        jpn7.add(jlbl6);jpn7.add(combo);
        jpn8.add(jbtn);
        jfr1.add(jpn1);
        jfr1.add(jpn2);
        jfr1.add(jpn3);
        jfr1.add(jpn4);
        jfr1.add(jpn5);
        jfr1.add(jpn6);
        jfr1.add(jpn7);
        jfr1.add(jpn8);
        
        jfr1.pack(); jfr1.setLocation(400,200); jfr1.setVisible(true); jfr1.setResizable(false);
        
        jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               insertar();
            }
        });
        
        
    }
    
    
    public void nuevo(){
    jtxt1.setText("");jtxt2.setText("");jtxt3.setText("");jtxt4.setText("");jcal.setDate(null);
}
    public void insertar() {
    try {

        // convertir la fecha correctamente
        java.sql.Date fechaSQL = new java.sql.Date(jcal.getDate().getTime());

        sql = "INSERT INTO jugadores (nombre, apellido, fecha_nacimiento, telefono, correo, posicion) VALUES(";
        sql += "'" + jtxt1.getText() + "',";
        sql += "'" + jtxt2.getText() + "',";
        sql += "'" + fechaSQL + "',";
        sql += "'" + jtxt3.getText() + "',";
        sql += "'" + jtxt4.getText() + "',";
        sql += "'" + combo.getSelectedItem().toString() + "')";
        
        System.out.println(sql);

        stmt = con.prepareStatement(sql);
        sw = stmt.executeUpdate();

        if (sw != 0) {
            DesktopNotify.showDesktopMessage("GUARDADO", "Jugador Guardado", 3, 3000);
            nuevo();
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}

    
    
    public static void main(String[] args) {
        NuevoJugador nv = new NuevoJugador();
        nv.usar();
    }
}
