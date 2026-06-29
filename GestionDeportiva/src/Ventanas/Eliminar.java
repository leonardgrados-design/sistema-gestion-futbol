
package Ventanas;
import conexion.Conexion;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import ds.desktop.notify.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author leona
 */

public class Eliminar {
    JFrame frm;
    JButton btnBuscar, btnEliminar, btnTodo;
    JTable tablajugadores;
    JScrollPane jsptabla;
    JTextField txtBuscar; 
    JLabel lbl1, lbl2;
    JPanel pan1, pan2, pan3, pan4;
    Conexion mycon;
    Connection con;
    ResultSet tabla;
    PreparedStatement stmt;
    String sql;
    int sw = 0;
    
    public Eliminar(){
        mycon = new Conexion();
        frm = new JFrame("Eliminar Jugador");
        btnBuscar = new JButton("Buscar");
        btnEliminar = new JButton("Eliminar Seleccionado");
        btnTodo = new JButton("Ver Todos");
        tablajugadores = new JTable();
        jsptabla = new JScrollPane();
        txtBuscar = new JTextField(20);
        lbl1 = new JLabel("ELIMINAR JUGADOR");
        lbl2 = new JLabel("Nombre del Jugador: ");
        pan1 = new JPanel();
        pan2 = new JPanel();
        pan3 = new JPanel();
        pan4 = new JPanel();
    }
    
    public void usar() {
        con = mycon.conecta();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Fecha Nac.");
        modelo.addColumn("Telefono");
        modelo.addColumn("Correo");
        modelo.addColumn("Posicion");
        tablajugadores.setModel(modelo);

        frm.setLayout(new FlowLayout()); 
        
        pan1.setLayout(new FlowLayout());
        pan1.add(lbl1);
        
        pan2.setLayout(new FlowLayout());
        pan2.add(lbl2);
        pan2.add(txtBuscar);
        pan2.add(btnBuscar);
        pan2.add(btnTodo);

        jsptabla.setViewportView(tablajugadores);
        pan3.add(jsptabla);
        
        pan4.setLayout(new FlowLayout());
        pan4.add(btnEliminar);

        frm.add(pan1);
        frm.add(pan2);
        frm.add(pan3);
        frm.add(pan4);
        
        frm.setSize(550, 650);
        frm.setLocation(400, 100);
        frm.setVisible(true);
        frm.setResizable(false);

        mostrarJugadores();

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarJugador();
            }
        });

        btnTodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtBuscar.setText("");
                mostrarJugadores();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar();
            }
        });
    }

    public void mostrarJugadores() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tablajugadores.getModel();
            modelo.setRowCount(0);

            sql = "SELECT * FROM jugadores";
            stmt = con.prepareStatement(sql);
            tabla = stmt.executeQuery();

            while (tabla.next()) {
                modelo.addRow(new Object[]{
                    tabla.getString("id_jugador"),
                    tabla.getString("nombre"),
                    tabla.getString("apellido"),
                    tabla.getDate("fecha_nacimiento"),
                    tabla.getString("telefono"),
                    tabla.getString("correo"),
                    tabla.getString("posicion")
                });
            }

        } catch (Exception e) {
            DesktopNotify.showDesktopMessage("ERROR", "Error al cargar datos", 3, 3000);
        }
    }

    public void buscarJugador() {
        try {
            if(txtBuscar.getText().equals("")){
                DesktopNotify.showDesktopMessage("ADVERTENCIA", "Escribe un nombre para buscar", 2, 3000);
                mostrarJugadores();
            } else {
                String nombre = txtBuscar.getText();
                DefaultTableModel modelo = (DefaultTableModel) tablajugadores.getModel();
                modelo.setRowCount(0);

                sql = "SELECT * FROM jugadores WHERE nombre = '" + nombre + "'";
                stmt = con.prepareStatement(sql);
                tabla = stmt.executeQuery();

                if (tabla.next()) {
                    do {
                        modelo.addRow(new Object[]{
                            tabla.getString("id_jugador"),
                            tabla.getString("nombre"),
                            tabla.getString("apellido"),
                            tabla.getDate("fecha_nacimiento"),
                            tabla.getString("telefono"),
                            tabla.getString("correo"),
                            tabla.getString("posicion")
                        });
                    } while(tabla.next());
                } else {
                    DesktopNotify.showDesktopMessage("INFO", "No se encontró el jugador", 3, 3000);
                }
            }
        } catch (Exception e) {
            DesktopNotify.showDesktopMessage("ERROR", "Error en la búsqueda", 3, 3000);
        }
    }
    
    public void eliminar() {
        try {
            int fila = tablajugadores.getSelectedRow();
            if (fila == -1) {
                DesktopNotify.showDesktopMessage("ERROR", "Selecciona un jugador de la tabla", 3, 3000);
            } else {
                String id = tablajugadores.getValueAt(fila, 0).toString();
                
                sql = "DELETE FROM jugadores WHERE id_jugador = " + id;
                System.out.println(sql);
                
                stmt = con.prepareStatement(sql);
                sw = stmt.executeUpdate();
                
                if (sw != 0) {
                    DesktopNotify.showDesktopMessage("ELIMINADO", "Jugador eliminado correctamente", 3, 3000);
                    mostrarJugadores();
                }
            }
        } catch (Exception e) {
            DesktopNotify.showDesktopMessage("ERROR", "Error al eliminar", 3, 3000);
        }
    }
}