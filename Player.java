package batl;

import java.io.*;
import java.net.Socket;

/**
 * Created by Admin on 16.08.17.
 */
public class Player {

    protected Conection conection;
    BufferedReader asd= new BufferedReader(new InputStreamReader(System.in));

     class Hand extends Thread{
        Socket socket;


        private Hand(Socket sock) throws IOException{
            this.socket = sock;

        }

        @Override
        public void run() {
            try{
                conection = new Conection(socket);
                System.out.println(conection.res());


            }
            catch (IOException s){
                s.printStackTrace();
                System.out.println("ошибка");
            }



        }
    }

    public void run(){
        try(Socket socket = new Socket("localhost",666)){
            new Hand(socket).start();
            while (true){
                String a = asd.readLine();
                conection.send(a);

            }

        }
        catch (IOException io){
            //io.printStackTrace();
            System.out.println("Ошибка при создании сокета!");
        }
    }

    public static void main(String[] args) {
        new Player().run();

    }
}
