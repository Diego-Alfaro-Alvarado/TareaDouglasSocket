import java.io.*;  
import java.net.*;

import javax.swing.*;
import java.awt.*;
public class servidor {


 
  public static void main(String argv[]) throws Exception  
    {  
        // Toda esta parte es meramente visual, es la parte de interfaz gráfica 
      JFrame principal = new JFrame ("----- Bienvenido al servidor -----");
      JPanel panel = new JPanel (new GridLayout(2,2)); 

      JLabel recibido = new JLabel ("Mensaje del cliente");
      JTextField texRecibido = new JTextField();
      texRecibido.setEditable(false);

      JLabel envio = new JLabel ("Mensaje convertido a Mayúscula");
      JTextField texEnvio = new JTextField();
      texEnvio.setEditable(false);

      panel.add(recibido);
      panel.add(texRecibido);
      panel.add(envio);
      panel.add(texEnvio);

      principal.add(panel);
      principal.setSize(400,100);
      principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      principal.setVisible(true);

      // Un hilo para que la interfaz gráfica no se congele mientras corre 
      Thread servidor = new Thread(()->{
         String clientSentence;  
         String nuevocamb; // nueva variable para aplicar el nuevo cambio 
        try {
            ServerSocket welcomeSocket = new ServerSocket(6789);
            while (true) {
                Socket connectionSocket = welcomeSocket.accept();

                BufferedReader inFromClient = new BufferedReader (new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                clientSentence = inFromClient.readLine();  
                nuevocamb = convertirMensaje(clientSentence);  

                texRecibido.setText(clientSentence);
                texEnvio.setText(nuevocamb);

                outToClient.writeBytes(nuevocamb + '\n');
                connectionSocket.close();
            }
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error" + e.getMessage());
        }
      });
      servidor.start(); 
    } 
    // Se hacen 2 metodos para que el código quede más organizado 
    //Estos métodos permiten que se convierta a mayúscula y ver cuantas letras tiene la palabra
    public static String convertirMensaje(String mensaje) { 
      if (mensaje == null) return "";

  String converMayusculas = mensaje.toUpperCase();
  int letras = contar(mensaje);
  int palabras = contPalabras(mensaje);
  String inverso = invertir(mensaje);

  return converMayusculas + " (" + letras + " letras, " + palabras + " palabras) -----> Inverso: " + inverso;
      } 
      // Este metodo cuenta las letras del mensaje 
      public static int contar(String text) {
        int conta =0;
        for (char cont : text.toCharArray()){
          if (Character.isLetter(cont)) {
            conta ++;
          }
        }
        return conta;
      }
      //Cuenta las palabras que tiene el mensaje
      public static int contPalabras (String text) {
        if (text ==null || text.trim().isEmpty()) return 0;
        return text.trim().split("\\s+").length;
      } 
      // Invierte el texto 
      public static String invertir(String text) {
        return new StringBuilder(text).reverse().toString();
      }
}

      

