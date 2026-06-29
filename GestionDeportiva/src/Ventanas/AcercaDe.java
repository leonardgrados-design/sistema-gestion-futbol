
package Ventanas;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author leona
 */

public class AcercaDe {
    JFrame frm;
    JPanel pan1, pan2, pan3;
    JLabel lblTitulo, lblVersion, lblAutor, lblMateria, lblAnio;
    JButton btnAceptar;
    
    public AcercaDe(){
        frm = new JFrame("Acerca de");
        pan1 = new JPanel();
        pan2 = new JPanel();
        pan3 = new JPanel();
        
        lblTitulo = new JLabel("SISTEMA DE GESTIÓN DEPORTIVA");
        lblVersion = new JLabel("Versión: 1.0");
        lblAutor = new JLabel("Desarrollado por: Adan Leonardo Granados Martinez");
        lblMateria = new JLabel("Materia: Base de Datos");
        lblAnio = new JLabel("Año: 2025");
        btnAceptar = new JButton("Aceptar");
    }
    
    public void usar(){
        frm.setLayout(new GridLayout(3, 1));
        
        pan1.setLayout(new FlowLayout());
        pan1.add(lblTitulo);
        
        pan2.setLayout(new GridLayout(4, 1));
        pan2.add(lblVersion);
        pan2.add(lblAutor);
        pan2.add(lblMateria);
        pan2.add(lblAnio);
        
        // Centrar etiquetas del panel 2
        lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
        lblAutor.setHorizontalAlignment(SwingConstants.CENTER);
        lblMateria.setHorizontalAlignment(SwingConstants.CENTER);
        lblAnio.setHorizontalAlignment(SwingConstants.CENTER);
        
        pan3.add(btnAceptar);
        
        frm.add(pan1);
        frm.add(pan2);
        frm.add(pan3);
        
        frm.setSize(400, 300);
        frm.setLocation(500, 250);
        frm.setVisible(true);
        frm.setResizable(false);
        
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frm.dispose();
            }
        });
    }
    
    
}