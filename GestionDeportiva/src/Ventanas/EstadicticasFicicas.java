
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
public class EstadicticasFicicas {
    JFrame frm;
    JPanel pan1, pan2, pan3;
    JLabel lblTitulo, lblJugador, lblPeso, lblAltura, lblGrasa, lblMasa, lblImc;
    JTextField txtPeso, txtAltura, txtGrasa, txtMasa, txtImc;
    JComboBox<String> cmbJugador;
    JButton btnGuardar;
    
    Conexion mycon;
    Connection con;
    PreparedStatement stmt;
    ResultSet rs;
    String sql;
    int sw = 0;
    
    public EstadicticasFicicas(){
        mycon = new Conexion();
        frm = new JFrame("Estadísticas Físicas");
        
        pan1 = new JPanel();
        pan2 = new JPanel();
        pan3 = new JPanel();
        
        lblTitulo = new JLabel("REGISTRAR ESTADÍSTICAS FÍSICAS");
        lblJugador = new JLabel("Selecciona Jugador:");
        lblPeso = new JLabel("Peso (kg):");
        lblAltura = new JLabel("Altura (m):");
        lblGrasa = new JLabel("Porcentaje Grasa (%):");
        lblMasa = new JLabel("Masa Muscular (kg):");
        lblImc = new JLabel("IMC:");
        
        cmbJugador = new JComboBox<>();
        
        txtPeso = new JTextField(10);
        txtAltura = new JTextField(10);
        txtGrasa = new JTextField(10);
        txtMasa = new JTextField(10);
        txtImc = new JTextField(10);
        
        btnGuardar = new JButton("Guardar Datos");
    }
    
    public void usar(){
        con = mycon.conecta();
        
        cargarJugadores();
        
        frm.setLayout(new BorderLayout());
        
        pan1.setLayout(new FlowLayout());
        pan1.add(lblTitulo);
        
        pan2.setLayout(new GridLayout(6, 2, 10, 10));
        pan2.add(lblJugador); pan2.add(cmbJugador);
        pan2.add(lblPeso); pan2.add(txtPeso);
        pan2.add(lblAltura); pan2.add(txtAltura);
        pan2.add(lblGrasa); pan2.add(txtGrasa);
        pan2.add(lblMasa); pan2.add(txtMasa);
        pan2.add(lblImc); pan2.add(txtImc);
        
        pan3.setLayout(new FlowLayout());
        pan3.add(btnGuardar);
        
        frm.add(pan1, BorderLayout.NORTH);
        frm.add(pan2, BorderLayout.CENTER);
        frm.add(pan3, BorderLayout.SOUTH);
        
        frm.setSize(450, 400);
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
        txtPeso.setText("");
        txtAltura.setText("");
        txtGrasa.setText("");
        txtMasa.setText("");
        txtImc.setText("");
        cmbJugador.setSelectedIndex(0);
    }
    
    public void guardar(){
        try {
            String seleccion = cmbJugador.getSelectedItem().toString();
            String[] partes = seleccion.split(" - ");
            String idJugador = partes[0];
            
            String peso = txtPeso.getText().isEmpty() ? "0" : txtPeso.getText();
            String altura = txtAltura.getText().isEmpty() ? "0" : txtAltura.getText();
            String grasa = txtGrasa.getText().isEmpty() ? "0" : txtGrasa.getText();
            String masa = txtMasa.getText().isEmpty() ? "0" : txtMasa.getText();
            String imc = txtImc.getText().isEmpty() ? "0" : txtImc.getText();
            
            sql = "INSERT INTO estadisticas_fisicas (id_jugador, peso, altura, porcentaje_grasa, masa_muscular, imc) VALUES(";
            sql += "'" + idJugador + "',";
            sql += "'" + peso + "',";
            sql += "'" + altura + "',";
            sql += "'" + grasa + "',";
            sql += "'" + masa + "',";
            sql += "'" + imc + "')";
            
            System.out.println(sql);
            
            stmt = con.prepareStatement(sql);
            sw = stmt.executeUpdate();
            
            if(sw != 0){
                DesktopNotify.showDesktopMessage("EXITO", "Datos físicos guardados", 3, 3000);
                limpiar();
            }
            
        } catch (Exception e) {
            DesktopNotify.showDesktopMessage("ERROR", "Error al guardar: " + e.getMessage(), 3, 3000);
        }
    }
}