package batl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.net.Socket;

/**
 * Created by Admin on 16.08.17.
 */
public class Player {

    private int[][] ya = new int[10][10];
    private int[][] ti = new int[10][10];
    private JFrame frame;

    private int a, b;
    private volatile boolean ss = false;

    BufferedReader asd= new BufferedReader(new InputStreamReader(System.in));

    class MyPanel1 extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.green);
            for (int i = 1; i <= 11; i++) {
                g.drawLine(i*20,20,i*20,220);
                g.drawLine(20,i*20,220,i*20);
            }

            g.setColor(Color.black);
            for (int i = 0; i < ya.length; i++){
                for (int j = 0; j < ya[i].length; j++){
                    if(ya[i][j] == 1){
                        g.fillRect(i*20+21,j*20+21,19,19);
                    }

                }
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

     class Hand extends Thread{

         private Conection conection;
        Socket socket;

        private Hand(Socket sock){
            this.socket = sock;
        }
        public Hand(){

        }
        @Override
        public void run() {

            try{
                conection = new Conection(socket);

                while (!conection.socket.isClosed()){
                    String s = conection.res();
                    ya[Integer.parseInt(s.split(" ")[0])][Integer.parseInt(s.split(" ")[1])] = 1;
                    frame.repaint();

                }


            }
            catch (IOException s){
                s.printStackTrace();
                System.out.println("ошибка");
            }
        }

    }

    public void run(){
        osn();
        try(Socket socket = new Socket("localhost",666)){
            new Hand(socket).start();
            while (true){
                String a = asd.readLine();

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
    public void osn() {

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(480, 280);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        frame.setLayout(new GridLayout());

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
                frame.repaint();

            }
        });
        myPanel2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(String.format("%d %d",(a/20*20-20)/20-1, (b/20*20-20)/20-1));
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
        frame.add(myPanel1);
        frame.add(myPanel2);

    }
}
