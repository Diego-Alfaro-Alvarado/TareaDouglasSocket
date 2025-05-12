import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class TCPClient {

    public static void main(String argv[]) {
        JFrame ventana = new JFrame("Requerimientos del Cliente");
        ventana.setSize(400, 250);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        JLabel labelEntrada = new JLabel("Ingrese texto que desea modificar:");
        TextField inFromUser = new TextField();
        JButton enviar = new JButton("Enviar al servidor");
        JLabel respuestaServidor = new JLabel("Esperando respuesta...");

        panel.add(labelEntrada);
        panel.add(inFromUser);
        panel.add(enviar);
        panel.add(respuestaServidor);

        ventana.add(panel, BorderLayout.CENTER);
        ventana.setVisible(true);

        enviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String sentence = inFromUser.getText();
                    Socket clientSocket = new Socket("localhost", 6789);

                    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    outToServer.writeBytes(sentence + '\n');

                    String modifiedSentence = inFromServer.readLine();
                    respuestaServidor.setText("Desde el servidor: " + modifiedSentence);

                    clientSocket.close();
                } catch (Exception ex) {
                    respuestaServidor.setText("Error: " + ex.getMessage());
                }
            }
        });
    }
}
