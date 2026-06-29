
package Ventanas;
import conexion.Conexion;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import ds.desktop.notify.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author leona
 */
public class EstadisticasDeportivas {
    JFrame frm;
    JPanel pan1, pan2, pan3;
    JLabel lblTitulo, lblJugador, lblGoles, lblAsistencias, lblFaltas, lblMinutos, lblVelocidad, lblResistencia;
    JTextField txtGoles, txtAsistencias, txtFaltas, txtMinutos, txtVelocidad;
    JComboBox<String> cmbJugador, cmbResistencia;
    JButton btnGuardar;
    
    Conexion mycon;
    Connection con;
    PreparedStatement stmt;
    ResultSet rs;
    String sql;
    int sw = 0;
    
    public EstadisticasDeportivas(){
        mycon = new Conexion();
        frm = new JFrame("Estadísticas Deportivas");
        
        pan1 = new JPanel();
        pan2 = new JPanel();
        pan3 = new JPanel();
        
        lblTitulo = new JLabel("REGISTRAR ESTADÍSTICAS DEPORTIVAS");
        lblJugador = new JLabel("Selecciona Jugador:");
        lblGoles = new JLabel("Goles:");
        lblAsistencias = new JLabel("Asistencias:");
        lblFaltas = new JLabel("Faltas:");
        lblMinutos = new JLabel("Minutos Entrenados:");
        lblVelocidad = new JLabel("Velocidad Max (km/h):");
        lblResistencia = new JLabel("Nivel Resistencia:");
        
        cmbJugador = new JComboBox<>();
        cmbResistencia = new JComboBox<>();
        
        txtGoles = new JTextField(10);
        txtAsistencias = new JTextField(10);
        txtFaltas = new JTextField(10);
        txtMinutos = new JTextField(10);
        txtVelocidad = new JTextField(10);
        
        btnGuardar = new JButton("Guardar Estadísticas");
    }
    
    public void usar(){
        con = mycon.conecta();
        
        cargarJugadores();
        cmbResistencia.addItem("Baja");
        cmbResistencia.addItem("Media");
        cmbResistencia.addItem("Alta");
        cmbResistencia.addItem("Muy Alta");
        
        frm.setLayout(new BorderLayout());
        
        pan1.setLayout(new FlowLayout());
        pan1.add(lblTitulo);
        
        pan2.setLayout(new GridLayout(7, 2, 10, 10));
        pan2.add(lblJugador); pan2.add(cmbJugador);
        pan2.add(lblGoles); pan2.add(txtGoles);
        pan2.add(lblAsistencias); pan2.add(txtAsistencias);
        pan2.add(lblFaltas); pan2.add(txtFaltas);
        pan2.add(lblMinutos); pan2.add(txtMinutos);
        pan2.add(lblVelocidad); pan2.add(txtVelocidad);
        pan2.add(lblResistencia); pan2.add(cmbResistencia);
        
        pan3.setLayout(new FlowLayout());
        pan3.add(btnGuardar);
        
        frm.add(pan1, BorderLayout.NORTH);
        frm.add(pan2, BorderLayout.CENTER);
        frm.add(pan3, BorderLayout.SOUTH);
        
        frm.setSize(450, 450);
        frm.setLocation(450, 150);
        frm.setVisible(true);
        frm.setResizable(false);
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });
    }
    
    public void cargarJugadores(){
        try {
            sql = "SELECT id_jugador, nombre, apellido FROM jugadores";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                String item = rs.getString("id_jugador") + " - " + rs.getString("nombre") + " " + rs.getString("apellido");
                cmbJugador.addItem(item);
            }
        } catch (Exception e) {
            DesktopNotify.showDesktopMessage("ERROR", "Error al cargar jugadores", 3, 3000);
        }
    }
    
    public void limpiar(){
        txtGoles.setText("");
        txtAsistencias.setText("");
        txtFaltas.setText("");
        txtMinutos.setText("");
        txtVelocidad.setText("");
        cmbJugador.setSelectedIndex(0);
        cmbResistencia.setSelectedIndex(0);
    }
    
    public void guardar(){
        try {
            String seleccion = cmbJugador.getSelectedItem().toString();
            String[] partes = seleccion.split(" - ");
            String idJugador = partes[0];
            
            String goles = txtGoles.getText().isEmpty() ? "0" : txtGoles.getText();
            String asistencias = txtAsistencias.getText().isEmpty() ? "0" : txtAsistencias.getText();
            String faltas = txtFaltas.getText().isEmpty() ? "0" : txtFaltas.getText();
            String minutos = txtMinutos.getText().isEmpty() ? "0" : txtMinutos.getText();
            String velocidad = txtVelocidad.getText().isEmpty() ? "0.0" : txtVelocidad.getText();
            String resistencia = cmbResistencia.getSelectedItem().toString();
            
            sql = "INSERT INTO estadisticas_deportivas (id_jugador, goles, asistencias, faltas, minutos_entrenados, velocidad_max, resistencia) VALUES(";
            sql += "'" + idJugador + "',";
            sql += "'" + goles + "',";
            sql += "'" + asistencias + "',";
            sql += "'" + faltas + "',";
            sql += "'" + minutos + "',";
            sql += "'" + velocidad + "',";
            sql += "'" + resistencia + "')";
            
            System.out.println(sql);
            
            stmt = con.prepareStatement(sql);
            sw = stmt.executeUpdate();
            
            if(sw != 0){
                DesktopNotify.showDesktopMessage("EXITO", "Estadísticas guardadas", 3, 3000);
                limpiar();
            }
            
        } catch (Exception e) {
            DesktopNotify.showDesktopMessage("ERROR", "Error al guardar: " + e.getMessage(), 3, 3000);
        }
    }
}