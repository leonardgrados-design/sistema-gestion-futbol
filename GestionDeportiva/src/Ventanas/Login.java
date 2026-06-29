
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
public class Login {
    
    JFrame jfr1;
    JPanel jpnl1, jpnl2, jpnl3;
    JLabel jlbl1, jlbl2, jimglbl;
    JTextField jtxt1;
    JPasswordField jpswrd;
    JButton jbtn;
    ImageIcon imgcon;
    Conexion mycon;
    Connection con;
    ResultSet tabla;
    PreparedStatement stmt;
    String sql = "", cadena = "";
    int sw = 0;
    
    public Login(){
        mycon = new Conexion();
        jfr1 = new JFrame("Iniciar Sesion");
        jpnl1 = new JPanel();
        jpnl2 = new JPanel();
        jpnl3 = new JPanel();
        jlbl1 = new JLabel("USUARIO");
        jlbl2 = new JLabel("CONTRASEÑA");
        //imgcon = new ImageIcon("C:\\Users\\leona\\Documents\\NetBeansProjects\\GestionDeportiva\\src\\Imagenes\\GestionDeportiva.png");
        //jimglbl = new JLabel(imgcon);
        jtxt1 = new JTextField(25);
        jpswrd = new JPasswordField(25);
        jbtn = new JButton("INICIAR SESION");
        
    }
     
   public void usar(){
       
       //agregamos cada elemto del login al frame, se divide en tres filas y una columna
       //cada panel es una fila; el .pack es para acomodar cada elemento y darle un tamano ajustable
       jfr1.setLayout(new GridLayout(3,1));
       jpnl1.add(jlbl1);
       jpnl1.add(jtxt1);
       jpnl2.add(jlbl2);
       jpnl2.add(jpswrd);
       jpnl3.add(jbtn);
       jfr1.add(jpnl1);
       jfr1.add(jpnl2);
       jfr1.add(jpnl3);
       jpnl1.setBackground(Color.BLACK);
       jpnl2.setBackground(Color.BLACK);
       jpnl3.setBackground(Color.BLACK);
       jlbl1.setForeground(Color.WHITE);
       jlbl2.setForeground(Color.WHITE);
       jfr1.pack();
       jfr1.setResizable(false);
       jfr1.setVisible(true);
       jfr1.setLocation(450, 300);
       jfr1.getRootPane().setDefaultButton(jbtn);
       
       jbtn.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               valida();
           }
       });
       
       jtxt1.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if(jtxt1.getText().equals("")){
                   DesktopNotify.showDesktopMessage("ERROR", "Ingresa el usuario",3,3000);
               }else{
                   jtxt1.requestFocusInWindow();
               }
           }
       });
       jpswrd.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if(jpswrd.getText().equals("")){
                   DesktopNotify.showDesktopMessage("ERROR", "Ingresa la contraseña",3,3000);
               }else{
                   jpswrd.requestFocusInWindow();
               }
           }
       });
       
       
   }
   
   public void valida(){
       try {
           sw = 0 ;
           con = mycon.conecta();
           sql = "SELECT * FROM usuarios where usuario =" + "\"" + jtxt1.getText() + "\"";
           sql += " and password =\"" +jpswrd.getText() + "\"";
           System.out.println(sql);
           stmt = con.prepareStatement(sql);
           tabla = stmt.executeQuery();
           
           if(tabla.next()){
               DesktopNotify.showDesktopMessage("BIENVENIDO", "Credenciales correctas", 3, 3000);
               MenuPrincipal menuprincipal = new MenuPrincipal();
               menuprincipal.usar();
               jfr1.dispose();
           }else{
               DesktopNotify.showDesktopMessage("ERROR", "Usuario o Contraseña Incorrecta", 3, 3000);
           }
           
           
           
           
       } catch (Exception e) {
            DesktopNotify.showDesktopMessage("ERROR","Error en la base de datos", 3, 3000);
       }

       
       
               
   }
           
    
    public static void main(String[] args) {
        Login mylogin = new Login();
        mylogin.usar();
    }
    
}

