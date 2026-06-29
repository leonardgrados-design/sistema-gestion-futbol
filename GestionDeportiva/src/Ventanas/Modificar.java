
package Ventanas;
import conexion.Conexion;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import ds.desktop.notify.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
/**
 *
 * @author leona
 */
public class Modificar {
    JFrame frm;
    JButton btnBuscar, btnModificar, btnTodo;
    JTable tablajugadores;
    JScrollPane jsptabla;
    JTextField txtBuscar;
    
    JTextField txtNombre, txtApellido, txtTelefono, txtCorreo;
    JDateChooser jcal;
    JComboBox<String> cmbPosicion;
    JLabel lblNombre, lblApellido, lblFecha, lblTelefono, lblCorreo, lblPosicion;
    
    JLabel lblTitulo, lblBuscar;
    JPanel panBusqueda, panTabla, panFormulario, panBotones;
    Conexion mycon;
    Connection con;
    ResultSet tabla;
    PreparedStatement stmt;
    String sql;
    int sw = 0;
    String idSeleccionado = "";
    
    public Modificar(){
        mycon = new Conexion();
        frm = new JFrame("Modificar Jugador");
        
        btnBuscar = new JButton("Buscar");
        btnModificar = new JButton("Modificar");
        btnTodo = new JButton("Ver Todos");
        
        tablajugadores = new JTable();
        jsptabla = new JScrollPane();
        txtBuscar = new JTextField(15);
        
        txtNombre = new JTextField(15);
        txtApellido = new JTextField(15);
        txtTelefono = new JTextField(15);
        txtCorreo = new JTextField(15);
        jcal = new JDateChooser();
        cmbPosicion = new JComboBox<>();
        
        lblTitulo = new JLabel("MODIFICAR JUGADOR");
        lblBuscar = new JLabel("Buscar Nombre:");
        
        lblNombre = new JLabel("Nombre:");
        lblApellido = new JLabel("Apellido:");
        lblFecha = new JLabel("Fecha Nac:");
        lblTelefono = new JLabel("Telefono:");
        lblCorreo = new JLabel("Correo:");
        lblPosicion = new JLabel("Posicion:");
        
        panBusqueda = new JPanel();
        panTabla = new JPanel();
        panFormulario = new JPanel();
        panBotones = new JPanel();
    }
    
    public void usar() {
        con = mycon.conecta();
        
        cmbPosicion.addItem("Portero");
        cmbPosicion.addItem("Defensa");
        cmbPosicion.addItem("Medio");
        cmbPosicion.addItem("Delantero");

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Fecha Nac.");
        modelo.addColumn("Telefono");
        modelo.addColumn("Correo");
        modelo.addColumn("Posicion");
        tablajugadores.setModel(modelo);

        frm.setLayout(new BorderLayout());
        
        panBusqueda.setLayout(new FlowLayout());
        panBusqueda.add(lblTitulo);
        panBusqueda.add(lblBuscar);
        panBusqueda.add(txtBuscar);
        panBusqueda.add(btnBuscar);
        panBusqueda.add(btnTodo);
        
        panTabla.setLayout(new BoxLayout(panTabla, BoxLayout.Y_AXIS));
        jsptabla.setPreferredSize(new Dimension(550, 200));
        jsptabla.setViewportView(tablajugadores);
        panTabla.add(jsptabla);
        
        panFormulario.setLayout(new GridLayout(6, 2, 5, 5));
        panFormulario.add(lblNombre); panFormulario.add(txtNombre);
        panFormulario.add(lblApellido); panFormulario.add(txtApellido);
        panFormulario.add(lblFecha); panFormulario.add(jcal);
        panFormulario.add(lblTelefono); panFormulario.add(txtTelefono);
        panFormulario.add(lblCorreo); panFormulario.add(txtCorreo);
        panFormulario.add(lblPosicion); panFormulario.add(cmbPosicion);
        
        panBotones.add(btnModificar);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(panTabla);
        centerPanel.add(panFormulario);
        
        frm.add(panBusqueda, BorderLayout.NORTH);
        frm.add(centerPanel, BorderLayout.CENTER);
        frm.add(panBotones, BorderLayout.SOUTH);
        
        frm.setSize(600, 600);
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
                limpiarCampos();
            }
        });
        
        tablajugadores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                llenarCampos();
            }
        });
        
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificar();
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
                DesktopNotify.showDesktopMessage("ADVERTENCIA", "Escribe un nombre", 2, 3000);
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
    
    public void llenarCampos() {
        int fila = tablajugadores.getSelectedRow();
        if (fila != -1) {
            idSeleccionado = tablajugadores.getValueAt(fila, 0).toString();
            txtNombre.setText(tablajugadores.getValueAt(fila, 1).toString());
            txtApellido.setText(tablajugadores.getValueAt(fila, 2).toString());
            
            try {
                String fechaStr = tablajugadores.getValueAt(fila, 3).toString();
                java.util.Date fecha = java.sql.Date.valueOf(fechaStr);
                jcal.setDate(fecha);
            } catch (Exception e) {
                jcal.setDate(null);
            }
            
            txtTelefono.setText(tablajugadores.getValueAt(fila, 4).toString());
            txtCorreo.setText(tablajugadores.getValueAt(fila, 5).toString());
            
            String pos = tablajugadores.getValueAt(fila, 6).toString();
            cmbPosicion.setSelectedItem(pos);
        }
    }
    
    public void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        jcal.setDate(null);
        cmbPosicion.setSelectedIndex(0);
        idSeleccionado = "";
    }
    
    public void modificar() {
        try {
            if (idSeleccionado.equals("")) {
                DesktopNotify.showDesktopMessage("ERROR", "Selecciona un jugador primero", 3, 3000);
            } else {
                java.sql.Date fechaSQL = new java.sql.Date(jcal.getDate().getTime());
                
                sql = "UPDATE jugadores SET nombre=?, apellido=?, fecha_nacimiento=?, telefono=?, correo=?, posicion=? WHERE id_jugador=?";
                stmt = con.prepareStatement(sql);
                
                stmt.setString(1, txtNombre.getText());
                stmt.setString(2, txtApellido.getText());
                stmt.setDate(3, fechaSQL);
                stmt.setString(4, txtTelefono.getText());
                stmt.setString(5, txtCorreo.getText());
                stmt.setString(6, cmbPosicion.getSelectedItem().toString());
                stmt.setString(7, idSeleccionado);
                
                sw = stmt.executeUpdate();
                
                if (sw != 0) {
                    DesktopNotify.showDesktopMessage("EXITO", "Jugador modificado", 3, 3000);
                    mostrarJugadores();
                    limpiarCampos();
                }
            }
        } catch (Exception e) {
            DesktopNotify.showDesktopMessage("ERROR", "Error al modificar: " + e.getMessage(), 3, 3000);
        }
    }
}