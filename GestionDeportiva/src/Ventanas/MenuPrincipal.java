
package Ventanas;
import conexion.Conexion;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import ds.desktop.notify.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.net.URL;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author leona
 */
public class MenuPrincipal {
    JFrame jfrm;
    JMenu menu1, menu2, menu3, menu4, menu5;
    JMenuItem menu11, menu12, menu13, menu14;
    JMenuItem menu21, menu22; 
    JMenuItem menu31, menu32;
    JMenuItem menu41, menu42;
    JMenuItem menu51, menu52;
    JMenuBar mb1;
    JSeparator js1;
    JLabel lbl1;
    JPanel pan1;
    
    
    public MenuPrincipal(){
        jfrm = new JFrame("Menu Principal");
        menu1 = new JMenu("Jugadores");
        menu2 = new JMenu("Ingresa Estadisticas");
        menu3 = new JMenu("Reportes");
        menu4 = new JMenu("Ayuda");
        menu5 = new JMenu("Salir");
        menu11 = new JMenuItem("Nuevo Jugador");
        menu12 = new JMenuItem("Consultar Jugador");
        menu13 = new JMenuItem("Modificar Jugador");
        menu14 = new JMenuItem("Eliminar Jugador");
        menu21 = new JMenuItem("Deportivas");
        menu22 = new JMenuItem("Fisicas");
        menu31 = new JMenuItem("Reporte Estadistica Deportiva");
        menu32 = new JMenuItem("Reporte Estadistica Fisica");
        menu41 = new JMenuItem("Acerca de");
        menu42 = new JMenuItem("Ayuda(f1)");
        menu51 = new JMenuItem("Cerrar Sesion");
        menu52 = new JMenuItem("Salir");
        mb1 = new JMenuBar();
        js1 = new JSeparator();
        pan1 = new JPanel();
        
        URL urlImagen = getClass().getResource("/Imagenes/FondoGD.png");
        ImageIcon Image;
        
        if (urlImagen != null) {
            Image = new ImageIcon(urlImagen);
        } else {
            // Fallback por si no encuentra la imagen, para que no de error fatal
            Image = new ImageIcon(); 
            System.out.println("Advertencia: No se encontró la imagen /Imagenes/FondoGD.png");
        }
        
        lbl1 = new JLabel(Image);
    }
    
    public void usar(){
        jfrm.setLayout(new FlowLayout());
        jfrm.add(mb1);
        menu1.add(menu11);menu1.add(menu12);menu1.add(menu13);menu1.add(menu14);
        menu2.add(menu21);menu2.add(menu22);
        menu3.add(menu31);menu3.add(menu32);
        menu4.add(menu41);menu4.add(menu42);
        menu5.add(menu51);menu5.add(js1);menu5.add(menu52);
        mb1.add(menu1);mb1.add(menu2);mb1.add(menu3);mb1.add(menu4);mb1.add(menu5);
        jfrm.setJMenuBar(mb1);
        jfrm.add(pan1);
        pan1.add(lbl1);
        jfrm.setVisible(true);
        jfrm.setSize(490,490);
        jfrm.setResizable(false);
        jfrm.setLocation(400, 150);
        
        menu11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NuevoJugador nv = new NuevoJugador();
                nv.usar();

            }
        });
        menu12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Consultar cn = new Consultar();
                cn.usar();
            }
        });
        menu13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Eliminar eliminar = new Eliminar();
                eliminar.usar();
            }
        });
        menu14.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Modificar modificar = new Modificar();
                modificar.usar();
            }
        });
        menu21.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EstadisticasDeportivas ED = new EstadisticasDeportivas();
                ED.usar();
            }
        });
        menu22.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EstadicticasFicicas EF = new EstadicticasFicicas();
                EF.usar();
            }
        });
        menu31.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             try {
                    Conexion cc = new Conexion();
                    Connection cn = cc.conecta();
                    InputStream archivo = getClass().getResourceAsStream("/Reportes/ReporteEDJ.jasper");
                    
                    if (archivo == null) {
                        JOptionPane.showMessageDialog(null, "No se encuentra el archivo /reportes/ReporteEDJ.jasper");
                    } else {
                        JasperPrint reporte = JasperFillManager.fillReport(archivo, null, cn);
                        JasperViewer ventana = new JasperViewer(reporte, false);
                        ventana.setTitle("Reporte Estadística Deportiva");
                        ventana.setVisible(true);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al abrir reporte: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        menu32.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Conexion cc = new Conexion();
                    Connection cn = cc.conecta();
                    InputStream archivo = getClass().getResourceAsStream("/Reportes/ReporteEFJ.jasper");
                    
                    if (archivo == null) {
                        JOptionPane.showMessageDialog(null, "No se encuentra el archivo /reportes/ReporteEFJ.jasper");
                    } else {
                        JasperPrint reporte = JasperFillManager.fillReport(archivo, null, cn);
                        JasperViewer ventana = new JasperViewer(reporte, false);
                        ventana.setTitle("Reporte Estadística Física");
                        ventana.setVisible(true);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al abrir reporte: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        menu41.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AcercaDe AD = new AcercaDe();
                AD.usar();
            }
        });
        menu42.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ayuda A = new Ayuda();
                A.usar();
            }
        });
        menu51.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login log = new Login();
                log.usar();
                jfrm.dispose();
            }
        });
        menu52.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
            
    
    public static void main(String[] args) {
        MenuPrincipal mp = new MenuPrincipal();
        mp.usar();
    }

}
