package batl;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Admin on 16.08.17.
 */
public class Conection {
    public final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    public Conection(Socket sock) throws IOException{

        this.socket = sock;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void send(String s){

        synchronized (out){
            try{
                out.writeObject(s);
            }
            catch (IOException io){
                io.printStackTrace();
            }
        }

    }

    public String res(){

        synchronized (in){
            String a = "";
            try{
                a = (String)in.readObject();
            }
            catch (Exception e){

            }
            return a;
        }

    }


}
