
package Ventanas;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author leona
 */
public class Ayuda {
    JFrame frm;
    JPanel pan1, pan2;
    JLabel lblTitulo;
    JTextArea txtArea;
    JButton btnCerrar;
    JScrollPane scroll;
    
    public Ayuda(){
        frm = new JFrame("Ayuda del Sistema");
        pan1 = new JPanel();
        pan2 = new JPanel();
        lblTitulo = new JLabel("AYUDA - GESTIÓN DEPORTIVA");
        txtArea = new JTextArea(15, 40);
        btnCerrar = new JButton("Cerrar");
        scroll = new JScrollPane(txtArea);
    }
    
    public void usar(){
        frm.setLayout(new BorderLayout());
        
        pan1.setLayout(new FlowLayout());
        pan1.add(lblTitulo);
        
        txtArea.setEditable(false);
        txtArea.setText("MÓDULOS DEL SISTEMA:\n\n" +
                        "1. JUGADORES:\n" +
                        "   - Nuevo: Registra un jugador.\n" +
                        "   - Consultar: Busca jugadores por nombre.\n" +
                        "   - Modificar: Edita datos existentes.\n" +
                        "   - Eliminar: Borra un jugador.\n\n" +
                        "2. ESTADÍSTICAS:\n" +
                        "   - Deportivas: Goles, asistencias, faltas.\n" +
                        "   - Físicas: Peso, altura, IMC.\n\n" +
                        "Para soporte técnico contacte al administrador.");
        
        pan2.add(btnCerrar);
        
        frm.add(pan1, BorderLayout.NORTH);
        frm.add(scroll, BorderLayout.CENTER);
        frm.add(pan2, BorderLayout.SOUTH);
        
        frm.setSize(500, 400);
        frm.setLocation(450, 200);
        frm.setVisible(true);
        frm.setResizable(false);
        
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frm.dispose();
            }
        });
    }
    
    public static void main(String[] args) {
        Ayuda ay = new Ayuda();
        ay.usar();
    }
}