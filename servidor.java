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
         String capitalizedSentence; 
        try {
            ServerSocket welcomeSocket = new ServerSocket(6789);
            while (true) {
                Socket connectionSocket = welcomeSocket.accept();

                BufferedReader inFromClient = new BufferedReader (new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                clientSentence = inFromClient.readLine();  
                capitalizedSentence = clientSentence.toUpperCase() + '\n';  

                texRecibido.setText(clientSentence);
                texEnvio.setText(capitalizedSentence.trim());

                outToClient.writeBytes(capitalizedSentence);
                connectionSocket.close();
            }
        } catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error" + e.getMessage());
        }
      });
      servidor.start(); 
    }
}

      

