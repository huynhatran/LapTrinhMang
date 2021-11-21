package laptrinhmang_doan_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JTextPane;

public class CustomSocket {
    private Socket socket;
    private JTextPane txtPane;
    private PrintWriter out;
    private BufferedReader reader;

    public CustomSocket(Socket socket, JTextPane txtPane) throws IOException {
        this.socket = socket;
        this.txtPane = txtPane;
        out = new PrintWriter(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        receive();
    }
    private void receive(){
        Thread th = new Thread(){
            public void run(){
                while(true){
                    try{
                        String line = reader.readLine();
                        if(line!=null){
                            txtPane.setText(txtPane.getText() + "<br>" + line);
                        }
                    }catch(Exception e){
                        
                    }
                }
            }
        };
        th.start();
    }
    public void send(String string){
        String current = txtPane.getText();
        txtPane.setText(current + " sent "+string);
        out.println(string);
        out.flush();
    }
    public void close(){
        try{
            out.close();
            reader.close();
            socket.close();
        }catch(IOException e){
            
        }
    }
}
