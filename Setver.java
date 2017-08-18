package batl;

import com.javarush.task.task30.task3008.ConsoleHelper;

import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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

    private int[][] ya = new int[10][10];
    private int[][] ti = new int[10][10];

    private int a, b;
    private volatile boolean ss = false;


    class MyPanel1 extends JPanel {


        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.green);
            for (int i = 1; i <= 11; i++) {
                g.drawLine(i*20,20,i*20,220);
                g.drawLine(20,i*20,220,i*20);
            }
        }
    }

    class MyPanel2 extends JPanel{
        @Override
        protected void paintComponent(Graphics g) {

            g.setColor(Color.orange);
            for (int i = 1; i <= 11; i++) {
                g.drawLine(i*20,20,i*20,220);
                g.drawLine(20,i*20,220,i*20);
            }

            g.setColor(Color.black);
            for (int i = 0; i < ti.length; i++){
                for (int j = 0; j < ti[i].length; j++){
                    if(ti[i][j] == 1){
                        g.fillRect(i*20+21,j*20+21,19,19);
                    }

                }
            }

            g.setColor(Color.red);
            if(a>=40&&a<240&&b>=40&&b<240){
                if(ss){
                    g.fillOval(a/20*20-15,b/20*20-15,10,10);
                }
            }



        }
    }

    class AddPlayer extends Thread {

        private Conection conection;
        private Socket socket;

        private AddPlayer(Socket sock) {
            this.socket = sock;
            setDaemon(true);
        }
        @Override
        public void run() {
            System.out.println(socket);
            try{
                conection = new Conection(socket);
                osn();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        public void osn() {

            final JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(480, 280);
            f.setLocationRelativeTo(null);
            f.setResizable(false);
            f.setVisible(true);

            f.setLayout(new GridLayout());
            MyPanel1 myPanel1 = new MyPanel1();
            MyPanel2 myPanel2 = new MyPanel2();

            myPanel2.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {

                }
                @Override
                public void mouseMoved(MouseEvent e) {
                    a = e.getX()+20;
                    b = e.getY()+20;
                    ss=true;
                    f.repaint();

                }
            });
            myPanel2.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    conection.send(String.format("%d %d",(a/20*20-20)/20-1, (b/20*20-20)/20-1));
                    ti[(a/20*20-20)/20-1][(b/20*20-20)/20-1] = 1;
                    f.repaint();


                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            f.add(myPanel1);
            f.add(myPanel2);

        }

    }

    public static void main(String[] args) throws Exception{
        new Setver().start();
    }

    public void start(){
        while (true){
            try(ServerSocket ss = new ServerSocket(666)){
                new AddPlayer(ss.accept()).start();
            }
            catch (IOException io){

            }
        }


    }

}
