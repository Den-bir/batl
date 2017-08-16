package batl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;


/**
 * Created by Admin on 16.08.17.
 */
public class Setver {

    Map<String, List<Player>> map = new ConcurrentHashMap<>();


    static class AddPlayer extends Thread {
        private Socket socket;


        private AddPlayer(Socket sock) throws IOException{
            this.socket = sock;

        }

        @Override
        public void run() {
            System.out.println(socket);

            try{

                Conection conection = new Conection(socket);
                conection.send("выберите команду\n\t 1 - создать игру.\n\t 2 - играть с игроком.");
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        try(ServerSocket aa = new ServerSocket(666)){
            while (true){
                new AddPlayer(aa.accept()).start();


            }

        }
        catch (IOException io){
            io.printStackTrace();
        }
    }
}
